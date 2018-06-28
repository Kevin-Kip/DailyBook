package com.truekenyan.dailybook.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.truekenyan.dailybook.interfaces.JournalDao;
import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.Constants;

/**
 * Created by password
 * on 6/28/18.
 */

@Database(entities = {JournalEntry.class}, version = 1, exportSchema = false)
public abstract class DailyBookDatabase extends RoomDatabase {

    private static DailyBookDatabase dailyBookDatabase;

    public static DailyBookDatabase getDatabaseInstance(Context context){
        if (dailyBookDatabase == null){
            synchronized (new Object()){
                dailyBookDatabase = Room.databaseBuilder(context, DailyBookDatabase.class, Constants.DB_NAME)
                        .build();
            }
        }
        return dailyBookDatabase;
    }

    public abstract JournalDao journalDao();
}
