package com.ellycrab.applemarketone.View

import MainAdapter
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
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


    //좋아요 콜백함수 초기화
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    // 어댑터 초기화
    private lateinit var rvBoardAdapter: MainAdapter

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        // 메인 게시물 데이터 삽입
        addWritingsData()
        // 리싸이클러뷰 설정
        setupRecyclerView()

        //동선택 스피너
        val selectDong = listOf(resources.getString(R.string.main_selectTxt),
            resources.getString(R.string.main_selectTxt2),
            resources.getString(R.string.main_selectTxt3))

        var dongAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selectDong)

        binding.mainSelect.adapter = dongAdapter

        //사용자가 스피너를 선택하면 선택한 값을 선택 결과에 보여주는 코드
        binding.mainSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?,
                                        position: Int, id: Long) {}

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true


        //알림버튼 클릭시
        binding.notification.setOnClickListener {
            notification(this)
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val likePosition = it.data?.getIntExtra("likePosition",0) as Int
                val isLiked = it.data?.getBooleanExtra("isLiked",false) as Boolean
                if(isLiked){
                    MainList[likePosition].isLiked = true
                    MainList[likePosition].likeCnt += 1
                }else{
                    if(MainList[likePosition].isLiked){
                        MainList[likePosition].isLiked = false
                        MainList[likePosition].likeCnt -= 1
                    }
                }
                rvBoardAdapter.notifyItemChanged(likePosition)
            }
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



    fun addWritingsData(){

        MainList.add(
            DataAll(
                R.drawable.sample1,resources.getString(R.string.sample1_title),
                resources.getString(R.string.sample1_intro),
                resources.getString(R.string.sample1_seller),
                resources.getString(R.string.sample1_price),
                resources.getString(R.string.sample1_address),
                13,25,false))

        MainList.add(
            DataAll(
                R.drawable.sample2,resources.getString(R.string.sample2_title),
                resources.getString(R.string.sample2_intro),
                resources.getString(R.string.sample2_seller),
                resources.getString(R.string.sample2_price),resources.getString(R.string.sample2_address),
                8,28,false))

        MainList.add(
            DataAll(
                R.drawable.sample3,resources.getString(R.string.sample3_title),
                resources.getString(R.string.sample3_intro),
                resources.getString(R.string.sample3_seller),
                resources.getString(R.string.sample3_price),resources.getString(R.string.sample3_address),
                23,5,false))

        MainList.add(
            DataAll(
                R.drawable.sample4,resources.getString(R.string.sample4_title),
                resources.getString(R.string.sample4_intro),
                resources.getString(R.string.sample4_seller),
                resources.getString(R.string.sample4_price),resources.getString(R.string.sample4_address),
                14,17,false))

        MainList.add(
            DataAll(
                R.drawable.sample5,resources.getString(R.string.sample5_title),
                resources.getString(R.string.sample5_intro),
                resources.getString(R.string.sample5_seller),
                resources.getString(R.string.sample5_price),resources.getString(R.string.sample5_address),
                22,9,false))

        MainList.add(
            DataAll(
                R.drawable.sample6,resources.getString(R.string.sample6_title),
                resources.getString(R.string.sample6_intro),
                resources.getString(R.string.sample6_seller),
                resources.getString(R.string.sample6_price),resources.getString(R.string.sample6_address),
                25,16,false))

        MainList.add(
            DataAll(
                R.drawable.sample7,
                resources.getString(R.string.sample7_title),
                resources.getString(R.string.sample7_intro),
                resources.getString(R.string.sample7_seller),
                resources.getString(R.string.sample7_price),resources.getString(R.string.sample7_address),
                142,54,false))

        MainList.add(
            DataAll(
                R.drawable.sample8,resources.getString(R.string.sample8_title),
                resources.getString(R.string.sample8_intro),
                resources.getString(R.string.sample8_seller),
                resources.getString(R.string.sample8_price),resources.getString(R.string.sample8_address),
                31,7,false))

        MainList.add(
            DataAll(
                R.drawable.sample9,
                resources.getString(R.string.sample9_title),
                resources.getString(R.string.sample9_intro),
                resources.getString(R.string.sample9_seller),
                resources.getString(R.string.sample9_price),resources.getString(R.string.sample9_address),
                7,28,false))

        MainList.add(
            DataAll(
                R.drawable.sample10,resources.getString(R.string.sample10_title),
                resources.getString(R.string.sample10_intro),
                resources.getString(R.string.sample10_seller),
                resources.getString(R.string.sample10_price),resources.getString(R.string.sample10_address),
                40,6,false))
    }
    //Main 화면에서 Detail화면으로 이동할때,=>리싸이클러뷰에서 클릭
    private fun setupRecyclerView(){

        val recyclerBoardContent = binding.mainRv
        rvBoardAdapter = MainAdapter(MainList)

        recyclerBoardContent.adapter = rvBoardAdapter
        recyclerBoardContent.layoutManager = LinearLayoutManager(this)

        //클릭한 데이터 상세페이지로 이동 및 좋아요 누른 포지션과 리싸이클러뷰-해당포지션 아이템
        rvBoardAdapter.itemClick = object :MainAdapter.ItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                //디테일페이지로 모든 데이터 넘기기

                val clickedItem = MainList[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("likePosition", position)
                intent.putExtra("myItem", clickedItem)
                activityResultLauncher.launch(intent)

            }
        }

        //삭제 dialog
        rvBoardAdapter.longItemClick = object :MainAdapter.LongItemClick{
            override fun onItemLongClick(view: View, position: Int) {
                val itemRemove = MainList[position]
                AlertDialog.Builder(this@MainActivity)
                    .setIcon(R.drawable.bell)
                    .setTitle("삭제 확인 창")
                    .setMessage("이 항목을 삭제하시겠습니까?")
                    .setPositiveButton("확인"){
                            dialog,_->
                        MainList.remove(itemRemove)
                        rvBoardAdapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("취소"){
                            dialog,_->
                        dialog.dismiss()
                    }
                    .show()
            }
        }



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