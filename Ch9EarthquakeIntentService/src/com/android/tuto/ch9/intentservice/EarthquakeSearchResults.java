package com.android.tuto.ch9.intentservice;

import com.android.tuto.ch9.intentservice.util.DatabaseHelper;

import android.app.ListActivity;
import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class EarthquakeSearchResults extends ListActivity {

    private SimpleCursorAdapter adapter;

    private DatabaseHelper dbHelper;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        dbHelper = DatabaseHelper.getInstance(this);

        String searchQuery = getIntent().getStringExtra(SearchManager.QUERY);
        Cursor cursor = dbHelper.searchQuakesAsRawData(searchQuery, 0);
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { DatabaseHelper.COL_SUMMARY_3 },
                new int[] { android.R.id.text1 }, 0);
        setListAdapter(adapter);
    }
}
