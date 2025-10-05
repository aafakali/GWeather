package com.demo.gweather.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.gweather.data.local.WeatherEntity
import com.demo.gweather.databinding.ItemHistoryBinding
import java.text.DateFormat
import java.util.Date

class HistoryAdapter : ListAdapter<WeatherEntity, HistoryAdapter.HistoryViewHolder>(
    DiffCallback()
) {
    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherEntity) {
            binding.tvCity.text = "${item.city}, ${item.country}"
            binding.tvTemp.text = "${item.temperature.toInt()}°C"
            binding.tvDesc.text = item.description
            binding.tvTime.text = DateFormat.getDateTimeInstance().format(Date(item.timestamp))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity) =
            oldItem == newItem
    }
}
