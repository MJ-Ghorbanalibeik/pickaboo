package com.pickaboo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.SeekBar;

public class NewValue extends AppCompatActivity {
    public static final String NEW_VALUE = "com.pickaboo.NEW_VALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_value);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void addValue(View view) {
        Intent intent = new Intent(this, RecentValues.class);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        Integer value = seekBar.getProgress();
        intent.putExtra(NEW_VALUE, value);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
