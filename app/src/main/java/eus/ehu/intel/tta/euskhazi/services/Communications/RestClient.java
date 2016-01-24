package eus.ehu.intel.tta.euskhazi.services.Communications;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import eus.ehu.intel.tta.euskhazi.services.dataType.Mobile;

/**
 * Created by eduardo on 1/01/16.
 */
public class RestClient {

    private final Map<String,String> properties=new HashMap<>();



    private final static String AUTH="Authorization";


    private String pathServer="http://ec2-54-213-59-103.us-west-2.compute.amazonaws.com/EuskhaziRestAndroid/rest/prueba";
    //private String pathServer="http://192.168.0.13:8080/EuskhaziRestAndroid/rest/prueba";

    private String pathApplication="euskhazi";

    public static final String  PATH_SAVE_MOBILE="saveMobile1";

    public static final String  PATH_GET_MOBILE="getMobile1/";


    public RestClient(String pathServer,String pathApplication){
        this.pathServer=pathServer;
        this.pathApplication=pathApplication;
    }
    public RestClient(String pathApplication){
        this.pathApplication=pathApplication;
    }
    public RestClient(){
    }

    private HttpURLConnection getConnection(String path) throws IOException {
        if(pathServer==null || path==null)return null;
        URL url=new URL(String.format("%s/%s",pathServer,path));
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        for(Map.Entry<String,String> property:properties.entrySet()){
            conn.setRequestProperty(property.getKey(),property.getValue());
        }
        conn.setUseCaches(false);
        return conn;
    }

    public int setHttpBasicAuth(String user, String passwd){
        if(user == null || passwd==null || user.trim().equals(new String("")) )return -1;
        String basicAuth= Base64.encodeToString(String.format("%s:%s",user.trim(),passwd.trim()).getBytes(),Base64.DEFAULT);
        properties.put(AUTH, String.format("Basic %s", basicAuth));
        return 1;
    }

    public static String getAUTH() {
        return AUTH;
    }

    public String getAuthorization(){
        return properties.get(AUTH);
    }
    public int setAuthorization(String auth){
        if(auth==null)return -1;
        properties.put(AUTH,auth);
        return 1;
    }

    public String getString(String path) throws IOException {
        if(path==null || path.equals("")){
            throw new RestException("Except that you do not have parameters");
        }
        HttpURLConnection conn=null;
        InputStream inputStream=null;
        try{
            conn=getConnection(path);
            if(conn==null)throw new RestException("Except:HttpURLConnection is null");
            inputStream=conn.getInputStream();
            if(inputStream==null)throw new RestException("Except:InputStream is null");

            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            if(inputStreamReader==null) throw new RestException("Except:InputStreamReader is null");

            BufferedReader br=new BufferedReader(inputStreamReader);
            if(br==null)throw new RestException("Except:BufferedReader is null ");

                return br.readLine();

        }finally {
            if(conn!=null){
                conn.disconnect();
                if(inputStream!=null)
                    inputStream.close();
            }
        }

    }

    public int postJson(final String json,String path) throws IOException {
        if (path == null || json == null || path.equals("")) {
            throw new RestException("Except that you do not have parameters");
        }
        System.out.println(json);
        HttpURLConnection conn = null;
        try {
            conn = getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(json);
            pw.close();
            return conn.getResponseCode();
        } finally {
            if (conn != null) conn.disconnect();
        }
    }


    public int postFile(String path,InputStream is,String fileName) throws IOException {
        String boundary=Long.toString(System.currentTimeMillis());
        String newLine="\r\n";
        String prefix="--";
        HttpURLConnection conn=null;
        try{
            conn=getConnection(path);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream out=new DataOutputStream(conn.getOutputStream());
            out.writeBytes(prefix+boundary+newLine);
            out.writeBytes("Content-Disposition: form-data; name=\"uploadedFile\";filename=\""+fileName+"\""+newLine);
            out.writeBytes(newLine);
            byte[] data=new byte[1024*1024];
            int len;
            while ((len=is.read(data))>0){
                out.write(data,0,len);
            }

            out.writeBytes(newLine);
            out.writeBytes(prefix+boundary+prefix+newLine);
            out.close();
            return conn.getResponseCode();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }
    }

    public String getPathServer() {
        return pathServer;
    }

    public void setPathServer(String pathServer) {
        this.pathServer = pathServer;
    }

    public class RestException extends IOException {
        public RestException() { super(); }
        public RestException(String message) { super(message); }
        public RestException(String message, Throwable cause) { super(message, cause); }
        public RestException(Throwable cause) { super(cause); }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




}
