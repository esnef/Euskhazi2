package eus.ehu.intel.tta.euskhazi.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.services.dataType.Mobile;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;
import eus.ehu.intel.tta.euskhazi.utils.UtilsServices;

/**
 * Created by alumno on 11/01/16.
 */
public class UsersManager {
    private final static String TAG = UsersManager.class.getCanonicalName();
    private static UsersManager mUsersManager;
    private final  Context contextApplication;
    private Mobile mMobile;
    private User mUserNow;
    private static final String PREFERENCE_MOBILE_ID=TAG+"PREFERENCE_MOBILE_ID";



    //Eventos
    private OnUserListener onUserListener=null;
    private PreferencesManager mPreferencesManager;
    private OnSaveUserListener onSaveUserListener;
    private OnGetUserListener onGetUserListener;
    private OnAddUserListener onAddUserListener;
    private OnGetUserNowListener onGetUserNowListener;
    private OnIsUserListener onIsUserListener;
    private OnGetAllUserListener onGetAllUserListener;

    public UsersManager(Context contextApplication){
        this.contextApplication=contextApplication;
        this.mPreferencesManager=PreferencesManager.getInstance();
        updateMobile();

    }


    public static UsersManager getInstance(Context contextApplication){
        if(contextApplication==null){
            Log.e(TAG, "The parameter contextApplication is nullpoint");
            return null;
        }
        if(mUsersManager == null){
            Log.d(TAG,"the instance UsersManager is created");
            mUsersManager = new UsersManager(contextApplication);
        }
        return mUsersManager;
    }

    //INIT USERS//

    public boolean saveUser(){
        if(mMobile!=null){
            if(saveMobile()){
                if(onUserListener!=null) onUserListener.onSaveUser(true);
                if(onSaveUserListener!=null) onSaveUserListener.onSaveUser(true);
                return true;
            }

        }
        if(onUserListener!=null) onUserListener.onSaveUser(false);
        if(onSaveUserListener!=null) onSaveUserListener.onSaveUser(false);


        return false;
    }


