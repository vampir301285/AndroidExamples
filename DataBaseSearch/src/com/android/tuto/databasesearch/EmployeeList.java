package com.android.tuto.databasesearch;

import com.android.tuto.databasesearch.util.DatabaseHelper;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EmployeeList extends ListActivity {

    /** The search text view */
    private EditText searchText;

    /** The list adapter */
    private ListAdapter adapter;

    /** The data base helper */
    private DatabaseHelper dbHelper;

    /** The list view */
    // private ListView employeeListView;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbHelper = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        // employeeListView = (ListView) findViewById(R.id.list);

        searchText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
                    search(v);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Search action
     * 
     * @param view
     *            the current view
     */
    @SuppressWarnings("deprecation")
    public void search(View view) {
        String searchString = searchText.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM employee WHERE first_name || ' ' || last_name LIKE ?", new String[] { "%" + searchString + "%" });
        adapter = new SimpleCursorAdapter(this, R.layout.employee_list_item, cursor, new String[] { DatabaseHelper.COL_FIRST_NAME,
                DatabaseHelper.COL_LAST_NAME, DatabaseHelper.COL_TITLE }, new int[] { R.id.firstName, R.id.lastName, R.id.title });
        // employeeListView.setAdapter(adapter);
        setListAdapter(adapter);
    }

    /**
     * starts new intent to go to detail views
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) adapter.getItem(position);
        Intent intent = new Intent(this, EmployeeDetails.class);
        intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
        startActivity(intent);
    }

}
