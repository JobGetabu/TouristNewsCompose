package ke.newsarticles.feature_home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ke.newsarticles.core_utils.navigation.Routes
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ke.newsarticles.core_utils.navigation.UiEvent
import ke.newsarticles.feature_news.presentation.NewsPage
import ke.newsarticles.feature_tourist.presentation.TouristPage


sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Articles : BottomNavItem("Articles", ke.newsarticles.core_resourses.R.drawable.ic_logo, Routes.articlePage)
    object Tourists: BottomNavItem("Tourists", ke.newsarticles.core_resourses.R.drawable.baseline_tourist_skiing, Routes.tourisPage)

}

@Composable
fun HomePage(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) { innerPadding ->

        NavigationGraph(navController = navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Articles.screen_route) {
        composable(BottomNavItem.Articles.screen_route) {
            NewsPage { event ->
                if (event is UiEvent.OnNavigate) {
                    navController.navigate(event.route)
                }
            }
        }
        composable(BottomNavItem.Tourists.screen_route) {
            TouristPage { event ->
                if (event is UiEvent.OnNavigate) {
                    navController.navigate(event.route)
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Articles,
        BottomNavItem.Tourists,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = ke.newsarticles.core_resourses.R.color.white),
        contentColor = MaterialTheme.colors.primaryVariant
    ) {

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
