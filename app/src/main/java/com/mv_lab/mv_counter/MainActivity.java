package com.mv_lab.mv_counter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements OnClickListener {
    Spinner indexNameSpinner;
    Button buttonNewIndex, buttonPlus, buttonMinus, buttonSave;
    EditText edtxValue;
    ArrayList<String> indexList;
    int presentValue = 0;
    String sValue;
    boolean indexValueFound;
    SharedPreferences sPref;
    final String  PRESENT_VALUE_PREF = "PresentValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNewIndex = (Button) findViewById(R.id.buttonNewIndex);
        buttonNewIndex.setOnClickListener(this);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(this);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(this);
        buttonSave = (Button) findViewById(R.id.buttonOK);
        buttonSave.setOnClickListener(this);
        edtxValue = (EditText) findViewById(R.id.editText);

        MV_DataBase.initialize(this);
        indexList = Index.createIndexList();

        if (indexList.isEmpty()) {//open index creator
            Intent intent = new Intent(this, IndexCreator.class);
            startActivity(intent);
        }
        this.indexList.add("...");

        loadPreferences();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.indexList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        indexNameSpinner = (Spinner) findViewById(R.id.index_Name);
        indexNameSpinner.setAdapter(adapter);

        indexNameSpinner.setPrompt("Index name");

        indexNameSpinner.setSelection(Index.getPresentIndexPos());

        indexNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (indexList.get(position).equals("...")) {
                    openIndexCatalog();
                }
                else {
                    Index.setPresentIndexPosition(position);
                    getSavedValue();
                    // Toast.makeText(getBaseContext(), "Pos: " + position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerForContextMenu(indexNameSpinner);

        //getting values from db
        getSavedValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.index_Name :
                menu.add(0, 0, 0, "delete all");
            break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0 :
                ArrayList<String> tablesList =  new ArrayList<String>();
                tablesList.add("indexTable");
                tablesList.add("valueTable");
                MV_DataBase.deleteTables(tablesList);
                Toast.makeText(getBaseContext(), "deleted", Toast.LENGTH_SHORT).show();

                //open index creator screen
                Intent intent = new Intent(this, IndexCreator.class);
                startActivity(intent);
            break;
        }
        return super.onContextItemSelected(item);
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
            case R.id.buttonNewIndex :
                Intent intent = new Intent(this, IndexCreator.class);
                startActivity(intent);
            break;
            case R.id.buttonPlus :
                sValue = edtxValue.getText().toString();
                if (sValue.length() == 0) presentValue = 0;
                else presentValue = Integer.parseInt(sValue);
                presentValue++;
                edtxValue.setText("" + presentValue);
            break;
            case R.id.buttonMinus :
                sValue = edtxValue.getText().toString();
                if (sValue.length() == 0) presentValue = 0;
                else presentValue = Integer.parseInt(sValue);
                presentValue--;
                edtxValue.setText("" + presentValue);
            break;
            case R.id.buttonOK :
                sValue = edtxValue.getText().toString();
                if (sValue.length() == 0) presentValue = 0;
                else presentValue = Integer.parseInt(sValue);
                edtxValue.setText("" + presentValue);

                MV_DataBase.updateIndexValue(Index.getIdByName(indexList.get(Index.getPresentIndexPos())), presentValue);

                saveValue();
                savePreferences();
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.indexList.clear();
        indexList = Index.getSavedIndexList();
        this.indexList.add("...");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.indexList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        indexNameSpinner.setAdapter(adapter);
        indexNameSpinner.setSelection(Index.getPresentIndexPos());
    }

    private void getSavedValue() {
        indexValueFound = false;
        if (indexList.size() > 0) {
            String indexName = indexList.get(Index.getPresentIndexPos());
            long recordId = Index.getIdByName(indexName);
            if (recordId < 0) return;
            presentValue = MV_DataBase.readIndexValue(recordId, indexValueFound);
            edtxValue.setText("" + presentValue);

            //Toast.makeText(getBaseContext(), "" + indexValueFound + " : " + indexName, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveValue() {
        indexValueFound = false;
        if (indexList.size() > 0) {
            String indexName = indexList.get(Index.getPresentIndexPos());
            long recordId = Index.getIdByName(indexName);
            if (recordId < 0) return;

            MV_DataBase.writeIndexValue(recordId, presentValue);
        }
    }

    private void savePreferences() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PRESENT_VALUE_PREF, "" + indexList.get(Index.getPresentIndexPos()));
        ed.commit();
    }

    private void loadPreferences() {
        sPref = getPreferences(MODE_PRIVATE);
        String presentValueName = sPref.getString(PRESENT_VALUE_PREF, "");
        int presentValuePos = indexList.indexOf(presentValueName);
        if (presentValuePos >= 0) Index.setPresentIndexPosition(presentValuePos);
            else Index.setPresentIndexPosition(0);
    }

    private void openIndexCatalog() {
        Intent indexCatalogIntent = new Intent(this, IndexCatalog.class);
        startActivity(indexCatalogIntent);
    }
}
