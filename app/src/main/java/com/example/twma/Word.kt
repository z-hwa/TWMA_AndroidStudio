package com.example.twma

//單字資料
class Word constructor(_foreignData: String, _localData: String, _correctRate: Double = 0.0,
                       _extraInf: String, _wordId: Long) {

    var foreignData: String //儲存外語資料
    var localData: String   //儲存國語資料
    var correctRate: Double     //正確率
    var extraInf: String        //額外資訊
    var wordId: Long     //在資料庫的ID

    init {
        this.foreignData = _foreignData
        this.localData = _localData
        this.correctRate = _correctRate
        this.extraInf = _extraInf
        this.wordId = _wordId
    }

    //次建構函數 創建預設實體
    constructor(): this(_foreignData = "?foreign", _localData = "?local", _correctRate = 0.0,
        _extraInf = "?Null", _wordId = 0) {
        if(foreignData == "?foreign" && localData == "?local") {
            //LogSystem.output("word class has not give init value.")
        }
    }

    //次建構函數by WordE
    constructor(word: WordE): this(_foreignData = "?foreign", _localData = "?local", _correctRate = 0.0,
        _extraInf = "?Null", _wordId = 0) {

        if(word.foreignData != null && word.localData != null && word.id != null &&
            word.extraInf!=null && word.correctRate != null) {
            this.foreignData = word.foreignData
            this.localData = word.localData
            this.wordId = word.id
            this.extraInf = word.extraInf
            this.correctRate = word.correctRate
        }

    }
}