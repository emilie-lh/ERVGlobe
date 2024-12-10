package vglobe

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

data class skipper(
    val date : String,
    val rank: Int,
    val nation: String,
    val nom: String,
    val bateau : String,
    val latitude: String,
    val longitude: String,
    val vitesse: String,
    val distanceToFinish: String,
    val distanceToLeader: String
)


fun remplacerSlash(value: String?): String {
    return value?.replace("\n", "")?.trim() ?: ""
}

// Fonction pour extraire les données des fichiers JSON
fun extraireDonnees(dossierPath: String): Map<String, List<skipper>> {
    val gson = Gson()

    // Récupérer tous les fichiers JSON dans le dossier
    val fichiersJson = File(dossierPath).listFiles { file ->
        file.extension == "json"
    } ?: emptyArray()

    if (fichiersJson.isEmpty()) {
        println("Aucun fichier JSON trouvé dans le dossier : $dossierPath")
        return emptyMap()
    }

    // Stocker les classements par date
    val classementsSkipperDate = mutableMapOf<String, List<skipper>>()

    fichiersJson.forEach { fichier ->
        val filePath = fichier.absolutePath
        val jsonFile = fichier.readText()

        // Extraire la date du nom de fichier (format "YYYYMMDD")
        val date = Regex("""\d{8}""").find(filePath)?.value?.let {
            "${it.substring(0, 4)}-${it.substring(4, 6)}-${it.substring(6, 8)}"
        } ?: "unknown_date"


        // Définir un type pour la liste des skippers
        val skipperListType = object : TypeToken<List<Map<String, String>>>() {}.type

        // Parser les données en une liste de Map<String, String>
        val rawData: List<Map<String, String>> = gson.fromJson(jsonFile, skipperListType)

        // Transformer les données en objets Skipper
        val skippers = rawData.mapNotNull { row ->
            try {
                skipper(
                    date = date, // Utiliser la date extraite
                    rank = row["Rang"]?.toIntOrNull() ?: -1,
                    nation = row["Nat. / Voile"]?.split("\n")?.first() ?: "",
                    nom = row["Skipper / Bateau"]?.split("\n")?.first() ?: "",
                    bateau = row["Skipper / Bateau"]?.split("\n")?.getOrNull(1) ?: "",
                    latitude = row["Latitude"] ?: row["Latitude\n"]?.let { remplacerSlash(it) } ?: "",
                    longitude = row["Longitude"] ?: row["Longitude\n"]?.let { remplacerSlash(it) } ?: "",
                    vitesse = row["Vitesse"] ?: row["Vitesse\n"]?.let { remplacerSlash(it) } ?: "",
                    distanceToFinish = row["DTF"] ?: row["DTF\n"]?.let { remplacerSlash(it) } ?: "",
                    distanceToLeader = row["DTL"] ?: row["DTL\n"]?.let { remplacerSlash(it) } ?: ""
                )
            } catch (e: Exception) {
                println("Erreur lors du parsing d'une ligne dans $filePath : ${e.message}")
                null
            }
        }.filter { it.rank > 0 } // Filtrer les entrées valides

        classementsSkipperDate[date] = skippers
    }

    return classementsSkipperDate
}

// Fonction pour afficher les données
fun afficherClassement(classementsParDate: Map<String, List<skipper>>) {
    if (classementsParDate.isEmpty()) {
        println("Aucun classement disponible.")
        return
    }

    classementsParDate.forEach { (date, skippers) ->
        println("Classement pour la date : $date\n")
        skippers.sortedBy { it.rank }.forEach {
            println("${it.rank}. ${it.nom} (${it.nation}) - ${it.bateau} - ${it.latitude} / ${it.longitude} - ${it.vitesse}")
        }
        println("\n---\n")
    }
}