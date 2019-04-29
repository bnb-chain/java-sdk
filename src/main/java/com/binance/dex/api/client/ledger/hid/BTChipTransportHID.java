package com.binance.dex.api.client.ledger.hid;

import com.binance.dex.api.client.ledger.common.BTChipTransport;
import com.binance.dex.api.client.ledger.common.LedgerHelper;
import com.binance.dex.api.client.ledger.usb.HidUsb;
import org.usb4java.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class BTChipTransportHID implements BTChipTransport {

    private static final int VID = 0x2C97;
    private static final int PID = 0x0001;
    private static final int HID_BUFFER_SIZE = 64;
    private static final int SW1_DATA_AVAILABLE = 0x61;
    private static final int LEDGER_DEFAULT_CHANNEL = 0x0101;
    private static final int TIMEOUT = 20000;

    private DeviceHandle handle;
    private int timeout;
    private ByteBuffer responseBuffer;
    private IntBuffer sizeBuffer;
    private byte outEndpoint;
    private byte inEndpoint;

    BTChipTransportHID(DeviceHandle handle, int timeout, byte inEndpoint, byte outEndpoint, boolean ledger) {
        this.handle = handle;
        this.timeout = timeout;
        this.inEndpoint = inEndpoint;
        this.outEndpoint = outEndpoint;
        responseBuffer = ByteBuffer.allocateDirect(HID_BUFFER_SIZE);
        sizeBuffer = BufferUtils.allocateIntBuffer();
    }

    public static int discoverLedgerDevice() throws IOException {
        Device[] devices = HidUsb.enumDevices(VID, PID);
        return devices.length;
    }

    public static Device matchDevice(String deviceName, int vid, int pid) throws IOException {
        Device[] devices = HidUsb.enumDevices(vid, pid);
        Device targetDevice = null;
        for (Device device : devices) {
            if ((deviceName == null) || (deviceName.length() == 0) || (HidUsb.getDeviceId(device).equals(deviceName))) {
                targetDevice = device;
                break;
            }
        }
        return targetDevice;
    }

    public static BTChipTransportHID openDevice(String deviceName) throws IOException {
        byte inEndpoint = (byte) 0xff;
        byte outEndpoint = (byte) 0xff;
        boolean ledger = true;
        Device targetDevice = matchDevice(deviceName, VID, PID);
        if (targetDevice == null) {
            throw new IOException("Device not found, please check your ledger device connection");
        }
        ConfigDescriptor configDescriptor = new ConfigDescriptor();
        int result = LibUsb.getActiveConfigDescriptor(targetDevice, configDescriptor);
        if (result != LibUsb.SUCCESS) {
            throw new IOException("Failed to get config descriptor");
        }
        Interface[] interfaces = configDescriptor.iface();
        for (Interface deviceInterface : interfaces) {
            for (InterfaceDescriptor interfaceDescriptor : deviceInterface.altsetting()) {
                if (interfaceDescriptor.bInterfaceProtocol() != (byte) 0x00) {
                    continue;
                }
                for (EndpointDescriptor endpointDescriptor : interfaceDescriptor.endpoint()) {
                    if ((endpointDescriptor.bEndpointAddress() & LibUsb.ENDPOINT_IN) != 0) {
                        inEndpoint = endpointDescriptor.bEndpointAddress();
                    } else {
                        outEndpoint = endpointDescriptor.bEndpointAddress();
                    }
                }
            }
        }
        if (inEndpoint == (byte) 0xff) {
            throw new IOException("Couldn't find IN endpoint");
        }
        if (outEndpoint == (byte) 0xff) {
            throw new IOException("Couldn't find OUT endpoint");
        }

        DeviceHandle handle = new DeviceHandle();
        result = LibUsb.open(targetDevice, handle);
        if (result != LibUsb.SUCCESS) {
            throw new IOException("Failed to open ledger device");
        }
        LibUsb.detachKernelDriver(handle, 0);
        LibUsb.claimInterface(handle, 0);
        return new BTChipTransportHID(handle, TIMEOUT, inEndpoint, outEndpoint, ledger);
    }

    public byte[] exchange(byte[] command) throws IOException {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        byte[] responseData;
        int offset = 0;
        int result;

        command = LedgerHelper.wrapCommandAPDU(LEDGER_DEFAULT_CHANNEL, command, HID_BUFFER_SIZE);

        ByteBuffer commandBuffer = ByteBuffer.allocateDirect(HID_BUFFER_SIZE);
        byte[] chunk = new byte[HID_BUFFER_SIZE];
        while (offset != command.length) {
            int blockSize = (command.length - offset > HID_BUFFER_SIZE ? HID_BUFFER_SIZE : command.length - offset);
            System.arraycopy(command, offset, chunk, 0, blockSize);
            sizeBuffer.clear();
            commandBuffer.put(chunk);
            result = LibUsb.interruptTransfer(handle, outEndpoint, commandBuffer, sizeBuffer, timeout);
            if (result != LibUsb.SUCCESS) {
                throw new IOException("Write to ledger failed");
            }
            offset += blockSize;
            commandBuffer.clear();
            sizeBuffer.clear();
        }

        while ((responseData = LedgerHelper.unwrapResponseAPDU(LEDGER_DEFAULT_CHANNEL, response.toByteArray(), HID_BUFFER_SIZE)) == null) {
            responseBuffer.clear();
            sizeBuffer.clear();
            result = LibUsb.interruptTransfer(handle, inEndpoint, responseBuffer, sizeBuffer, timeout);
            if (result != LibUsb.SUCCESS) {
                throw new IOException("Read from ledger failed");
            }
            responseBuffer.get(chunk, 0, HID_BUFFER_SIZE);
            response.write(chunk, 0, HID_BUFFER_SIZE);
        }
        if (responseData[responseData.length - 2] != (byte) 0x90 || responseData[responseData.length - 1] != (byte) 0x00) {
            throw new IOException("Get invalid response from ledger");
        }
        responseData = Arrays.copyOfRange(responseData, 0, responseData.length - 2);

        return responseData;
    }

    public void close() {
        LibUsb.releaseInterface(handle, 0);
        LibUsb.attachKernelDriver(handle, 0);
        LibUsb.close(handle);
        HidUsb.exit();
    }
}