    public boolean addUser(User user) throws ExcepcionUser {
        if(user==null || user.getName()==null || user.getPass()==null || user.getName().equals("") || user.getPass().equals("")){
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        if(!isUserPrivate(user)){
            if(mMobile!=null){
                mMobile.getUsers().add(user);

                 boolean result=saveMobile();
                    if(onUserListener!=null) onUserListener.onAddUser(result);
                    if(onAddUserListener!=null) onAddUserListener.onAddUser(result);
                return result;

                //Version segundo hilo
                /*
                SaveMobileAsyncTask saveMobileAsyncTask=new SaveMobileAsyncTask();
                saveMobileAsyncTask.execute(mMobile);
                return true;
                */


            }
        }
        if(onUserListener!=null) onUserListener.onAddUser(false);
        if(onAddUserListener!=null) onAddUserListener.onAddUser(false);


        return false;
    }

    public boolean getUser(User user) throws ExcepcionUser {
        if(user==null){
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        if(mMobile==null){
            List<User> users=mMobile.getUsers();
            for(int con=0;con<users.size();con++){
                User userFor=users.get(con);
                if(userFor.getName().equals(user.getName()) && userFor.getPass().equals(user.getPass())){
                    if(onUserListener!=null) onUserListener.onGetUser(userFor);
                    if(onGetUserListener!=null)onGetUserListener.onGetUser(userFor);
                    return true;
                }
            }
        }
        if(onUserListener!=null)onUserListener.onGetUser(null);
        if(onGetUserListener!=null)onGetUserListener.onGetUser(null);
        return false;
    }
    public boolean getUser(String name,String password) throws ExcepcionUser {
        if(name==null || password==null || name.equals("") || password.equals("")) {
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        User user=new User();
        user.setName(name);
        user.setPass(password);
        return getUser(user);
    }

    public boolean getUserNow(){
        if(mUserNow!=null){
            if(onUserListener!=null)onUserListener.onGetUserNow(mUserNow);
            if(onGetUserNowListener!=null)onGetUserNowListener.onGetUserNow(mUserNow);
        }else{
            return false;
        }
        return true;
    }

    private boolean isUserPrivate(User user) throws ExcepcionUser {
        if(user==null){
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        if(mMobile!=null){
            List<User> users=mMobile.getUsers();
            for(int con=0;con<users.size();con++){
                User userFor=users.get(con);
                if(userFor.getName().equals(user.getName()) && userFor.getPass().equals(user.getPass())){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUser(User user) throws ExcepcionUser {
        if(user==null){
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        if(mMobile!=null){
            List<User> users=mMobile.getUsers();
            for(int con=0;con<users.size();con++){
                User userFor=users.get(con);
                if(userFor.getName().equals(user.getName()) && userFor.getPass().equals(user.getPass())){
                    if(onUserListener!=null)onUserListener.onIsUser(true);
                    if(onIsUserListener!=null)onIsUserListener.onIsUser(true);

                    return true;
                }
            }
        }
        if(onUserListener!=null)onUserListener.onIsUser(false);
        if(onIsUserListener!=null)onIsUserListener.onIsUser(false);

        return false;
    }

    public boolean isUser(String name) throws ExcepcionUser {
        if(name==null || name.equals("")) {
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        User user=new User();
        user.setName(name);
        if(mMobile!=null){
            List<User> users=mMobile.getUsers();
            for(int con=0;con<users.size();con++){
                User userFor=users.get(con);
                if(userFor.getName().equals(user.getName())){
                    if(onUserListener!=null)onUserListener.onIsUser(true);
                    if(onIsUserListener!=null)onIsUserListener.onIsUser(true);
                    return true;
                }
            }
        }
        if(onUserListener!=null)onUserListener.onIsUser(false);
        if(onIsUserListener!=null)onIsUserListener.onIsUser(false);
        return false;
    }


    public boolean isUser(String name,String password) throws ExcepcionUser {
        if(name==null || password==null || name.equals("") || password.equals("")) {
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        User user=new User();
        user.setName(name);
        user.setPass(password);
        return isUser(user);
    }

    public boolean getAllUser(){
        if(mMobile!=null){
            if(mMobile.getUsers()!=null){
                if(onUserListener!=null)onUserListener.onGetAllUser(mMobile.getUsers());
                if(onGetAllUserListener!=null)onGetAllUserListener.onGetAllUser(mMobile.getUsers());
                return true;
            }

        }
        if(onUserListener!=null)onUserListener.onGetAllUser(null);
        if(onGetAllUserListener!=null)onGetAllUserListener.onGetAllUser(null);
        return false;
    }


    public interface OnGetAllUserListener{
        public void onGetAllUser(List<User> users);
    }

    public void setOnGetAllUserListener(OnGetAllUserListener onGetAllUserListener){
        this.onGetAllUserListener=onGetAllUserListener;
    }

    public interface OnGetUserListener{
        public void onGetUser(User user);
    }

    public void setOnGetUserListener(OnGetUserListener onGetUserListener){
        this.onGetUserListener=onGetUserListener;
    }

    public interface OnGetUserNowListener{
        public void onGetUserNow(User user);
    }

    public void setOnGetUserNowListener(OnGetUserNowListener onGetUserNowListener){
        this.onGetUserNowListener=onGetUserNowListener;
    }

    public interface OnIsUserListener{
        public void onIsUser(boolean isUser);
    }

    public void setOnIsUserListener(OnIsUserListener onIsUserListener){
        this.onIsUserListener=onIsUserListener;
    }

    public interface OnAddUserListener{
        public void onAddUser(boolean isAddUser);
    }

    public void setOnAddUserListener(OnAddUserListener onAddUserListener){
        this.onAddUserListener=onAddUserListener;
    }

    public interface OnSaveUserListener{
        public void onSaveUser(boolean isSaveUser);
    }

    public void setOnSaveUserListener(OnSaveUserListener onSaveUserListener){
        this.onSaveUserListener=onSaveUserListener;
    }

    public interface OnUserListener{
        public void onGetUser(User user);
        public void onGetUserNow(User user);
        public void onIsUser(boolean isUser);
        public void onAddUser(boolean isAddUser);
        public void onSaveUser(boolean isSaveUser);
        public void onGetAllUser(List<User> users);
    }

    public void setOnUserListener(OnUserListener onUserListener){
        this.onUserListener=onUserListener;
    }

    public class ExcepcionUser extends Exception {
        public static final int PARAMETERS_NULL=1;
        public ExcepcionUser(String msg) {
            super(msg);
        }
    }

    //END USERS//

    //MOBILE INIT//
    private boolean saveMobile(){
        //Generamos el json
        Gson gson=new Gson();
        String json=gson.toJson(mMobile);
        return mPreferencesManager.putString(contextApplication, PREFERENCE_MOBILE_ID, json);
    }

    private boolean saveMobile(Mobile mobile){
        if(mobile==null)return false;
        //Generamos el json
        Gson gson=new Gson();
        String json=gson.toJson(mobile);
        PreferencesManager preferencesManager=new PreferencesManager();
        return preferencesManager.putString(contextApplication, PREFERENCE_MOBILE_ID, json);
    }

    private boolean updateMobile(){
        String json=mPreferencesManager.getString(contextApplication, PREFERENCE_MOBILE_ID);
        if(!json.equals(PreferencesManager.STRING_DEFAULT) && json!=null){
            Gson gson=new Gson();
            mMobile=gson.fromJson(json,Mobile.class);
        }else{
            //No hay movil guardado con lo que hay que crearlo
            mMobile=new Mobile();
            mMobile.setMobilesMAC(UtilsServices.getMACaddress(contextApplication));
            mMobile.setUsers(new ArrayList<User>());
        }
        return false;
    }
    /*
    private boolean updateMobile(Mobile mobile){
        PreferencesManager preferencesManager=new PreferencesManager();
        String json=preferencesManager.getString(contextApplication, PREFERENCE_MOBILE_ID);
        if(!json.equals(PreferencesManager.STRING_DEFAULT) && json!=null){
            Gson gson=new Gson();
            mMobile=gson.fromJson(json,Mobile.class);
        }else{
            //No hay movil guardado con lo que hay que crearlo
            mMobile=new Mobile();
            mMobile.setMobilesMAC(UtilsServices.getMACaddress(contextApplication));
            mMobile.setUsers(new ArrayList<User>());
        }
        return false;
    }
*/



    private class SaveMobileAsyncTask extends AsyncTask<Mobile, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Mobile... params) {
            int count = params.length;
            long totalSize = 0;
            Mobile mobile=params[0];
            return saveMobile(mobile);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(onUserListener!=null) onUserListener.onAddUser(result);
            if(onAddUserListener!=null) onAddUserListener.onAddUser(result);
        }
        @Override
        protected void onCancelled() {

        }
    }

    //MOBILE END//
}
