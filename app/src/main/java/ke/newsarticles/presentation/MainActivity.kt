package ke.newsarticles.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ke.newsarticles.core_utils.navigation.Routes
import ke.newsarticles.core_utils.navigation.UiEvent
import ke.newsarticles.core_utils.designs.NewsArticlesTheme
import ke.newsarticles.feature_home.HomePage
import ke.newsarticles.feature_news.presentation.NewsPage
import ke.newsarticles.feature_splash.SplashPage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsArticlesTheme {
                val systemUiController = rememberSystemUiController()
                val userDarkIcons = MaterialTheme.colors.isLight
                val context = LocalContext.current

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent, darkIcons = userDarkIcons
                    )
                    systemUiController.setStatusBarColor(if (userDarkIcons) Color.White else Color.Black)
                    systemUiController.setNavigationBarColor(if (userDarkIcons) Color.White else Color.Black)
                }

                val navController = rememberNavController()

                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = Routes.splashPage) {
                        composable(Routes.splashPage) {
                            SplashPage { event ->
                                if (event is UiEvent.OnNavigate) {
                                    navController.navigate(event.route) {
                                        popUpTo(0)
                                    }
                                }
                            }
                        }

                        composable(Routes.homePage) {
                            HomePage()
                        }

                        composable(Routes.articlePage) {
                            NewsPage { event ->
                                if (event is UiEvent.OnNavigate) {
                                    navController.navigate(event.route)
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}
