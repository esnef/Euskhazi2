package eus.ehu.intel.tta.euskhazi.services;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.Communications.RestClient;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.Level;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.ahozkoa.Ahozkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.atarikoa.Atarikoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.berridazketak.Berridazketak;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.entzunezkoa.Entzunezkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.idatzizkoa.Idatzizkoa;
import eus.ehu.intel.tta.euskhazi.services.dataType.exam.sinonimoak.Sinonimoak;

/**
 * Created by alumno on 11/01/16.
 */
public class LevelsManager  {
    private static String TAG = LevelsManager.class.getCanonicalName();
    private Context contextApplication=null;
    private static LevelsManager mLevelsManager=null;
    public static final String[] LEVEL_TYPES={"A1","A2","B1","B2","C1"};
    private ArrayList<Level> mLevels;
    private OnExamsListener onExamsListener;
    private OnGetLevelsListener onGetLevelsListener;
    private OnGetLevelListener onGetLevelListener;
    private OnUpdateFileListener onUpdateFileListener;


    public LevelsManager(Context contextApplication){
        this.contextApplication=contextApplication;

    }

    public static LevelsManager getInstance(Context contextApplication){
        if(contextApplication==null){
            Log.e(TAG, "The parameter contextApplication is nullpoint");
            return null;
        }
        if(mLevelsManager == null){
            Log.d(TAG,"the instance LevelManager is created");
            mLevelsManager = new LevelsManager(contextApplication);
        }
        return mLevelsManager;
    }


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

    public boolean getLevels(){
        if(mLevels==null){
            initLevel();
        }
        if(onExamsListener!=null)onExamsListener.onGetLevels(mLevels);
        if(onGetLevelsListener!=null)onGetLevelsListener.onGetLevels(mLevels);
        return true;

    }

    public boolean getLevel(String levelType){
        if(levelType==null || levelType.equals("")){
            return false;
        }
        if(mLevels==null){
            initLevel();
        }
        for(int con=0;con<mLevels.size();con++){
            if(mLevels.get(con).getTypeLevel().equals(levelType)){
                if(onGetLevelListener!=null)onGetLevelListener.onGetLevel(mLevels.get(con));
                if(onExamsListener!=null)onExamsListener.onGetLevel(mLevels.get(con));
            }
        }

        return true;
    }

    public interface OnGetLevelsListener{
        public void onGetLevels(ArrayList<Level> levels);
    }

    public interface OnGetLevelListener{
        public void onGetLevel(Level levels);
    }

    public interface OnExamsListener{
        public void onGetLevels(ArrayList<Level> levels);
        public void onGetLevel(Level levels);
    }

    public void setOnGetLevelsListener(OnGetLevelsListener onGetLevelsListener){
        this.onGetLevelsListener=onGetLevelsListener;
    }

    public void setOnGetLevelListener(OnGetLevelListener onGetLevelListener){
        this.onGetLevelListener=onGetLevelListener;
    }

    public void setOnExamsListener(OnExamsListener onExamsListener){
        this.onExamsListener=onExamsListener;
    }


    private void initLevel(){
        mLevels=new ArrayList<Level>();
        String[] strings=LEVEL_TYPES;
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


    private File getTempFile(Context context, String url) throws IOException {
        File file=null;
            String fileName = Uri.parse(url).getLastPathSegment();
            System.out.println("fileName: "+fileName);
            file = File.createTempFile(fileName, null, context.getCacheDir());
        return file;
    }


    public boolean updateFile(Uri uri,String fileName){
        if(uri==null || fileName==null || fileName.equals("")){
            return false;
        }
        File file = null;
        try {

            file = getTempFile(contextApplication,uri.getPath());
            if(file==null)return false;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        try {
            if(file.exists()){
                //Se saca del proveedor de contenidos el inputsteam necesario facilitandole la uri para ello.
                InputStream inputStream = contextApplication.getContentResolver().openInputStream(uri);
                ServerUpdateFile serverUpdateFile=new ServerUpdateFile(fileName,inputStream);
                ServerUpdateFileAsyncTask serverUpdateFileAsyncTask=new ServerUpdateFileAsyncTask();
                serverUpdateFileAsyncTask.execute(serverUpdateFile);

            }else{
                return false;
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG,e.getMessage());
            return false;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }

    public interface OnUpdateFileListener{
        public void onUpdateFile(Boolean result);
    }

    public void setOnUpdateFileListener(OnUpdateFileListener onUpdateFileListener){
        this.onUpdateFileListener=onUpdateFileListener;
    }
    
    

    private class ServerUpdateFile{
        private String fileName;
        private InputStream inputStream;


        private ServerUpdateFile(String fileName,InputStream inputStream){
            this.fileName=fileName;
            this.inputStream=inputStream;
        }
        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }


    }

    private class ServerUpdateFileAsyncTask extends AsyncTask<ServerUpdateFile, Void, Integer> {

        @Override
        protected Integer doInBackground(ServerUpdateFile... params) {
            int count = params.length;
            long totalSize = 0;
            int d=-1;
            ServerUpdateFile serverUpdateFile=params[0];
            RestClient restClient=new RestClient();
            System.out.println("a4");
            try {
                d=restClient.postFile("upload", serverUpdateFile.getInputStream(),serverUpdateFile.getFileName());
                serverUpdateFile.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return d;

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result==200){
                if(onUpdateFileListener!=null)onUpdateFileListener.onUpdateFile(true);
            }else{
                if(onUpdateFileListener!=null)onUpdateFileListener.onUpdateFile(false);
            }


        }
        @Override
        protected void onCancelled() {

        }
    }
}
