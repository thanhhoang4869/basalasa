package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.CustomerHistory
import com.squareup.picasso.Picasso

class CustomerOrderTabRCAdapter (private val arrHistory: ArrayList<CustomerHistory>, private val pending: Boolean): RecyclerView.Adapter<CustomerOrderTabRCAdapter.ViewHolder>() {
    var onItemClick: ((CustomerHistory) -> Unit)? = null
    var onCancelClick: ((CustomerHistory) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var date: TextView
        var name: TextView
        var quantity: TextView
        var total: TextView
        var cancel: TextView

        init {
            image = itemView.findViewById(R.id.customerOrderImage)
            date = itemView.findViewById(R.id.customerOrderDate)
            name = itemView.findViewById(R.id.customerOrderName)
            quantity = itemView.findViewById(R.id.customerOrderQuantity)
            total = itemView.findViewById(R.id.customerOrderTotalMoney)
            cancel = itemView.findViewById(R.id.customerOrderCancelBtn)

            itemView.setOnClickListener {
                onItemClick?.invoke(arrHistory[adapterPosition])
            }

            cancel.setOnClickListener {
                onCancelClick?.invoke(arrHistory[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerOrderTabRCAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val customerOrderTabRCView =
            inflater.inflate(R.layout.fragment_customer_order_tab_rc_item, parent, false)
        return ViewHolder(customerOrderTabRCView)
    }

    override fun onBindViewHolder(holder: CustomerOrderTabRCAdapter.ViewHolder, position: Int) {
        var order = arrHistory[position]

        Picasso.get().load(order.product?.get(0)?.picture).into(holder.image);
        holder.date.text = order.date
        holder.name.text = order.product?.get(0)?.name
        holder.quantity.text = "x" + order.product?.get(0)?.quantity.toString()
        holder.total.text = order.total.toString()

        if(!pending) {
            holder.cancel.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return arrHistory.size
    }
}