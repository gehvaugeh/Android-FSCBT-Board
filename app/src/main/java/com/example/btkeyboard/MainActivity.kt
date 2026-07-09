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
import androidx.compose.ui.platform.LocalDensity
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.foundation.gestures.detectDragGestures
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

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
    var currentLayoutIndex by remember { mutableIntStateOf(0) }
    val layouts = listOf(
        KeyboardLayout.Qwerty,
        KeyboardLayout.Qwertz,
        KeyboardLayout.ThumbQwerty,
        KeyboardLayout.ThumbQwertz
    )
    val currentLayout = layouts[currentLayoutIndex]

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
                                    currentLayoutIndex = (currentLayoutIndex + 1) % layouts.size
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
    var isFnPressed by remember { mutableStateOf(false) }
    var isFnSticky by remember { mutableStateOf(false) }
    var isCapsLockActive by remember { mutableStateOf(false) }
    val pressedKeys = remember { mutableStateListOf<Byte>() }

    val currentModifiers = (activeModifiers.toInt() or stickyModifiers.toInt()).toByte()
    val isShiftActive = (currentModifiers.toInt() and HidKeyCodes.MOD_LEFT_SHIFT.toInt() != 0) ||
                        (currentModifiers.toInt() and HidKeyCodes.MOD_RIGHT_SHIFT.toInt() != 0)
    val isAltGrActive = (currentModifiers.toInt() and HidKeyCodes.MOD_RIGHT_ALT.toInt() != 0)
    val isFnActive = isFnPressed || isFnSticky

    val coroutineScope = rememberCoroutineScope()

    fun notifyChanges() {
        onKeysChanged(pressedKeys.toByteArray(), currentModifiers)
    }

    fun sendTemporaryKey(keyCode: Byte) {
        coroutineScope.launch {
            onKeysChanged(byteArrayOf(keyCode), currentModifiers)
            onKeyTyped()
            delay(50)
            onKeysChanged(byteArrayOf(), currentModifiers)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        if (!layout.isSplit) {
            layout.rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    row.forEach { key ->
                        KeyboardKey(
                            key, isFnActive, isShiftActive, isAltGrActive, isCapsLockActive, stickyModifiers, isFnSticky,
                            stickyKeysEnabled, activeModifiers, pressedKeys,
                            onKeyTyped, { notifyChanges() }, ::sendTemporaryKey,
                            { activeModifiers = it }, { stickyModifiers = it },
                            { isFnPressed = it }, { isFnSticky = it }, { isCapsLockActive = it },
                            Modifier.weight(key.weight)
                        )
                    }
                }
            }
        } else {
            layout.splitRows.forEach { splitRow ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // Left half
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        splitRow.left.forEach { key ->
                            KeyboardKey(
                                key, isFnActive, isShiftActive, isAltGrActive, isCapsLockActive, stickyModifiers, isFnSticky,
                                stickyKeysEnabled, activeModifiers, pressedKeys,
                                onKeyTyped, { notifyChanges() }, ::sendTemporaryKey,
                                { activeModifiers = it }, { stickyModifiers = it },
                                { isFnPressed = it }, { isFnSticky = it }, { isCapsLockActive = it },
                                Modifier.weight(key.weight)
                            )
                        }
                    }

                    // Gap
                    Spacer(modifier = Modifier.width(120.dp))

                    // Right half
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        splitRow.right.forEach { key ->
                            KeyboardKey(
                                key, isFnActive, isShiftActive, isAltGrActive, isCapsLockActive, stickyModifiers, isFnSticky,
                                stickyKeysEnabled, activeModifiers, pressedKeys,
                                onKeyTyped, { notifyChanges() }, ::sendTemporaryKey,
                                { activeModifiers = it }, { stickyModifiers = it },
                                { isFnPressed = it }, { isFnSticky = it }, { isCapsLockActive = it },
                                Modifier.weight(key.weight)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(
    key: KeyInfo,
    isFnActive: Boolean,
    isShiftActive: Boolean,
    isAltGrActive: Boolean,
    isCapsLockActive: Boolean,
    stickyModifiers: Byte,
    isFnSticky: Boolean,
    stickyKeysEnabled: Boolean,
    activeModifiers: Byte,
    pressedKeys: MutableList<Byte>,
    onKeyTyped: () -> Unit,
    notifyChanges: () -> Unit,
    sendTemporaryKey: (Byte) -> Unit,
    setActiveModifiers: (Byte) -> Unit,
    setStickyModifiers: (Byte) -> Unit,
    setIsFnPressed: (Boolean) -> Unit,
    setIsFnSticky: (Boolean) -> Unit,
    setIsCapsLockActive: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isStickKey = key.keyCode == HidKeyCodes.KEY_K && isFnActive

    KeyCap(
        key = key,
        modifier = modifier,
        isShiftActive = isShiftActive,
        isAltGrActive = isAltGrActive,
        isFnActive = isFnActive,
        isCapsLockActive = isCapsLockActive,
        isSticky = (stickyModifiers.toInt() and key.modifierBit.toInt()) != 0 || (key.isFn && isFnSticky),
        onPress = {
            if (key.isModifier) {
                if (stickyKeysEnabled) {
                    setStickyModifiers((stickyModifiers.toInt() xor key.modifierBit.toInt()).toByte())
                } else {
                    setActiveModifiers((activeModifiers.toInt() or key.modifierBit.toInt()).toByte())
                }
            } else if (key.isFn) {
                if (stickyKeysEnabled) {
                    setIsFnSticky(!isFnSticky)
                } else {
                    setIsFnPressed(true)
                }
            } else if (key.keyCode == HidKeyCodes.KEY_CAPS_LOCK) {
                setIsCapsLockActive(!isCapsLockActive)
                if (!pressedKeys.contains(key.keyCode)) {
                    pressedKeys.add(key.keyCode)
                }
                onKeyTyped()
            } else if (key.keyCode != HidKeyCodes.KEY_NONE) {
                if (!(key.keyCode == HidKeyCodes.KEY_K && isFnActive)) {
                    if (!pressedKeys.contains(key.keyCode)) {
                        pressedKeys.add(key.keyCode)
                    }
                    onKeyTyped()
                }
            }
            notifyChanges()
        },
        onRelease = {
            if (key.isModifier) {
                if (!stickyKeysEnabled) {
                    setActiveModifiers((activeModifiers.toInt() and key.modifierBit.toInt().inv()).toByte())
                }
            } else if (key.isFn) {
                if (!stickyKeysEnabled) {
                    setIsFnPressed(false)
                }
            } else if (key.keyCode == HidKeyCodes.KEY_CAPS_LOCK) {
                pressedKeys.remove(key.keyCode)
            } else if (key.keyCode != HidKeyCodes.KEY_NONE) {
                pressedKeys.remove(key.keyCode)
                if (stickyKeysEnabled && (stickyModifiers != 0.toByte() || isFnSticky)) {
                    setStickyModifiers(0)
                    setIsFnSticky(false)
                }
            }
            notifyChanges()
        },
        onDragIncrement = if (isStickKey) { direction ->
            val arrowKey = when (direction) {
                "UP" -> HidKeyCodes.KEY_UP
                "DOWN" -> HidKeyCodes.KEY_DOWN
                "LEFT" -> HidKeyCodes.KEY_LEFT
                "RIGHT" -> HidKeyCodes.KEY_RIGHT
                else -> HidKeyCodes.KEY_NONE
            }
            if (arrowKey != HidKeyCodes.KEY_NONE) {
                sendTemporaryKey(arrowKey)
            }
        } else null
    )
}

@Composable
fun KeyCap(
    key: KeyInfo,
    modifier: Modifier = Modifier,
    isShiftActive: Boolean = false,
    isAltGrActive: Boolean = false,
    isFnActive: Boolean = false,
    isCapsLockActive: Boolean = false,
    isSticky: Boolean = false,
    onPress: () -> Unit,
    onRelease: () -> Unit,
    onDragIncrement: ((String) -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isFirstRun by remember { mutableStateOf(true) }

    var totalDragX by remember { mutableStateOf(0f) }
    var totalDragY by remember { mutableStateOf(0f) }

    val density = LocalDensity.current
    val thresholdPx = with(density) { 50.dp.toPx() }

    LaunchedEffect(isPressed) {
        if (isFirstRun) {
            isFirstRun = false
            return@LaunchedEffect
        }
        if (isPressed) {
            totalDragX = 0f
            totalDragY = 0f
            onPress()
        } else {
            onRelease()
        }
    }

    val label = when {
        isFnActive && key.fnLabel != null -> key.fnLabel
        isAltGrActive && key.altGrLabel != null -> key.altGrLabel
        isShiftActive && key.shiftedLabel != null -> key.shiftedLabel
        isCapsLockActive && key.label.length == 1 && key.label[0].isLetter() -> key.label.uppercase()
        else -> key.label
    }

    val isActiveHighlight = isPressed || isSticky || (key.keyCode == HidKeyCodes.KEY_CAPS_LOCK && isCapsLockActive)

    val backgroundColor = when {
        isActiveHighlight -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = when {
        isActiveHighlight -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = modifier
            .fillMaxHeight()
            .pointerInput(key.keyCode, isFnActive) {
                if (onDragIncrement != null) {
                    detectDragGestures(
                        onDragStart = {
                            totalDragX = 0f
                            totalDragY = 0f
                        },
                        onDragEnd = { onRelease() },
                        onDragCancel = { onRelease() },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            totalDragX += dragAmount.x
                            totalDragY += dragAmount.y

                            if (abs(totalDragX) >= thresholdPx) {
                                onDragIncrement(if (totalDragX > 0) "RIGHT" else "LEFT")
                                totalDragX = 0f
                            }
                            if (abs(totalDragY) >= thresholdPx) {
                                onDragIncrement(if (totalDragY > 0) "DOWN" else "UP")
                                totalDragY = 0f
                            }
                        }
                    )
                }
            },
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
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                softWrap = false
            )
        }
    }
}
