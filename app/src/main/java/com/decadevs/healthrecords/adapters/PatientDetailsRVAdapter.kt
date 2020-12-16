package com.decadevs.healthrecords.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decadevs.healthrecords.data.PatientDetails
import com.decadevs.healthrecords.databinding.PatientDetailsTemplateBinding

class PatientDetailsRVAdapter(private var patientDetails: List<PatientDetails>, var listener: OnItemClick) :
    RecyclerView.Adapter<PatientDetailsRVAdapter.PatientDetailsViewHolder>() {

    inner class PatientDetailsViewHolder(private val binding: PatientDetailsTemplateBinding):
        RecyclerView.ViewHolder(binding.root){

        private lateinit var pDetails: PatientDetails

        fun bind(patientDetails: PatientDetails, action: OnItemClick) {
            this.pDetails = patientDetails
            binding.apply {
                binding.apply {
                    practitioner.text = patientDetails.practitioner
                    date.text = patientDetails.date
                    details.text = patientDetails.details
                }
            }

            itemView.setOnClickListener {
                action.onItemClick(patientDetails, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientDetailsViewHolder {
        val binding = PatientDetailsTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientDetailsViewHolder, position: Int) {
        val patientDetails = patientDetails[position]
        holder.bind(patientDetails, listener)
    }

    override fun getItemCount(): Int {
        return patientDetails.size
    }
}

interface OnItemClick {
    fun onItemClick(item: PatientDetails, position: Int)
}