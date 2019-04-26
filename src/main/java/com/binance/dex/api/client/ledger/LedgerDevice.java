package com.binance.dex.api.client.ledger;

import com.binance.dex.api.client.ledger.common.BTChipException;
import com.binance.dex.api.client.ledger.hid.BTChipTransportHID;
import com.binance.dex.api.client.ledger.utils.Utils;
import com.google.common.primitives.Bytes;
import org.apache.commons.codec.binary.Hex;

public class LedgerDevice implements Ledger {
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

    public void close() throws BTChipException {
        this.device.close();
    }

    public LedgerVersion getVersion() {
        return version;
    }

    public byte[] signSECP256K1(int[] bip32Path, byte[] transaction) {
        return null;
    }

    public byte[] getPublicKeySECP256K1(int[] bip32Path) throws BTChipException {
        byte[] pathBytes = Utils.GetBip32bytes(bip32Path, 3);

        byte[] command = new byte[]{userCLA, userINSPublicKeySECP256K1, 0, 0, (byte)pathBytes.length};
        command = Bytes.concat(command, pathBytes);
        byte[] pubkey = device.exchange(command);
        return Utils.compressedLedgerPubkey(pubkey);
    }

    public int ShowAddressSECP256K1(int[] bip32Path, boolean isMainnet) throws BTChipException {
        byte[] pathBytes = Utils.GetBip32bytes(bip32Path, 3);

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
        ledgerDevice.ShowAddressSECP256K1(bip44Path, true);
        byte[] pubkey = ledgerDevice.getPublicKeySECP256K1(bip44Path);
        System.out.println(Hex.encodeHexString(pubkey));
    }
}
