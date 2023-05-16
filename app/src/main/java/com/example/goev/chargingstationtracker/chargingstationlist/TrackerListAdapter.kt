package com.example.goev.chargingstationtracker.chargingstationlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.R
import com.example.goev.chargingstationlist.TrackerFragmentDirections
import com.example.goev.database.ChargingStation

class TrackerListAdapter : RecyclerView.Adapter<TrackerListAdapter.MyViewHolder>() {

    private var chargingStationList = emptyList<ChargingStation>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.view_ev_station_name)
        val address: TextView = itemView.findViewById(R.id.ev_station_address)
        val trackerListItem: ViewGroup = itemView.findViewById(R.id.tracker_list_item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tracker_list_item, parent, false)
        )
    }

    override fun getItemCount() = chargingStationList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = chargingStationList[position]
        holder.name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.trackerListItem.setOnClickListener {
            navigateToViewStationFragment(currentItem, holder)
        }
    }

    private fun navigateToViewStationFragment(
        currentItem: ChargingStation, holder: MyViewHolder
    ) {
        val action =
            TrackerFragmentDirections.actionTrackerFragmentToViewStationFragment(currentItem.id)
        holder.itemView.findNavController().navigate(action)
    }

    // used to update recyclerview every time there is a data change
    @SuppressLint("NotifyDataSetChanged")
    fun setData(chargingStation: List<ChargingStation>) {
        this.chargingStationList = chargingStation
        notifyDataSetChanged()
    }

}