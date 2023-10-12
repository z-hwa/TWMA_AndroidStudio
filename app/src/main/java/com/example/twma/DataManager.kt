package com.example.twma

import android.util.Log

var wordList = WordList()

class WordList{

    var list = List<Word>(0){Word()}

    fun AddWord(foreignData: String, localData: String, extraInf: String ) {
        this.list = this.list.plusElement(Word(_foreignData = foreignData,
            _localData =  localData,
            _correctRate = 0.0,
            _extraInf = extraInf
        ))

        //Log.d("class test", "is add?")
        //this.IsEmpty()
    }

    fun IsEmpty(): Boolean {
        //Log.d("class test", "isempty")
        //Log.d("class test", this.list.isEmpty().toString())
        return this.list.isEmpty()
    }
}