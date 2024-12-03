package vglobe

import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp


@Composable
fun HeaderWithImage() {
    val image: ImageBitmap = useResource("images/departVG.png") { loadImageBitmap(it) }
    Image(
        bitmap = image,
        contentDescription = "Header Image",
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}