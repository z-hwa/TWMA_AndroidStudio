package com.example.twma

import android.util.Log

//目前唯一的單字list
var wordList = WordList()

class WordList{

    var list = MutableList<Word>(0){Word()}

    //傳入參數添加
    fun AddWord(foreignData: String, localData: String, extraInf: String , correctRate:Double = 0.0, wordId: Long) {
        this.list.add(Word(_foreignData = foreignData,
            _localData =  localData,
            _correctRate = correctRate,
            _extraInf = extraInf,
            _wordId = wordId
        ))
    }

    //傳入單字添加
    fun AddWord(word:Word) {
        this.list.add(Word(_foreignData = word.foreignData,
            _localData =  word.localData,
            _correctRate = word.correctRate,
            _extraInf = word.extraInf,
            _wordId = word.wordId
        ))
    }

    fun IsEmpty(): Boolean {
        //Log.d("class test", "isempty")
        //Log.d("class test", this.list.isEmpty().toString())
        return this.list.isEmpty()
    }

    fun RenewList() {
        this.list.clear()

        Thread() {
            for(wordE in userDao.queryAll()) {
                val word = Word(wordE)   //根據表中資料創建單字
                this.AddWord(word)  //塞進實際使用的list中
            }
        }.start()
    }
}