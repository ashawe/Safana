package com.ashstudios.safana.others;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String IS_LOGGED_IN="IS_LOGGED_IN";
    private final String EMAIL = "EMAIL";
    private final String EMP_ID = "EMP_ID";
    private final String NAME = "NAME";
    private Context context;

    @SuppressLint("CommitPrefEdits")
    public SharedPref(Context context, boolean isLoggedIn, String empid, String emp_name, String emp_email) {
        this.context = context;
        String PREF_NAME = "safana";
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN,isLoggedIn);
        editor.putString(EMP_ID,empid);
        editor.putString(NAME,emp_name);
        editor.putString(EMAIL,emp_email);
        editor.apply();
    }

    public SharedPref(Context context) {
        this.context = context;
        String PREF_NAME = "safana";
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean getIS_LOGGED_IN() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

    public String getEMAIL() {
        return sharedPreferences.getString(EMAIL,null);
    }

    public String getEMP_ID() {
        return sharedPreferences.getString(EMP_ID,null);
    }

    public String getNAME() {
        return sharedPreferences.getString(NAME,null);
    }

    public void logout() {
        editor.putBoolean(IS_LOGGED_IN,false).apply();
    }

    public void setName(String name) {
        editor.putString(NAME,name).apply();
    }

    public void setEmail(String email) {
        editor.putString(EMAIL,email).apply();
    }
}
