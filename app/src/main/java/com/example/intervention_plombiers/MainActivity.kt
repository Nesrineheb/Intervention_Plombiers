package com.example.intervention_plombiers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intervention_plombiers.utiles.loadIntervention
import com.example.intervention_plombiers.utiles.persistIntervention

//import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

    lateinit var interventions: MutableList<Intervention>
    lateinit var adapter: InterventionAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
       
        setContentView(R.layout.activity_intervention_list)

        interventions= loadIntervention(this)












        adapter=InterventionAdapter(interventions, this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)




        val recyclerView = findViewById(R.id.interventions_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        super.onCreate(savedInstanceState)

        val fab: View = findViewById(R.id.create_intervention_fab)
        fab.setOnClickListener {
            createNewIntervention()

        }
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            showDetailActivity(view.tag as Int)
            Log.i("messaqe","hello")
        }
        else {
            when (view.id) {
                R.id.create_intervention_fab -> createNewIntervention()
            }
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!=Activity.RESULT_OK ||  data==null){
            return
        }
        when (requestCode){
            InterventionDetailActivity.REQUEST_EDIT_Intervention -> processEditResult(data)
        }
    }


    private fun processEditResult(data: Intent) {
        val interventionIndex = data.getIntExtra(InterventionDetailActivity.EXTRA_Intervention_INDEX, -1)

        when(data.action) {
            InterventionDetailActivity.ACTION_SAVE-> {

                val intervention = data.getParcelableExtra<Intervention>(InterventionDetailActivity.EXTRA_Intervention)
                save(intervention, interventionIndex)
            }
            InterventionDetailActivity.ACTION_DELETE -> delete(interventionIndex)
        }

    }


    fun delete(interventionIndex: Int) {
        if (interventionIndex < 0) {
            return
        }
      val intervention= interventions.removeAt(interventionIndex)
        com.example.intervention_plombiers.utiles.deleteIntervention(this,intervention)
        adapter.notifyDataSetChanged()


    }



     fun save(intervention: Intervention, Index: Int) {
         persistIntervention(this,intervention)
         if (Index<0){
             interventions.add(0,intervention)
         }else{
             interventions[Index] = intervention
         }

        adapter.notifyDataSetChanged()
    }

    fun  createNewIntervention() {

        Log.i("message","hi creat")
       showDetailActivity(-1)
    }

    fun showDetailActivity(Index: Int) {
        val intervention = if (Index < 0) Intervention() else interventions[Index]

        val intent = Intent(this, InterventionDetailActivity::class.java)
        intent.putExtra(InterventionDetailActivity.EXTRA_Intervention, intervention as Parcelable)
        intent.putExtra(InterventionDetailActivity.EXTRA_Intervention_INDEX, Index)
        startActivityForResult(intent, InterventionDetailActivity.REQUEST_EDIT_Intervention)
    }

}
