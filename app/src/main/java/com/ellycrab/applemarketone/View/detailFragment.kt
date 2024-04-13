package com.ellycrab.applemarketone.View


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ellycrab.applemarketone.databinding.FragmentDetailBinding


class detailFragment(private val remainData: Array<Any>) : Fragment() {


    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = binding.root

        return view


    }

    //로직 실행
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //사진 아이콘 세팅
        val imgIcon = remainData[0] as Int
        binding.detailImgTop.setImageResource(imgIcon)

            //판매자 이름세팅
        val sellerName = remainData[1].toString()
        binding.seller.text = sellerName

        //주소
        val address = remainData[2].toString()
        binding.address.text = address

        //이미지 제목
        val imgTitle = remainData[3].toString()
        binding.sampleName.text = imgTitle

        //본문 글
        val content = remainData[4].toString()
        binding.sampleIntro.text = content





        //previous 버튼클릭시 메인화면으로 돌아감
        val previousBtn = binding.previousBtn

        previousBtn.setOnClickListener{
            activity?.onBackPressed()
        }

        }
    }



