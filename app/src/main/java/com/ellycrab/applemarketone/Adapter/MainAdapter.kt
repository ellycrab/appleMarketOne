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

class MainAdapter(val context: Context, val MainList: MutableList<DataAll>) :
    RecyclerView.Adapter<MainAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MainitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)



        fun onItemLongClick(view: View, position: Int) // 롱클릭 함수 추가
    }


    private var itemClick: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        this.itemClick = listener
    }

    override fun getItemCount(): Int = MainList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(MainList[position])

        holder.itemView.setOnLongClickListener { // 아이템 롱클릭 했을 때
            itemClick?.onItemLongClick(it, position)
            return@setOnLongClickListener (false) // 직후 click event 를 받기 위해 false 반환
        }


        holder.itemView.setOnClickListener {
            itemClick?.onItemClick(position)


        }

    }

    inner class Holder(val binding: MainitemBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(dataAll: DataAll) {
            binding.apply {
                imgIcon.setImageResource(dataAll.imgIcon)
                imgTitle.text = dataAll.imgTitle
                addressmain.text = dataAll.addressmain

                val formatter = DecimalFormat("#,###")
                val formattedPrice = formatter.format(dataAll.price.toInt())
                price.text = formattedPrice
                commentCnt.text = dataAll.commentCnt
                likeCnt.text = dataAll.likeCnt


            }
        }
    }
}
