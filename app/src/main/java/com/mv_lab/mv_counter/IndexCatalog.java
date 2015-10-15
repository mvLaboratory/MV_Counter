package com.mv_lab.mv_counter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class IndexCatalog extends ActionBarActivity implements View.OnClickListener {

    private static ArrayList<String> indexList = new ArrayList<String>();
    private static ArrayList<String> newIndexList = new ArrayList<String>();
    private static ArrayList<String> savedIndexList = new ArrayList<String>();

    private static HashMap<Long, String> savedIndexMap = new HashMap<Long, String>();
    private static HashMap<Long, String> newIndexMap = new HashMap<Long, String>();
    private static int presentPos = 0;

    private void createActivity() {
        RelativeLayout relLayout = new RelativeLayout(this);
        relLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        TableLayout tabLayout = new TableLayout(this);
        RelativeLayout.LayoutParams tabLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tabLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tabLayoutParams.addRule(RelativeLayout.ABOVE, R.id.buttonNewIndex);
//        tabLayout.setBackgroundResource(R.color.bgBlack);
        tabLayout.setLayoutParams(tabLayoutParams);

        //table cap+++
        TableRow newTabRow = new TableRow(this);
        LayoutParams rowLayParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        newTabRow.setBackgroundResource(R.color.bgBlack);
        newTabRow.setLayoutParams(rowLayParams);

        TextView textViewIDCap = new TextView(this);
        textViewIDCap.setText("ID");
        textViewIDCap.setTextColor(Color.BLACK);
        textViewIDCap.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams tvIDLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
        tvIDLayoutParams.setMargins(0, 0, 1, 0);
        textViewIDCap.setBackgroundResource(R.color.bgColor);
        textViewIDCap.setGravity(Gravity.CENTER_HORIZONTAL);
        textViewIDCap.setLayoutParams(tvIDLayoutParams);
        newTabRow.addView(textViewIDCap);

        TextView textViewNameCap = new TextView(this);
        textViewNameCap.setText("Name");
        textViewNameCap.setTextColor(Color.BLACK);
        textViewNameCap.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams tvNameLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 30f);
        tvNameLayoutParams.setMargins(1, 0, 1, 0);
        textViewNameCap.setBackgroundResource(R.color.bgColor);
        textViewNameCap.setGravity(Gravity.CENTER_HORIZONTAL);
        textViewNameCap.setLayoutParams(tvNameLayoutParams);
        newTabRow.addView(textViewNameCap);

        TextView tvEditCap = new TextView(this);
        tvEditCap.setText("Edit");
        tvEditCap.setTextColor(Color.BLACK);
        tvEditCap.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams tvEditLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2f);
        tvEditLayoutParams.setMargins(1, 0, 1, 0);
        tvEditCap.setBackgroundResource(R.color.bgColor);
        tvEditCap.setGravity(Gravity.CENTER_HORIZONTAL);
        tvEditCap.setLayoutParams(tvEditLayoutParams);
        newTabRow.addView(tvEditCap);

        TextView tvDelCap = new TextView(this);
        tvDelCap.setText("Delete");
        tvDelCap.setTextColor(Color.BLACK);
        tvDelCap.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams tvDeleteLayoutParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);
        tvDeleteLayoutParams.setMargins(1, 0, 1, 0);
        tvDelCap.setBackgroundResource(R.color.bgColor);
        tvDelCap.setGravity(Gravity.CENTER_HORIZONTAL);
        tvDelCap.setLayoutParams(tvDeleteLayoutParams);
        newTabRow.addView(tvDelCap);

        tabLayout.addView(newTabRow);
        //table cap---

        readSavedIndexes();
        //int rowNumber = 0;
        int bottomRowMargin = 1;
        int rowId;
        //Catalog+++
        for (HashMap.Entry<Long, String> indexRow : savedIndexMap.entrySet()) {
            // rowNumber++;
            // bottomRowMargin = (rowNumber % 2 == 0) ? 1 : 0;
            rowId = Integer.parseInt("" + indexRow.getKey()) * 1000;

            newTabRow = new TableRow(this);
            LayoutParams rowElementLayParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            newTabRow.setLayoutParams(rowElementLayParams);
            newTabRow.setBackgroundResource(R.color.bgBlack);

            TextView textViewID = new TextView(this);
            textViewID.setText("" + indexRow.getKey());
            TableRow.LayoutParams tvIdLayParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f);
            tvIdLayParam.setMargins(0, bottomRowMargin, 1, 0);
            textViewID.setLayoutParams(tvIdLayParam);
            textViewID.setBackgroundResource(R.color.bgWhite);
            textViewID.setId(rowId + 1);
            textViewID.setOnClickListener(this);
            newTabRow.addView(textViewID);

            TextView textViewName = new TextView(this);
            textViewName.setText("" + "" + indexRow.getValue());
            TableRow.LayoutParams tvNameLayParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 30f);
            tvNameLayParam.setMargins(1, bottomRowMargin, 1, 0);
            textViewName.setLayoutParams(tvNameLayParam);
            textViewName.setBackgroundResource(R.color.bgWhite);
            textViewName.setOnClickListener(this);
            textViewName.setId(rowId + 2);
            newTabRow.addView(textViewName);

            Button butEdit = new Button(this);
            butEdit.setText("I");
            TableRow.LayoutParams btEdLayParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2f);
            btEdLayParam.setMargins(1, bottomRowMargin, 1, 0);
            butEdit.setLayoutParams(btEdLayParam);
            butEdit.setBackgroundResource(R.color.bgWhite);
            butEdit.setOnClickListener(this);
            butEdit.setId(rowId + 3);
            newTabRow.addView(butEdit);

            Button butDel = new Button(this);
            butDel.setText("X");
            TableRow.LayoutParams btDelLayParam = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);
            btDelLayParam.setMargins(1, bottomRowMargin, 1, 0);
            butDel.setLayoutParams(btDelLayParam);
            butDel.setBackgroundResource(R.color.bgWhite);
            butDel.setId(rowId + 4);
            butDel.setOnClickListener(this);
            newTabRow.addView(butDel);

            tabLayout.addView(newTabRow);
        }
        //Catalog---

        relLayout.addView(tabLayout);

        Button btnNew = new Button(this);
        RelativeLayout.LayoutParams btnRelLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btnRelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        btnRelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        btnRelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        btnNew.setLayoutParams(btnRelLayoutParams);
        btnNew.setText("New");
        btnNew.setId(R.id.buttonNewIndex);
        btnNew.setTypeface(null, Typeface.BOLD);
        btnNew.setOnClickListener(this);
        relLayout.addView(btnNew);

        setContentView(relLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivity();
//        RelativeLayout relLayout = new RelativeLayout(this);
//        relLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//        TableLayout tabLayout = new TableLayout(this);
//        RelativeLayout.LayoutParams tabLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        tabLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        tabLayoutParams.addRule(RelativeLayout.ABOVE, R.id.buttonNewIndex);
////        tabLayout.setBackgroundResource(R.color.bgBlack);
//        tabLayout.setLayoutParams(tabLayoutParams);
//
//        //table cap+++
//        TableRow newTabRow = new TableRow(this);
//        LayoutParams rowLayParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        newTabRow.setBackgroundResource(R.color.bgBlack);
//        newTabRow.setLayoutParams(rowLayParams);
//
//        TextView textViewIDCap = new TextView(this);
//        textViewIDCap.setText("ID");
//        textViewIDCap.setTextColor(Color.BLACK);
//        textViewIDCap.setTypeface(null, Typeface.BOLD);
//        TableRow.LayoutParams tvIDLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
//        tvIDLayoutParams.setMargins(0, 0, 1, 0);
//        textViewIDCap.setBackgroundResource(R.color.bgColor);
//        textViewIDCap.setGravity(Gravity.CENTER_HORIZONTAL);
//        textViewIDCap.setLayoutParams(tvIDLayoutParams);
//        newTabRow.addView(textViewIDCap);
//
//        TextView textViewNameCap = new TextView(this);
//        textViewNameCap.setText("Name");
//        textViewNameCap.setTextColor(Color.BLACK);
//        textViewNameCap.setTypeface(null, Typeface.BOLD);
//        TableRow.LayoutParams tvNameLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 30f);
//        tvNameLayoutParams.setMargins(1, 0, 1, 0);
//        textViewNameCap.setBackgroundResource(R.color.bgColor);
//        textViewNameCap.setGravity(Gravity.CENTER_HORIZONTAL);
//        textViewNameCap.setLayoutParams(tvNameLayoutParams);
//        newTabRow.addView(textViewNameCap);
//
//        TextView tvEditCap = new TextView(this);
//        tvEditCap.setText("Edit");
//        tvEditCap.setTextColor(Color.BLACK);
//        tvEditCap.setTypeface(null, Typeface.BOLD);
//        TableRow.LayoutParams tvEditLayoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2f);
//        tvEditLayoutParams.setMargins(1, 0, 1, 0);
//        tvEditCap.setBackgroundResource(R.color.bgColor);
//        tvEditCap.setGravity(Gravity.CENTER_HORIZONTAL);
//        tvEditCap.setLayoutParams(tvEditLayoutParams);
//        newTabRow.addView(tvEditCap);
//
//        TextView tvDelCap = new TextView(this);
//        tvDelCap.setText("Delete");
//        tvDelCap.setTextColor(Color.BLACK);
//        tvDelCap.setTypeface(null, Typeface.BOLD);
//        TableRow.LayoutParams tvDeleteLayoutParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);
//        tvDeleteLayoutParams.setMargins(1, 0, 1, 0);
//        tvDelCap.setBackgroundResource(R.color.bgColor);
//        tvDelCap.setGravity(Gravity.CENTER_HORIZONTAL);
//        tvDelCap.setLayoutParams(tvDeleteLayoutParams);
//        newTabRow.addView(tvDelCap);
//
//        tabLayout.addView(newTabRow);
//        //table cap---
//
//        readSavedIndexes();
//        //int rowNumber = 0;
//        int bottomRowMargin = 1;
//        int rowId;
//        //Catalog+++
//        for (HashMap.Entry<Long, String> indexRow : savedIndexMap.entrySet()) {
//           // rowNumber++;
//           // bottomRowMargin = (rowNumber % 2 == 0) ? 1 : 0;
//            rowId = Integer.parseInt("" + indexRow.getKey()) * 1000;
//
//            newTabRow = new TableRow(this);
//            LayoutParams rowElementLayParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            newTabRow.setLayoutParams(rowElementLayParams);
//            newTabRow.setBackgroundResource(R.color.bgBlack);
//
//            TextView textViewID = new TextView(this);
//            textViewID.setText("" + indexRow.getKey());
//            TableRow.LayoutParams tvIdLayParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f);
//            tvIdLayParam.setMargins(0, bottomRowMargin, 1, 0);
//            textViewID.setLayoutParams(tvIdLayParam);
//            textViewID.setBackgroundResource(R.color.bgWhite);
//            textViewID.setId(rowId + 1);
//            newTabRow.addView(textViewID);
//
//            TextView textViewName = new TextView(this);
//            textViewName.setText("" + "" + indexRow.getValue());
//            TableRow.LayoutParams tvNameLayParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 30f);
//            tvNameLayParam.setMargins(1, bottomRowMargin, 1, 0);
//            textViewName.setLayoutParams(tvNameLayParam);
//            textViewName.setBackgroundResource(R.color.bgWhite);
//            textViewName.setOnClickListener(this);
//            textViewName.setId(rowId + 2);
//            newTabRow.addView(textViewName);
//
//            Button butEdit = new Button(this);
//            butEdit.setText("I");
//            TableRow.LayoutParams btEdLayParam = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2f);
//            btEdLayParam.setMargins(1, bottomRowMargin, 1, 0);
//            butEdit.setLayoutParams(btEdLayParam);
//            butEdit.setBackgroundResource(R.color.bgWhite);
//            butEdit.setOnClickListener(this);
//            butEdit.setId(rowId + 3);
//            newTabRow.addView(butEdit);
//
//            Button butDel = new Button(this);
//            butDel.setText("X");
//            TableRow.LayoutParams btDelLayParam = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);
//            btDelLayParam.setMargins(1, bottomRowMargin, 1, 0);
//            butDel.setLayoutParams(btDelLayParam);
//            butDel.setBackgroundResource(R.color.bgWhite);
//            butDel.setId(rowId + 4);
//            butDel.setOnClickListener(this);
//            newTabRow.addView(butDel);
//
//            tabLayout.addView(newTabRow);
//        }
//        //Catalog---
//
//        relLayout.addView(tabLayout);
//
//        Button btnNew = new Button(this);
//        RelativeLayout.LayoutParams btnRelLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        btnRelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        btnRelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        btnRelLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        btnNew.setLayoutParams(btnRelLayoutParams);
//        btnNew.setText("New");
//        btnNew.setId(R.id.buttonNewIndex);
//        btnNew.setTypeface(null, Typeface.BOLD);
//        btnNew.setOnClickListener(this);
//        relLayout.addView(btnNew);
//
//        setContentView(relLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_catalog, menu);
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
        boolean idFound = false;
        int viewID = v.getId();
        switch (viewID) {
            case R.id.buttonNewIndex :
                idFound = true;
                Intent intent = new Intent(this, IndexCreator.class);
                startActivity(intent);
            break;
        }

        if (idFound) return;

        double rowIDParse = (double) viewID / 1000;
        long rowID = (long) rowIDParse;
        int btnID = viewID % 1000;

        switch (btnID) {
            case 4 :
                MV_DataBase.deleteIndex(rowID);
                createActivity();
            break;

            case 3 :
            break;

            case 2 :
                Index.setPresentIndexPosition(Index.getIndexList().indexOf(savedIndexMap.get(rowID)));
                finish();
            break;

            case 1:
                Index.setPresentIndexPosition(Index.getIndexList().indexOf(savedIndexMap.get(rowID)));
                finish();
            break;
        }
    }

    public static void readSavedIndexes() {
        savedIndexList = new ArrayList<String>();
        savedIndexMap = MV_DataBase.ReadIndex();
        for (HashMap.Entry<Long, String> dbRow : savedIndexMap.entrySet()) {
            savedIndexList.add(dbRow.getValue());
        }
    }
}
