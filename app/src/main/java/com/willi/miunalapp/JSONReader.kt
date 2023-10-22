package com.willi.miunalapp

import android.content.Context
import android.content.res.AssetManager
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class JSONReader {
    // Llama a una función para cargar y parsear el JSON
    fun ListaJson(context: Context) {
        val empleados = cargarJsonDesdeAssets(context,"Empleados.json")

        // Accede a la lista de empleados y realiza operaciones
        for (empleado in empleados?.empleados ?: emptyList()) {
            val nombre = empleado.nombre
            val edad = empleado.edad
            val puesto = empleado.puesto

            // Haz lo que necesites con los datos de cada empleado
            val toast = Toast.makeText(context,"$nombre + $edad + $puesto",Toast.LENGTH_LONG )
            toast.show()
        }
    }

 fun cargarJsonDesdeAssets(context: Context, fileName: String): Empleados? {

    val assetManager: AssetManager = context.assets
    var inputStream: InputStream? = null

    try {
        inputStream = assetManager.open(fileName)
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        return gson.fromJson(reader, Empleados::class.java)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }

    return null
}
}
