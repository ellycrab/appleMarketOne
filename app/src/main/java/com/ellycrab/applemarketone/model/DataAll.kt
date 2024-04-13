package com.ellycrab.applemarketone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataAll(
    var imgIcon:Int,
    var imgTitle: String,
    var pdIntroduce:String,
    var seller:String,
    var price:String,
    var addressmain:String,
    var likeCnt:Int,
    var commentCnt:Int,
    var isLiked:Boolean,

) : Parcelable
