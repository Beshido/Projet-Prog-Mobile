package fr.lejeune.banane.projettraduction

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.lejeune.banane.projettraduction.databinding.DictLayoutBinding

class MyRecycleAdapter(val liste: MutableList<Dict>) : RecyclerView.Adapter<MyRecycleAdapter.ViewHolder>() {

    var selectedItem: Dict? = null
    var selectedView: CardView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DictLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        val viewHolder = ViewHolder(binding)
        viewHolder.binding.nomDict.setOnClickListener {
            selectedView?.setBackgroundColor(0)

            selectedItem = viewHolder.dict
            selectedView = viewHolder.binding.cardView

            selectedView!!.setBackgroundColor(Color.parseColor("#FFAEC9"))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("HEELL")
        holder.dict = liste[position]
        holder.binding.nomDict.text = holder.dict.url
        println(holder.dict.language_source)
        println(holder.dict.language_dest)

    }

    override fun getItemCount(): Int = liste.size


    fun setListContent(data: List<Dict>) {
        liste.clear()
        liste.addAll(data)
    }

    fun getSelected(): Dict? {
        return selectedItem
    }

    class ViewHolder(val binding: DictLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var dict: Dict
    }
}