package com.binance.dex.api.client.ledger;

import com.binance.dex.api.client.ledger.hid.BTChipTransportHID;
import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.util.Arrays;


public class LedgerDevice {
    private static final byte userCLA = (byte) 0xBC;
    private static final byte userINSGetVersion = 0;
    private static final byte userINSPublicKeySECP256K1 = 1;
    private static final byte userINSSignSECP256K1 = 2;
    private static final byte userINSPublicKeySECP256K1ShowBech32 = 3;
    private static final byte userINSHash = 100;
    private static final int userMessageChunkSize = 250;

    public LedgerVersion version;
    public BTChipTransportHID device;

    public LedgerDevice(LedgerVersion version, BTChipTransportHID device) {
        this.version = version;
        this.device = device;
    }

    static public boolean hasLedgerConnected() throws IOException {
        if (BTChipTransportHID.discoverLedgerDevice() == 1) {
            return true;
        }
        throw new IOException("More one one ledger devices are found. You must connect only one ledger device");
    }

    static public LedgerDevice findLedgerDevice() throws IOException {
        BTChipTransportHID device = BTChipTransportHID.openDevice(null);
        byte[] command = new byte[]{userCLA, userINSGetVersion, 0, 0, 0};
        byte[] result = device.exchange(command);

        if (result.length < 4) {
            throw new IOException("Failed to get ledger app version");
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

    public byte[] signSECP256K1(int[] bip32Path, byte[] transaction) throws IOException {
        byte packetIndex = 1;
        int count = (int) Math.ceil((double) transaction.length / (double) userMessageChunkSize);
        byte packetCount = (byte) (count);
        packetCount++;

        byte[] finalResponse = null;

        byte[] message;

        while (packetIndex <= packetCount) {
            int chunk = userMessageChunkSize;
            if (packetIndex == 1) {
                byte[] pathBytes = LedgerUtils.bipPathToBytes(bip32Path, 3);
                if (pathBytes == null) {
                    return null;
                }
                byte[] header = new byte[]{userCLA, userINSSignSECP256K1, packetIndex, packetCount, (byte) pathBytes.length};
                message = Bytes.concat(header, pathBytes);
            } else {
                if (transaction.length < userMessageChunkSize) {
                    chunk = transaction.length;
                }
                byte[] header = new byte[]{userCLA, userINSSignSECP256K1, packetIndex, packetCount, (byte) chunk};
                message = Bytes.concat(header, Arrays.copyOfRange(transaction, 0, chunk));
            }

            finalResponse = this.device.exchange(message);

            if (packetIndex > 1) {
                transaction = Arrays.copyOfRange(transaction, chunk, transaction.length);
            }
            packetIndex++;
        }
        return finalResponse;
    }

    public byte[] getPublicKeySECP256K1(int[] bip32Path) throws IOException {
        byte[] pathBytes = LedgerUtils.bipPathToBytes(bip32Path, 3);

        byte[] command = new byte[]{userCLA, userINSPublicKeySECP256K1, 0, 0, (byte) pathBytes.length};
        command = Bytes.concat(command, pathBytes);
        byte[] pubkey = device.exchange(command);
        return LedgerUtils.compressedLedgerPubkey(pubkey);
    }

    public int showAddressSECP256K1(int[] bip32Path, String hrp) throws IOException {
        byte[] pathBytes = LedgerUtils.bipPathToBytes(bip32Path, 3);

        byte[] command = new byte[]{userCLA, userINSPublicKeySECP256K1ShowBech32, 0, 0, 0, 0};
        command[5] = (byte) hrp.length();
        command = Bytes.concat(command, hrp.getBytes());

        command = Bytes.concat(command, pathBytes);
        command[4] = (byte) (command.length - 5);
        byte[] result = device.exchange(command);
        return result.length;
    }
}
