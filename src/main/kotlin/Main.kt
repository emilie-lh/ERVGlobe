import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import vglobe.HeaderWithImage
import vglobe.afficherClassement
import vglobe.extraireDonnees

@Composable
@Preview
fun App() {
    val classementsParDate = remember { extraireDonnees("C:/Users/User/Documents/M1/IHM/Kotlin/ERVGlobe/src/main/resources/classement") }

    MaterialTheme {
        Column {
            HeaderWithImage(classementsParDate)

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "ERVGLobe") {
        /*val dossierPath = //"C:/Users/mimie/IdeaProjects/ERVGLobe/src/main/resources/classement"
            javaClass.getResource("/classement")?.path ?: throw IllegalArgumentException("Le dossier 'classement' est introuvable dans les ressources.")
        // Étape 1 : Extraire les données
        val classementsParDate = extraireDonnees(dossierPath)*/
        App()
    }
}

