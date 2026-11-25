package upca.example.mealapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import upca.example.mealapp.R


val PlayfairDisplay = FontFamily(
    Font(R.font.playfair_display_bold, FontWeight.Bold)
)

val Nunito = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold)
)

private val base = Typography()

val Typography = Typography(
    // Títulos → Playfair Display
    headlineLarge = base.headlineLarge.copy(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold),
    headlineMedium = base.headlineMedium.copy(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold),
    headlineSmall = base.headlineSmall.copy(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold),
    titleLarge = base.titleLarge.copy(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold),

    // Texto e etiquetas → Nunito
    titleMedium = base.titleMedium.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    titleSmall = base.titleSmall.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    bodyLarge = base.bodyLarge.copy(fontFamily = Nunito),
    bodyMedium = base.bodyMedium.copy(fontFamily = Nunito),
    bodySmall = base.bodySmall.copy(fontFamily = Nunito),
    labelLarge = base.labelLarge.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    labelMedium = base.labelMedium.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold),
    labelSmall = base.labelSmall.copy(fontFamily = Nunito, fontWeight = FontWeight.SemiBold)
)