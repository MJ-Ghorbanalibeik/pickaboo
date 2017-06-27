package com.pickaboo;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecentValues extends ListActivity {
    private static final int newValueActivityCode = 1;
    private static final String dataFileName = "data.csv";
    private ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_values);
        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
        this.readDataFromFile(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == newValueActivityCode) {
            if (resultCode == Activity.RESULT_OK) {
                Integer new_value = data.getIntExtra(NewValue.NEW_VALUE, 0);
                this.addDataToFile(DateFormat.getDateTimeInstance().format(new Date()), new_value.toString());
                this.readDataFromFile(true);
            }
        }
    }

    public void newValue(View view) {
        Intent intent = new Intent(this, NewValue.class);
        startActivityForResult(intent, newValueActivityCode);
    }

    private void addDataToFile(String dateTime, String value) {
        FileOutputStream outputStream;
        String toCSV = dateTime + ',' + value + '\n';
        try {
            File dataFile = new File(this.getFilesDir(), dataFileName);
            dataFile.getParentFile().mkdirs();
            dataFile.createNewFile();
            outputStream = new FileOutputStream(dataFile, true);
            outputStream.write(toCSV.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readDataFromFile(boolean readLastOnly) {
        try {
            File dataFile = new File(this.getFilesDir(), dataFileName);
            if(!dataFile.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(dataFile));
            String line;
            String lastLine = "";
            while ((line = br.readLine()) != null) {
                if(!readLastOnly)
                    listItems.add(line);
                lastLine = line;
            }
            br.close();
            if(readLastOnly)
                listItems.add(lastLine);
            adapter.notifyDataSetChanged();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
