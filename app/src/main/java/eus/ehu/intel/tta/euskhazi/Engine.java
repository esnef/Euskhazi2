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

import eus.ehu.intel.tta.euskhazi.services.PreferencesManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Mobile;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.Atarikoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.berridazketak.Berridazketak;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa.Entzunezkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa.Idatzizkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.Sinonimoak;

/**
 * Created by eduardo.zarate on 18/12/2015.
 */
public class Engine {
    private static String TAG = Engine.class.getCanonicalName();
    private static Engine mEngine=null;
    private Context contextApplication=null;
    private PreferencesManager mPreferencesManager;
    private Mobile mMobile;
    private User mUserNow;


    private static final String PREFERENCE_MOBILE_ID=TAG+"PREFERENCE_MOBILE_ID";


    private ArrayList<Level> mLevels;

    //Eventos
    private OnUserListener onUserListener=null;


    public Engine(Context contextApplication){
        this.contextApplication=contextApplication;
        this.mPreferencesManager=PreferencesManager.getInstance();
        initLevel();




        updateMobile();

    }

    private void initLevel(){
        mLevels=new ArrayList<Level>();
        String[] strings={"A1","A2","B1","B2","C1"};
        int[] intAhozkoa={R.raw.ahozkoa,R.raw.ahozkoa,R.raw.ahozkoa,R.raw.ahozkoa,R.raw.ahozkoa};
        int[] intAtarikoa={R.raw.atarikoa,R.raw.atarikoa,R.raw.atarikoa,R.raw.atarikoa,R.raw.atarikoa};
        int[] intBerridazketak={R.raw.berridazketak,R.raw.berridazketak,R.raw.berridazketak,R.raw.berridazketak,R.raw.berridazketak};
        int[] intEntzunezkoa={R.raw.entzunezkoa,R.raw.entzunezkoa,R.raw.entzunezkoa,R.raw.entzunezkoa,R.raw.entzunezkoa};
        int[] intIdatzizkoa={R.raw.idatzizkoa,R.raw.idatzizkoa,R.raw.idatzizkoa,R.raw.idatzizkoa,R.raw.idatzizkoa};
        int[] intSinonimoak={R.raw.sinonimoak,R.raw.sinonimoak,R.raw.sinonimoak,R.raw.sinonimoak,R.raw.sinonimoak};

        for(int con=0;con<strings.length;con++){
            Level level=new Level();
            level.setTypeLevel(strings[con]);
            level.setAhozkoas(new ArrayList<Ahozkoa>());
            level.setAtarikoas(new ArrayList<Atarikoa>());
            level.setBerridazketaks(new ArrayList<Berridazketak>());
            level.setEntzunezkoas(new ArrayList<Entzunezkoa>());
            level.setIdatzizkoas(new ArrayList<Idatzizkoa>());
            level.setSinonimoaks(new ArrayList<Sinonimoak>());
            updateLevel(level,intAhozkoa[con],intAtarikoa[con],intBerridazketak[con],intEntzunezkoa[con],intIdatzizkoa[con],intSinonimoak[con]);
            mLevels.add(level);
        }
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

    //INIT USERS//

    public boolean saveUser(){
        if(mMobile!=null){
            if(saveMobile()){
                if(onUserListener!=null) onUserListener.onSaveUser(true);
                return true;
            }

        }
        if(onUserListener!=null) onUserListener.onSaveUser(false);
        return false;
    }


    public boolean addUser(User user) throws ExcepcionUser {
        if(user==null || user.getName()==null || user.getPass()==null || user.getName().equals("") || user.getPass().equals("")){
            throw new ExcepcionUser(new Integer(ExcepcionUser.PARAMETERS_NULL).toString());
        }
        if(!isUser(user)){
            if(mMobile!=null){
                mMobile.getUsers().add(user);
                boolean result=saveMobile();
                if(onUserListener!=null) onUserListener.onAddUser(result);
                return result;
            }
        }
        if(onUserListener!=null) onUserListener.onAddUser(false);
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
                    return true;
                }
            }
        }
        if(onUserListener!=null)onUserListener.onGetUser(null);
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
        }else{
            return false;
        }
        return true;
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
                    return true;
                }
            }
        }
        if(onUserListener!=null)onUserListener.onIsUser(false);
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


    public interface OnUserListener{
        public void onGetUser(User user);
        public void onGetUserNow(User user);
        public void onIsUser(boolean isUser);
        public void onAddUser(boolean isAddUser);
        public void onSaveUser(boolean isSaveUser);
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

    private boolean updateMobile(){
        String json=mPreferencesManager.getString(contextApplication, PREFERENCE_MOBILE_ID);
        if(!json.equals(PreferencesManager.STRING_DEFAULT) && json!=null){
            Gson gson=new Gson();
            mMobile=gson.fromJson(json,Mobile.class);
        }else{
            //No hay movil guardado con lo que hay que crearlo
            mMobile=new Mobile();
            mMobile.setMobilesMAC(getMACaddress());
            mMobile.setUsers(new ArrayList<User>());
        }
        return false;
    }



    //MOBILE END//

    //LEVEL INIT//
    private boolean updateLevel(Level level, int ahozkoa, int atarikoa,int berridazketak,int entzunezkoa,int idatzizkoa,int sinonimoak){
        String ahozkoas=readDataRAW(ahozkoa);
        String atarikoas=readDataRAW(atarikoa);
        String berridazketaks=readDataRAW(berridazketak);
        String entzunezkoas=readDataRAW(entzunezkoa);
        String idatzizkoas=readDataRAW(idatzizkoa);
        String sinonimoaks=readDataRAW(sinonimoak);



        Gson gson=new Gson();
        if(ahozkoas!=null) {
            Type listType = new TypeToken<ArrayList<Ahozkoa>>() {
            }.getType();
            ArrayList<Ahozkoa> ahozkoasList = gson.fromJson(ahozkoas, listType);
            level.setAhozkoas(ahozkoasList);
        }
        if(atarikoas!=null){
            Type listType = new TypeToken<ArrayList<Atarikoa>>() { }.getType();
            ArrayList<Atarikoa> atarikoaList=gson.fromJson(atarikoas,listType);
            level.setAtarikoas(atarikoaList);
        }
        if(berridazketaks!=null){
            Type listType = new TypeToken<ArrayList<Berridazketak>>() { }.getType();
            ArrayList<Berridazketak> berridazketakList=gson.fromJson(berridazketaks,listType);
            level.setBerridazketaks(berridazketakList);
        }
        if(entzunezkoas!=null){
            Type listType = new TypeToken<ArrayList<Entzunezkoa>>() { }.getType();
            ArrayList<Entzunezkoa> entzunezkoaList=gson.fromJson(entzunezkoas,listType);
            level.setEntzunezkoas(entzunezkoaList);
        }
        if(idatzizkoas!=null){
            Type listType = new TypeToken<ArrayList<Idatzizkoa>>() { }.getType();
            ArrayList<Idatzizkoa> idatzizkoaList=gson.fromJson(idatzizkoas,listType);
            level.setIdatzizkoas(idatzizkoaList);
        }
        if(sinonimoaks!=null){
            Type listType = new TypeToken<ArrayList<Sinonimoak>>() { }.getType();
            ArrayList<Sinonimoak> sinonimoakList=gson.fromJson(sinonimoaks,listType);
            level.setSinonimoaks(sinonimoakList);
        }

        return true;
    }

    private String readDataRAW(int id){
        String mResponse=null;
        if(id<0)return null;
        InputStream is=null;
        try {
            is = contextApplication.getResources().openRawResource(id);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            mResponse = new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(is!=null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return mResponse;
    }

    // END LEVEL//

    private String getMACaddress(){
        WifiManager manager = (WifiManager) contextApplication.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        return address;
    }



}
