package ke.newsarticles.feature_tourist.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import ke.newsarticles.core_resourses.R
import ke.newsarticles.core_utils.navigation.UiEvent
import ke.newsarticles.core_utils.utils.TimeUtils
import ke.newsarticles.designs.NewsArticlesTheme
import ke.newsarticles.feature_tourist.domain.models.TouristModel

@Composable
fun TouristPage(
    modifier: Modifier = Modifier,
    viewModel: TouristVm = hiltViewModel(),
    onNavigate: (UiEvent.OnNavigate) -> Unit
){
    val touristUiState = viewModel.touristUiState.collectAsState()

    NewsArticlesTheme {
     Box {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(bottom = 56.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp, top = 16.dp, start = 10.dp),
                text = stringResource(R.string.tourist),
                style = MaterialTheme.typography.h1.copy(fontSize = 30.sp)
            )

            Divider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // loading
            if (touristUiState.value.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                    )
                }
            }

            if (touristUiState.value.errorMessage != null && touristUiState.value.tourist.isEmpty()) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = touristUiState.value.errorMessage ?: "")
            }

            if (touristUiState.value.tourist.isNotEmpty()) {
                val tourists = touristUiState.value.tourist
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(items = tourists, key = { listItem -> listItem.id!! }) { person ->

                        PersonCard(
                            modifier = modifier, person
                        ) {
                            onNavigate.invoke(
                                UiEvent.OnNavigate(
                                    "tourist_page?id=${person.id}"
                                )
                            )
                        }

                    }
                }
            }
        }
     }
    }
}

@Composable
fun PersonCard(modifier: Modifier, person: TouristModel, onItemClick: (TouristModel) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                model = stringResource(id = R.string.avator),
                contentDescription = stringResource(id = R.string.article_image_content_description),
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier.size(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }

                    is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                        )
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = person.touristName ?: "--",
                    style = MaterialTheme.typography.h2.copy(
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${person.touristEmail}",
                    style = MaterialTheme.typography.h3.copy(
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Location: ${person.touristLocation}",
                    style = MaterialTheme.typography.h3.copy(
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Since: ${TimeUtils.formatDate("${person.createdat}", "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", "E,MMM yyyy HH:mm")}",
                    style = MaterialTheme.typography.h3.copy(
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(end = 60.dp)
                    .fillMaxWidth()
            )
        }
    }
}