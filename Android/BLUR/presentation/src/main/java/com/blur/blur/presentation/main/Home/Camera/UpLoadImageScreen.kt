package com.blur.blur.presentation.Main.Home.Camera

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.components.Text.MessageText2
import com.blur.blur.domain.model.Image
import com.blur.blur.presentation.Component.Text.MessageText
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun UpLoadImageScreen(
    viewmodel: GalleryViewModel = hiltViewModel(),
    onNavigateToImageSelectScreen:()-> Unit,
    onNavigateSuccessImage:()->Unit
){
    val state = viewmodel.collectAsState().value
    val savedSelectedImages = state.savedSelectedImages // 선택된 이미지 목록
    val context = LocalContext.current

    viewmodel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is GallerySideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            is GallerySideEffect.NavigateUpLoadImageScreen -> {
            }

            GallerySideEffect.SuccessImage -> {
                onNavigateSuccessImage()
            }

            else -> {}
        }
    }


    UpLoadImageScreen(
        AddButton = onNavigateToImageSelectScreen,
        images = savedSelectedImages,
        UpLoadFaceImage=viewmodel::upLoadFaceImage
    )
}


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UpLoadImageScreen(
    AddButton:()->Unit,
    images: List<Image>,
    UpLoadFaceImage:()->Unit
) {
    val context = LocalContext.current


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
                }
            )
        },
        content = { contentPadding ->
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(contentPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    MessageText(
                        value= "원활한 얼굴인식을 위해 \n 측면 사진도 같이 넣어주세요.",
                        size = 22
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    MessageText2(
                        value= "이미지 학습에 사진 한장당 약 15초의 시간이 소요됩니다.",
                        size = 15
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AddImage(
                        AddButton = AddButton,
                        images= images
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = UpLoadFaceImage,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "얼굴 등록")
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
    )
}


@Preview
@Composable
fun CameraScreenPreview() {
    UpLoadImageScreen(AddButton = {}, images = emptyList(), UpLoadFaceImage = {})
}
