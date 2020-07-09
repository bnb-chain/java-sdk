package com.binance.dex.api.client.rlp;

import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.EncodeUtils;
import org.ethereum.util.DecodeResult;
import org.ethereum.util.RLP;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Decoder extends RLP {

    public static <T> List<T> decodeList(byte[] raw, Class<T> clazz) throws Exception {
        DecodeResult decodeResult = decode(raw, 0);
        if (!(decodeResult.getDecoded() instanceof Object[])) {
            throw new RuntimeException("not a list");
        }
        Object[] objects = (Object[]) decodeResult.getDecoded();
        return decodeList(objects, clazz);
    }

    private static <T> List<T> decodeList(Object[] objects, Class<T> clazz) throws Exception {
        List<T> list = new ArrayList<>();
        for (Object ob : objects) {
            if (ob instanceof byte[]) {
                list.add((T) decodeBytes((byte[]) ob, clazz, null));
            } else if (ob instanceof Object[]) {
                Object[] fieldValues = (Object[]) ob;
                list.add(decodeObject(fieldValues, clazz));
            }
        }
        return list;
    }

    public static <T> T decodeObject(byte[] raw, Class<T> clazz) throws Exception {
        DecodeResult decodeResult = decode(raw, 0);
        return decodeObject((Object[]) decodeResult.getDecoded(), clazz);
    }


    private static Object decodeBytes(byte[] value, Class<?> type, Object superInstance) throws Exception {
        if (type == Integer.class) {

            return decodeInt(value, 0);

        } else if (type == Long.class) {

            return decodeLong(value, 0);

        } else if (type == BigInteger.class) {

            return decodeBigInteger(value, 0);

        } else if (type == String.class) {

            return new String(value);

        } else if (type == byte[].class) {

            return value;

        } else if (isImplement(type, RlpDecodable.class)) {

            Method method = type.getMethod("decode", byte[].class, Object.class);
            Object instance = type.newInstance();
            method.invoke(instance, value, superInstance);
            return instance;

        } else {
            throw new RuntimeException(String.format("unsupported type: %s ", type.getCanonicalName()));
        }
    }

    private static <T> T decodeObject(Object[] fieldValues, Class<T> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        T instance = clazz.newInstance();
        if (fieldValues.length != fields.length) {
            throw new RuntimeException("failed to decode, inconsistent fields numbers");
        }
        for (int i = 0; i < fieldValues.length; i++) {
            Object fieldValue = fieldValues[i];
            Field field = fields[i];

            field.setAccessible(true);
            if (fieldValue.equals("")) {
                Class<?> fieldType = field.getType();
                if (fieldType == Integer.class) {
                    field.set(instance, 0);
                } else if (fieldType == Long.class) {
                    field.set(instance, 0L);
                } else if (fieldType == UnsignedNumber.class || fieldType.getSuperclass() == UnsignedNumber.class){
                    field.set(instance, fieldType.newInstance());
                } else {
                    throw new RuntimeException(String.format("Failed to decode %s, its type should be Integer or Long or UnsignedNumber", field.getName()));
                }
            } else if (fieldValue instanceof byte[]) {
                Class<?> fieldType = field.getType();
                field.set(instance, decodeBytes((byte[]) fieldValue, fieldType, instance));
            } else if (fieldValue instanceof Object[]) {

                Class<?> fieldType = field.getType();
                if (fieldType == List.class) {
                    Type genericType = field.getGenericType();
                    if (!(genericType instanceof ParameterizedType)) {
                        throw new RuntimeException(String.format("Failed to decode %s, can not decode List without generic type", field.getName()));
                    }
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    field.set(instance, decodeList((Object[]) fieldValue, genericClazz));
                } else {
                    throw new RuntimeException(String.format("Failed to decode %s, only List is supported as an array type", field.getName()));
                }
            } else {
                throw new RuntimeException("ERROR: unrecognised type for RLP encoded bytes");
            }
        }

        return instance;
    }

    private static boolean isImplement(Class<?> clazz, Class<?> interfaceClass) {
        for (Class<?> item : clazz.getInterfaces()) {
            if (item == interfaceClass) {
                return true;
            }
        }
        for (Class<?> item : clazz.getSuperclass().getInterfaces()) {
            if (item == interfaceClass) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
        byte[] bz = encodeInt(-100);
        System.out.printf("result: %s\n", EncodeUtils.bytesToHex(bz));

        byte[] bz1 = EncodeUtils.hexStringToByteArray("100000");

        int r = decodeInt(bz1, 0);
        System.out.println(r);
    }

}
