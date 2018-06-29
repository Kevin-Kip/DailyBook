package com.truekenyan.dailybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.truekenyan.dailybook.R;
import com.truekenyan.dailybook.database.DailyBookDatabase;
import com.truekenyan.dailybook.fragments.HomeFragment;
import com.truekenyan.dailybook.models.JournalEntry;
import com.truekenyan.dailybook.utilities.AppExecutors;
import com.truekenyan.dailybook.utilities.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings ("WeakerAccess")
public class DetailsActivity extends AppCompatActivity {

    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.date_display)
    TextView dateDisplay;
    @BindView (R.id.day_display)
    TextView dayDisplay;
    @BindView (R.id.month_and_year_display)
    TextView monthAndYearDisplay;
    @BindView (R.id.time_display)
    TextView timeDisplay;
    @BindView (R.id.content_display)
    TextView contentDisplay;

    private int position;
    private JournalEntry journalEntry;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<JournalEntry> entryList;
        entryList = HomeFragment.getList();

        Intent intent = getIntent();
        position = Integer.parseInt(String.valueOf(intent.getIntExtra(Constants.POSITION, 0)));
        journalEntry = entryList.get(position);

        dateDisplay.setText(journalEntry.getWriteDate());
        dayDisplay.setText(journalEntry.getWriteDay());
        monthAndYearDisplay.setText(String.format("%s %s",journalEntry.getWriteMonth(),journalEntry.getWriteYear()));
        timeDisplay.setText(journalEntry.getWriteTime());
        contentDisplay.setText(journalEntry.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.edit:
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra(Constants.POSITION, position);
                startActivity(intent);
                break;
            case R.id.delete:
                new MaterialDialog.Builder(this)
                        .title("Confirmation")
                        .content("Are you sure you want to delete?")
                        .positiveText("SURE")
                        .negativeText("NO")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                AppExecutors.getExecutors().getDiskIO().execute(new Runnable() {
                                    @Override
                                    public void run () {
                                        DailyBookDatabase
                                                .getDatabaseInstance(getApplicationContext())
                                                .journalDao()
                                                .deleteEntry(journalEntry);
                                    }
                                });
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick (@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                .show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
