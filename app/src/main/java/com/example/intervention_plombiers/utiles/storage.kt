package com.example.intervention_plombiers.utiles
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.intervention_plombiers.Intervention
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG="storage"
fun persistTache(context:Context, intervention: Intervention){
    if(TextUtils.isEmpty(intervention.filename)){
        intervention.filename=UUID.randomUUID().toString()+".tache"

    }
    val fileOutput=context.openFileOutput(intervention.filename,Context.MODE_PRIVATE)//mode d'acces pour le fichier acces seulemnt a l application
   val  outputStream=ObjectOutputStream(fileOutput)
    outputStream.writeObject(intervention)
    outputStream.close()
}
fun loadTache(context: Context):MutableList<Intervention>{
    val taches= mutableListOf<Intervention>()
    val tacheDir=context.filesDir
    for (filename in tacheDir.list()){
        val tache= loadTache(context, filename)
        Log.i(TAG,"Tache chargee $tache")
        taches.add(tache)


    }
    return taches
}

fun deleteTache(context: Context, intervention: Intervention){
    context.deleteFile(intervention.filename)
}

private fun loadTache(context:Context,filename:String):Intervention{
    val fileIntput=context.openFileInput(filename)//mode d'acces pour le fichier acces seulemnt a l application
    val  inputStream=ObjectInputStream(fileIntput)
    val tache=inputStream.readObject() as Intervention
    inputStream.close()
    return tache

}