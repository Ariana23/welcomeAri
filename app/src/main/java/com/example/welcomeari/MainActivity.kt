package com.example.welcomeari

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.welcomeari.datastore.StoreUserName
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentView()
        }
    }


    @Composable
    private fun ContentView() {
    val pagerState = rememberPagerState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ){
        PagerContent(pagerState = pagerState)
    }
    }

    //SEGUNDO PASO, CREO HORIZONTAL PAGE
    // TENGO QUE IMPORTAR LAS DEPENDENCIAS

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun PagerContent(pagerState: PagerState){
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            pageCount = 3,
            state = pagerState
            ) {pager ->

                when (pager){
                    0 -> {
                        ViewOne()
                    }
                    1 -> {
                      ViewTwo()
                  }
                }

        }
    }
    }
    // PRIMERO PASO, CREO LAS FUNCIONES DE MIS SCREENS, boton paasar a la segunda screen y boton confirmar nombre y va al home. sacar gesto de cambio de pantalla,
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ViewOne() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_name_v2),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
            )

            Text(
                text = "Bienvenido",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 40.sp
            )
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ViewTwo() {




        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_name_v2),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
            )

            Text(
                text = "Trip Out, Aplicación gratuita que nos permite planificar y organizar viajes, pudiendo guardar todos los detalles importantes y permitiendo al usuario realizar múltiples acciones",
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))

           val context = LocalContext.current

            val scope = rememberCoroutineScope()

            val dataStore = StoreUserName(context)

            val savedName = dataStore.getName.collectAsState(initial = "")


            var nombreValue by remember {
               mutableStateOf("")
           }
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 80.dp),
                value = nombreValue,
                label = { Text("Ingrese Nombre")},
                onValueChange = { newValue ->
                    nombreValue = newValue

                })
            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 14.dp, end = 14.dp),
                onClick =  {

                           scope.launch {
                               dataStore.saveName(nombreValue)
                           }

            },
            ){
                Text(text = "Ingresar",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
// LO MUESTRO ACA PERO DEBERI IR EN EL HOME
            Text(text = savedName.value!!,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}