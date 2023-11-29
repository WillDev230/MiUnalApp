package com.willi.miunalapp


import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.willi.miunalapp.ui.theme.MiUnalAppTheme



class MainActivity : ComponentActivity() {

//la Funcion onCreate es una funcion la cual se llama cada ves que se ejecuta la aplicacion gracias a la palabra clave override me
//permite sobreescribirla y es el lugar en el cual ejecutaremos nuestro codigo
override fun onCreate(savedInstanceState: Bundle?) {
        // la palabra "super" hace referencia a la clase padre de la clase actual a la cual se llama la funcion onCreate
        // y se le pasa el parametro savedInstanceState para que recuerde variables de la app en caso de recomposiciones (cambios de orientacion, etc)
        super.onCreate(savedInstanceState)
            //se crea el objeto frontEnd de la clase FrontEnd la cual es donde tenemos toda nuestra parte grafica
            val frontEnd:FrontEnd = FrontEnd()
        //se inicializan valores los caules nos pueden ser de utilidad
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        //llamamos en nuestro objeto una funcion la cual le asigna las variables inicializadas
        frontEnd.Tamano(height,width)
        // llamamos a la palabra setContent de la libreria jetpack compose la cual nos permitira usar funciones compose en nuestro codigo
            setContent {
                // llamamos el tema usado en la app (el cual normalmente son colores predefinidos)
                MiUnalAppTheme {
                    //surface es una funcion la cual me crea una superficie la cual se usara como fondo
                    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xff3b0080))
                    {
                        //se llama a la funcion donde esta toda la parte visible del la app
                        frontEnd.Principal(this)
                    }
                }
            }
        }
    }

