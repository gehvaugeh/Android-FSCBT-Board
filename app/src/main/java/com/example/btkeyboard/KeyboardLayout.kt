package com.example.btkeyboard

data class KeyInfo(
    val label: String,
    val keyCode: Byte,
    val weight: Float = 1f,
    val isModifier: Boolean = false,
    val modifierBit: Byte = 0
)

object HidKeyCodes {
    const val KEY_NONE: Byte = 0x00
    const val KEY_A: Byte = 0x04
    const val KEY_B: Byte = 0x05
    const val KEY_C: Byte = 0x06
    const val KEY_D: Byte = 0x07
    const val KEY_E: Byte = 0x08
    const val KEY_F: Byte = 0x09
    const val KEY_G: Byte = 0x0A
    const val KEY_H: Byte = 0x0B
    const val KEY_I: Byte = 0x0C
    const val KEY_J: Byte = 0x0D
    const val KEY_K: Byte = 0x0E
    const val KEY_L: Byte = 0x0F
    const val KEY_M: Byte = 0x10
    const val KEY_N: Byte = 0x11
    const val KEY_O: Byte = 0x12
    const val KEY_P: Byte = 0x13
    const val KEY_Q: Byte = 0x14
    const val KEY_R: Byte = 0x15
    const val KEY_S: Byte = 0x16
    const val KEY_T: Byte = 0x17
    const val KEY_U: Byte = 0x18
    const val KEY_V: Byte = 0x19
    const val KEY_W: Byte = 0x1A
    const val KEY_X: Byte = 0x1B
    const val KEY_Y: Byte = 0x1C
    const val KEY_Z: Byte = 0x1D
    const val KEY_1: Byte = 0x1E
    const val KEY_2: Byte = 0x1F
    const val KEY_3: Byte = 0x20
    const val KEY_4: Byte = 0x21
    const val KEY_5: Byte = 0x22
    const val KEY_6: Byte = 0x23
    const val KEY_7: Byte = 0x24
    const val KEY_8: Byte = 0x25
    const val KEY_9: Byte = 0x26
    const val KEY_0: Byte = 0x27
    const val KEY_ENTER: Byte = 0x28
    const val KEY_ESC: Byte = 0x29
    const val KEY_BACKSPACE: Byte = 0x2A
    const val KEY_TAB: Byte = 0x2B
    const val KEY_SPACE: Byte = 0x2C
    const val KEY_MINUS: Byte = 0x2D
    const val KEY_EQUAL: Byte = 0x2E
    const val KEY_LEFT_BRACKET: Byte = 0x2F
    const val KEY_RIGHT_BRACKET: Byte = 0x30
    const val KEY_BACKSLASH: Byte = 0x31
    const val KEY_SEMICOLON: Byte = 0x33
    const val KEY_APOSTROPHE: Byte = 0x34
    const val KEY_GRAVE: Byte = 0x35
    const val KEY_COMMA: Byte = 0x36
    const val KEY_DOT: Byte = 0x37
    const val KEY_SLASH: Byte = 0x38
    const val KEY_CAPS_LOCK: Byte = 0x39

    const val KEY_F1: Byte = 0x3A
    const val KEY_F2: Byte = 0x3B
    const val KEY_F3: Byte = 0x3C
    const val KEY_F4: Byte = 0x3D
    const val KEY_F5: Byte = 0x3E
    const val KEY_F6: Byte = 0x3F
    const val KEY_F7: Byte = 0x40
    const val KEY_F8: Byte = 0x41
    const val KEY_F9: Byte = 0x42
    const val KEY_F10: Byte = 0x43
    const val KEY_F11: Byte = 0x44
    const val KEY_F12: Byte = 0x45

    const val KEY_RIGHT: Byte = 0x4F
    const val KEY_LEFT: Byte = 0x50
    const val KEY_DOWN: Byte = 0x51
    const val KEY_UP: Byte = 0x52

    // Modifiers
    const val MOD_LEFT_CTRL: Byte = 0x01
    const val MOD_LEFT_SHIFT: Byte = 0x02
    const val MOD_LEFT_ALT: Byte = 0x04
    const val MOD_LEFT_GUI: Byte = 0x08
    const val MOD_RIGHT_CTRL: Byte = 0x10
    const val MOD_RIGHT_SHIFT: Byte = 0x20
    const val MOD_RIGHT_ALT: Byte = 0x40
    const val MOD_RIGHT_GUI: Byte = 0x80.toByte()
}

