package com.ellycrab.applemarketone.View

import MainAdapter
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ellycrab.applemarketone.R
import com.ellycrab.applemarketone.databinding.ActivityMainBinding
import com.ellycrab.applemarketone.model.DataAll
import com.ellycrab.applemarketone.utils.NotificationUtils.notification

class MainActivity : AppCompatActivity(){

    private val DETAIL_REQUEST_CODE = 100

    //데이터 원본 준비-메인 게시물데이터를 담을 리스트 초기화
    private var MainList = mutableListOf<DataAll>()

    //DataAll 초기화
    lateinit var dataAllList: Array<DataAll>

    //좋아요 콜백함수 초기화
    //lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    // 어댑터 초기화
    private lateinit var rvBoardAdapter: MainAdapter

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        // 리싸이클러뷰 설정
        setupRecyclerView()


        // 메인 게시물 데이터 삽입
        addWritingsData()



        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true


        //알림버튼 클릭시
        binding.notification.setOnClickListener {
            notification(this)
        }
        //플로팅버튼 클릭시
        binding.mainRv.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.mainRv.canScrollVertically(-1)
                    &&newState == RecyclerView.SCROLL_STATE_IDLE){
                    binding.floatbtn.startAnimation(fadeOut)
                    binding.floatbtn.visibility = View.GONE
                    isTop = true
                    Log.d("myLog", "Top")
                }else{
                    if(isTop){
                        binding.floatbtn.visibility = View.VISIBLE
                        binding.floatbtn.startAnimation(fadeIn)
                        isTop = false
                        Log.d("myLog", "Not Top")
                    }
                }
            }
        })
        binding.floatbtn.setOnClickListener {
            binding.mainRv.smoothScrollToPosition(0)
            binding.floatbtn.backgroundTintList = getColorStateList(R.color.purple)
        }






    }

    //detail 좋아요결과반영-activityResultLauncher 이용하다 앱이 꺼져서 onActivityResult이용하였으나 구현 안됩니다 ㅠ
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedLikeStatus = data?.getBooleanExtra("updatedLikeStatus", false) ?: false
            val likeCountChange = data?.getIntExtra("likeCountChange", 1) ?: 0
            val position = data?.getIntExtra("position", -1) ?: -1



            MainList.forEach { item ->
                item.apply {
                    this.isLiked = updatedLikeStatus

                    val currentLikeCount = this.likeCnt.toInt()
                    val newLikeCount =
                        if (updatedLikeStatus) currentLikeCount - likeCountChange
                        else currentLikeCount + likeCountChange
                    this.likeCnt = newLikeCount.toString()
                }
            }


            rvBoardAdapter.notifyDataSetChanged()


        }
    }

    fun addWritingsData(){

        MainList.add(
            DataAll(
            R.drawable.sample1,resources.getString(R.string.sample1_title),"",
                "",resources.getString(R.string.sample1_price),resources.getString(R.string.sample1_address),
            resources.getString(R.string.sample1_like),resources.getString(R.string.sample1_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample2,resources.getString(R.string.sample2_title),"",
                "",resources.getString(R.string.sample2_price),resources.getString(R.string.sample2_address),
                resources.getString(R.string.sample2_like),resources.getString(R.string.sample2_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample3,resources.getString(R.string.sample3_title),"",
                "",resources.getString(R.string.sample3_price),resources.getString(R.string.sample3_address),
                resources.getString(R.string.sample3_like),resources.getString(R.string.sample3_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample4,resources.getString(R.string.sample4_title),"",
                "",resources.getString(R.string.sample4_price),resources.getString(R.string.sample4_address),
                resources.getString(R.string.sample4_like),resources.getString(R.string.sample4_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample5,resources.getString(R.string.sample5_title),"",
                "",resources.getString(R.string.sample5_price),resources.getString(R.string.sample5_address),
                resources.getString(R.string.sample5_like),resources.getString(R.string.sample5_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample6,resources.getString(R.string.sample6_title),"",
                "",resources.getString(R.string.sample6_price),resources.getString(R.string.sample6_address),
                resources.getString(R.string.sample6_like),resources.getString(R.string.sample6_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample7,resources.getString(R.string.sample7_title),"",
                "",resources.getString(R.string.sample7_price),resources.getString(R.string.sample7_address),
                resources.getString(R.string.sample7_like),resources.getString(R.string.sample7_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample8,resources.getString(R.string.sample8_title),"",
                "",resources.getString(R.string.sample8_price),resources.getString(R.string.sample8_address),
                resources.getString(R.string.sample8_like),resources.getString(R.string.sample8_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample9,resources.getString(R.string.sample9_title),"",
                "",resources.getString(R.string.sample9_price),resources.getString(R.string.sample9_address),
                resources.getString(R.string.sample9_like),resources.getString(R.string.sample9_chatting),false,0))

        MainList.add(
            DataAll(
                R.drawable.sample10,resources.getString(R.string.sample10_title),"",
                "",resources.getString(R.string.sample10_price),resources.getString(R.string.sample10_address),
                resources.getString(R.string.sample10_like),resources.getString(R.string.sample10_chatting),false,0))
    }
    //Main 화면에서 Detail화면으로 이동할때,=>리싸이클러뷰에서 클릭
    private fun setupRecyclerView(){

        val recyclerBoardContent = binding.mainRv
        rvBoardAdapter = MainAdapter(baseContext,MainList)

        recyclerBoardContent.adapter = rvBoardAdapter
        recyclerBoardContent.layoutManager = LinearLayoutManager(this)

        rvBoardAdapter.setItemClickListener(object :MainAdapter.ItemClickListener{
            override fun onItemClick(position: Int) {

                dataAllList = arrayOf(DataAll(
                    R.drawable.sample1,
                    resources.getString(R.string.sample1_title),
                    resources.getString(R.string.sample1_intro),
                    resources.getString(R.string.sample1_seller),
                    resources.getString(R.string.sample1_price),
                    resources.getString(R.string.sample1_address),
                    "resources.getString(R.string.sample1_like)",
                    " resources.getString(R.string.sample1_chatting)",
                    false,0
                ),
                    DataAll(
                        R.drawable.sample2,
                        resources.getString(R.string.sample2_title),
                        resources.getString(R.string.sample2_intro),
                        resources.getString(R.string.sample2_seller),
                        resources.getString(R.string.sample2_price),
                        resources.getString(R.string.sample2_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample3,
                        resources.getString(R.string.sample3_title),
                        resources.getString(R.string.sample3_intro),
                        resources.getString(R.string.sample3_seller),
                        resources.getString(R.string.sample3_price),
                        resources.getString(R.string.sample3_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample4,
                        resources.getString(R.string.sample4_title),
                        resources.getString(R.string.sample4_intro),
                        resources.getString(R.string.sample4_seller),
                        resources.getString(R.string.sample4_price),
                        resources.getString(R.string.sample4_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample5,
                        resources.getString(R.string.sample5_title),
                        resources.getString(R.string.sample5_intro),
                        resources.getString(R.string.sample5_seller),
                        resources.getString(R.string.sample5_price),
                        resources.getString(R.string.sample5_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample6,
                        resources.getString(R.string.sample6_title),
                        resources.getString(R.string.sample6_intro),
                        resources.getString(R.string.sample6_seller),
                        resources.getString(R.string.sample6_price),
                        resources.getString(R.string.sample6_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample7,
                        resources.getString(R.string.sample7_title),
                        resources.getString(R.string.sample7_intro),
                        resources.getString(R.string.sample7_seller),
                        resources.getString(R.string.sample7_price),
                        resources.getString(R.string.sample7_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample8,
                        resources.getString(R.string.sample8_title),
                        resources.getString(R.string.sample8_intro),
                        resources.getString(R.string.sample8_seller),
                        resources.getString(R.string.sample8_price),
                        resources.getString(R.string.sample8_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample9,
                        resources.getString(R.string.sample9_title),
                        resources.getString(R.string.sample9_intro),
                        resources.getString(R.string.sample9_seller),
                        resources.getString(R.string.sample9_price),
                        resources.getString(R.string.sample9_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    ),
                    DataAll(
                        R.drawable.sample10,
                        resources.getString(R.string.sample10_title),
                        resources.getString(R.string.sample10_intro),
                        resources.getString(R.string.sample10_seller),
                        resources.getString(R.string.sample10_price),
                        resources.getString(R.string.sample10_address),
                        "resources.getString(R.string.sample1_like)",
                        " resources.getString(R.string.sample1_chatting)",
                        false,0
                    )
                )


                //좋아요버튼 업데이트 위해서 작성



                //가격정보
                openDetail(dataAllList[position])

            }




            override fun onItemLongClick(view: View, position: Int) {
                val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setTitle("삭제 확인 창")
                alertDialogBuilder.setMessage("이 항목을 삭제하시겠습니까?")
                alertDialogBuilder.setIcon(R.drawable.bell)

                //버튼 클릭시 작업
                val listener = object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when(p1){
                            DialogInterface.BUTTON_POSITIVE -> { // "확인" 버튼 클릭 시
                                // 해당 항목 삭제
                                MainList.removeAt(position)
                                // 어댑터에 변경사항 알리기
                                rvBoardAdapter.notifyItemRemoved(position)
                                // 리스트 업데이트
                                rvBoardAdapter.notifyDataSetChanged()

                            }
                            DialogInterface.BUTTON_NEGATIVE -> { // "취소" 버튼 클릭 시
                                // 삭제 취소
                                p0?.dismiss()
                            }
                        }
                    }

                }

                alertDialogBuilder.setPositiveButton("확인",listener)
                alertDialogBuilder.setNegativeButton("취소",listener)

                alertDialogBuilder.show()

            }



        })

    }



    //디테일내 하단 가격바에만 적용되는 함수
    private fun openDetail(dataAll: DataAll) {

        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("datafromMain", dataAll)
        startActivityForResult(intent, DETAIL_REQUEST_CODE)

    }




    //뒤로가기 버튼 클릭시
    override fun onBackPressed() {
        // Build the alert dialog
        AlertDialog.Builder(this)
            .setMessage("종료하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                super.onBackPressed()
            }
            .setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int ->

                dialogInterface.dismiss()
            }
            .show()
    }


}
