package com.truekenyan.dailybook.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.truekenyan.dailybook.utilities.Constants;

/**
 * Created by password
 * on 6/28/18.
 */

@Entity(tableName = Constants.TABLE_NAME)
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String writeDate;
    private String writeYear;
    private String writeMonth;
    private String writeDay;
    private String writeTime;

    @Ignore
    public JournalEntry () {
    }

    @Ignore
    public JournalEntry (String content, String writeDate, String writeYear, String writeMonth, String writeDay, String writeTime) {
        this.content = content;
        this.writeDate = writeDate;
        this.writeYear = writeYear;
        this.writeMonth = writeMonth;
        this.writeDay = writeDay;
        this.writeTime = writeTime;
    }

    public JournalEntry (int id, String content, String writeDate, String writeYear, String writeMonth, String writeDay, String writeTime) {
        this.id = id;
        this.content = content;
        this.writeDate = writeDate;
        this.writeYear = writeYear;
        this.writeMonth = writeMonth;
        this.writeDay = writeDay;
        this.writeTime = writeTime;
    }

    public int getId () {
        return id;
    }

    public String getContent () {
        return content;
    }

    public String getWriteDate () {
        return writeDate;
    }

    public String getWriteYear () {
        return writeYear;
    }

    public String getWriteMonth () {
        return writeMonth;
    }

    public String getWriteDay () {
        return writeDay;
    }

    public String getWriteTime () {
        return writeTime;
    }

}
