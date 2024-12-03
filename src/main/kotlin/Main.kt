import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import vglobe.HeaderWithImage

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello") }

    MaterialTheme {
        Column {
            HeaderWithImage()
            Button(onClick = {
                text = "Hello!"
            }) {
                Text(text)
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "ERVGLobe") {
        App() // Lancement de la fonction Composable principale
    }
}

