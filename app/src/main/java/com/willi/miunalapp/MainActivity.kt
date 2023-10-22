package com.willi.miunalapp


import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Range
import android.util.Size
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import java.lang.Math.abs
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composer
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin

import androidx.compose.ui.platform.LocalDensity

import com.willi.miunalapp.ui.theme.MiUnalAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.logging.Logger.global
import kotlin.math.roundToInt


//var GlobalImageID=

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val width = this.resources.displayMetrics.widthPixels.toFloat()
            val height = this.resources.displayMetrics.heightPixels.toFloat()
            setContent {
                MiUnalAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xff3b0080)
                    ) {

                        principal(this)

                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun principal(context:Context) {
    val a = JSONReader()
    val buttonColor = remember { mutableStateListOf(Color.White, Color.White, Color.White,Color.White,Color.White) }
    val map=painterResource(id = R.drawable.mapa)
    var list= remember{ mutableStateListOf(map)
    }
    var listtexto: MutableList<String> by remember {
        mutableStateOf(mutableListOf("Cyt","Viejo","Aulas","Laboratorios","???"))
    }
    val density = LocalDensity.current
    val edificios =painterResource(R.drawable.edificios)
    val banos = painterResource(R.drawable.banos)
    val restaurante = painterResource(R.drawable.restaurante)
    val tienda = painterResource(R.drawable.tienda)


    var visible by remember { mutableStateOf(true) }
    var visibleMenu by remember { mutableStateOf(false) }
    val grades by animateFloatAsState(if (visible) 0f else 180f)
   fun CambiarVisibilidad(){
        visible = !visible
       a.ListaJson(context)
    }
    fun CambiarVisibilidadDeMenu(){
        visibleMenu = !visibleMenu
    }
    fun Sobreponer(painter:Painter=map){
        list.clear()
        list.add(map)
        if (painter!=map){ list.add(painter) }

    }

    Box(modifier = Modifier.padding(20.dp)) {
        ZoomableComposable(list)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(bottom = 15.dp),
        ) {

            IconButton(
                onClick = {CambiarVisibilidad();Sobreponer();visibleMenu=false;
                    for(i in 0..buttonColor.size-1){
                        buttonColor[i]=Color.White
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




            AnimatedVisibility(visible = visible,enter = fadeIn(
                animationSpec = keyframes {
                    this.durationMillis = 500 }, initialAlpha = 0.2f)+ slideInVertically {
                with(density) { -20.dp.roundToPx() }
            }+ scaleIn( transformOrigin = TransformOrigin(0.8f, 0f))
                 ,exit=
                fadeOut(animationSpec = keyframes {
                this.durationMillis = 500 })+slideOutVertically{
                    with(density) { -20.dp.roundToPx() }} + scaleOut( transformOrigin = TransformOrigin(0.8f, 0f)))

            {
                LazyColumn() {
                    item {
                        BotonRapido(image = edificios) { CambiarVisibilidad(); Sobreponer(edificios);CambiarVisibilidadDeMenu()}
                        BotonRapido(image = banos){ CambiarVisibilidad() ;Sobreponer(banos);CambiarVisibilidadDeMenu()}
                        BotonRapido(image = restaurante){ CambiarVisibilidad();Sobreponer(restaurante);CambiarVisibilidadDeMenu()}
                        BotonRapido(image = tienda,){ CambiarVisibilidad() ;Sobreponer(tienda);CambiarVisibilidadDeMenu()}

                    }
            }

            }
            AnimatedVisibility(visible =(visibleMenu) )
            {
                LazyColumn(modifier=Modifier.padding(start = 20.dp)){
                    item {
                            for (e in 0..listtexto.size-1){
                        TextButton( shape = CircleShape,modifier= Modifier
                            .padding(2.dp)
                            .border(width = 2.dp, color = Color(0xff3b0080), shape = CircleShape)
                            .background(
                                buttonColor[e],
                                CircleShape
                            )
                            .width(250.dp)
                            .height(50.dp),
                            onClick = {Sobreponer();
                                if(buttonColor[e]== Color.White) {
                                    buttonColor[e] = Color.Blue
                                }
                                for (i in 0..listtexto.size-1){
                                    if (i != e){
                                        buttonColor[i] = Color.White
                                    }

                                }
                            }
                          ){
                            Text(text = listtexto[e]) }
                    }}
                }

            }
        }


    }

}


@Composable
fun BotonRapido(image: Painter,a:()->Unit){
//val image=ImageBitmap.imageResource(R.drawable.flecha)
    val modific = Modifier.size(100.dp)
    LargeFloatingActionButton(
        onClick = a,
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

@Composable
fun ZoomableComposable(list: MutableList<Painter>) {
    // Reacting to state changes is the core behavior of Compose.
    // We use the state composable that is used for holding a
    // state value in this composable for representing the current
    // value scale(for zooming in the image)
    // & translation(for panning across the image).
    // Any composable that reads the value of counter will
    // be recomposed any time the value changes.
    //val tamanox = 700F//tamaño de imagen en x, para que ocupe lo nesesario
    var scale by remember { mutableStateOf(2.2F) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    // In the example below, we make the Column composable zoomable
    // by leveraging the Modifier.pointerInput modifier
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        do {


                            val event = awaitPointerEvent()
                            scale *= event.calculateZoom()
                            val offset = event.calculatePan()
                            if (scale > 1 /*&&offsetX<800F&& offsetX >-800F*/) {
                                if (abs(offset.x) > abs(offset.y)) {
                                    offsetX += offset.x
                                }
                                if ((abs(offset.x) < abs(offset.y))) {
                                    offsetY += offset.y
                                }
                            }
                            if (abs(offsetX) >= 10F*((scale-2.2F)*35)) {
                                if (offsetX < 0) {
                                    offsetX = -10f*(scale-2.2F)*35
                                } else {
                                    offsetX = 10F*((scale-2.2F)*35)
                                }
                            }
                            if (abs(offsetY) >= 10F*((scale-2.2F)*35)) {
                                if (offsetY < 0) {
                                    offsetY = -10f*(scale-2.2F)*35
                                } else {
                                    offsetY = 10F*((scale-2.2F)*35)
                                }
                            }

                            if (scale < 2.2F) {
                                scale = (2.2F)
                                offsetX = 0F
                                offsetY = 0F
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
            }
    ) {
        // painterResource method loads an image resource asynchronously

        //me permite sobreponer imagenes
        // We use the graphicsLayer modifier to modify the scale & translation
        // of the image.
        // This is read from the state properties that we created above.
        //  textos(scale)
        // textos()
        //textos()
        Box() {
            for (i in list){
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY,

                        ),
                painter = i,
                contentDescription = "androids launcher default launcher background image"

            )}

        }
    }


}
