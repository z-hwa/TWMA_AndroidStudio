package com.example.twma

//單字資料
class Word constructor(_foreignData: String, _localData: String, _correctRate: Double = 0.0, _extraInf: String) {
    var foreignData: String //儲存外語資料
    var localData: String   //儲存國語資料
    var correctRate: Double     //正確率
    var extraInf: String        //額外資訊

    init {
        this.foreignData = _foreignData
        this.localData = _localData
        this.correctRate = _correctRate
        this.extraInf = _extraInf
    }

    //次建構函數
    constructor(): this(_foreignData = "?foreign", _localData = "?local", _correctRate = 0.0, _extraInf = "?Null") {
        if(foreignData == "?foreign" && localData == "?local") {
            //LogSystem.output("word class has not give init value.")
        }
    }
}