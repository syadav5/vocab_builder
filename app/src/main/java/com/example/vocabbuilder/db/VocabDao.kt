package com.example.vocabbuilder.db

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "vocabulary")

data class WordMeaning(
    @PrimaryKey val word: String,
    @ColumnInfo()
    val meaning: String,
    @ColumnInfo
    val ex1: String,
    @ColumnInfo
    val synonym: String? = null,
    @ColumnInfo
    val ex2: String? = null,
    @ColumnInfo
    val ex3: String? = null
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString()?:"--",
        parcel.readString()?:"--",
        parcel.readString()?:"--",
        parcel.readString()?:"--",
        parcel.readString()?:"--",
        parcel.readString()?:"--"
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(meaning)
        parcel.writeString(ex1)
        parcel.writeString(synonym)
        parcel.writeString(ex2)
        parcel.writeString(ex3)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WordMeaning> {
        override fun createFromParcel(parcel: Parcel): WordMeaning {
            return WordMeaning(parcel)
        }

        override fun newArray(size: Int): Array<WordMeaning?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface VocabDao {

    @Query("Select * from vocabulary")
    fun getAllWords(): LiveData<List<WordMeaning>>

    //@Query("update vocabulary set word")
    @Update
    suspend fun update(word: WordMeaning)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg words: WordMeaning)

    @Delete
    suspend fun delete(word: WordMeaning)

    @Delete
    suspend fun delete(words: List<WordMeaning>)
}