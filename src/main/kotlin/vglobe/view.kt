package vglobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.useResource
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward


@Composable
fun HeaderWithImage(classementParDate: Map<String, List<skipper>>) {
    val image: ImageBitmap = useResource("images/departVG.png") { loadImageBitmap(it) }

    val allDate = classementParDate.keys.sorted()
    val (currentDate, navigate) = dateNavigation(allDate)

    Column(modifier = Modifier.fillMaxSize()) {
        // Image du haut avec superposition de texte
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 1f)
        ) {
            Image(
                bitmap = image,
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )
            Text(
                text = "Vendée Globe Classement",
                color = Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Section grisée modernisée
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(Color.LightGray, Color.Gray)
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Classement Vendée Globe 2024",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Classement du $currentDate",
                    color = Color.DarkGray,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Navigation modernisée
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Button(
                        onClick = { navigate(false) },
                        enabled = allDate.indexOf(currentDate) > 0,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Jour Précédent"
                        )
                        Text(
                            text = "Précédent",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Button(
                        onClick = { navigate(true) },
                        enabled = allDate.indexOf(currentDate) < allDate.size - 1,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "Suivant",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Jour Suivant"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Liste des skippers pour le jour sélectionné
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            val skippers = classementParDate[currentDate] ?: emptyList()
            items(skippers) { skipper ->
                detailSkipper(skipper)
            }
        }
    }
}


@Composable
fun detailSkipper(skipper: skipper) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                if (skipper.rank in 1..3) {
                    val trophyIcon = when (skipper.rank) {
                        1 -> useResource("images/trophe-or.png") { loadImageBitmap(it) }
                        2 -> useResource("images/troper-argent.png") { loadImageBitmap(it) }
                        3 -> useResource("images/tropher-bronze.png") { loadImageBitmap(it) }
                        else -> null
                    }

                    trophyIcon?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Trophée",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(32.dp)) // Alignement pour les skippers sans trophée
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${skipper.rank}. ${skipper.nom} (${skipper.nation})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Bateau : ${skipper.bateau}", color = Color.Gray)
            Text("Position : ${skipper.latitude} / ${skipper.longitude} | Vitesse : ${skipper.vitesse}", color = Color.Gray)
            Text("Distance au leader : ${skipper.distanceToLeader} | Distance à l'arrivée : ${skipper.distanceToFinish}" , color = Color.Gray)
        }
    }
}