sealed class KeyboardLayout(val name: String, val rows: List<List<KeyInfo>>) {
    object Qwerty : KeyboardLayout("QWERTY", listOf(
        listOf(
            KeyInfo("Esc", HidKeyCodes.KEY_ESC),
            KeyInfo("F1", HidKeyCodes.KEY_F1),
            KeyInfo("F2", HidKeyCodes.KEY_F2),
            KeyInfo("F3", HidKeyCodes.KEY_F3),
            KeyInfo("F4", HidKeyCodes.KEY_F4),
            KeyInfo("F5", HidKeyCodes.KEY_F5),
            KeyInfo("F6", HidKeyCodes.KEY_F6),
            KeyInfo("F7", HidKeyCodes.KEY_F7),
            KeyInfo("F8", HidKeyCodes.KEY_F8),
            KeyInfo("F9", HidKeyCodes.KEY_F9),
            KeyInfo("F10", HidKeyCodes.KEY_F10),
            KeyInfo("F11", HidKeyCodes.KEY_F11),
            KeyInfo("F12", HidKeyCodes.KEY_F12),
        ),
        listOf(
            KeyInfo("`", HidKeyCodes.KEY_GRAVE),
            KeyInfo("1", HidKeyCodes.KEY_1),
            KeyInfo("2", HidKeyCodes.KEY_2),
            KeyInfo("3", HidKeyCodes.KEY_3),
            KeyInfo("4", HidKeyCodes.KEY_4),
            KeyInfo("5", HidKeyCodes.KEY_5),
            KeyInfo("6", HidKeyCodes.KEY_6),
            KeyInfo("7", HidKeyCodes.KEY_7),
            KeyInfo("8", HidKeyCodes.KEY_8),
            KeyInfo("9", HidKeyCodes.KEY_9),
            KeyInfo("0", HidKeyCodes.KEY_0),
            KeyInfo("-", HidKeyCodes.KEY_MINUS),
            KeyInfo("=", HidKeyCodes.KEY_EQUAL),
            KeyInfo("Back", HidKeyCodes.KEY_BACKSPACE, 1.5f)
        ),
        listOf(
            KeyInfo("Tab", HidKeyCodes.KEY_TAB, 1.5f),
            KeyInfo("Q", HidKeyCodes.KEY_Q),
            KeyInfo("W", HidKeyCodes.KEY_W),
            KeyInfo("E", HidKeyCodes.KEY_E),
            KeyInfo("R", HidKeyCodes.KEY_R),
            KeyInfo("T", HidKeyCodes.KEY_T),
            KeyInfo("Y", HidKeyCodes.KEY_Y),
            KeyInfo("U", HidKeyCodes.KEY_U),
            KeyInfo("I", HidKeyCodes.KEY_I),
            KeyInfo("O", HidKeyCodes.KEY_O),
            KeyInfo("P", HidKeyCodes.KEY_P),
            KeyInfo("[", HidKeyCodes.KEY_LEFT_BRACKET),
            KeyInfo("]", HidKeyCodes.KEY_RIGHT_BRACKET),
            KeyInfo("\\", HidKeyCodes.KEY_BACKSLASH)
        ),
        listOf(
            KeyInfo("Caps", HidKeyCodes.KEY_CAPS_LOCK, 1.8f),
            KeyInfo("A", HidKeyCodes.KEY_A),
            KeyInfo("S", HidKeyCodes.KEY_S),
            KeyInfo("D", HidKeyCodes.KEY_D),
            KeyInfo("F", HidKeyCodes.KEY_F),
            KeyInfo("G", HidKeyCodes.KEY_G),
            KeyInfo("H", HidKeyCodes.KEY_H),
            KeyInfo("J", HidKeyCodes.KEY_J),
            KeyInfo("K", HidKeyCodes.KEY_K),
            KeyInfo("L", HidKeyCodes.KEY_L),
            KeyInfo(";", HidKeyCodes.KEY_SEMICOLON),
            KeyInfo("'", HidKeyCodes.KEY_APOSTROPHE),
            KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 2.2f)
        ),
        listOf(
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.3f, true, HidKeyCodes.MOD_LEFT_SHIFT),
            KeyInfo("Z", HidKeyCodes.KEY_Z),
            KeyInfo("X", HidKeyCodes.KEY_X),
            KeyInfo("C", HidKeyCodes.KEY_C),
            KeyInfo("V", HidKeyCodes.KEY_V),
            KeyInfo("B", HidKeyCodes.KEY_B),
            KeyInfo("N", HidKeyCodes.KEY_N),
            KeyInfo("M", HidKeyCodes.KEY_M),
            KeyInfo(",", HidKeyCodes.KEY_COMMA),
            KeyInfo(".", HidKeyCodes.KEY_DOT),
            KeyInfo("/", HidKeyCodes.KEY_SLASH),
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.7f, true, HidKeyCodes.MOD_RIGHT_SHIFT)
        ),
        listOf(
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_LEFT_CTRL),
            KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_GUI),
            KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_ALT),
            KeyInfo("Space", HidKeyCodes.KEY_SPACE, 6f),
            KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_ALT),
            KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_GUI),
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_RIGHT_CTRL)
        )
    ))

    object Qwertz : KeyboardLayout("QWERTZ", listOf(
        listOf(
            KeyInfo("Esc", HidKeyCodes.KEY_ESC),
            KeyInfo("F1", HidKeyCodes.KEY_F1),
            KeyInfo("F2", HidKeyCodes.KEY_F2),
            KeyInfo("F3", HidKeyCodes.KEY_F3),
            KeyInfo("F4", HidKeyCodes.KEY_F4),
            KeyInfo("F5", HidKeyCodes.KEY_F5),
            KeyInfo("F6", HidKeyCodes.KEY_F6),
            KeyInfo("F7", HidKeyCodes.KEY_F7),
            KeyInfo("F8", HidKeyCodes.KEY_F8),
            KeyInfo("F9", HidKeyCodes.KEY_F9),
            KeyInfo("F10", HidKeyCodes.KEY_F10),
            KeyInfo("F11", HidKeyCodes.KEY_F11),
            KeyInfo("F12", HidKeyCodes.KEY_F12),
        ),
        listOf(
            KeyInfo("^", HidKeyCodes.KEY_GRAVE),
            KeyInfo("1", HidKeyCodes.KEY_1),
            KeyInfo("2", HidKeyCodes.KEY_2),
            KeyInfo("3", HidKeyCodes.KEY_3),
            KeyInfo("4", HidKeyCodes.KEY_4),
            KeyInfo("5", HidKeyCodes.KEY_5),
            KeyInfo("6", HidKeyCodes.KEY_6),
            KeyInfo("7", HidKeyCodes.KEY_7),
            KeyInfo("8", HidKeyCodes.KEY_8),
            KeyInfo("9", HidKeyCodes.KEY_9),
            KeyInfo("0", HidKeyCodes.KEY_0),
            KeyInfo("ß", HidKeyCodes.KEY_MINUS),
            KeyInfo("´", HidKeyCodes.KEY_EQUAL),
            KeyInfo("Back", HidKeyCodes.KEY_BACKSPACE, 1.5f)
        ),
        listOf(
            KeyInfo("Tab", HidKeyCodes.KEY_TAB, 1.5f),
            KeyInfo("Q", HidKeyCodes.KEY_Q),
            KeyInfo("W", HidKeyCodes.KEY_W),
            KeyInfo("E", HidKeyCodes.KEY_E),
            KeyInfo("R", HidKeyCodes.KEY_R),
            KeyInfo("T", HidKeyCodes.KEY_T),
            KeyInfo("Z", HidKeyCodes.KEY_Z),
            KeyInfo("U", HidKeyCodes.KEY_U),
            KeyInfo("I", HidKeyCodes.KEY_I),
            KeyInfo("O", HidKeyCodes.KEY_O),
            KeyInfo("P", HidKeyCodes.KEY_P),
            KeyInfo("Ü", HidKeyCodes.KEY_LEFT_BRACKET),
            KeyInfo("*", HidKeyCodes.KEY_RIGHT_BRACKET),
            KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1.5f)
        ),
        listOf(
            KeyInfo("Caps", HidKeyCodes.KEY_CAPS_LOCK, 1.8f),
            KeyInfo("A", HidKeyCodes.KEY_A),
            KeyInfo("S", HidKeyCodes.KEY_S),
            KeyInfo("D", HidKeyCodes.KEY_D),
            KeyInfo("F", HidKeyCodes.KEY_F),
            KeyInfo("G", HidKeyCodes.KEY_G),
            KeyInfo("H", HidKeyCodes.KEY_H),
            KeyInfo("J", HidKeyCodes.KEY_J),
            KeyInfo("K", HidKeyCodes.KEY_K),
            KeyInfo("L", HidKeyCodes.KEY_L),
            KeyInfo("Ö", HidKeyCodes.KEY_SEMICOLON),
            KeyInfo("Ä", HidKeyCodes.KEY_APOSTROPHE),
            KeyInfo("#", HidKeyCodes.KEY_BACKSLASH),
            KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1f)
        ),
        listOf(
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 1.3f, true, HidKeyCodes.MOD_LEFT_SHIFT),
            KeyInfo("<", 0x64.toByte()),
            KeyInfo("Y", HidKeyCodes.KEY_Y),
            KeyInfo("X", HidKeyCodes.KEY_X),
            KeyInfo("C", HidKeyCodes.KEY_C),
            KeyInfo("V", HidKeyCodes.KEY_V),
            KeyInfo("B", HidKeyCodes.KEY_B),
            KeyInfo("N", HidKeyCodes.KEY_N),
            KeyInfo("M", HidKeyCodes.KEY_M),
            KeyInfo(",", HidKeyCodes.KEY_COMMA),
            KeyInfo(".", HidKeyCodes.KEY_DOT),
            KeyInfo("-", HidKeyCodes.KEY_SLASH),
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.7f, true, HidKeyCodes.MOD_RIGHT_SHIFT)
        ),
        listOf(
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_LEFT_CTRL),
            KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_GUI),
            KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_ALT),
            KeyInfo("Space", HidKeyCodes.KEY_SPACE, 6f),
            KeyInfo("AltGr", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_ALT),
            KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_GUI),
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_RIGHT_CTRL)
        )
    ))
}
