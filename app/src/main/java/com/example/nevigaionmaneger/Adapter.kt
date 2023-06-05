package com.example.nevigaionmaneger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MatchListAdapter(val modelMatchList: ArrayList<Model>, val fragmentCode : Int) :
    RecyclerView.Adapter<MatchListAdapter.VenueMatchHolder>() {

    inner class VenueMatchHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtId = view.findViewById<TextView>(R.id.txtID)
        var imgSave = view.findViewById<ImageView>(R.id.imgSave)

    }


    interface SetOnClickListener{
        fun setOnClick(position: Int)
    }

    var setOnClickListener: SetOnClickListener? = null

    fun setOnClickListener(listener: SetOnClickListener) {
        setOnClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueMatchHolder {
        return VenueMatchHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.venue_match_view, parent, false)
        )
    }

    override fun getItemCount() = modelMatchList.size

    override fun onBindViewHolder(holder: VenueMatchHolder, position: Int) {

        if (fragmentCode == 1) {holder.imgSave.setImageResource(R.drawable.baseline_star_24)}
        else {holder.imgSave.setImageResource(R.drawable.baseline_star_border_24)}

        val databaseHelper = DatabaseHelper(holder.itemView.context)
        val data = modelMatchList[position]

        if (fragmentCode == 0 &&  isVenueSaved(data,databaseHelper)){
            holder.imgSave.setImageResource(R.drawable.baseline_star_24)
        } else if (fragmentCode == 0 &&  isVenueSaved(data,databaseHelper)) {holder.imgSave.setImageResource(R.drawable.baseline_star_border_24)}

        holder.txtId.text = "${data.id} - ${data.city},${data.state},${data.country}"


        holder.imgSave.setOnClickListener {

            if (isVenueSaved(data, databaseHelper)) {
                //delete module
                databaseHelper.deleteModel(data.id)
                holder.imgSave.setImageResource(R.drawable.baseline_star_border_24)
                Toast.makeText(holder.itemView.context, "Venue deleted", Toast.LENGTH_SHORT).show()

                if (fragmentCode == 1){
                    setOnClickListener?.setOnClick(position)
                }

            } else {
                    //insert Model
                databaseHelper.insertModel(data)
                holder.imgSave.setImageResource(R.drawable.baseline_star_24)
                Toast.makeText(holder.itemView.context, "Model saved", Toast.LENGTH_SHORT).show()
            }

            databaseHelper.close()

        }

    }

    private fun isVenueSaved(model: Model, databaseHelper: DatabaseHelper): Boolean {
        val venuesList = databaseHelper.getAllModel()
        return venuesList.any { it.id == model.id }
    }

}