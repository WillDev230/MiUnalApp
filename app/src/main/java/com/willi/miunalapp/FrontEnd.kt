package com.willi.miunalapp

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Math.abs

//se crea una clase llamada FrontEnd en la cual usaremos la libreria JetpackCompose para realizar la parte visual de nuestra app

//JetpackCompose en grandes rasgos es una libreria en la cual en vez de usar etiquetas xml como se usa comunmente en android nativo
// usamos funciones compose
class FrontEnd {
    //creamos los atributos de la clase (Variables)
    var height = 0
    var widht = 0

    //creamos una funcion la cual asigne los atributos a partir de parametros
    fun Tamano(altura: Int, anchura: Int) {
        height = altura
        widht = anchura
    }

    //debido a que en el codigo se hace uso de una funcion experimental de animacion se debe usar la etiqueta  @OptIn(ExperimentalAnimationApi::class)
    @OptIn(ExperimentalAnimationApi::class)
    // asi mismo debido a que usaremos funciones de jetpackcompose en nuestra nueva funcion, usaremos la etiqueta @Composable
    @Composable
    fun Principal(context: Context) {
        //se inizializan variables y valores que seran usados
        val edificiosj = JSONEdificioReader().listaEdificios(context)
        val map = painterResource(id = R.drawable.mapa )
        var list = remember { mutableStateListOf (map) }
        var listtexto = remember { edificiosj }
        //forma antigua de hacer una lista a partir de el tamaño de otra lista (posiblemente cambie loa manera)
        var buttonColor = remember { mutableStateListOf<Color>(Color.White) }
        for (x in 0..listtexto.size - 2) {
            buttonColor.add(Color.White)
        }

        //mas valores y variables
        val density = LocalDensity.current
        val edificios = painterResource(R.drawable.edificios)
        val banos = painterResource(R.drawable.banos)
        val restaurante = painterResource(R.drawable.restaurante)
        val banosubicacion = painterResource(R.drawable.banosubicacion)
        val cafeteriaubic = painterResource(R.drawable.cafeteriaubic)
        var edificioSelec by remember { mutableStateOf("") }


        var visible by remember { mutableStateOf(true) }
        var cardVisibility by remember { mutableStateOf(false) }
        var visibleMenu by remember { mutableStateOf(false) }

        val grades by animateFloatAsState(if (visible) 0f else 180f)

        //funciones que cambian el valor de las variables bolleanas que determiann la visivilidad de objetos
        fun CambiarVisibilidadCard() {
            cardVisibility = !cardVisibility
        }

        fun CambiarVisibilidad() {
            visible = !visible
        }

        fun CambiarVisibilidadDeMenu() {
            visibleMenu = !visibleMenu
        }

        //funcion que me crea y limpia la lista de imagenes del ZoomableComposable
        fun Sobreponer(painter: Painter = map) {
            list.clear()
            list.add(map)
            if (painter != map) {
                list.add(painter)
            }
        }
        //funcion que me crea la info de los edificios
        @Composable
        fun DataCard(context: Context, edificio: String) {
            var edificio1 by remember {
                mutableStateOf(edificio)
            }
            val Elviejo = painterResource(id = R.drawable.elviejo)
            val postgrados = painterResource(id = R.drawable.postgrado)
            val Laboratorios = painterResource(id = R.drawable.laboratorios)
            val Aulas = painterResource(id = R.drawable.aulas)
            val Cyt = painterResource(id = R.drawable.cyt)
            val ElviejoN = painterResource(id = R.drawable.n401)
            val postgradosN = painterResource(id = R.drawable.n407)
            val LaboratoriosN = painterResource(id = R.drawable.n411)
            val AulasN = painterResource(id = R.drawable.n453)
            val CytN = painterResource(id = R.drawable.n454)
            val listaImage = listOf(ElviejoN, postgradosN, LaboratoriosN,AulasN,CytN)
            val Listafotos = listOf(Elviejo, postgrados, Laboratorios,Aulas,Cyt)


            //Se crea el objeto de la clase que me trae la informacion del Json
            val ajsonEdificioReader = JSONEdificioReader()
            val lista = ajsonEdificioReader.listaEdificios(context)
            var posicion = lista.indexOf(edificio1)
            Sobreponer(listaImage[posicion])
            //hace que cuando cambie la poscicion cambie la foto
            var Image by remember {
                mutableStateOf(Listafotos[posicion])
            }

            Card(
                modifier = Modifier
                    .size(400.dp, 600.dp)
                    .padding(start = 28.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(6.dp, color = Color(0xff3b0080))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //se usa unafuncion del objeto ajson... para obtener la informacion
                    var a = ajsonEdificioReader.nombreYsalones(context, edificio1)
                    item {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxSize()
                                .background(
                                    color = Color(0xff3b0080),
                                    shape = MaterialTheme.shapes.medium // Forma rectangular con esquinas no redondeadas
                                ), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .height(50.dp)
                                    .width(50.dp)
                                    .background(
                                        color = Color.White,
                                        shape = MaterialTheme.shapes.medium // Forma rectangular con esquinas no redondeadas
                                    )
                                    .clickable {
                                        //crea la logica para cambiar entre tarjetas
                                        if (posicion == 0) {
                                            edificio1 = lista[lista.size - 1]
                                            Sobreponer(listaImage[lista.size - 1])
                                            Image = Listafotos[lista.size - 1]
                                        } else {
                                            edificio1 = lista[posicion - 1]
                                            Sobreponer(listaImage[posicion - 1])
                                            Image = Listafotos[posicion - 1]
                                        }


                                    }, contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.flecha),
                                    contentDescription = "a", modifier = Modifier

                                        .rotate(-90F)
                                        .fillMaxSize(0.8f)
                                )
                            }
                            //texto del nombre
                            Text(
                                text = a[0],
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier

                                    .padding(12.dp)
                                    .width(180.dp),
                                fontSize = 30.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .height(50.dp)
                                    .width(50.dp)
                                    .background(
                                        color = Color.White,
                                        shape = MaterialTheme.shapes.medium // Forma rectangular con esquinas  redondeadas
                                    )
                                    .clickable {
                                        if (posicion == lista.size - 1) {
                                            edificio1 = lista[0]
                                            Sobreponer(listaImage[0])
                                            Image = Listafotos[0]

                                        } else {
                                            edificio1 = lista[posicion + 1]
                                            Sobreponer(listaImage[posicion + 1])
                                            Image = Listafotos[posicion + 1]

                                        }


                                    }, contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.flecha),
                                    contentDescription = "a", modifier = Modifier

                                        .rotate(90F)
                                        .fillMaxSize(0.8f)
                                )
                            }
                        }

                        Image(
                            painter = Image, contentDescription = "a",
                            modifier = Modifier
                                .padding(1.dp)
                                .height(200.dp)
                                .width(350.dp)
                        )
                        for (tex in 2 until a.size) {
                            //me crea textos segun cuantos pisos halla en el Json
                            Text(
                                text = "-" + a[tex], modifier = Modifier.padding(
                                    start = 40.dp, top = 5.dp, bottom =
                                    5.dp, end = 10.dp
                                )
                            )
                        }
                        //texto de la describcion
                        Text(
                            text = a[1], modifier = Modifier.padding(
                                start = 40.dp, top = 5.dp, bottom =
                                5.dp, end = 10.dp
                            )
                        )


                    }


                }

            }


        }
