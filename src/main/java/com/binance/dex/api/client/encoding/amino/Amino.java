package com.binance.dex.api.client.encoding.amino;

import com.binance.dex.api.client.encoding.ByteUtil;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class Amino {

    private static final int disambiguationByteSize = 3;

    private static final int prefixByteSize = 4;

    /**
     * > Amino Disamb and Prefix
     * > hash := sha256("com.tendermint.consensus/MyConcreteName")
     * > hex.EncodeBytes(hash) // 0x{00 00 A8 FC 54 00 00 00 BB 9C 83 DD ...} (example)
     * > rest = dropLeadingZeroBytes(hash) // 0x{A8 FC 54 00 00 00 BB 9C 83 DD ...}
     * > disamb = rest[0:3]
     * > rest = dropLeadingZeroBytes(rest[3:])
     * > prefix = rest[0:4]
     * > <0xA8 0xFC 0x54> [0xBB 0x9C 9x83 9xDD] // <Disamb Bytes> and [Prefix Bytes]
     **/

    /**
     * Return disambiguation bytes from given name, the first three non-zero bytes
     *
     * @param name the name
     */
    public byte[] nameToDisamb(String name) throws NoSuchAlgorithmException {
        return nameToDisambPrefix(name).getDisamb();
    }

    /**
     * Return prefix bytes from given name, the first four non-zero bytes
     *
     * @param name the name
     */
    public byte[] nameToPrefix(String name) throws NoSuchAlgorithmException {
        return nameToDisambPrefix(name).getPrefix();
    }

    public String nameToPrefixString(String name) throws NoSuchAlgorithmException {
        return Hex.toHexString(nameToDisambPrefix(name).getPrefix());
    }

    /**
     * Return disambiguation and prefix bytes pair from given name
     *
     * @param name the name
     * @see DisambPrefix
     */
    public DisambPrefix nameToDisambPrefix(String name) throws NoSuchAlgorithmException {
        DisambPrefix disambPrefix = new DisambPrefix();
        byte[] nameHash = new byte[0];
        nameHash = dropLeadingZero(AminoHashing.sha256(name));
        disambPrefix.setDisamb(Arrays.copyOfRange(nameHash, 0, disambiguationByteSize));

        byte[] rest = Arrays.copyOfRange(nameHash, disambiguationByteSize, nameHash.length);
        disambPrefix.setPrefix(Arrays.copyOfRange(dropLeadingZero(rest), 0, prefixByteSize));

        byte[] dp = new byte[disambiguationByteSize + prefixByteSize];
        System.arraycopy(disambPrefix.getDisamb(), 0, dp, 0, disambiguationByteSize);
        System.arraycopy(disambPrefix.getPrefix(), 0, dp, disambiguationByteSize, prefixByteSize);

        disambPrefix.setDisambPrefix(dp);

        return disambPrefix;
    }

    private byte[] dropLeadingZero(byte[] byteArray) {
        int offset = 0;
        for (byte oneByte : byteArray) {
            if (oneByte != 0) {
                break;
            }
            offset++;
        }
        return Arrays.copyOfRange(byteArray, offset, byteArray.length);

    }

    /**
     * > Amino encode
     * */
    public byte[] encode(AminoSerializable message, byte[] typePrefix, boolean lengthPrefix) throws IOException {
        boolean writeTypePrefix = false;
        boolean writeLengthPrefix = false;
        int length = computeSerializedSize(message);

        if (typePrefix != null && typePrefix.length > 0){
            length += typePrefix.length;
            writeTypePrefix = true;
        }

        int bufSize = length;

        if (lengthPrefix){
            bufSize += computeLengthByteSize(length);
            writeLengthPrefix = true;
        }
        byte[] buf = new byte[bufSize];
        CodedOutputStream outputStream = CodedOutputStream.newInstance(buf);

        if (writeLengthPrefix){
            encodeLengthByte(length, outputStream);
        }

        if (writeTypePrefix) {
            outputStream.writeRawBytes(typePrefix);
        }

        encodeAminoMessageBare(message, outputStream);

        outputStream.flush();

        return buf;
    }

    private void encodeAminoMessageBare(AminoSerializable message, CodedOutputStream outputStream) throws IOException {
        int index = 0;
        for (AminoField<?> field : message.IterateFields()) {
            index ++;

            if (field.isSkipWhenEncode() || field.getT() == null){
                continue;
            }

            if (field.getT() instanceof AminoCustomSerialized){
                encodeAminoSerialized(index, (AminoCustomSerialized) field.getT(), outputStream);
                continue;
            }

            if (field.getT() instanceof AminoSerializable) {
                outputStream.writeTag(index, WireType.LENGTH_DELIMITED);
                int length = computeSerializedSize(((AminoSerializable) field.getT()));
                encodeLengthByte(length, outputStream);
                encodeAminoMessageBare(((AminoSerializable) field.getT()), outputStream);
                continue;
            }

            if (field.getT() instanceof String){
                encodeString(index, (String) field.getT(), outputStream);
                continue;
            }

            if (field.getT() instanceof byte[]){
                encodeByteArray(index, ((byte[]) field.getT()), outputStream);
                continue;
            }

            if (field.getT() instanceof Long){
                encodeInt64(index, ((Long) field.getT()), outputStream);
                continue;
            }

            if (field.getT() instanceof Integer){
                encodeInt32(index, (Integer) field.getT(), outputStream);
                continue;
            }
        }
    }

    public void decodeBare(byte[] data, AminoSerializable message) throws IOException, AminoDecodeException {
        CodedInputStream input = CodedInputStream.newInstance(data);
        try {
            decode(input, message);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new AminoDecodeException(e);
        }
    }

    public void decodeWithLengthPrefix(byte[] data, AminoSerializable message) throws IOException, AminoDecodeException {
        CodedInputStream input = CodedInputStream.newInstance(data);
        if (input.isAtEnd()){
            return;
        }

        int length = input.readRawVarint32();
        if (length == 0){
            return;
        }

        try {
            decode(input, message);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new AminoDecodeException(e);
        }
    }

    public static byte[] getByteArrayAfterTypePrefix(byte[] data) throws IllegalStateException {
        byte[] typePrefix = ByteUtil.read(data, prefixByteSize);
        //todo match type prefix from registered map
        int length = Byte.toUnsignedInt(data[4]);
        if (data.length - prefixByteSize - 1 != length){
            throw new IllegalStateException("Invalid Amino buffer");
        }
        return ByteUtil.pick(data, prefixByteSize + 1, length);
    }

    private void decode(CodedInputStream input, AminoSerializable message) throws IOException, IllegalAccessException, InstantiationException {
        int index = 0;
        int tag = 0;
        boolean pendTag = false;
        for (AminoField<?> iterateField : message.IterateFields()) {
            index ++;

            if (!pendTag) {
                tag = input.readTag();
            }

            int currentFieldTag = computeTag(index, iterateField.getClazz());

            if (currentFieldTag != tag){
                pendTag = true;
                continue;
            }else{
                pendTag = false;
            }

            if (AminoCustomSerialized.class.isAssignableFrom(iterateField.getClazz())){
                Object obj;
                if (iterateField.getT() != null){
                    ((AminoCustomSerialized) iterateField.getT()).decode(input);
                    obj = iterateField.getT();
                }else{
                    Object serializedObj = iterateField.getClazz().newInstance();
                    ((AminoCustomSerialized) serializedObj).decode(input);
                    obj = serializedObj;
                }
                message.setValueByFieldIndex(index, obj);
                continue;
            }

            if (AminoSerializable.class.isAssignableFrom(iterateField.getClazz())){
                int length = input.readRawVarint32();
                if (length == 0){
                    continue;
                }
                Object msg;
                if (iterateField.getT() != null){
                    msg = iterateField.getT();
                }else{
                    msg = iterateField.getClazz().newInstance();
                }

                final int oldLimit = input.pushLimit(length);
                decode(input, ((AminoSerializable) msg));
                //update lastTag in input stream
                int emptyTag = input.readTag();
                //check is at the end
                if (emptyTag != 0){
                    throw new AminoDecodeException("Embedded message tag did not match expected tag");
                }
                //double check
                input.checkLastTagWas(0);
                input.popLimit(oldLimit);

                message.setValueByFieldIndex(index, msg);
                continue;
            }

            if (iterateField.getClazz().isAssignableFrom(Boolean.class)){
                message.setValueByFieldIndex(index, input.readBool());
                continue;
            }

            if (iterateField.getClazz().isAssignableFrom(Integer.class)){
                message.setValueByFieldIndex(index, input.readInt32());
                continue;
            }

            if (iterateField.getClazz().isAssignableFrom(Long.class)){
                message.setValueByFieldIndex(index, input.readInt64());
                continue;
            }

            if (iterateField.getClazz().isAssignableFrom(String.class)){
                message.setValueByFieldIndex(index, input.readStringRequireUtf8());
                continue;
            }

            if (iterateField.getClazz().isAssignableFrom(byte[].class)){
                byte[] value = input.readByteArray();
                message.setValueByFieldIndex(index, value);
                continue;
            }
        }
    }

    private void encodeAminoSerialized(int fieldIndex, AminoCustomSerialized aminoCustomSerialized, CodedOutputStream outputStream) throws IOException {
        if (!aminoCustomSerialized.isDefaultOrEmpty()) {
            outputStream.writeTag(fieldIndex, aminoCustomSerialized.getWireType());
            aminoCustomSerialized.encode(outputStream);
        }
    }

    private void encodeString(int fieldIndex, String value, CodedOutputStream outputStream) throws IOException {
        outputStream.writeTag(fieldIndex, WireType.LENGTH_DELIMITED);
        outputStream.writeStringNoTag(value);
    }

    private void encodeByteArray(int fieldIndex, byte[] array, CodedOutputStream outputStream) throws IOException {
        outputStream.writeTag(fieldIndex, WireType.LENGTH_DELIMITED);
        outputStream.writeByteArrayNoTag(array);
    }

    private void encodeInt64(int fieldIndex, long value, CodedOutputStream outputStream) throws IOException {
        outputStream.writeTag(fieldIndex, WireType.VARINT);
        outputStream.writeInt64NoTag(value);
    }

    private void encodeInt32(int fieldIndex, int value, CodedOutputStream outputStream) throws IOException {
        outputStream.writeTag(fieldIndex, WireType.VARINT);
        outputStream.writeInt32NoTag(value);
    }

    private void encodeUInt32Bare(int value, CodedOutputStream codedOutputStream) throws IOException {
        codedOutputStream.writeUInt32NoTag(value);
    }

    private void encodeLengthByte(int length, CodedOutputStream codedOutputStream) throws IOException {
        codedOutputStream.writeUInt32NoTag(length);
    }

    public int computeSerializedSize(AminoSerializable message){
        int size = 0;
        int index = 0;
        for (AminoField<?> iterateField : message.IterateFields()) {
            index ++;

            if (iterateField.isSkipWhenEncode() || iterateField.getT() == null){
                continue;
            }

            if (iterateField.getT() instanceof AminoCustomSerialized){
                size += computeTagSize(index, ((AminoCustomSerialized) iterateField.getT()).getWireType());
                size += ((AminoCustomSerialized) iterateField.getT()).getSerializedSize();
                continue;
            }

            if (iterateField.getT() instanceof AminoSerializable){
                size += computeTagSize(index, WireType.LENGTH_DELIMITED);
                int embeddedSize = computeSerializedSize(((AminoSerializable) iterateField.getT()));
                size += computeLengthByteSize(embeddedSize);
                size += embeddedSize;
                continue;
            }

            if (iterateField.getT() instanceof String){
                size += computeTagSize(index, WireType.LENGTH_DELIMITED);
                size += computeStringSize(((String)iterateField.getT()));
                continue;
            }

            if (iterateField.getT() instanceof byte[]) {
                size += computeTagSize(index, WireType.LENGTH_DELIMITED);
                size += computeByteArraySize(((byte[]) iterateField.getT()));
                continue;
            }

            if (iterateField.getT() instanceof Long){
                size += computeTagSize(index, WireType.VARINT);
                size += computeInt64Size(((Long) iterateField.getT()));
                continue;
            }

            if (iterateField.getT() instanceof Integer){
                size += computeTagSize(index, WireType.VARINT);
                size += computeInt32Size((Integer) iterateField.getT());
                continue;
            }
        }

        return size;
    }

    private int computeTagSize(final int fieldNumber, final int wireType){
        return CodedOutputStream.computeUInt32SizeNoTag(makeTag(fieldNumber, wireType));
    }

    private int computeByteArraySize(final byte[] bytes){
        return CodedOutputStream.computeByteArraySizeNoTag(bytes);
    }

    private int computeStringSize(final String str){
        return CodedOutputStream.computeStringSizeNoTag(str);
    }

    private int computeInt32Size(final int value){
        return CodedOutputStream.computeInt32SizeNoTag(value);
    }

    private int computeUInt32Size(final int value){
        return CodedOutputStream.computeUInt32SizeNoTag(value);
    }

    private int computeLengthByteSize(final int length){
        return CodedOutputStream.computeUInt32SizeNoTag(length);
    }

    private int computeInt64Size(final long value){
        return CodedOutputStream.computeInt64SizeNoTag(value);
    }

    private int computeUInt64Size(final long value){
        return CodedOutputStream.computeUInt64SizeNoTag(value);
    }

    private int computeTag(int fieldIndex, Class<?> clazz){
        if (AminoCustomSerialized.class.isAssignableFrom(clazz) ||
                AminoSerializable.class.isAssignableFrom(clazz) ||
                clazz.isAssignableFrom(byte[].class) ||
                clazz.isAssignableFrom(String.class)){
            return makeTag(fieldIndex, WireType.LENGTH_DELIMITED);
        }

        if (clazz.isAssignableFrom(Long.class) ||
            clazz.isAssignableFrom(Integer.class) ||
            clazz.isAssignableFrom(Boolean.class)) {
            return makeTag(fieldIndex, WireType.VARINT);
        }

        throw new IllegalArgumentException("Unsupported class " + clazz.getCanonicalName());
    }

    private int makeTag(final int fieldNumber, final int wireType){
        return (fieldNumber << 3) | wireType;
    }

    private byte[] appendBytesArray(byte[] first, byte[] second){
        byte[] newArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, newArray, 0, first.length);
        System.arraycopy(second, 0, newArray, first.length, second.length);
        return newArray;
    }

}
