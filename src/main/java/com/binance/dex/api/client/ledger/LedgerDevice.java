package com.binance.dex.api.client.ledger;

import com.binance.dex.api.client.ledger.common.BTChipException;
import com.binance.dex.api.client.ledger.hid.BTChipTransportHID;
import com.google.common.primitives.Bytes;
import org.apache.commons.codec.binary.Hex;
import java.util.Arrays;

public class LedgerDevice {
    private static final byte userCLA = (byte)0xBC;
    private static final byte userINSGetVersion = 0;
    private static final byte userINSPublicKeySECP256K1 = 1;
    private static final byte userINSSignSECP256K1 = 2;
    private static final byte userINSPublicKeySECP256K1ShowBech32 = 3;
    private static final byte userINSHash = 100;
    private static final int userMessageChunkSize = 250;

    private static final String testnetPrefix = "tbnb";
    private static final String mainnetPrefix = "bnb";

    public LedgerVersion version;
    public BTChipTransportHID device;

    public LedgerDevice(LedgerVersion version, BTChipTransportHID device) {
        this.version = version;
        this.device = device;
    }

    static public boolean hasLedgerConnected() throws BTChipException {
        return BTChipTransportHID.discoverLedgerDevice() > 0;
    }

    static public LedgerDevice findLedgerDevice() throws BTChipException {
        BTChipTransportHID device = BTChipTransportHID.openDevice(null);
        byte[] command = new byte[]{userCLA, userINSGetVersion, 0, 0, 0};
        byte[] result = device.exchange(command);

        if (result.length < 4) {
            throw new BTChipException("Failed to get ledger app version");
        }
        LedgerVersion version = new LedgerVersion(result[0], result[1], result[2], result[3]);
        return new LedgerDevice(version, device);
    }

    public void close() {
        this.device.close();
    }

    public LedgerVersion getVersion() {
        return version;
    }

    public byte[] signSECP256K1(int[] bip32Path, byte[] transaction) throws BTChipException {
        byte packetIndex = 1;
        int count = (int) Math.ceil((double) transaction.length / (double)userMessageChunkSize);
        byte packetCount = (byte)(count);
        packetCount++;

        byte[] finalResponse = null;

        byte[] message;

        while (packetIndex <= packetCount)  {
            int chunk = userMessageChunkSize;
            if (packetIndex == 1) {
                byte[] pathBytes = LedgerUtils.getBip32bytes(bip32Path, 3);
                if (pathBytes == null) {
                    return null;
                }
                byte[] header = new byte[]{userCLA, userINSSignSECP256K1, packetIndex, packetCount, (byte)pathBytes.length};
                message = Bytes.concat(header, pathBytes);
            } else {
                if (transaction.length < userMessageChunkSize) {
                    chunk = transaction.length;
                }
                byte[] header = new byte[]{userCLA, userINSSignSECP256K1, packetIndex, packetCount, (byte)chunk};
                message = Bytes.concat(header, Arrays.copyOfRange(transaction, 0, chunk));
            }

            finalResponse = this.device.exchange(message);

            if (packetIndex > 1) {
                transaction = Arrays.copyOfRange(transaction,chunk,transaction.length);
            }
            packetIndex++;
        }
        return finalResponse;
    }

    public byte[] getPublicKeySECP256K1(int[] bip32Path) throws BTChipException {
        byte[] pathBytes = LedgerUtils.getBip32bytes(bip32Path, 3);

        byte[] command = new byte[]{userCLA, userINSPublicKeySECP256K1, 0, 0, (byte)pathBytes.length};
        command = Bytes.concat(command, pathBytes);
        byte[] pubkey = device.exchange(command);
        return LedgerUtils.compressedLedgerPubkey(pubkey);
    }

    public int showAddressSECP256K1(int[] bip32Path, boolean isMainnet) throws BTChipException {
        byte[] pathBytes = LedgerUtils.getBip32bytes(bip32Path, 3);

        byte[] command = new byte[]{userCLA, userINSPublicKeySECP256K1ShowBech32, 0, 0, 0, 0};
        if (isMainnet) {
            command[5] = (byte)mainnetPrefix.length();
            command = Bytes.concat(command, mainnetPrefix.getBytes());
        } else {
            command[5] = (byte)testnetPrefix.length();
            command = Bytes.concat(command, testnetPrefix.getBytes());
        }
        command = Bytes.concat(command, pathBytes);
        command[4] = (byte)(command.length-5);
        byte[] result = device.exchange(command);
        return result.length;
    }

    public static void main(String[] args) throws Exception {
        if (!hasLedgerConnected()) {
            System.out.println("No ledger device is connected to the machine");
        }
        LedgerDevice ledgerDevice = findLedgerDevice();
        LedgerVersion version = ledgerDevice.getVersion();
        System.out.println(version.toString());

        int[] bip44Path = new int[]{44,714,0,0,0};
        ledgerDevice.showAddressSECP256K1(bip44Path, true);
        byte[] pubkey = ledgerDevice.getPublicKeySECP256K1(bip44Path);
        System.out.println(Hex.encodeHexString(pubkey));
        String msgString= "7b226163636f756e745f6e756d626572223a2230222c22636861696e5f6964223a2262696e616e63652d636861696" +
                "e222c2264617461223a6e756c6c2c226d656d6f223a2274657374206c6564676572207369676e222c226d736773223a5b7b2269" +
                "6e70757473223a5b7b2261646472657373223a22626e62317979633971353375746d6a3066717833706671376c386338336c727" +
                "0303674707065306e6370222c22636f696e73223a5b7b22616d6f756e74223a3130303030303030303030303030302c2264656e" +
                "6f6d223a22424e42227d5d7d5d2c226f757470757473223a5b7b2261646472657373223a22626e6231706b70706632617233776" +
                "a333863753779386b68796730636c6d6876663266326e7a74347736222c22636f696e73223a5b7b22616d6f756e74223a313030" +
                "3030303030303030303030302c2264656e6f6d223a22424e42227d5d7d5d7d5d2c2273657175656e6365223a2230222c22736f75726365223a2230227d";
        byte[] signature = ledgerDevice.signSECP256K1(bip44Path, LedgerUtils.hexToBin(msgString));
        System.out.println(Hex.encodeHexString(signature));

        ledgerDevice.close();
    }
}
