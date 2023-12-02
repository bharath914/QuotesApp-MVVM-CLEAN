package com.bharath.dailyquotesapp.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val light_colors = listOf(
    Color(0xFF4DB6AC),
    Color(0xFF80DEEA),
    Color(0xFF81C784),
    Color(0xFF90CAF9),

    Color(0xFF9FA8DA),
    Color(0xFFBA68C8),
    Color(0xFFDCE775),
    Color(0xFFEF9A9A),
    Color(0xFFFFAB40),
    Color(0xFFFFD54F),
    Color(0xFF6B8F71),
    Color(0xFF7E78D2),
    Color(0xFF8A8E91),

    Color(0xFFA01A7D),
    Color(0xFFBA68C8),
    Color(0xFFC2EABA),
    Color(0xFFDAF0EE),
    Color(0xFFDE6449),
    Color(0xFFE49273),
    Color(0xFFEE85B5),
    Color(0xFFF7FFE0),
)


val dark_colors = listOf(
    Color(0xFF005B41),
    Color(0xFF0E4F4F),
    Color(0xFF2F2A55),
    Color(0xFF334062),
    Color(0xFF371B58),
    Color(0xFF3F2E3E),
    Color(0xFF5C5470),
    Color(0xFF662549),
    Color(0xFF7D0633),

    Color(0xFFA27B5C),
    Color(0xFF002333),
    Color(0xFF013220),
    Color(0xFF023535),
    Color(0xFF222823),
    Color(0xFF3B0F30),
    Color(0xFF414447),
    Color(0xFF424841),
    Color(0xFF673D16),
    Color(0xFF7D4029),

    Color(0xFFBD2A2E),
)
//
//val dark_colors = listOf(
//    Color(0xffC1B9C0),
//    Color(0xffC0A7A5),
//    Color(0xff739FB5),
//    Color(0xffC0BEAB),
//    Color(0xff5AC25A),
//    Color(0xffB4BEB9),
//    Color(0xffC0BEA0),
//    Color(0xffB98D8B),
//    Color(0xffBEAE9E),
//    Color(0xff9E4D4D),
//    Color(0xffD8D8D8), // Darkened Light Gray
//    Color(0xff79AFC5), // Darkened Sky Blue
//    Color(0xffC3D682), // Darkened Khaki
//    Color(0xffAB76A5), // Darkened Plum
//    Color(0xff7A8D9D), // Darkened Light Steel Blue
//    Color(0xff4E8A4E), // Darkened Aquamarine
//    Color(0xff006D5A), // Darkened Medium Spring Green
//    Color(0xffCCA700), // Darkened Gold
//    Color(0xffBAA588), // Darkened Moccasin
//    Color(0xff978C52), // Darkened Dark Khaki
//    Color(0xff197262), // Darkened Turquoise
//    Color(0xffD97860), // Darkened Light Salmon
//    Color(0xff7FAAA7), // Darkened Pale Turquoise
//    Color(0xff6C8F6C), // Darkened Pale Green
//    Color(0xffD3B28A), // Darkened Bisque
//    Color(0xff5A1A7B), // Darkened Blue Violet
//    Color(0xffD7EFD7), // Darkened Honeydew
//    Color(0xff75A5BF), // Darkened Light Sky Blue
//    Color(0xffD18A3E), // Darkened Sandy Brown
//    Color(0xffDFD4D4)  // Darkened Seashell
//)
//
//val light_colors = listOf(
//    Color(0xffFFF0F5),
//    Color(0xffFFE4E1),
//    Color(0xffADD8E6),
//    Color(0xffFFFDE8),
//    Color(0xff98FF98),
//    Color(0xffF5FFFA),
//    Color(0xffFFFACD),
//    Color(0xffF7CAC9),
//    Color(0xffFAEBD7),
//    Color(0xffF08080),
//    Color(0xffffffe0), // Ivory
//    Color(0xFFFFFFC4), // Snow
//    Color(0xffD3D3D3), // Light Gray
//    Color(0xff87CEEB), // Sky Blue
//    Color(0xffF0E68C), // Khaki
//    Color(0xffDDA0DD), // Plum
//    Color(0xffB0C4DE), // Light Steel Blue
//    Color(0xff7FFFD4), // Aquamarine
//    Color(0xff00FA9A), // Medium Spring Green
//    Color(0xffFFD700), // Gold
//    Color(0xffFFE4B5), // Moccasin
//    Color(0xffBDB76B), // Dark Khaki
//    Color(0xff40E0D0), // Turquoise
//    Color(0xffFFA07A), // Light Salmon
//    Color(0xffAFEEEE), // Pale Turquoise
//    Color(0xff98FB98), // Pale Green
//    Color(0xffFFE4C4), // Bisque
//    Color(0xff8A2BE2), // Blue Violet
//    Color(0xffF0FFF0), // Honeydew
//    Color(0xff87CEFA), // Light Sky Blue
//    Color(0xffF4A460), // Sandy Brown
//    Color(0xffFFF5EE)  // Seashell
//)
//

fun getColors(darkTheme: Boolean): List<Color> {


    if (darkTheme) {
        return light_colors.shuffled()
    }
    return dark_colors.shuffled()
}