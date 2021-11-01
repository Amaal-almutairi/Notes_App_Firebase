package com.example.notesappfirebase.MyAdApter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfirebase.MainActivity
import com.example.notesappfirebase.Notes
import com.example.notesappfirebase.databinding.ItemRowBinding



class myadap (val activity:MainActivity, var Notes:List<Notes>): RecyclerView.Adapter<myadap.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            (ItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        )


    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val NOTE = Notes[position]
        holder.binding.apply {
            textv.text = NOTE.Note
            imgbrvEdit.setOnClickListener {
                activity.openwendow(NOTE)
            }


            imgbrvdelete.setOnClickListener {
                activity.deletedialog(NOTE)


            }

        }
    }

    override fun getItemCount() = Notes.size
}

