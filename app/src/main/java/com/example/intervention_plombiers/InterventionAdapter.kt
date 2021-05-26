package com.example.intervention_plombiers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class InterventionAdapter(val interventions: List<Intervention>, val itemClickListener: MainActivity )
    : RecyclerView.Adapter<InterventionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById(R.id.card_view) as CardView
        val nomView = cardView.findViewById(R.id.constraint) as TextView
        val dateView = cardView.findViewById(R.id.date) as TextView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_intervention, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val intervention = interventions[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.nomView.text = intervention.nom
        holder.dateView.text = intervention.date

    }

    override fun getItemCount(): Int {
        return interventions.size
    }
}