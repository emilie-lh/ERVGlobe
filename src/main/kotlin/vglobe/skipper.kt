package vglobe

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

/*
    * Cette classe représente un skipper.
    * Elle contient les informations suivantes :
    * - date : la date du classement
    * - rank : le rang du skipper
    * - nation : la nation du skipper
    * - nom : le nom du skipper
    * - bateau : le nom du bateau du skipper
    * - latitude : la latitude du skipper
    * - longitude : la longitude du skipper
    * - vitesse : la vitesse du skipper
    * - distanceToFinish : la distance restante pour finir la course
    * - distanceToLeader : la distance par rapport au leader
 */
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

/*
    * Cette fonction sert à remplacer les caractères spéciaux dans les données.
    * Elle retourne une chaîne de caractères sans les caractères spéciaux.
    * Cette fonction a été créée au cas où les données contiennent des caractères spéciaux.
 */
fun remplacerSlash(value: String?): String {
    return value?.replace("\n", "")?.trim() ?: ""
}

/*
    * Cette fonction sert à extraire les données des fichiers JSON issues d'un dossier.
    * Elle retourne un Map<String, List<skipper>> où la clé
    * est la date du classement et la valeur est la liste des skippers.
 */
fun extraireDonnees(dossierPath: String): Map<String, List<skipper>> {
    val gson = Gson()

    // Récupérer tous les fichiers JSON dans le dossier
    val fichiersJson = File(dossierPath).listFiles { file ->
        file.extension == "json"
    } ?: emptyArray()

    // Si aucun fichier n'est trouvé, afficher un message et retourner une map vide
    if (fichiersJson.isEmpty()) {
        println("Aucun fichier JSON trouvé dans le dossier : $dossierPath")
        return emptyMap()
    }

    // Stocker les classements par date
    val classementsSkipperDate = mutableMapOf<String, List<skipper>>()

    // Parcourir les fichiers JSON
    fichiersJson.forEach { fichier ->
        val filePath = fichier.absolutePath
        val jsonFile = fichier.readText()

        // Extraction de la date du fichier
        val date = Regex("""\d{8}""").find(filePath)?.value?.let {
            "${it.substring(0, 4)}-${it.substring(4, 6)}-${it.substring(6, 8)}"
        } ?: "unknown_date"

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
        }.filter { it.rank > 0 }

        classementsSkipperDate[date] = skippers
    }

    return classementsSkipperDate
}