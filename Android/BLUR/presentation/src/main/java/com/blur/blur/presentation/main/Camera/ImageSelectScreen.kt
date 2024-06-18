package com.blur.blur.presentation.Main.Camera


import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.blur.blur.domain.model.Image
import com.blur.blur.presentation.Component.Text.MessageText
import com.blur.blur.presentation.Main.Camera.CameraX.CameraXActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ImageSelectScreen(
    viewmodel: GalleryViewModel = hiltViewModel(),
    maxSelectedImages: Int = 6,
    onNavigateUpLoadImageScreen: () -> Unit,
    onNavigateSuccessImage:()->Unit
) {
    val state = viewmodel.collectAsState().value
    val context = LocalContext.current
    viewmodel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is GallerySideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            is GallerySideEffect.NavigateUpLoadImageScreen -> {
                onNavigateUpLoadImageScreen()
            }

            GallerySideEffect.SuccessImage -> {
                onNavigateSuccessImage()
            }

            else -> {}
        }
    }

    ImageSelectScreen(
        selectedImages = state.selectedImages,
        images = state.images,
        onItemClick = viewmodel::onItemClick,
        onAddButton = viewmodel::onAddButton,
        maxSelectedImages = maxSelectedImages
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageSelectScreen(
    selectedImages: List<Image>,
    images: List<Image>,
    onItemClick: (Image) -> Unit,
    onAddButton:()->Unit,
    maxSelectedImages: Int
) {
    val context = LocalContext.current

    Surface {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text(text = "얼굴 등록") },
                    navigationIcon = {
                        // 뒤로 가기 버튼에 뒤로 가기 기능을 추가합니다.
                        IconButton(onClick = {
                            (context as? Activity)?.onBackPressed()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                        }
                    },
                    actions = {
                       TextButton(onClick = onAddButton) {
                           Text(text ="추가")
                       }
                    }
                )
            },
            content = { contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(
                                model = selectedImages.lastOrNull()?.uri
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )

                        if (selectedImages.isEmpty()) {
                            MessageText(
                                "선택된 이미지가 없습니다.",
                                15
                            )
                        }
                    }
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.onBackground),
                        columns = GridCells.Adaptive(110.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .size(110.dp)
                                    .padding(16.dp)
                                    .clickable {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                CameraXActivity::class.java
                                            )
                                        )
                                        if (context is Activity) {
                                            context.finish()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    Modifier.padding(top = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Rounded.Add,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Text(
                                        "사진 촬영",
                                        style =
                                        MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.background)
                                    )
                                }
                            }
                        }
                        items(count = images.size,
                            key = { index -> images[index].uri }
                        ) { index ->
                            val image = images[index]

                            Box(modifier = Modifier.clickable {
                                if (selectedImages.size < maxSelectedImages || selectedImages.contains(image)) {
                                    onItemClick(image)
                                }
                            }) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .aspectRatio(1f),
                                    painter = rememberAsyncImagePainter(
                                        model = image.uri,
                                        contentScale = ContentScale.Crop
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                                if (selectedImages.contains(image)) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(start = 4.dp, top = 4.dp)
                                            .clip(CircleShape)
                                            .background(color = Color.White),
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}


@Preview
@Composable
fun ImageSelectScreenPreview() {
    ImageSelectScreen(
        images = emptyList(),
        selectedImages = emptyList(),
        onItemClick = {},
        onAddButton = {}, maxSelectedImages = 3264
    )

}