//se crea el logo/boton superior que dice MIUnal
        Box(modifier = Modifier.padding(20.dp)) {
            ZoomableComposable(list)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
            ) {

                IconButton(
                    onClick = {
                        CambiarVisibilidad();Sobreponer();visibleMenu = false;cardVisibility =
                        false
                        for (i in 0..buttonColor.size - 1) {
                            buttonColor[i] = Color.White
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .padding(start = 10.dp)
                        .background(Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logounal),
                        contentDescription = "a", modifier = Modifier.fillMaxWidth()
                    )
                    Image(
                        painter = painterResource(id = R.drawable.flecha),
                        contentDescription = "a", modifier = Modifier
                            .padding(top = 50.dp)
                            .rotate(grades)
                            .height(10.dp)
                    )
                }

                //se usa AnimatedVisibility para hacer que aparezcan o desaparezcan los botones segun una variable boleana
                AnimatedVisibility(visible = (visibleMenu))
                {
                    LazyColumn(modifier = Modifier.padding(start = 20.dp)) {
                        item {
                            for (e in 0..listtexto.size - 1) {
                                TextButton(shape = CircleShape, modifier = Modifier
                                    .padding(2.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xff3b0080),
                                        shape = CircleShape
                                    )
                                    .background(
                                        buttonColor[e],
                                        CircleShape
                                    )
                                    .width(250.dp)
                                    .height(50.dp),
                                    onClick = {
                                        edificioSelec = listtexto[e];CambiarVisibilidadCard()
                                        if (buttonColor[e] == Color.White) {
                                            buttonColor[e] = Color(0xff3b0080)
                                        }
                                        for (i in 0..listtexto.size - 1) {
                                            if (i != e) {
                                                buttonColor[i] = Color.White
                                            }

                                        }

                                    }

                                ) {

                                    Text(text = listtexto[e])
                                }
                            }
                        }
                    }

                }
                AnimatedVisibility(visible = cardVisibility) {
                    visibleMenu = false
                    DataCard(context = context, edificio = edificioSelec)

                }
                AnimatedVisibility(visible = visible, enter = fadeIn(
                    animationSpec = keyframes {
                        this.durationMillis = 500
                    }, initialAlpha = 0.2f
                ) + slideInVertically {
                    with(density) { -20.dp.roundToPx() }
                } + scaleIn(transformOrigin = TransformOrigin(0.8f, 0f)), exit =
                fadeOut(animationSpec = keyframes {
                    this.durationMillis = 500
                }) + slideOutVertically {
                    with(density) { -20.dp.roundToPx() }
                } + scaleOut(transformOrigin = TransformOrigin(0.8f, 0f)))

                {
                    LazyColumn() {
                        item {
                            BotonRapido(image = edificios) {
                                CambiarVisibilidad()
                                Sobreponer()
                                CambiarVisibilidadDeMenu()
                            }
                            BotonRapido(image = banos) { Sobreponer(banosubicacion) }
                            BotonRapido(image = restaurante) { Sobreponer(cafeteriaubic) }

                        }
                    }

                }

            }


        }


    }

    //Nueva funcion que nos permite el zoom que se puede hacer en el fondo a partir de una lista de iamgenes
    @Composable
    fun ZoomableComposable(list: MutableList<Painter>) {
//749 x 603 es el tamaño de la imagen
        val EscalaMinima = 1.3F
        var scale by remember { mutableStateOf((EscalaMinima)) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                //las siguientes 4 lineas de codigo solo dictan que el usuario realize algun gesto mientras tenga presionado la pantalla
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            //entra en un bucel DO-While el caul primero ejecuta el codigo y luego verifica si se cumple una condicion para volverlo a ejecutar
                            do {
                                val event = awaitPointerEvent()
                                scale *= event.calculateZoom()
                                val offset = event.calculatePan()
                                //Hace que el movimiento solo se pueda haceer en vertical u horizontal no ambos a la vez
                                if (scale > EscalaMinima) {
                                    if (abs(offset.x) > abs(offset.y)) {
                                        offsetX += offset.x
                                    }
                                    if ((abs(offset.x) < abs(offset.y))) {
                                        offsetY += offset.y
                                    }
                                }

                                //permite el movimiento horizontal
                                if (abs(offsetX) >= (scale * 603 - 1) / 5 * scale) {
                                    if (offsetX < 0) {
                                        offsetX = (-scale * 603 - 1) / 5 * scale
                                    } else {
                                        offsetX = (scale * 603 - 1) / 5 * scale
                                    }
                                }
                                //permite el movimiento vertical
                                if (abs(offsetY) >= (scale * 749 - 1) / 7 * scale) {
                                    if (offsetY < 0) {
                                        offsetY = -(scale * 749 - 1) / 7 * scale
                                    } else {
                                        offsetY = (scale * 749 - 1) / 7 * scale
                                    }
                                }

                                //hace que al escalar a un minimo quede centrado el mapa
                                if (scale < EscalaMinima) {
                                    scale = EscalaMinima
                                    offsetX = 0F
                                    offsetY = 0F
                                }
                                //hace que la escala maxima sea de x5
                                if (scale > 5F) {
                                    scale = (5F)

                                }
                                //esto sucedera siempre que se este haciendo un gesto en la pantalla
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
        ) {
            Box() {
                //se crea una imagen compuesta a partir de las imagenes de la lista pasada como parametro
                for (i in list) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                //se le asigna las variables que van variando en funcion a los gestos del usuario
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY,
                            ),
                        painter = i,
                        contentDescription = "Imagen"
                    )
                }
            }
        }
    }

    @Composable
    fun BotonRapido(image: Painter, hacer: () -> Unit) {
        val modific = Modifier.size(100.dp)
        LargeFloatingActionButton(
            onClick = hacer,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .padding(start = 50.dp)
                .size(80.dp)
                .padding(bottom = 5.dp)
                .shadow(
                    shape = CircleShape, spotColor = Color(0xff000000),
                    elevation = 30.dp,
                )
        ) {
            Icon(
                image, "Large a action button",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),

                )
        }
    }


}