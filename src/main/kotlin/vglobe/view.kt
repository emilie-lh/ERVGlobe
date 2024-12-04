package vglobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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


@Composable
fun HeaderWithImage() {
    val image: ImageBitmap = useResource("images/departVG.png") { loadImageBitmap(it) }
   Column(
       modifier = Modifier.fillMaxSize()
   ) {
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .aspectRatio(image.width.toFloat() / image.height.toFloat())
       ) {

           Image(
               bitmap = image,
               contentDescription = "Header Image",
               contentScale = ContentScale.Crop,
               modifier = Modifier.fillMaxWidth()
                   .height(300.dp)
           )
           Text(
               text = "Vendée Globe Classement",
               color = Color.White,
               fontSize = 20.sp,
               textAlign = TextAlign.Center,
               modifier = Modifier
                   .padding(top = 16.dp)
                   .align(Alignment.Center)
           )
       }

       Spacer(modifier = Modifier.height(16.dp)) // Espacement entre les blocs

       // Section grisée contenant le titre
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .background(Color.LightGray)
               .padding(16.dp) // Padding interne de la Box
       ) {
           Column(modifier = Modifier.fillMaxWidth()) {
               Text(
                   text = "Classement Vendée Globe 2024",
                   color = Color.Black,
                   fontSize = 20.sp,
                   fontWeight = FontWeight.Bold,
                   textAlign = TextAlign.Center,
                   modifier = Modifier.fillMaxWidth() // Centré horizontalement
               )
           }
       }
   }
}