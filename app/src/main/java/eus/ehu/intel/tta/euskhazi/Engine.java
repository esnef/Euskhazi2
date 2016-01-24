package eus.ehu.intel.tta.euskhazi;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.PreferencesManager;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Mobile;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.Atarikoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.berridazketak.Berridazketak;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa.Entzunezkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa.Idatzizkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.Sinonimoak;
import eus.ehu.intel.tta.euskhazi.utils.UtilsServices;

/**
 * Created by eduardo.zarate on 18/12/2015.
 */
public class Engine {
    private static String TAG = Engine.class.getCanonicalName();
    private static Engine mEngine=null;
    private static LevelsManager mLevelsManager;
    private Context contextApplication=null;
    private UsersManager mUsersManager;
    private Mobile mMobile;
    private User mUserNow;
    public static final String[] LEVEL_TYPES={"A1","A2","B1","B2","C1"};


    private static final String PREFERENCE_MOBILE_ID=TAG+"PREFERENCE_MOBILE_ID";


    private ArrayList<Level> mLevels;



    public Engine(Context contextApplication){
        this.contextApplication=contextApplication;
        this.mLevelsManager=LevelsManager.getInstance(contextApplication);
        this.mUsersManager=UsersManager.getInstance(contextApplication);


    }


    public static Engine getInstance(Context contextApplication){
        if(contextApplication==null){
            Log.e(TAG,"The parameter contextApplication is nullpoint");
            return null;
        }
        if(mEngine == null){
            Log.d(TAG,"the instance engine is created");
            mEngine = new Engine(contextApplication);
        }
        return mEngine;
    }

    public boolean initialization(){
        return mUsersManager.initialization();
    }

    public Context getContextApplication() {
        return contextApplication;
    }

    public void setContextApplication(Context contextApplication) {
        this.contextApplication = contextApplication;
    }

    //INIT USERS//

    public boolean saveUser(){
        return mUsersManager.saveUser();
    }


    public boolean addUser(User user) throws UsersManager.ExcepcionUser {
        return mUsersManager.addUser(user);
    }

    public boolean getUser(User user) throws UsersManager.ExcepcionUser {
        return mUsersManager.getUser(user);
    }
    public boolean getUser(String name,String password) throws UsersManager.ExcepcionUser {
        return mUsersManager.getUser(name, password);
    }

    public boolean getUserNow(){
        return mUsersManager.getUserNow();
    }

    public boolean isUser(User user) throws  UsersManager.ExcepcionUser {
        return mUsersManager.isUser(user);
    }
    public boolean getAllUser(){
        return mUsersManager.getAllUser();
    }

    /**
     *
     * @param name
     * @param password
     * @return
     * @throws UsersManager.ExcepcionUser
     */
    public boolean isUser(String name,String password) throws UsersManager.ExcepcionUser {
        return mUsersManager.isUser(name, password);
    }

    public boolean isUser(String name) throws UsersManager.ExcepcionUser {
        return mUsersManager.isUser(name);
    }


    public void setOnGetAllUserListener(UsersManager.OnGetAllUserListener onGetAllUserListener){
        mUsersManager.setOnGetAllUserListener(onGetAllUserListener);

    }


    public void setOnGetUserListener(UsersManager.OnGetUserListener onGetUserListener){
        mUsersManager.setOnGetUserListener(onGetUserListener);
    }



    public void setOnGetUserNowListener(UsersManager.OnGetUserNowListener onGetUserNowListener){
        mUsersManager.setOnGetUserNowListener(onGetUserNowListener);
    }



    public void setOnIsUserListener(UsersManager.OnIsUserListener onIsUserListener){
        mUsersManager.setOnIsUserListener(onIsUserListener);
    }



    public void setOnAddUserListener(UsersManager.OnAddUserListener onAddUserListener){
        mUsersManager.setOnAddUserListener(onAddUserListener);
    }



    public void setOnSaveUserListener(UsersManager.OnSaveUserListener onSaveUserListener){
        mUsersManager.setOnSaveUserListener(onSaveUserListener);
    }

    public void setOnUserListener(UsersManager.OnUserListener onUserListener){
        mUsersManager.setOnUserListener(onUserListener);
    }

    public void setOnInitializationOfflineListener(UsersManager.OnInitializationOfflineListener onInitializationOfflineListener){
        mUsersManager.setOnInitializationOfflineListener(onInitializationOfflineListener);
    }


    public void setOnInitializationOnlineListener(UsersManager.OnInitializationOnlineListener onInitializationOnlineListener){
        mUsersManager.setOnInitializationOnlineListener(onInitializationOnlineListener);
    }


    //END USERS//





    //LEVEL INIT//
    public boolean getLevels(){
        return mLevelsManager.getLevels();
    }

    public boolean getLevel(String levelType){
        return mLevelsManager.getLevel(levelType);
    }

    public void setOnExamsListener(LevelsManager.OnExamsListener onExamsListener){
        mLevelsManager.setOnExamsListener(onExamsListener);
    }

    public void setOnGetLevelsListener(LevelsManager.OnGetLevelsListener onGetLevelsListener){
        mLevelsManager.setOnGetLevelsListener(onGetLevelsListener);
    }

    public void setOnGetLevelListener(LevelsManager.OnGetLevelListener onGetLevelListener){
        mLevelsManager.setOnGetLevelListener(onGetLevelListener);
    }
    //LEVEL END//




}
