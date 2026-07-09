package com.example.btkeyboard

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.util.Log
import java.util.concurrent.Executors

class BluetoothHidService(private val context: Context) {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private var hidDevice: BluetoothHidDevice? = null
    private var connectedDevice: BluetoothDevice? = null

    private val TAG = "BluetoothHidService"

    private val hidDeviceCallback = object : BluetoothHidDevice.Callback() {
        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
            Log.d(TAG, "onAppStatusChanged: registered=$registered")
        }

        override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
            Log.d(TAG, "onConnectionStateChanged: device=$device, state=$state")
            if (state == BluetoothProfile.STATE_CONNECTED) {
                connectedDevice = device
            } else if (state == BluetoothProfile.STATE_DISCONNECTED) {
                connectedDevice = null
            }
        }

        override fun onGetReport(device: BluetoothDevice?, type: Byte, id: Byte, bufferSize: Int) {
            Log.d(TAG, "onGetReport")
        }

        override fun onSetReport(device: BluetoothDevice?, type: Byte, id: Byte, data: ByteArray?) {
            Log.d(TAG, "onSetReport")
        }

        override fun onInterruptData(device: BluetoothDevice?, reportId: Byte, data: ByteArray?) {
            Log.d(TAG, "onInterruptData")
        }
    }

    init {
        bluetoothAdapter.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                if (profile == BluetoothProfile.HID_DEVICE) {
                    hidDevice = proxy as BluetoothHidDevice
                    registerApp()
                }
            }

            override fun onServiceDisconnected(profile: Int) {
                if (profile == BluetoothProfile.HID_DEVICE) {
                    hidDevice = null
                }
            }
        }, BluetoothProfile.HID_DEVICE)
    }

    @SuppressLint("MissingPermission")
    private fun registerApp() {
        val sdpSettings = BluetoothHidDeviceAppSdpSettings(
            "Android BT Keyboard",
            "Android BT Keyboard",
            "Android",
            1, // REPORT_ID_KEYBOARD
            HID_REPORT_DESCRIPTOR
        )

        hidDevice?.registerApp(
            sdpSettings,
            null,
            null,
            Executors.newSingleThreadExecutor(),
            hidDeviceCallback
        )
    }

    @SuppressLint("MissingPermission")
    fun sendKeyDown(keyCode: Byte, modifiers: Byte) {
        val report = ByteArray(8)
        report[0] = modifiers
        report[1] = 0 // Reserved
        report[2] = keyCode
        // report[3-7] are 0 for other keys if only one pressed
        hidDevice?.sendReport(connectedDevice, 1, report)
    }

    @SuppressLint("MissingPermission")
    fun sendKeyUp() {
        val report = ByteArray(8) // All zeros for no keys pressed
        hidDevice?.sendReport(connectedDevice, 1, report)
    }

    @SuppressLint("MissingPermission")
    fun sendKeys(keyCodes: ByteArray, modifiers: Byte) {
        val report = ByteArray(8)
        report[0] = modifiers
        report[1] = 0
        for (i in 0 until minOf(keyCodes.size, 6)) {
            report[2 + i] = keyCodes[i]
        }
        hidDevice?.sendReport(connectedDevice, 1, report)
    }

    companion object {
        private val HID_REPORT_DESCRIPTOR = byteArrayOf(
            0x05.toByte(), 0x01.toByte(), // Usage Page (Generic Desktop)
            0x09.toByte(), 0x06.toByte(), // Usage (Keyboard)
            0xA1.toByte(), 0x01.toByte(), // Collection (Application)
            0x85.toByte(), 0x01.toByte(), // Report ID (1)
            0x05.toByte(), 0x07.toByte(), // Usage Page (Keyboard)
            0x19.toByte(), 0xE0.toByte(), // Usage Minimum (Keyboard LeftControl)
            0x29.toByte(), 0xE7.toByte(), // Usage Maximum (Keyboard Right GUI)
            0x15.toByte(), 0x00.toByte(), // Logical Minimum (0)
            0x25.toByte(), 0x01.toByte(), // Logical Maximum (1)
            0x75.toByte(), 0x01.toByte(), // Report Size (1)
            0x95.toByte(), 0x08.toByte(), // Report Count (8)
            0x81.toByte(), 0x02.toByte(), // Input (Data, Variable, Absolute) - Modifier byte
            0x95.toByte(), 0x01.toByte(), // Report Count (1)
            0x75.toByte(), 0x08.toByte(), // Report Size (8)
            0x81.toByte(), 0x01.toByte(), // Input (Constant) - Reserved byte
            0x95.toByte(), 0x05.toByte(), // Report Count (5)
            0x75.toByte(), 0x01.toByte(), // Report Size (1)
            0x05.toByte(), 0x08.toByte(), // Usage Page (LEDs)
            0x19.toByte(), 0x01.toByte(), // Usage Minimum (Num Lock)
            0x29.toByte(), 0x05.toByte(), // Usage Maximum (Kana)
            0x91.toByte(), 0x02.toByte(), // Output (Data, Variable, Absolute) - LED report
            0x95.toByte(), 0x01.toByte(), // Report Count (1)
            0x75.toByte(), 0x03.toByte(), // Report Size (3)
            0x91.toByte(), 0x01.toByte(), // Output (Constant) - LED report padding
            0x95.toByte(), 0x06.toByte(), // Report Count (6)
            0x75.toByte(), 0x08.toByte(), // Report Size (8)
            0x15.toByte(), 0x00.toByte(), // Logical Minimum (0)
            0x25.toByte(), 0x65.toByte(), // Logical Maximum (101)
            0x05.toByte(), 0x07.toByte(), // Usage Page (Keyboard)
            0x19.toByte(), 0x00.toByte(), // Usage Minimum (Reserved (no event indicated))
            0x29.toByte(), 0x65.toByte(), // Usage Maximum (Keyboard Application)
            0x81.toByte(), 0x00.toByte(), // Input (Data, Array, Absolute) - Key codes
            0xC0.toByte()                // End Collection
        )
    }
}
