package eus.ehu.intel.tta.euskhazi.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by eduardo.zarate on 18/12/2015.
 */
public class PreferencesManager {
    private static String TAG = PreferencesManager.class.getCanonicalName();
    private static PreferencesManager mPreferencesManager=null;
    private static final String PREFERENCE_ID=TAG+"PREFERENCE_ID";
    public static final String STRING_DEFAULT=TAG+"";



    SharedPreferences sharedPref;
    public PreferencesManager(){

    }
    private SharedPreferences createSharedPreferences(Context context){
        if(context==null){
            return null;
        }
        sharedPref = context.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);
        return sharedPref;
    }
    public static PreferencesManager getInstance(){
        if(mPreferencesManager==null){
            mPreferencesManager=new PreferencesManager();
        }
        return mPreferencesManager;
    }

    public boolean putString(Context context,String key,String data){
        if(context==null || key==null || data==null){
            Log.e(TAG,"Some parameter is nullpoint in putString");
           return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putString(key,data);
        return editor.commit();
    }

    public String getString(Context context,String key){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is nullpoint in getString");
            return null;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        return createSharedPreferences(context).getString(key,STRING_DEFAULT);

    }

    public boolean putInt(Context context,String key,int data){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is nullpoint in putString");
            return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putInt(key,data);
        return editor.commit();
    }

    public int getInt(Context context,String key){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is nullpoint in getString");
            return -1;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        return createSharedPreferences(context).getInt(key,-1);

    }
}
