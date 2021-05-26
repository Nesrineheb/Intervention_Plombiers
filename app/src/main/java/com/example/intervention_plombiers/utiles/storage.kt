package com.example.intervention_plombiers.utiles
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.intervention_plombiers.Intervention
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG="storage"
fun persistIntervention(context:Context, intervention: Intervention){
    if(TextUtils.isEmpty(intervention.filename)){
        intervention.filename=UUID.randomUUID().toString()+".intervention"

    }
    val fileOutput=context.openFileOutput(intervention.filename,Context.MODE_PRIVATE)//mode d'acces pour le fichier acces seulemnt a l application
   val  outputStream=ObjectOutputStream(fileOutput)
    outputStream.writeObject(intervention)
    outputStream.close()
}
fun loadIntervention(context: Context):MutableList<Intervention>{
    val interventions= mutableListOf<Intervention>()
    val interventionDir=context.filesDir
    for (filename in interventionDir.list()){
        val intervention= loadIntervention(context, filename)

        interventions.add(intervention)


    }
    return interventions
}

fun deleteIntervention(context: Context, intervention: Intervention){
    context.deleteFile(intervention.filename)
}

private fun loadIntervention(context:Context,filename:String):Intervention{
    val fileIntput=context.openFileInput(filename)//mode d'acces pour le fichier acces seulemnt a l application
    val  inputStream=ObjectInputStream(fileIntput)
    val intervention=inputStream.readObject() as Intervention
    inputStream.close()
    return intervention

}