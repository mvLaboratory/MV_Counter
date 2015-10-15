package com.mv_lab.mv_counter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by admin on 02.01.2015.
 */
public class Index {
    private static ArrayList<String> indexList = new ArrayList<String>();
    private static ArrayList<String> newIndexList = new ArrayList<String>();
    private static ArrayList<String> savedIndexList = new ArrayList<String>();

    private static HashMap<Long, String> savedIndexMap = new HashMap<Long, String>();
    private static HashMap<Long, String> newIndexMap = new HashMap<Long, String>();
    private static int presentPos = 0;
//    private static final String ALL_INDEX_STRING = "...";

    public static ArrayList<String> getIndexList() {
//        int indexSize = indexList.size();
//        if (!indexList.get(indexSize - 1).equals(ALL_INDEX_STRING))
//            indexList.add(indexList.size(), ALL_INDEX_STRING);
        return (ArrayList<String>) indexList.clone();
    }

    public static ArrayList<String> getSavedIndexList() {
        savedIndexList = new ArrayList<String>();
        savedIndexMap = MV_DataBase.ReadIndex();
        for (HashMap.Entry<Long, String> dbRow : savedIndexMap.entrySet()) {
            savedIndexList.add(dbRow.getValue());
        }
        return (ArrayList<String>) savedIndexList.clone();
    }

    public static ArrayList<String> getNewIndexList() {
        return newIndexList;
    }

    public static void addNewIndex(String newIndex) {
        Index.newIndexList.add(newIndex);
        Index.indexList.add(newIndex);
        presentPos = indexList.size() - 1;
    }

    public static void addNewIndex(String newIndex, Long id) {
        Index.newIndexList.add(newIndex);
        Index.indexList.add(newIndex);
        newIndexMap.put(id, newIndex);
        savedIndexMap.put(id, newIndex);
        presentPos = indexList.size() - 1;
    }

    public static int getPresentIndexPos() {
        return presentPos;
    }

    public static void setPresentIndexPosition(int presentPos) {
        Index.presentPos = presentPos;
    }

    public static ArrayList<String> createIndexList() {
        indexList = new ArrayList<String>();
        indexList.addAll(getSavedIndexList());
        presentPos = 0;

        return (ArrayList<String>) indexList.clone();
    }

    public static boolean canAddNewIndex(String newIndexName) {
        if (indexList.contains(newIndexName)) return false;
        else return true;
    }

    public static long getIdByName(String indexName) {
        for (HashMap.Entry<Long, String> row: savedIndexMap.entrySet()) {
            if (row.getValue().equals(indexName)) return row.getKey();
        }
        return -1;
    }
}
