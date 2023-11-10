package com.example.mad_app090_p11

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerPersonlistAdapter(
    private var context: MainActivity,
    private var arr_records: ArrayList<PersonListCardModel>
) : RecyclerView.Adapter<RecyclerPersonlistAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_name: TextView = itemView.findViewById(R.id.txt_name)
        val txt_email: TextView = itemView.findViewById(R.id.txt_email)
        val txt_mobno: TextView = itemView.findViewById(R.id.txt_mobno)
        val txt_address: TextView = itemView.findViewById(R.id.txt_address)
        val btn_map: ImageButton = itemView.findViewById(R.id.btn_map)
        val btn_del: ImageButton = itemView.findViewById(R.id.btn_del)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_personlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_name.text = arr_records[position].name
        holder.txt_email.text = arr_records[position].emailld
        holder.txt_mobno.text = arr_records[position].phoneNo
        holder.txt_address.text = arr_records[position].address

        val selectedItem = arr_records[position]
        holder.btn_map.setOnClickListener {
            Intent (this@RecyclerPersonlistAdapter.context, MapsActivity::class.java).apply {
                putExtra("Object",selectedItem)
                this@RecyclerPersonlistAdapter.context.startActivity( this)
            }
        }
        holder.btn_del.setOnClickListener{
            context.deletePerson(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return arr_records.size
    }
}