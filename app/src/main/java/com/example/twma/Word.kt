package com.example.twma

//單字資料
class Word(_foreignData: String, _localData: String, _correctRate: Double = 0.0, _extraInf: String) {
    var foreignData: String = _foreignData //儲存外語資料
    var localData: String = _localData //儲存國語資料
    var correctRate: Double = _correctRate  //正確率
    var extraInf: String = _extraInf    //額外資訊

    //次建構函數
    constructor(): this(_foreignData = "?foreign", _localData = "?local", _correctRate = 0.0, _extraInf = "?Null") {
        if(foreignData == "?foreign" && localData == "?local") {
            //LogSystem.output("word class has not give init value.")
        }
    }
}