package eus.ehu.intel.tta.euskhazi.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.services.Communications.RestClient;
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
    private OnInitializationOfflineListener onInitializationOfflineListener;
    private OnInitializationOnlineListener onInitializationOnlineListener;


    private boolean isInitialization=false;


    public UsersManager(Context contextApplication){
        this.contextApplication=contextApplication;
        this.mPreferencesManager=PreferencesManager.getInstance();
        //Version Primer hilo
        //updateMobile();
        //
        //Version Segundo plano
        //UpdateMobileAsyncTask updateMobileAsyncTask=new UpdateMobileAsyncTask();
        //updateMobileAsyncTask.execute(contextApplication);
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

    public boolean initialization(){
        UpdateMobileAsyncTask updateMobileAsyncTask=new UpdateMobileAsyncTask();
        updateMobileAsyncTask.execute(contextApplication);
        return true;
    }

    public boolean isInitialization(){
        return isInitialization;
    }

    public boolean saveUser(){
        if(mMobile!=null){
            if(saveMobile()){
                if(onUserListener!=null) onUserListener.onSaveUser(true);
                if(onSaveUserListener!=null) onSaveUserListener.onSaveUser(true);
                SaveMobileServerAsyncTask saveMobileServerAsyncTask=new SaveMobileServerAsyncTask();
                SaveMobileData saveMobileData=new SaveMobileData();
                saveMobileData.setContext(contextApplication);
                saveMobileData.setMobile(mMobile);
                saveMobileServerAsyncTask.execute(saveMobileData);
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
                /*
                 boolean result=saveMobile();
                    if(onUserListener!=null) onUserListener.onAddUser(result);
                    if(onAddUserListener!=null) onAddUserListener.onAddUser(result);
                return result;
                */
                //Version segundo hilo

                SaveMobileData saveMobileData=new SaveMobileData();
                saveMobileData.setMobile(mMobile);
                saveMobileData.setContext(contextApplication);
                SaveMobileAsyncTask saveMobileAsyncTask=new SaveMobileAsyncTask();
                saveMobileAsyncTask.execute(saveMobileData);
                return true;



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
        if(mMobile!=null){
            List<User> users=mMobile.getUsers();
            for(int con=0;con<users.size();con++){
                User userFor=users.get(con);
                if(userFor.getName().equals(user.getName()) && userFor.getPass().equals(user.getPass())){
                    mUserNow=userFor;
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
            if(onUserListener!=null)onUserListener.onGetUserNow(mUserNow);
            if(onGetUserNowListener!=null)onGetUserNowListener.onGetUserNow(mUserNow);
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

    public interface OnInitializationOfflineListener{
        public void onInitializationOffline(boolean isInitializationOffline);
    }

    public void setOnInitializationOfflineListener(OnInitializationOfflineListener onInitializationOfflineListener){
        this.onInitializationOfflineListener=onInitializationOfflineListener;
    }

    public interface OnInitializationOnlineListener{
        public void onInitializationOnline(boolean isInitializationOnline);
    }

    public void setOnInitializationOnlineListener(OnInitializationOnlineListener onInitializationOnlineListener){
        this.onInitializationOnlineListener=onInitializationOnlineListener;
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

    private boolean saveMobile(Mobile mobile, Context context){
        if(mobile==null)return false;
        //Generamos el json
        Gson gson=new Gson();
        String json=gson.toJson(mobile);
        PreferencesManager preferencesManager=new PreferencesManager();
        boolean result=preferencesManager.putString(context, PREFERENCE_MOBILE_ID, json);
        return result;
    }

    private boolean saveMobileServer(String json, Context context) throws IOException {
        if(json==null || json.equals("") || context==null){
            return false;
        }
        if(RestClient.isOnline(context)){
            RestClient restClient=new RestClient();
            int response=restClient.postJson(json,RestClient.PATH_SAVE_MOBILE);
            if(response==200){
                return true;
            }

        }else{
            Log.e(TAG,"No se tiene internet para subir el archivo");
        }

        return false;
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

    private Mobile updateMobile(Context context){
        PreferencesManager preferencesManager=new PreferencesManager();
        String json=preferencesManager.getString(context, PREFERENCE_MOBILE_ID);
        Mobile mobile=null;
        if(!json.equals(PreferencesManager.STRING_DEFAULT) && json!=null){
            Gson gson=new Gson();
            mobile=gson.fromJson(json,Mobile.class);
        }else{
            //No hay movil guardado con lo que hay que crearlo
            mobile=new Mobile();
            mobile.setMobilesMAC(UtilsServices.getMACaddress(contextApplication));
            mobile.setUsers(new ArrayList<User>());
        }
        return mobile;
    }


    private Mobile updateMobileServer(Mobile mobile,Context context){
        if(mobile==null  || context==null)return null;
        Mobile mobileNew;
        RestClient restClient=new RestClient();
        try {
            if(!RestClient.isOnline(context))return mobile;
            String json=restClient.getString(RestClient.PATH_GET_MOBILE+mobile.getMobilesMAC());
            System.out.println("json: "+json);
            if(json!=null && !json.equals("")){
                Gson gson=new Gson();
                if(gson.toJson(mobile).trim().equals(json.trim())){
                    Log.d(TAG,"No hay modificaciones");
                    return mobile;
                }
                mobileNew=gson.fromJson(json,Mobile.class);
                return  mobileNew;
            }else{
                Log.e(TAG,"Error al actualizar el mobile contra la base de datos 1");
            }
            return mobile;
        } catch (IOException e) {
            e.printStackTrace();
           Log.e(TAG,"Error al actualizar el mobile contra la base de datos");
        }

        return mobile;

    }




    private class SaveMobileData{
        private Mobile mobile;
        private Context context;

        public Mobile getMobile() {
            return mobile;
        }

        public void setMobile(Mobile mobile) {
            this.mobile = mobile;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }

    private class SaveMobileAsyncTask extends AsyncTask<SaveMobileData, Void, Boolean> {

        @Override
        protected Boolean doInBackground(SaveMobileData... params) {
            int count = params.length;
            long totalSize = 0;
            SaveMobileData saveMobileData=params[0];
            return saveMobile(saveMobileData.getMobile(),saveMobileData.getContext());
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
            if(result){
                SaveMobileServerAsyncTask saveMobileServerAsyncTask=new SaveMobileServerAsyncTask();
                SaveMobileData saveMobileData=new SaveMobileData();
                saveMobileData.setContext(contextApplication);
                saveMobileData.setMobile(mMobile);
                saveMobileServerAsyncTask.execute(saveMobileData);
            }
        }
        @Override
        protected void onCancelled() {

        }
    }

    private class SaveMobileServerAsyncTask extends AsyncTask<SaveMobileData, Void, Boolean> {

        @Override
        protected Boolean doInBackground(SaveMobileData... params) {
            int count = params.length;
            long totalSize = 0;
            SaveMobileData saveMobileData=params[0];
            Gson gson=new Gson();
            String json=gson.toJson(saveMobileData.getMobile());
            System.out.println(json);
            try {
                return saveMobileServer(json,saveMobileData.getContext());
            } catch (IOException e) {
                Log.e(TAG, "Error en el almacenamiento de datos");
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Log.d(TAG,"Se ha realizado correctamente el almacenamiento en el servidor");
            }else{
                Log.d(TAG, "No se ha produccido correctamente el envio al servidor");
            }
        }
        @Override
        protected void onCancelled() {

        }
    }


    private class UpdateMobileAsyncTask extends AsyncTask<Context, Void, Mobile> {

        @Override
        protected Mobile doInBackground(Context... params) {
            int count = params.length;
            long totalSize = 0;
            Context context=params[0];
            return updateMobile(context);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Mobile mobile) {
            if(mobile==null){
                isInitialization=false;
                if(onInitializationOfflineListener!=null)onInitializationOfflineListener.onInitializationOffline(false);
            }else{
                if(onInitializationOfflineListener!=null)onInitializationOfflineListener.onInitializationOffline(true);
                isInitialization=true;

            }
            mMobile=mobile;
            UpdateMobileServerAsyncTask updateMobileServerAsyncTask=new UpdateMobileServerAsyncTask();
            SaveMobileData saveMobileData=new SaveMobileData();
            saveMobileData.setContext(contextApplication);
            saveMobileData.setMobile(mMobile);
            updateMobileServerAsyncTask.execute(saveMobileData);
        }
        @Override
        protected void onCancelled() {

        }
    }


    private class UpdateMobileServerAsyncTask extends AsyncTask<SaveMobileData, Void, Mobile> {

        @Override
        protected Mobile doInBackground(SaveMobileData... params) {
            int count = params.length;
            long totalSize = 0;
            SaveMobileData saveMobileData=params[0];
            return updateMobileServer(saveMobileData.getMobile(),saveMobileData.getContext());
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Mobile mobile) {
            if(mobile!=null){
                Gson gson=new Gson();
                System.out.println(gson.toJson(mobile));
                if(onInitializationOnlineListener!=null)onInitializationOnlineListener.onInitializationOnline(true);
                mMobile=mobile;
            }else{
                if(onInitializationOnlineListener!=null)onInitializationOnlineListener.onInitializationOnline(false);
            }


        }
        @Override
        protected void onCancelled() {

        }
    }



    //MOBILE END//
}
