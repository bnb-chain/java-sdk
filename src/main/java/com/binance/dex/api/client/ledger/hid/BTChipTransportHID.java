package com.binance.dex.api.client.ledger.hid;

import com.binance.dex.api.client.ledger.common.BTChipException;
import com.binance.dex.api.client.ledger.common.BTChipTransport;
import com.binance.dex.api.client.ledger.common.LedgerHelper;
import com.binance.dex.api.client.ledger.usb.HidUsb;
import com.binance.dex.api.client.ledger.utils.Utils;
import org.usb4java.*;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Vector;

public class BTChipTransportHID implements BTChipTransport {

	private static final int VID = 0x2C97;
	private static final int PID = 0x0001;
	private static final int PID_LEDGER = 0xffa0;
	private static final int PID_LEDGER_PROTON = 0x4b7c;
	private static final int HID_BUFFER_SIZE = 64;
	private static final int SW1_DATA_AVAILABLE = 0x61;
	private static final int LEDGER_DEFAULT_CHANNEL = 0x0101;
	private static final int TIMEOUT = 20000;

	private DeviceHandle handle;
	private int timeout;
	private ByteBuffer responseBuffer;
	private IntBuffer sizeBuffer;
	private boolean debug;
	private byte outEndpoint;
	private byte inEndpoint;
	private boolean ledger;
	
	BTChipTransportHID(DeviceHandle handle, int timeout, byte inEndpoint, byte outEndpoint, boolean ledger) {
		this.handle = handle;
		this.timeout = timeout;
		this.inEndpoint = inEndpoint;
		this.outEndpoint = outEndpoint;
		this.ledger = ledger;
		if (!this.ledger) {
			this.ledger = (inEndpoint & 0x7f) != (outEndpoint & 0x7f); 
		}
		responseBuffer = ByteBuffer.allocateDirect(HID_BUFFER_SIZE);
		sizeBuffer = BufferUtils.allocateIntBuffer();
	}

	@Override
	public byte[] exchange(byte[] command) throws BTChipException {
		ByteArrayOutputStream response = new ByteArrayOutputStream();
		byte[] responseData = null;
		int offset = 0;
		int responseSize;
		int result;
		if (ledger) {
			command = LedgerHelper.wrapCommandAPDU(LEDGER_DEFAULT_CHANNEL, command, HID_BUFFER_SIZE);
		}
		ByteBuffer commandBuffer = ByteBuffer.allocateDirect(HID_BUFFER_SIZE);
		byte[] chunk = new byte[HID_BUFFER_SIZE];
		while(offset != command.length) {
			int blockSize = (command.length - offset > HID_BUFFER_SIZE ? HID_BUFFER_SIZE : command.length - offset);
			System.arraycopy(command, offset, chunk, 0, blockSize);
			sizeBuffer.clear();
			commandBuffer.put(chunk);
			result = LibUsb.interruptTransfer(handle, outEndpoint, commandBuffer, sizeBuffer, timeout);
			if (result != LibUsb.SUCCESS) {
				throw new BTChipException("Write failed");
			}
			offset += blockSize;
			commandBuffer.clear();
			sizeBuffer.clear();
		}
		if (!ledger) {
			responseBuffer.clear();
			result = LibUsb.interruptTransfer(handle, inEndpoint, responseBuffer, sizeBuffer, timeout);
			if (result != LibUsb.SUCCESS) {
				throw new BTChipException("Read failed");
			}
			int sw1 = (int)(responseBuffer.get() & 0xff);
			int sw2 = (int)(responseBuffer.get() & 0xff);
			if (sw1 != SW1_DATA_AVAILABLE) {
				response.write(sw1);
				response.write(sw2);
			}
			else {
				responseSize = sw2 + 2;
				offset = 0;
				int blockSize = (responseSize > HID_BUFFER_SIZE - 2 ? HID_BUFFER_SIZE - 2 : responseSize);
				responseBuffer.get(chunk, 0, blockSize);
				response.write(chunk, 0, blockSize);
				offset += blockSize;
				responseBuffer.clear();
				sizeBuffer.clear();
				while (offset != responseSize) {
					result = LibUsb.interruptTransfer(handle, inEndpoint, responseBuffer, sizeBuffer, timeout);
					if (result != LibUsb.SUCCESS) {
						throw new BTChipException("Read failed");
					}
					blockSize = (responseSize - offset > HID_BUFFER_SIZE ? HID_BUFFER_SIZE : responseSize - offset);
					responseBuffer.get(chunk, 0, blockSize);
					response.write(chunk, 0, blockSize);
					offset += blockSize;				
				}
				responseBuffer.clear();
				sizeBuffer.clear();
			}
			responseData = response.toByteArray();
		}
		else {			
			while ((responseData = LedgerHelper.unwrapResponseAPDU(LEDGER_DEFAULT_CHANNEL, response.toByteArray(), HID_BUFFER_SIZE)) == null) {
				responseBuffer.clear();
				sizeBuffer.clear();
				result = LibUsb.interruptTransfer(handle, inEndpoint, responseBuffer, sizeBuffer, timeout);
				if (result != LibUsb.SUCCESS) {
					throw new BTChipException("Read failed");
				}
				responseBuffer.get(chunk, 0, HID_BUFFER_SIZE);
				response.write(chunk, 0, HID_BUFFER_SIZE);				
			}
			if (responseData[responseData.length-2] != (byte) 0x90 || responseData[responseData.length-1] != (byte) 0x00) {
				throw new BTChipException("Wrong response");
			}
			responseData= Arrays.copyOfRange(responseData, 0, responseData.length-2);
		}
		return responseData;		
	}

