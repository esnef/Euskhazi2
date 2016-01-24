package eus.ehu.intel.tta.euskhazi.screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.concurrent.TimeUnit;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;

public class ScreenLoading extends ScreenBase {


    private static final int SEC_MAX_LOADING=3;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.show();

        mEngine.setOnInitializationOfflineListener(new UsersManager.OnInitializationOfflineListener() {
            @Override
            public void onInitializationOffline(boolean isUser) {
                /*
                mHandler= new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("AAAA");

                    }
                }, 1000 * SEC_MAX_LOADING);
                */
            }
        });
        mEngine.setOnInitializationOnlineListener(new UsersManager.OnInitializationOnlineListener() {
            @Override
            public void onInitializationOnline(boolean isUser) {
                if (isUser) {
                    Intent intent = new Intent(mEngine.getContextApplication(), ScreenLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mEngine.initialization();


    }



}
