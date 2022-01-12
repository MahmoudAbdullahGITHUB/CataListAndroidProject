package com.example.cproductandroid.Ui.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cproductandroid.R
import com.example.cproductandroid.Ui.Activity.ProductDetailsActivity
import com.example.cproductandroid.model.AllProduct

class ProductListAdapter(val context: Context, val productsList: List<AllProduct>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(context,ProductDetailsActivity::class.java)
                intent.putExtra("productName", productsList[holder.absoluteAdapterPosition].productName)
                intent.putExtra("productID",productsList[holder.absoluteAdapterPosition].barCode)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        })
        holder.productName.text = productsList[position].productName
    }

    override fun getItemCount(): Int {
        return productsList.size
    }


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productName: TextView

        init {
            productName = itemView.findViewById(R.id.product_name)
//            itemView.setOnClickListener(this)
        }

//        override fun onClick(p0: View?) {
//            val position = absoluteAdapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                context.onItemClick(position)
//            }
//        }

    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }


}