package eus.ehu.intel.tta.euskhazi;

import android.content.Context;
import android.util.Log;

import java.net.ContentHandler;

import eus.ehu.intel.tta.euskhazi.services.PreferencesManager;

/**
 * Created by eduardo.zarate on 18/12/2015.
 */
public class Engine {
    private static String TAG = Engine.class.getCanonicalName();
    private static Engine mEngine=null;
    private Context contextApplication=null;
    private PreferencesManager mPreferencesManager;
    public Engine(Context contextApplication){
        this.contextApplication=contextApplication;
        this.mPreferencesManager=PreferencesManager.getInstance();
    }

    public static Engine getInstance(Context contextApplication){
        if(contextApplication==null){
            Log.e(TAG,"The parameter contextApplication is nullpoint");
            return null;
        }
        if(mEngine == null){
            Log.d(TAG,"the instance enfine is created");
            mEngine = new Engine(contextApplication);
        }
        return mEngine;
    }


}