	@Override
	public void close() throws BTChipException {
		LibUsb.releaseInterface(handle, 0);
		LibUsb.attachKernelDriver(handle, 0);		
		LibUsb.close(handle);		
	}
	
	public static String[] listDevices() throws BTChipException {
		Device devices[] = HidUsb.enumDevices(VID, PID);
		Vector<String> result = new Vector<String>();
		for (Device device : devices) {
			result.add(HidUsb.getDeviceId(device));
		}
		return result.toArray(new String[0]);
	}

	public static int discoverLedgerDevice() throws BTChipException {
		Device[] devices = HidUsb.enumDevices(VID, PID);
		return devices.length;
	}

	public static Device matchDevice(String deviceName, int vid, int pid) throws BTChipException {
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
	
	public static BTChipTransportHID openDevice(String deviceName) throws BTChipException {
		byte inEndpoint = (byte)0xff;
		byte outEndpoint = (byte)0xff;
		boolean ledger = true;
		Device targetDevice = matchDevice(deviceName, VID, PID);
		if (targetDevice == null) {
			targetDevice = matchDevice(deviceName, VID, PID_LEDGER);
			if (targetDevice != null) {
				ledger = true;
			}
			else {
				targetDevice = matchDevice(deviceName, VID, PID_LEDGER_PROTON);
				if (targetDevice != null) {
					ledger = true;
				}
			}
		}
		if (targetDevice == null) {
			throw new BTChipException("Device not found");
		}
		ConfigDescriptor configDescriptor = new ConfigDescriptor();
		int result = LibUsb.getActiveConfigDescriptor(targetDevice, configDescriptor);
		if (result != LibUsb.SUCCESS) {
			throw new BTChipException("Failed to get config descriptor");
		}
		Interface[] interfaces = configDescriptor.iface();
		for (Interface deviceInterface : interfaces) {
			for (InterfaceDescriptor interfaceDescriptor : deviceInterface.altsetting()) {
				if ( interfaceDescriptor.bInterfaceProtocol() != (byte)0x00 ) {
					continue;
				}
				for (EndpointDescriptor endpointDescriptor : interfaceDescriptor.endpoint()) {
					if ((endpointDescriptor.bEndpointAddress() & LibUsb.ENDPOINT_IN) != 0) {
						inEndpoint = endpointDescriptor.bEndpointAddress();
					}
					else {
						outEndpoint = endpointDescriptor.bEndpointAddress();
					}					
				}
			}
		}
		if (inEndpoint == (byte)0xff) {
			throw new BTChipException("Couldn't find IN endpoint");
		}
		if (outEndpoint == (byte)0xff) {
			throw new BTChipException("Couldn't find OUT endpoint");
		}
		
		DeviceHandle handle = new DeviceHandle();
		result = LibUsb.open(targetDevice, handle);
		if (result != LibUsb.SUCCESS) {
			throw new BTChipException("Failed to open device");
		}
		LibUsb.detachKernelDriver(handle, 0);
		LibUsb.claimInterface(handle, 0);					
		return new BTChipTransportHID(handle, TIMEOUT, inEndpoint, outEndpoint, ledger);
	}
	
	public static void main(String args[]) throws Exception {
		BTChipTransportHID device = openDevice(null);
		byte[] result = device.exchange(Utils.hexToBin("BC00000000"));
		System.out.println(Utils.dump(result));

		result = device.exchange(Utils.hexToBin("BC01000029052c000080CA0200800000008000000000000000000000000000000000000000000000000000000000"));
		System.out.println(Utils.dump(result));
		device.close();
	}
}
