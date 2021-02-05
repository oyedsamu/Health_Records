package com.decadevs.healthrecords.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decadevs.healthrecords.databinding.PatientDetailsTemplateBinding
import com.decadevs.healthrecords.model.response.PatientAllRecordsResponse
import com.decadevs.healthrecords.model.response.PatientRecordDataResponse

class PatientDetailsRVAdapter(private var patientDetails: ArrayList<PatientRecordDataResponse>, var listener: OnItemClick) :
    RecyclerView.Adapter<PatientDetailsRVAdapter.PatientDetailsViewHolder>() {

    inner class PatientDetailsViewHolder(private val binding: PatientDetailsTemplateBinding):
        RecyclerView.ViewHolder(binding.root){

        private lateinit var pDetails: PatientRecordDataResponse

        fun bind(patientDetails: PatientRecordDataResponse, action: OnItemClick) {
            this.pDetails = patientDetails
            binding.apply {
                binding.apply {
                    practitioner.text = patientDetails.doctorOnCall
                    date.text = patientDetails.createdAt
                    diagnosisDetailsTv.text = patientDetails.doctorNotes
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
    fun onItemClick(item: PatientRecordDataResponse, position: Int)
}