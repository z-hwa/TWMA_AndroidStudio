package com.example.twma

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

//每個entity相當於一張表
//類似定義一個表格中 一整列中每一行的欄位(名稱、儲存型態)
@Entity(tableName = "wordE")
data class WordE(
    //主鍵 用於在資料庫中識別不同的資料列
    @PrimaryKey(autoGenerate = true)    //自動分配key值 初始為1
    val id: Int?,

    @ColumnInfo(name = "foreignData") val foreignData: String?,
    @ColumnInfo(name = "localData") val localData: String?,
    @ColumnInfo(name = "extraInf") val extraInf: String?,
    @ColumnInfo(name = "correctRate") val correctRate: Double?
)

@Dao
interface UserDao {

    //插入單字
    @Insert
    fun insertWord(vararg wordE: WordE?)

    //查詢特定單字
    @Query("SELECT * FROM wordE WHERE id like :wordID")
    fun queryWord(wordID: Int): WordE?

    //取出全部資料
    @Query("SELECT * FROM wordE")
    fun queryAll(): List<WordE>

    //根據主鍵刪除單字
    @Delete
    fun delete(wordE: WordE?): Int
}

//資料庫
@Database(entities = [WordE::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}