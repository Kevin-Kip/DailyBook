package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.database.DailyBookDatabase;
import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.AppExecutors;
import com.truekenyan.dailybook.utilities.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings ("WeakerAccess")
public class WriteActivity extends AppCompatActivity {

    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.date)
    TextView date;
    @BindView (R.id.day)
    TextView day;
    @BindView (R.id.month_and_year)
    TextView monthAndYear;
    @BindView (R.id.time)
    TextView time;
    @BindView (R.id.content_input)
    EditText contentInput;

    private String dateText;
    private String dayText;
    private String monthText;
    private String timeText;
    private String yearText;
    private boolean isEdit = false;
    private Intent intent;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();

        intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constants.POSITION)) {
                int position = Integer.parseInt(String.valueOf(intent.getIntExtra(Constants.POSITION, 0)));
                JournalEntry journalEntry = MainActivity.getList().get(position);
                dayText = journalEntry.getWriteDate();
                dayText = journalEntry.getWriteDay();
                monthText = journalEntry.getWriteMonth();
                yearText = journalEntry.getWriteYear();
                timeText = journalEntry.getWriteTime();
                contentInput.setText(journalEntry.getContent());
                isEdit = true;
            } else {
                dateText = new SimpleDateFormat("dd", Locale.getDefault()).format(calendar.getTime());
                dayText = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                monthText = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                yearText = new SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar.getTime());
                timeText = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.getTime());
                isEdit = false;
            }
        }

        date.setText(dateText);
        day.setText(dayText);
        monthAndYear.setText(String.format("%s, %s",monthText,yearText));
        time.setText(timeText);
    }

    @OnClick ({R.id.save_entry, R.id.icon_close, R.id.content_input})
    public void onViewClicked (View view) {
        switch (view.getId()) {
            case R.id.save_entry:
                String content = contentInput.getText().toString().trim();
                JournalEntry journalEntry = new JournalEntry(content,dateText,yearText,monthText,dayText,timeText);
                if (isEdit){
                    updateItem(journalEntry);
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    isEdit = false;
                    finish();
                } else {
                    saveItem(journalEntry);
                    Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                    isEdit = false;
                    finish();
                }
                break;
            case R.id.icon_close:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                isEdit = false;
                finish();
                break;
        }
    }

    private void updateItem (final JournalEntry journalEntry) {
        AppExecutors.getExecutors().getDiskIO().execute(new Runnable() {
            @Override
            public void run () {
                DailyBookDatabase
                        .getDatabaseInstance(getApplicationContext())
                        .journalDao()
                        .updateEntry(journalEntry);
            }
        });
    }

    private void saveItem (final JournalEntry journalEntry) {
        AppExecutors.getExecutors().getDiskIO().execute(new Runnable() {
            @Override
            public void run () {
                DailyBookDatabase
                        .getDatabaseInstance(getApplicationContext())
                        .journalDao()
                        .saveEntry(journalEntry);
            }
        });
    }

    @Override
    protected void onStop () {
        super.onStop();
        isEdit = false;
        intent.removeExtra(Constants.POSITION);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        intent.removeExtra(Constants.POSITION);
    }
}
