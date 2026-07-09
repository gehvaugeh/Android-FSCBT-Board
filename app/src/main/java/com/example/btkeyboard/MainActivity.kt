package com.example.btkeyboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Context
import android.os.Vibrator
import android.os.VibrationEffect
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothHidService: BluetoothHidService

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            bluetoothHidService = BluetoothHidService(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            permissions.add(Manifest.permission.BLUETOOTH_ADVERTISE)
        } else {
            permissions.add(Manifest.permission.BLUETOOTH)
            permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
        }

        if (permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            bluetoothHidService = BluetoothHidService(this)
        } else {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }

        setContent {
            if (::bluetoothHidService.isInitialized) {
                KeyboardApp(bluetoothHidService)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Permissions required for Bluetooth")
                }
            }
        }
    }

    fun makeDiscoverable() {
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        }
        startActivity(discoverableIntent)
    }
}

@Composable
fun KeyboardApp(bluetoothHidService: BluetoothHidService) {
    var isDarkTheme by remember { mutableStateOf(true) }
    var currentLayout by remember { mutableStateOf<KeyboardLayout>(KeyboardLayout.Qwerty) }
    var hapticEnabled by remember { mutableStateOf(true) }
    var stickyKeysEnabled by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                if (showSettings) {
                    // Settings Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .height(40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("BT Keyboard", style = MaterialTheme.typography.titleSmall)
                        Row {
                            Button(
                                onClick = { (context as MainActivity).makeDiscoverable() },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Pair", fontSize = 10.sp)
                            }
                            Spacer(Modifier.width(4.dp))
                            Button(
                                onClick = { isDarkTheme = !isDarkTheme },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(if (isDarkTheme) "Light" else "Dark", fontSize = 10.sp)
                            }
                            Spacer(Modifier.width(4.dp))
                            Button(
                                onClick = {
                                    currentLayout = if (currentLayout == KeyboardLayout.Qwerty) KeyboardLayout.Qwertz else KeyboardLayout.Qwerty
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(currentLayout.name, fontSize = 10.sp)
                            }
                            Spacer(Modifier.width(4.dp))
                            Button(
                                onClick = { stickyKeysEnabled = !stickyKeysEnabled },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                                modifier = Modifier.height(32.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (stickyKeysEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text("Sticky", fontSize = 10.sp)
                            }
                            Spacer(Modifier.width(4.dp))
                            Button(
                                onClick = { showSettings = false },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Hide", fontSize = 10.sp)
                            }
                        }
                    }
                }

                // Keyboard Area
                Box(modifier = Modifier.fillMaxSize()) {
                    KeyboardUI(
                        layout = currentLayout,
                        stickyKeysEnabled = stickyKeysEnabled,
                        onKeysChanged = { keyCodes, modifiers ->
                            bluetoothHidService.sendKeys(keyCodes, modifiers)
                        },
                        onKeyTyped = {
                            if (hapticEnabled) {
                                vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                            }
                        }
                    )

                    if (!showSettings) {
                        Button(
                            onClick = { showSettings = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(30.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.3f))
                        ) {
                            Text("S", fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardUI(
    layout: KeyboardLayout,
    stickyKeysEnabled: Boolean,
    onKeysChanged: (ByteArray, Byte) -> Unit,
    onKeyTyped: () -> Unit
) {
    var activeModifiers by remember { mutableStateOf(0.toByte()) }
    var stickyModifiers by remember { mutableStateOf(0.toByte()) }
    val pressedKeys = remember { mutableStateListOf<Byte>() }

    val currentModifiers = (activeModifiers.toInt() or stickyModifiers.toInt()).toByte()

    fun notifyChanges() {
        onKeysChanged(pressedKeys.toByteArray(), currentModifiers)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        layout.rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                row.forEach { key ->
                    KeyCap(
                        key = key,
                        modifier = Modifier.weight(key.weight),
                        isSticky = (stickyModifiers.toInt() and key.modifierBit.toInt()) != 0,
                        onPress = {
                            if (key.isModifier) {
                                if (stickyKeysEnabled) {
                                    stickyModifiers = (stickyModifiers.toInt() xor key.modifierBit.toInt()).toByte()
                                } else {
                                    activeModifiers = (activeModifiers.toInt() or key.modifierBit.toInt()).toByte()
                                }
                            } else if (key.keyCode != HidKeyCodes.KEY_NONE) {
                                if (!pressedKeys.contains(key.keyCode)) {
                                    pressedKeys.add(key.keyCode)
                                }
                                onKeyTyped()
                            }
                            notifyChanges()
                        },
                        onRelease = {
                            if (key.isModifier) {
                                if (!stickyKeysEnabled) {
                                    activeModifiers = (activeModifiers.toInt() and key.modifierBit.toInt().inv()).toByte()
                                }
                            } else if (key.keyCode != HidKeyCodes.KEY_NONE) {
                                pressedKeys.remove(key.keyCode)
                                if (stickyKeysEnabled && stickyModifiers != 0.toByte()) {
                                    stickyModifiers = 0
                                }
                            }
                            notifyChanges()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun KeyCap(
    key: KeyInfo,
    modifier: Modifier = Modifier,
    isSticky: Boolean = false,
    onPress: () -> Unit,
    onRelease: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isFirstRun by remember { mutableStateOf(true) }

    LaunchedEffect(isPressed) {
        if (isFirstRun) {
            isFirstRun = false
            return@LaunchedEffect
        }
        if (isPressed) {
            onPress()
        } else {
            onRelease()
        }
    }

    val backgroundColor = when {
        isPressed -> MaterialTheme.colorScheme.primary
        isSticky -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = when {
        isPressed -> MaterialTheme.colorScheme.onPrimary
        isSticky -> MaterialTheme.colorScheme.onTertiary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(4.dp),
        color = backgroundColor,
        interactionSource = interactionSource,
        onClick = {}
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = key.label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                softWrap = false
            )
        }
    }
}
