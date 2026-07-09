package com.example.btkeyboard

data class KeyInfo(
    val label: String,
    val keyCode: Byte,
    val weight: Float = 1f,
    val isModifier: Boolean = false,
    val modifierBit: Byte = 0,
    val shiftedLabel: String? = null,
    val fnLabel: String? = null,
    val isFn: Boolean = false
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

data class SplitRows(val left: List<KeyInfo>, val right: List<KeyInfo>)

sealed class KeyboardLayout(val name: String, val rows: List<List<KeyInfo>>, val isSplit: Boolean = false, val splitRows: List<SplitRows> = emptyList()) {
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
            KeyInfo("`", HidKeyCodes.KEY_GRAVE, shiftedLabel = "~"),
            KeyInfo("1", HidKeyCodes.KEY_1, shiftedLabel = "!"),
            KeyInfo("2", HidKeyCodes.KEY_2, shiftedLabel = "@"),
            KeyInfo("3", HidKeyCodes.KEY_3, shiftedLabel = "#"),
            KeyInfo("4", HidKeyCodes.KEY_4, shiftedLabel = "$"),
            KeyInfo("5", HidKeyCodes.KEY_5, shiftedLabel = "%"),
            KeyInfo("6", HidKeyCodes.KEY_6, shiftedLabel = "^"),
            KeyInfo("7", HidKeyCodes.KEY_7, shiftedLabel = "&"),
            KeyInfo("8", HidKeyCodes.KEY_8, shiftedLabel = "*"),
            KeyInfo("9", HidKeyCodes.KEY_9, shiftedLabel = "("),
            KeyInfo("0", HidKeyCodes.KEY_0, shiftedLabel = ")"),
            KeyInfo("-", HidKeyCodes.KEY_MINUS, shiftedLabel = "_"),
            KeyInfo("=", HidKeyCodes.KEY_EQUAL, shiftedLabel = "+"),
            KeyInfo("Back", HidKeyCodes.KEY_BACKSPACE, 1.5f)
        ),
        listOf(
            KeyInfo("Tab", HidKeyCodes.KEY_TAB, 1.5f),
            KeyInfo("q", HidKeyCodes.KEY_Q, shiftedLabel = "Q"),
            KeyInfo("w", HidKeyCodes.KEY_W, shiftedLabel = "W"),
            KeyInfo("e", HidKeyCodes.KEY_E, shiftedLabel = "E"),
            KeyInfo("r", HidKeyCodes.KEY_R, shiftedLabel = "R"),
            KeyInfo("t", HidKeyCodes.KEY_T, shiftedLabel = "T"),
            KeyInfo("y", HidKeyCodes.KEY_Y, shiftedLabel = "Y"),
            KeyInfo("u", HidKeyCodes.KEY_U, shiftedLabel = "U"),
            KeyInfo("i", HidKeyCodes.KEY_I, shiftedLabel = "I"),
            KeyInfo("o", HidKeyCodes.KEY_O, shiftedLabel = "O"),
            KeyInfo("p", HidKeyCodes.KEY_P, shiftedLabel = "P"),
            KeyInfo("[", HidKeyCodes.KEY_LEFT_BRACKET, shiftedLabel = "{"),
            KeyInfo("]", HidKeyCodes.KEY_RIGHT_BRACKET, shiftedLabel = "}"),
            KeyInfo("\\", HidKeyCodes.KEY_BACKSLASH, shiftedLabel = "|")
        ),
        listOf(
            KeyInfo("Caps", HidKeyCodes.KEY_CAPS_LOCK, 1.8f),
            KeyInfo("a", HidKeyCodes.KEY_A, shiftedLabel = "A"),
            KeyInfo("s", HidKeyCodes.KEY_S, shiftedLabel = "S"),
            KeyInfo("d", HidKeyCodes.KEY_D, shiftedLabel = "D"),
            KeyInfo("f", HidKeyCodes.KEY_F, shiftedLabel = "F"),
            KeyInfo("g", HidKeyCodes.KEY_G, shiftedLabel = "G"),
            KeyInfo("h", HidKeyCodes.KEY_H, shiftedLabel = "H"),
            KeyInfo("j", HidKeyCodes.KEY_J, shiftedLabel = "J"),
            KeyInfo("k", HidKeyCodes.KEY_K, shiftedLabel = "K", fnLabel = "Stick"),
            KeyInfo("l", HidKeyCodes.KEY_L, shiftedLabel = "L"),
            KeyInfo(";", HidKeyCodes.KEY_SEMICOLON, shiftedLabel = ":"),
            KeyInfo("'", HidKeyCodes.KEY_APOSTROPHE, shiftedLabel = "\""),
            KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 2.2f)
        ),
        listOf(
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.3f, true, HidKeyCodes.MOD_LEFT_SHIFT),
            KeyInfo("z", HidKeyCodes.KEY_Z, shiftedLabel = "Z"),
            KeyInfo("x", HidKeyCodes.KEY_X, shiftedLabel = "X"),
            KeyInfo("c", HidKeyCodes.KEY_C, shiftedLabel = "C"),
            KeyInfo("v", HidKeyCodes.KEY_V, shiftedLabel = "V"),
            KeyInfo("b", HidKeyCodes.KEY_B, shiftedLabel = "B"),
            KeyInfo("n", HidKeyCodes.KEY_N, shiftedLabel = "N"),
            KeyInfo("m", HidKeyCodes.KEY_M, shiftedLabel = "M"),
            KeyInfo(",", HidKeyCodes.KEY_COMMA, shiftedLabel = "<"),
            KeyInfo(".", HidKeyCodes.KEY_DOT, shiftedLabel = ">"),
            KeyInfo("/", HidKeyCodes.KEY_SLASH, shiftedLabel = "?"),
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.7f, true, HidKeyCodes.MOD_RIGHT_SHIFT)
        ),
        listOf(
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_LEFT_CTRL),
            KeyInfo("Fn", HidKeyCodes.KEY_NONE, 1.2f, isFn = true),
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
            KeyInfo("^", HidKeyCodes.KEY_GRAVE, shiftedLabel = "°"),
            KeyInfo("1", HidKeyCodes.KEY_1, shiftedLabel = "!"),
            KeyInfo("2", HidKeyCodes.KEY_2, shiftedLabel = "\""),
            KeyInfo("3", HidKeyCodes.KEY_3, shiftedLabel = "§"),
            KeyInfo("4", HidKeyCodes.KEY_4, shiftedLabel = "$"),
            KeyInfo("5", HidKeyCodes.KEY_5, shiftedLabel = "%"),
            KeyInfo("6", HidKeyCodes.KEY_6, shiftedLabel = "&"),
            KeyInfo("7", HidKeyCodes.KEY_7, shiftedLabel = "/"),
            KeyInfo("8", HidKeyCodes.KEY_8, shiftedLabel = "("),
            KeyInfo("9", HidKeyCodes.KEY_9, shiftedLabel = ")"),
            KeyInfo("0", HidKeyCodes.KEY_0, shiftedLabel = "="),
            KeyInfo("ß", HidKeyCodes.KEY_MINUS, shiftedLabel = "?"),
            KeyInfo("´", HidKeyCodes.KEY_EQUAL, shiftedLabel = "`"),
            KeyInfo("Back", HidKeyCodes.KEY_BACKSPACE, 1.5f)
        ),
        listOf(
            KeyInfo("Tab", HidKeyCodes.KEY_TAB, 1.5f),
            KeyInfo("q", HidKeyCodes.KEY_Q, shiftedLabel = "Q"),
            KeyInfo("w", HidKeyCodes.KEY_W, shiftedLabel = "W"),
            KeyInfo("e", HidKeyCodes.KEY_E, shiftedLabel = "E"),
            KeyInfo("r", HidKeyCodes.KEY_R, shiftedLabel = "R"),
            KeyInfo("t", HidKeyCodes.KEY_T, shiftedLabel = "T"),
            KeyInfo("z", HidKeyCodes.KEY_Y, shiftedLabel = "Z"),
            KeyInfo("u", HidKeyCodes.KEY_U, shiftedLabel = "U"),
            KeyInfo("i", HidKeyCodes.KEY_I, shiftedLabel = "I"),
            KeyInfo("o", HidKeyCodes.KEY_O, shiftedLabel = "O"),
            KeyInfo("p", HidKeyCodes.KEY_P, shiftedLabel = "P"),
            KeyInfo("ü", HidKeyCodes.KEY_LEFT_BRACKET, shiftedLabel = "Ü"),
            KeyInfo("+", HidKeyCodes.KEY_RIGHT_BRACKET, shiftedLabel = "*"),
            KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1.5f)
        ),
        listOf(
            KeyInfo("Caps", HidKeyCodes.KEY_CAPS_LOCK, 1.8f),
            KeyInfo("a", HidKeyCodes.KEY_A, shiftedLabel = "A"),
            KeyInfo("s", HidKeyCodes.KEY_S, shiftedLabel = "S"),
            KeyInfo("d", HidKeyCodes.KEY_D, shiftedLabel = "D"),
            KeyInfo("f", HidKeyCodes.KEY_F, shiftedLabel = "F"),
            KeyInfo("g", HidKeyCodes.KEY_G, shiftedLabel = "G"),
            KeyInfo("h", HidKeyCodes.KEY_H, shiftedLabel = "H"),
            KeyInfo("j", HidKeyCodes.KEY_J, shiftedLabel = "J"),
            KeyInfo("k", HidKeyCodes.KEY_K, shiftedLabel = "K", fnLabel = "Stick"),
            KeyInfo("l", HidKeyCodes.KEY_L, shiftedLabel = "L"),
            KeyInfo("ö", HidKeyCodes.KEY_SEMICOLON, shiftedLabel = "Ö"),
            KeyInfo("ä", HidKeyCodes.KEY_APOSTROPHE, shiftedLabel = "Ä"),
            KeyInfo("#", HidKeyCodes.KEY_BACKSLASH, shiftedLabel = "'"),
            KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1f)
        ),
        listOf(
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 1.3f, true, HidKeyCodes.MOD_LEFT_SHIFT),
            KeyInfo("<", 0x64.toByte(), shiftedLabel = ">"),
            KeyInfo("y", HidKeyCodes.KEY_Z, shiftedLabel = "Y"),
            KeyInfo("x", HidKeyCodes.KEY_X, shiftedLabel = "X"),
            KeyInfo("c", HidKeyCodes.KEY_C, shiftedLabel = "C"),
            KeyInfo("v", HidKeyCodes.KEY_V, shiftedLabel = "V"),
            KeyInfo("b", HidKeyCodes.KEY_B, shiftedLabel = "B"),
            KeyInfo("n", HidKeyCodes.KEY_N, shiftedLabel = "N"),
            KeyInfo("m", HidKeyCodes.KEY_M, shiftedLabel = "M"),
            KeyInfo(",", HidKeyCodes.KEY_COMMA, shiftedLabel = ";"),
            KeyInfo(".", HidKeyCodes.KEY_DOT, shiftedLabel = ":"),
            KeyInfo("-", HidKeyCodes.KEY_SLASH, shiftedLabel = "_"),
            KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.7f, true, HidKeyCodes.MOD_RIGHT_SHIFT)
        ),
        listOf(
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_LEFT_CTRL),
            KeyInfo("Fn", HidKeyCodes.KEY_NONE, 1.2f, isFn = true),
            KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_ALT),
            KeyInfo("Space", HidKeyCodes.KEY_SPACE, 6f),
            KeyInfo("AltGr", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_ALT),
            KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_GUI),
            KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.5f, true, HidKeyCodes.MOD_RIGHT_CTRL)
        )
    ))

    object ThumbQwerty : KeyboardLayout("T-QWERTY", emptyList(), true, listOf(
        SplitRows(
            listOf(KeyInfo("Esc", HidKeyCodes.KEY_ESC), KeyInfo("F1", HidKeyCodes.KEY_F1), KeyInfo("F2", HidKeyCodes.KEY_F2), KeyInfo("F3", HidKeyCodes.KEY_F3), KeyInfo("F4", HidKeyCodes.KEY_F4), KeyInfo("F5", HidKeyCodes.KEY_F5)),
            listOf(KeyInfo("F6", HidKeyCodes.KEY_F6), KeyInfo("F7", HidKeyCodes.KEY_F7), KeyInfo("F8", HidKeyCodes.KEY_F8), KeyInfo("F9", HidKeyCodes.KEY_F9), KeyInfo("F10", HidKeyCodes.KEY_F10), KeyInfo("F11", HidKeyCodes.KEY_F11), KeyInfo("F12", HidKeyCodes.KEY_F12))
        ),
        SplitRows(
            listOf(KeyInfo("`", HidKeyCodes.KEY_GRAVE, shiftedLabel = "~"), KeyInfo("1", HidKeyCodes.KEY_1, shiftedLabel = "!"), KeyInfo("2", HidKeyCodes.KEY_2, shiftedLabel = "@"), KeyInfo("3", HidKeyCodes.KEY_3, shiftedLabel = "#"), KeyInfo("4", HidKeyCodes.KEY_4, shiftedLabel = "$"), KeyInfo("5", HidKeyCodes.KEY_5, shiftedLabel = "%")),
            listOf(KeyInfo("6", HidKeyCodes.KEY_6, shiftedLabel = "^"), KeyInfo("7", HidKeyCodes.KEY_7, shiftedLabel = "&"), KeyInfo("8", HidKeyCodes.KEY_8, shiftedLabel = "*"), KeyInfo("9", HidKeyCodes.KEY_9, shiftedLabel = "("), KeyInfo("0", HidKeyCodes.KEY_0, shiftedLabel = ")"), KeyInfo("-", HidKeyCodes.KEY_MINUS, shiftedLabel = "_"), KeyInfo("Back", HidKeyCodes.KEY_BACKSPACE, 1.5f))
        ),
        SplitRows(
            listOf(KeyInfo("Tab", HidKeyCodes.KEY_TAB, 1.2f), KeyInfo("q", HidKeyCodes.KEY_Q, shiftedLabel = "Q"), KeyInfo("w", HidKeyCodes.KEY_W, shiftedLabel = "W"), KeyInfo("e", HidKeyCodes.KEY_E, shiftedLabel = "E"), KeyInfo("r", HidKeyCodes.KEY_R, shiftedLabel = "R"), KeyInfo("t", HidKeyCodes.KEY_T, shiftedLabel = "T")),
            listOf(KeyInfo("y", HidKeyCodes.KEY_Y, shiftedLabel = "Y"), KeyInfo("u", HidKeyCodes.KEY_U, shiftedLabel = "U"), KeyInfo("i", HidKeyCodes.KEY_I, shiftedLabel = "I"), KeyInfo("o", HidKeyCodes.KEY_O, shiftedLabel = "O"), KeyInfo("p", HidKeyCodes.KEY_P, shiftedLabel = "P"), KeyInfo("[", HidKeyCodes.KEY_LEFT_BRACKET, shiftedLabel = "{"), KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1.5f))
        ),
        SplitRows(
            listOf(KeyInfo("Caps", HidKeyCodes.KEY_CAPS_LOCK, 1.5f), KeyInfo("a", HidKeyCodes.KEY_A, shiftedLabel = "A"), KeyInfo("s", HidKeyCodes.KEY_S, shiftedLabel = "S"), KeyInfo("d", HidKeyCodes.KEY_D, shiftedLabel = "D"), KeyInfo("f", HidKeyCodes.KEY_F, shiftedLabel = "F"), KeyInfo("g", HidKeyCodes.KEY_G, shiftedLabel = "G")),
            listOf(KeyInfo("h", HidKeyCodes.KEY_H, shiftedLabel = "H"), KeyInfo("j", HidKeyCodes.KEY_J, shiftedLabel = "J"), KeyInfo("k", HidKeyCodes.KEY_K, shiftedLabel = "K", fnLabel = "Stick"), KeyInfo("l", HidKeyCodes.KEY_L, shiftedLabel = "L"), KeyInfo(";", HidKeyCodes.KEY_SEMICOLON, shiftedLabel = ":"), KeyInfo("'", HidKeyCodes.KEY_APOSTROPHE, shiftedLabel = "\""), KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1.2f))
        ),
        SplitRows(
            listOf(KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.0f, true, HidKeyCodes.MOD_LEFT_SHIFT), KeyInfo("z", HidKeyCodes.KEY_Z, shiftedLabel = "Z"), KeyInfo("x", HidKeyCodes.KEY_X, shiftedLabel = "X"), KeyInfo("c", HidKeyCodes.KEY_C, shiftedLabel = "C"), KeyInfo("v", HidKeyCodes.KEY_V, shiftedLabel = "V")),
            listOf(KeyInfo("b", HidKeyCodes.KEY_B, shiftedLabel = "B"), KeyInfo("n", HidKeyCodes.KEY_N, shiftedLabel = "N"), KeyInfo("m", HidKeyCodes.KEY_M, shiftedLabel = "M"), KeyInfo(",", HidKeyCodes.KEY_COMMA, shiftedLabel = "<"), KeyInfo(".", HidKeyCodes.KEY_DOT, shiftedLabel = ">"), KeyInfo("/", HidKeyCodes.KEY_SLASH, shiftedLabel = "?"), KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.0f, true, HidKeyCodes.MOD_RIGHT_SHIFT))
        ),
        SplitRows(
            listOf(KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_CTRL), KeyInfo("Fn", HidKeyCodes.KEY_NONE, 1.0f, isFn = true), KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.0f, true, HidKeyCodes.MOD_LEFT_ALT), KeyInfo("Space", HidKeyCodes.KEY_SPACE, 3f)),
            listOf(KeyInfo("Space", HidKeyCodes.KEY_SPACE, 3f), KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.0f, true, HidKeyCodes.MOD_RIGHT_ALT), KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.0f, true, HidKeyCodes.MOD_RIGHT_GUI), KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_CTRL))
        )
    ))

    object ThumbQwertz : KeyboardLayout("T-QWERTZ", emptyList(), true, listOf(
        SplitRows(
            listOf(KeyInfo("Esc", HidKeyCodes.KEY_ESC), KeyInfo("F1", HidKeyCodes.KEY_F1), KeyInfo("F2", HidKeyCodes.KEY_F2), KeyInfo("F3", HidKeyCodes.KEY_F3), KeyInfo("F4", HidKeyCodes.KEY_F4), KeyInfo("F5", HidKeyCodes.KEY_F5)),
            listOf(KeyInfo("F6", HidKeyCodes.KEY_F6), KeyInfo("F7", HidKeyCodes.KEY_F7), KeyInfo("F8", HidKeyCodes.KEY_F8), KeyInfo("F9", HidKeyCodes.KEY_F9), KeyInfo("F10", HidKeyCodes.KEY_F10), KeyInfo("F11", HidKeyCodes.KEY_F11), KeyInfo("F12", HidKeyCodes.KEY_F12))
        ),
        SplitRows(
            listOf(KeyInfo("^", HidKeyCodes.KEY_GRAVE, shiftedLabel = "°"), KeyInfo("1", HidKeyCodes.KEY_1, shiftedLabel = "!"), KeyInfo("2", HidKeyCodes.KEY_2, shiftedLabel = "\""), KeyInfo("3", HidKeyCodes.KEY_3, shiftedLabel = "§"), KeyInfo("4", HidKeyCodes.KEY_4, shiftedLabel = "$"), KeyInfo("5", HidKeyCodes.KEY_5, shiftedLabel = "%")),
            listOf(KeyInfo("6", HidKeyCodes.KEY_6, shiftedLabel = "&"), KeyInfo("7", HidKeyCodes.KEY_7, shiftedLabel = "/"), KeyInfo("8", HidKeyCodes.KEY_8, shiftedLabel = "("), KeyInfo("9", HidKeyCodes.KEY_9, shiftedLabel = ")"), KeyInfo("0", HidKeyCodes.KEY_0, shiftedLabel = "="), KeyInfo("ß", HidKeyCodes.KEY_MINUS, shiftedLabel = "?"), KeyInfo("Back", HidKeyCodes.KEY_BACKSPACE, 1.5f))
        ),
        SplitRows(
            listOf(KeyInfo("Tab", HidKeyCodes.KEY_TAB, 1.2f), KeyInfo("q", HidKeyCodes.KEY_Q, shiftedLabel = "Q"), KeyInfo("w", HidKeyCodes.KEY_W, shiftedLabel = "W"), KeyInfo("e", HidKeyCodes.KEY_E, shiftedLabel = "E"), KeyInfo("r", HidKeyCodes.KEY_R, shiftedLabel = "R"), KeyInfo("t", HidKeyCodes.KEY_T, shiftedLabel = "T")),
            listOf(KeyInfo("z", HidKeyCodes.KEY_Y, shiftedLabel = "Z"), KeyInfo("u", HidKeyCodes.KEY_U, shiftedLabel = "U"), KeyInfo("i", HidKeyCodes.KEY_I, shiftedLabel = "I"), KeyInfo("o", HidKeyCodes.KEY_O, shiftedLabel = "O"), KeyInfo("p", HidKeyCodes.KEY_P, shiftedLabel = "P"), KeyInfo("ü", HidKeyCodes.KEY_LEFT_BRACKET, shiftedLabel = "Ü"), KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1.5f))
        ),
        SplitRows(
            listOf(KeyInfo("Caps", HidKeyCodes.KEY_CAPS_LOCK, 1.5f), KeyInfo("a", HidKeyCodes.KEY_A, shiftedLabel = "A"), KeyInfo("s", HidKeyCodes.KEY_S, shiftedLabel = "S"), KeyInfo("d", HidKeyCodes.KEY_D, shiftedLabel = "D"), KeyInfo("f", HidKeyCodes.KEY_F, shiftedLabel = "F"), KeyInfo("g", HidKeyCodes.KEY_G, shiftedLabel = "G")),
            listOf(KeyInfo("h", HidKeyCodes.KEY_H, shiftedLabel = "H"), KeyInfo("j", HidKeyCodes.KEY_J, shiftedLabel = "J"), KeyInfo("k", HidKeyCodes.KEY_K, shiftedLabel = "K", fnLabel = "Stick"), KeyInfo("l", HidKeyCodes.KEY_L, shiftedLabel = "L"), KeyInfo("ö", HidKeyCodes.KEY_SEMICOLON, shiftedLabel = "Ö"), KeyInfo("ä", HidKeyCodes.KEY_APOSTROPHE, shiftedLabel = "Ä"), KeyInfo("Enter", HidKeyCodes.KEY_ENTER, 1.2f))
        ),
        SplitRows(
            listOf(KeyInfo("Shift", HidKeyCodes.KEY_NONE, 1.3f, true, HidKeyCodes.MOD_LEFT_SHIFT), KeyInfo("<", 0x64.toByte(), shiftedLabel = ">"), KeyInfo("y", HidKeyCodes.KEY_Z, shiftedLabel = "Y"), KeyInfo("x", HidKeyCodes.KEY_X, shiftedLabel = "X"), KeyInfo("c", HidKeyCodes.KEY_C, shiftedLabel = "C")),
            listOf(KeyInfo("v", HidKeyCodes.KEY_V, shiftedLabel = "V"), KeyInfo("b", HidKeyCodes.KEY_B, shiftedLabel = "B"), KeyInfo("n", HidKeyCodes.KEY_N, shiftedLabel = "N"), KeyInfo("m", HidKeyCodes.KEY_M, shiftedLabel = "M"), KeyInfo(",", HidKeyCodes.KEY_COMMA, shiftedLabel = ";"), KeyInfo(".", HidKeyCodes.KEY_DOT, shiftedLabel = ":"), KeyInfo("Shift", HidKeyCodes.KEY_NONE, 2.0f, true, HidKeyCodes.MOD_RIGHT_SHIFT))
        ),
        SplitRows(
            listOf(KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_LEFT_CTRL), KeyInfo("Fn", HidKeyCodes.KEY_NONE, 1.0f, isFn = true), KeyInfo("Alt", HidKeyCodes.KEY_NONE, 1.0f, true, HidKeyCodes.MOD_LEFT_ALT), KeyInfo("Space", HidKeyCodes.KEY_SPACE, 3f)),
            listOf(KeyInfo("Space", HidKeyCodes.KEY_SPACE, 3f), KeyInfo("AltGr", HidKeyCodes.KEY_NONE, 1.0f, true, HidKeyCodes.MOD_RIGHT_ALT), KeyInfo("Win", HidKeyCodes.KEY_NONE, 1.0f, true, HidKeyCodes.MOD_RIGHT_GUI), KeyInfo("Ctrl", HidKeyCodes.KEY_NONE, 1.2f, true, HidKeyCodes.MOD_RIGHT_CTRL))
        )
    ))
}
