package com.willi.miunalapp

import android.content.Context
import android.content.res.AssetManager
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class JSONEdificioReader {
    // Llama a una función para cargar y parsear el JSON
    fun ListaJson(context: Context) {
        val Json = cargarJsonDesdeAssets(context, "JsonFinal.json")

        // Accede a la lista de empleados y realiza operaciones
        for (Edificio in Json?.Edificios ?: emptyList()) {
            val nombre = Edificio.nombre
            val Numero = Edificio.Numero
            val Salones = Edificio.Salones

            // Haz lo que necesites con los datos de cada empleado
            val toast = Toast.makeText(context, "$nombre + $Numero + $Salones", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    fun nombreYsalones(context: Context, edificio: String): MutableList<String> {
        val Json = cargarJsonDesdeAssets(context, "JsonFinal.json")
        var ListaInfo = mutableListOf<String>()
        for (Info in Json?.Edificios ?: emptyList()) {
            if (Info.nombre == edificio) {
                ListaInfo.add(Info.nombre)
                ListaInfo.add(Info.descripcion)
                var salones = Info.Salones
                val pisos = salones.count { it == '*' }
                for (a in 0 until pisos) {
                    var primer = salones.indexOfFirst { it == '*' }
                    ListaInfo.add(salones.substring(0, primer))

                    salones = salones.substring(primer + 1)
                }
            }
        }
        if (ListaInfo == emptyList<String>()) {
            ListaInfo.add("No encontrado")
        }
        return ListaInfo
    }

    fun listaEdificios(context: Context): MutableList<String> {
        val Json = cargarJsonDesdeAssets(context, "JsonFinal.json")
        var ListaEdificios = mutableListOf<String>()
        for (Edificio in Json?.Edificios ?: emptyList()) {
            ListaEdificios.add(Edificio.nombre)
        }
        return ListaEdificios
    }

    fun BuscarInfo(context: Context, edificio: String): MutableList<String> {
        val Json = cargarJsonDesdeAssets(context, "JsonFinal.json")
        var Informacion = mutableListOf<String>()
        for (Info in Json?.Edificios ?: emptyList()) {
            if (Info.nombre == edificio) {
                Informacion.add(Info.nombre)
                Informacion.add(Info.Numero.toString())
                Informacion.add(Info.Salones)
            }
        }
        return Informacion
    }

    fun cargarJsonDesdeAssets(context: Context, fileName: String): JsonFinal? {
// me permite abrir la carpeta assets
        val assetManager: AssetManager = context.assets
        //me pide que le pase un archivo
        var inputStream: InputStream? = null
        try {
            inputStream = assetManager.open(fileName)
            val reader = InputStreamReader(inputStream)
            val gson = Gson()
            return gson.fromJson(reader, JsonFinal::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }

        return null
    }
}