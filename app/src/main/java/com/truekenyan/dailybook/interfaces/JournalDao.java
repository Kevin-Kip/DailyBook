package com.truekenyan.dailybook.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.Constants;

import java.util.List;

/**
 * Created by password
 * on 6/28/18.
 */

@Dao
public interface JournalDao {
    @Query("SELECT * FROM "+ Constants.TABLE_NAME+" ORDER BY id DESC")
    LiveData<List<JournalEntry>> getAllEntries();

    @Insert
    void saveEntry(JournalEntry journalEntry);

    @Delete
    void deleteEntry(JournalEntry journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(JournalEntry journalEntry);
}
