package ke.newsarticles.core_utils.designs

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val fonts = FontFamily(
    Font(ke.newsarticles.core_resourses.R.font.nunito),
    Font(ke.newsarticles.core_resourses.R.font.nunito_bold, FontWeight.Bold),
    Font(ke.newsarticles.core_resourses.R.font.nunito_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.Normal, fontSize = 16.sp
    ),
    h1 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Bold, fontSize = 16.sp),
    h2 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Medium, fontSize = 16.sp),
    h3 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    body2 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    button = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.W500, fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 12.sp
    )

)

