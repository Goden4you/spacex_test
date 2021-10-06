package com.example.spacextest.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacextest.R
import com.example.spacextest.entities.SuccessedMission

class MissionsAdapter(
    private val context: Context,
) : RecyclerView.Adapter<MissionsAdapter.ViewHolder>() {

    val missions: ArrayList<SuccessedMission> = arrayListOf()

    override fun getItemCount(): Int {
        return missions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.mission_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        missions[position].also { mission ->
            mission.small?.let { imageUrl ->
                Glide.with(context)
                    .load(imageUrl)
                    .into(holder.tvSmallMissionImage)
                holder.tvMissionName.text = mission.name
                holder.tvMissionStartDate.text = "Start date: ${mission.date_utc}"
                if (!mission.details.isNullOrEmpty())
                holder.tvMissionDetails.text = "Details: ${mission.details}"
            } ?: kotlin.run {
                Glide.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .into(holder.tvSmallMissionImage)
                holder.tvMissionName.text = mission.name
                holder.tvMissionStartDate.text = "Start date: ${mission.date_utc}"
                if (!mission.details.isNullOrEmpty())
                holder.tvMissionDetails.text = "Details: ${mission.details}"
            }
        }
    }

    fun submitUpdate(update: List<SuccessedMission>) {
        val callback = MissionsDiffCallback(missions, update)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)

        missions.clear()
        missions.addAll(update)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMissionName: TextView = view.findViewById(R.id.tvName)
        val tvMissionStartDate: TextView = view.findViewById(R.id.tvStartDate)
        val tvMissionDetails: TextView = view.findViewById(R.id.tvDetails)
        val tvSmallMissionImage: ImageView = view.findViewById(R.id.ivSmallImage)
    }

    class MissionsDiffCallback(
        private val oldMissions: List<SuccessedMission>,
        private val newMissions: List<SuccessedMission>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldMissions.size
        }

        override fun getNewListSize(): Int {
            return newMissions.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMissions[oldItemPosition].id == newMissions[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMissions[oldItemPosition].name == newMissions[newItemPosition].name
        }
    }
}