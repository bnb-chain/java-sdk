package com.binance.dex.api.client.ledger.usb;

import org.usb4java.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class HidUsb {

    private static Context context;

    static {
        context = new Context();
        LibUsb.init(context);
    }

    public static Device[] enumDevices(int vid, int pid) throws IOException {
        Vector<Device> devices = new Vector<Device>();
        DeviceList list = new DeviceList();
        int result = LibUsb.getDeviceList(null, list);
        if (result < 0) {
            throw new IOException("Unable to get ledger device list");
        }
        for (Device device : list) {
            DeviceDescriptor descriptor = new DeviceDescriptor();
            result = LibUsb.getDeviceDescriptor(device, descriptor);
            if (result < 0) {
                continue;
            }
            if ((descriptor.idVendor() == vid) && (descriptor.idProduct() == pid)) {
                devices.add(device);
            }
        }
        return devices.toArray(new Device[0]);
    }

    public static String getDeviceId(Device device) {
        StringBuffer result = new StringBuffer();
        ByteBuffer deviceListBuffer = ByteBuffer.allocateDirect(7);
        int size = LibUsb.getPortNumbers(device, deviceListBuffer);
        for (int i = 0; i < size; i++) {
            result.append(deviceListBuffer.get());
            if (i != (size - 1)) {
                result.append('/');
            }
        }
        return result.toString();
    }

    public static void exit() {
        LibUsb.exit(context);
    }
}
