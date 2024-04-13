import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ellycrab.applemarketone.R
import com.ellycrab.applemarketone.databinding.MainitemBinding

import com.ellycrab.applemarketone.model.DataAll
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class MainAdapter(val MainList: MutableList<DataAll>) :
    RecyclerView.Adapter<MainAdapter.Holder>() {


    interface ItemClickListener{
        fun onItemClick(view:View,position: Int)
    }

    interface LongItemClick{
        fun onItemLongClick(view:View,position: Int)
    }

    var itemClick:ItemClickListener? = null
    var longItemClick:LongItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MainitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }




    override fun getItemCount(): Int = MainList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.itemView.setOnClickListener {
            itemClick?.onItemClick(it,position)
        }
        holder.imgIcon.setImageResource(MainList[position].imgIcon)
        holder.imgTitle.text = MainList[position].imgTitle
        holder.address.text = MainList[position].addressmain


        val priceString = MainList[position].price
        val price = priceString.toInt()
        val formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault())
            .format(price)
        holder.price.text = "$formattedPrice 원"


        holder.chatCnt.text = MainList[position].commentCnt.toString()
        holder.likeCnt.text = MainList[position].likeCnt.toString()

        if(MainList[position].isLiked){
            holder.likeIcon.setImageResource(R.drawable.occypiedlike)
        }else{
            holder.likeIcon.setImageResource(R.drawable.emptylike)
        }

    }



    inner class Holder(val binding: MainitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgIcon = binding.imgIcon
        val imgTitle = binding.imgTitle
        val address = binding.addressmain
        val price = binding.price
        val chatCnt = binding.commentCnt
        val likeCnt = binding.likeCnt
        val likeIcon = binding.like

        init {
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    longItemClick?.onItemLongClick(it, position)
                    true
                } else {
                    false
                }
            }
        }
    }
}