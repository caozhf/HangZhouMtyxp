package com.mtyxp.hangzhoumtyxp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CaoZhF on 2017-10-24.
 */

public class SpUtils {

    private static SpUtils spUtils = new SpUtils();
    private static SharedPreferences sp;


    public static SpUtils getInstance(Context context){
        if (sp == null){
            sp = context.getSharedPreferences("account", Context.MODE_PRIVATE);
        }
        return spUtils;
    }

    public void save(String key, Object values){
        if (values instanceof String){
            sp.edit().putString(key, (String) values).commit();
        }
        if (values instanceof Boolean){
            sp.edit().putBoolean(key, (Boolean) values).commit();
        }
        if (values instanceof Integer){
            sp.edit().putInt(key, (Integer) values).commit();
        }
    }

    public String getString(String key, String defValues){
        return sp.getString(key,defValues);
    }

    public Boolean getBoolean(String key, boolean defValues){
        return sp.getBoolean(key,defValues);
    }

    public int getInt(String key, int defValues){
        return sp.getInt(key,defValues);
    }

}
