import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import vglobe.HeaderWithImage
import vglobe.extraireDonnees

@Composable
@Preview
fun App() {
    val dossier = System.getProperty("user.dir") + "/src/main/resources/classement"
    val classementsParDate = remember { extraireDonnees(dossier) }

    MaterialTheme {
        Column {
            HeaderWithImage(classementsParDate)

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "ERVGLobe") {
        App()
    }
}

