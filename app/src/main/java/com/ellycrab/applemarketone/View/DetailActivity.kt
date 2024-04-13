package com.ellycrab.applemarketone.View


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ellycrab.applemarketone.R
import com.ellycrab.applemarketone.databinding.ActivityDetailBinding
import com.ellycrab.applemarketone.model.DataAll
import com.google.android.material.snackbar.Snackbar

import java.text.NumberFormat
import java.util.Locale


class DetailActivity : AppCompatActivity() {

    //좋아요 상태 추적
    private var isLiked: Boolean = false
    private var likePosition:Int = 0


    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val dataAll = intent.getParcelableExtra<DataAll>("myItem")
        likePosition = intent.getIntExtra("likePosition",0)

        dataAll?.let {
            isLiked = it.isLiked == true
            binding.likeBtn
                .setImageResource(
                    if (isLiked) {
                        R.drawable.occypiedlike
                    } else {
                        R.drawable.emptylike
                    }
                )
            binding.likeBtn.setOnClickListener {
                if(!isLiked){//isLiked가 false(안눌렸을때 누르는 경우)
                    binding.likeBtn.setImageResource(R.drawable.occypiedlike)
                    Snackbar.make(binding.root,"관심 목록에 추가",Snackbar.LENGTH_SHORT).show()
                    isLiked=true
                }else{//isLiked가 true(눌렸을때 해제하는 경우)
                    binding.likeBtn.setImageResource(R.drawable.emptylike)
                    isLiked=false
                }

                sendLikeStateToMainActivity()
            }
        }


        // 가격을 콤마(,)로 형식화하여 설정
        val formattedPrice =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(dataAll!!.price.toInt())
        binding.detailPrice.text = "$formattedPrice 원"


        val remainData = arrayOf(
            dataAll.imgIcon,
            dataAll.seller,
            dataAll.addressmain,
            dataAll.imgTitle,
            dataAll.pdIntroduce,
            dataAll.isLiked
        )


        setFragment(remainData as Array<Any>)

    }

    private fun sendLikeStateToMainActivity() {
        val intent = Intent().apply {
            putExtra("likePosition", likePosition)
            putExtra("isLiked", isLiked)
        }
        setResult(Activity.RESULT_OK, intent)
    }


    private fun setFragment(remainData: Array<Any>) {
        val fragment = detailFragment(remainData).apply {
            arguments = Bundle().apply {
                putSerializable("remainData", remainData)
            }
        }

        supportFragmentManager.commit {
            replace(R.id.detailFrame, fragment)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exit()
    }

    private fun exit() {
        sendLikeStateToMainActivity()
        if (!isFinishing) finish()
    }

}