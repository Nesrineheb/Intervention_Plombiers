package com.example.intervention_plombiers
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class InterventionDetailActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    companion object {
        val REQUEST_EDIT_Intervention = 1
        val EXTRA_Intervention = "intervention"
        val EXTRA_Intervention_INDEX = "interventionIndex"
        val ACTION_DELETE = "actions.ACTION_DELETE"
        val ACTION_SAVE= "actions.ACTION_SAVE"
    }


    lateinit var intervention: Intervention
    var interventionIndex:Int = -1
    lateinit var nomView: TextView
    lateinit var typeView: TextView
    lateinit var numeroView: TextView
    lateinit var dateView: TextView
    var day =12
    var month =5
    var year =2021
    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")




    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intervention_detail)

        intervention = intent.getParcelableExtra<Intervention>(EXTRA_Intervention)
        interventionIndex = intent.getIntExtra(EXTRA_Intervention_INDEX, -1)




val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        nomView = findViewById(R.id.nom) as TextView
        dateView = findViewById(R.id.date_view) as TextView
        typeView = findViewById(R.id.type) as TextView
        numeroView = findViewById(R.id.numero) as TextView
        nomView.text = intervention.nom
        dateView.text = intervention.date
        typeView.text=intervention.type
        numeroView.text=intervention.numero
        val dateButton= findViewById(R.id.button) as Button
        dateButton.setOnClickListener{


            getDateCalendar()
            DatePickerDialog(this,this,year,month,day).show()
        }

    }







    private fun getDateCalendar(){
        val cal :Calendar= Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_intevention_detail,menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_save ->{
                save()
                return true  }
            R.id.action_delete ->{
                Log.i("meeeesage debug","delete action")
                showConfirmDeleteDialogue()
                return true
            }
            else ->{ return super.onOptionsItemSelected(item) }
           }
          }

    private fun showConfirmDeleteDialogue() {
        val confirmFragment = ConfirmDeleteDialogueFragment()
        confirmFragment.listener = object :ConfirmDeleteDialogueFragment.ConfirmDeleteListener {
            override fun onDialogPositiveClick() {
                delete()
            }
            override fun onDialogNegativeClick() {

            } }
        confirmFragment.show(fragmentManager, "delete confirm")
    }

    fun save() {
        intervention.nom= nomView.text.toString()
        intervention.type= typeView.text.toString()
        intervention.numero= numeroView.text.toString()


        intervention.date = LocalDate.of(year,month+1,day).format(formatter).toString()
        intent = Intent(ACTION_SAVE)
        intent.putExtra(EXTRA_Intervention, intervention as Parcelable)
        intent.putExtra(EXTRA_Intervention_INDEX, interventionIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun delete() {
        intent = Intent(ACTION_DELETE)
        intent.putExtra(EXTRA_Intervention_INDEX, interventionIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        day=p3
        month=p2
        year=p1
        dateView.text = LocalDate.of(year,month+1,day).format(formatter).toString()



        println("${year} -${month} -${day}")

    }

}
