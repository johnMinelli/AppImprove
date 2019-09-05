package com.example.myapplication.sleepypck.sleeptracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemSleepNightBinding
import com.example.myapplication.sleepypck.convertDurationToFormatted
import com.example.myapplication.sleepypck.convertNumericQualityToString
import com.example.myapplication.sleepypck.database.SleepNight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//(1) fai arrivare il clicklistener inizializzato dal fragment e lo passi durante il binding onBindViewHolder
//allo specifico holder tramite una funzione creata nella classe dell'holder (+bello)
//(2) fai arrivare il clicklistener inizializzato dal fragment e lo passi al costruttore durante la creazione onCreateViewHolder
//dello specifico holder    <-- in questo caso non gli passo solo il clicklistener al costruttore ma l'intero builder specifico per il tipo di ItemList (+versatile/riutilizza l'adapter)


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1
//(1)pre diffUtil
//class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
//(2)(3)pre header
//class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    //(1)
//    var data = listOf<SleepNight>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    override fun getItemCount() = data.size*/

    //////////////////////////////////////////////////////////////
    //(2)(3) to use DiffUtil cambiamo il ritorno da RecyclerView.Adapter a ListAdapter
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        //no more binding here but cliListener binding
//        //val item = getItem(position)
//        //holder.bind(item)
//        holder.bind(getItem(position)!!, clickListener)
//    }

    //(4)
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }

    //////////////////////////////////////////////////////////////

    //(1)
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {       <-- nota come cambia cosa restituisco
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
//        return TextItemViewHolder(view)
//    }
    //(2)
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {               <-- nota come cambia cosa restituisco
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
//        return ViewHolder(view)
//    }
    //(3) right one
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {               <-- nota come cambia cosa restituisco
//        return ViewHolder.from(parent)
//    }

    //(4) header add
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    //////////////////////////////////////////////////////////////

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                //(1)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                //(2)
                //val binding = DataBindingUtil.inflate<ListItemSleepNightBinding>(layoutInflater,R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
//Custom type for header add
//class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
//    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
//        return oldItem.nightId == newItem.nightId
//    }
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

//    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

//header add

sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}
