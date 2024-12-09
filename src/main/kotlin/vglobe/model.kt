package vglobe

import androidx.compose.runtime.*

/**
 * Gère la navigation entre les dates disponibles.
 *
 * @param dates Liste triée des dates disponibles.
 * @return Un état mutable contenant la date actuelle et des actions pour avancer ou reculer.
 */
@Composable
fun dateNavigation(dates: List<String>): Pair<String, (Boolean) -> Unit> {
    var currentDateIndex by remember { mutableStateOf(0) }

    // Fonction pour avancer ou reculer
    val navigate: (Boolean) -> Unit = { forward ->
        if (forward && currentDateIndex < dates.size - 1) {
            currentDateIndex++
        } else if (!forward && currentDateIndex > 0) {
            currentDateIndex--
        }
    }

    return dates[currentDateIndex] to navigate
}