package ke.newsarticles.feature_news.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import ke.newsarticles.core_utils.navigation.UiEvent
import ke.newsarticles.designs.NewsArticlesTheme
import ke.newsarticles.feature_news.domain.models.NewsModel

@Composable
fun NewsPage(
    modifier: Modifier = Modifier,
    viewModel: NewsVm = hiltViewModel(),
    onNavigate: (UiEvent.OnNavigate) -> Unit
) {
    val newsUiState = viewModel.newsUiState.collectAsState()

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
                    text = stringResource(ke.newsarticles.core_resourses.R.string.articles),
                    style = MaterialTheme.typography.h1.copy(fontSize = 30.sp)
                )

                Divider(thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // loading
                if (newsUiState.value.isLoading) {
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

                if (newsUiState.value.errorMessage != null && newsUiState.value.news.isEmpty()) Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = newsUiState.value.errorMessage ?: "")
                }

                if (newsUiState.value.news.isNotEmpty()) {
                    val news = newsUiState.value.news
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(items = news, key = { listItem -> listItem.id!! }) { article ->

                            NewsItem(
                                modifier = modifier, article
                            ) {
                                onNavigate.invoke(
                                    UiEvent.OnNavigate(
                                        "article_page?id=${article.id}"
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
fun NewsItem(modifier: Modifier, article: NewsModel, onItemClick: (NewsModel) -> Unit) {
    Row(
        modifier = modifier.clickable { onItemClick.invoke(article) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = article.title ?: "",
                style = MaterialTheme.typography.h2.copy(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.h3.copy(color = Color.DarkGray, fontSize = 14.sp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.location ?: "", style = MaterialTheme.typography.h4.copy(
                    color = Color.DarkGray, fontSize = 12.sp
                )
            )
            ProfileCard("${article.user?.name}", "${article.user?.profilepicture}")
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "Comments: ${article.commentCount.toString()}" ?: "",
                style = MaterialTheme.typography.h3.copy(color = Color.DarkGray, fontSize = 12.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

        val url = article.multiMedia?.firstOrNull { t -> t?.url != null }?.url
        if (!url.isNullOrEmpty()) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(1f),
                model = article.multiMedia?.firstOrNull { t -> t?.url != null }?.url,
                contentDescription = stringResource(id = ke.newsarticles.core_resourses.R.string.article_image_content_description),
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier.size(16.dp),
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
        }
    }
}

@Composable
fun ProfileCard(name: String, imageUrl: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
                model = imageUrl,
                contentDescription = stringResource(id = ke.newsarticles.core_resourses.R.string.article_image_content_description),
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
            Text(
                text = name,
                style = MaterialTheme.typography.h3.copy(color = Color.DarkGray, fontSize = 12.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .padding(end = 60.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatPage() {
    NewsPage() {

    }
}
