package com.ellycrab.applemarketone.View


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
    private var liked: Boolean = false
    private var position: Int = -1



    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        position = intent.getIntExtra("position",-1)
        //like버튼 클릭시
        setupLikeButton()

        val dataAll = intent.getParcelableExtra<DataAll>("datafromMain")



        // 가격을 콤마(,)로 형식화하여 설정
        val formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault()).format(dataAll!!.price.toInt())
        binding.detailPrice.text = formattedPrice


        val remainData = arrayOf(dataAll.imgIcon,
            dataAll.seller,
            dataAll.addressmain,
            dataAll.imgTitle,
            dataAll.pdIntroduce,
            dataAll.isLiked)


        setFragment(remainData as Array<Any>)

    }

    private fun setupLikeButton() {
        binding.likeBtn.setOnClickListener {
            if (!liked) {
                binding.likeBtn.setImageResource(R.drawable.occypiedlike)
                Snackbar.make(binding.root, "관심 목록에 추가됨", Snackbar.LENGTH_SHORT).show()
                liked = true


                val returnIntent = Intent()
                returnIntent.putExtra("updatedLikeStatus", true)
                returnIntent.putExtra("likeCountChange", 1)
                setResult(Activity.RESULT_OK, returnIntent)
                Log.d("resultok","$returnIntent")
            } else {
                binding.likeBtn.setImageResource(R.drawable.emptylike)
                liked = false


                val returnIntent = Intent()
                returnIntent.putExtra("updatedLikeStatus", false)
                returnIntent.putExtra("likeCountChange", -1)
                setResult(Activity.RESULT_OK, returnIntent)
            }
        }
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

    private fun exit(){
        val likePosition = intent.getIntExtra("likePosition",0)
        val intent = Intent(this,MainActivity::class.java).apply {
            putExtra("likePosition",likePosition)
            putExtra("liked",liked)


        }
        setResult(RESULT_OK,intent)
        if (!isFinishing) finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        exit()
    }

}