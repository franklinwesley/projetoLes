package com.example.diego.myapplication.Persistencia;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class BDHelper {
    Map<String,DataBaseHelper> bds;
    Context context;

    public BDHelper (Context context) {
        this.context = context;
        bds = new HashMap<String,DataBaseHelper>();
    }

    public DataBaseHelper getBD(String id) {
        DataBaseHelper result = null;
        if (bds.get(id) != null) {
            result = bds.get(id);
        } else {
            bds.put(id, new DataBaseHelper(context));
            result = bds.get(id);
        }
        return result;
    }
}
