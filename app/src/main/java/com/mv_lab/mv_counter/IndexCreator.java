package com.mv_lab.mv_counter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//data base+++
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.HashMap;
//----


public class IndexCreator extends ActionBarActivity implements View.OnClickListener {
    Button okButton;
    EditText indexNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_creator);

        okButton = (Button) findViewById(R.id.buttonOK);
        okButton.setOnClickListener(this);
        indexNameEdit = (EditText) findViewById(R.id.newIndexName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_creator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOK:
                String newIndexName = indexNameEdit.getText().toString();
                if (Index.canAddNewIndex(newIndexName)) {
                    long newIndexId = MV_DataBase.WriteIndex(newIndexName);
                    Index.addNewIndex(newIndexName, newIndexId);
                    finish(); }
                else Toast.makeText(getBaseContext(), "ERROR! You already have such index!", Toast.LENGTH_LONG).show();
            break;
        }
    }
}
