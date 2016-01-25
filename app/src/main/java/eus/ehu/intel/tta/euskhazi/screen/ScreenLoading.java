package eus.ehu.intel.tta.euskhazi.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.Communications.RestClient;
import eus.ehu.intel.tta.euskhazi.services.LevelsManager;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Mobile;

public class ScreenLoading extends ScreenBase {


    private static final int SEC_MAX_LOADING=3;

    private Handler mHandler;
    //prueba
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        mEngine.setOnInitializationOfflineListener(new UsersManager.OnInitializationOfflineListener() {
            @Override
            public void onInitializationOffline(boolean isUser) {

            }
        });
        mEngine.setOnInitializationOnlineListener(new UsersManager.OnInitializationOnlineListener() {
            @Override
            public void onInitializationOnline(boolean isUser) {
                if (isUser) {
                    mProgressDialog.cancel();
                    Intent intent = new Intent(mEngine.getContextApplication(), ScreenLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mEngine.initialization();




    }






}
