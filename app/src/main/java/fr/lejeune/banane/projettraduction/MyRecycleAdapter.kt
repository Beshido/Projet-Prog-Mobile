package fr.lejeune.banane.projettraduction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.lejeune.banane.projettraduction.databinding.DictLayoutBinding

class MyRecycleAdapter(val liste: MutableList<Dict>) : RecyclerView.Adapter<MyRecycleAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DictLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.nomDict.text = liste[position].url
    }

    override fun getItemCount(): Int = liste.size

    class ViewHolder(val binding: DictLayoutBinding): RecyclerView.ViewHolder(binding.root)

    fun setListContent(data: List<Dict>) {
        liste.addAll(data)
    }
}