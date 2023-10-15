package com.example.twma

//目前唯一的單字list
var wordList = WordList()

class WordList{

    var list = List<Word>(0){Word()}

    //傳入單字參數添加
    fun AddWord(foreignData: String, localData: String, extraInf: String , correctRate:Double = 0.0) {
        this.list = this.list.plusElement(Word(_foreignData = foreignData,
            _localData =  localData,
            _correctRate = correctRate,
            _extraInf = extraInf
        ))
    }

    //傳入單字添加
    fun AddWord(word:Word) {
        this.list = this.list.plusElement(Word(_foreignData = word.foreignData,
            _localData =  word.localData,
            _correctRate = word.correctRate,
            _extraInf = word.extraInf
        ))
    }

    fun IsEmpty(): Boolean {
        //Log.d("class test", "isempty")
        //Log.d("class test", this.list.isEmpty().toString())
        return this.list.isEmpty()
    }
}