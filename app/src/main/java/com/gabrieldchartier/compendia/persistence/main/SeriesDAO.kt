package com.gabrieldchartier.compendia.persistence.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gabrieldchartier.compendia.models.Series

@Dao
interface SeriesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSeriesOrIgnore(series: List<Series>): List<Long>?
}