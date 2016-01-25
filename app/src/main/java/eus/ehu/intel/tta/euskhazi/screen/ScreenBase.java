package eus.ehu.intel.tta.euskhazi.screen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.Engine;
import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class ScreenBase extends AppCompatActivity{
    protected static User newUser;
    protected static String TAG = ScreenBase.class.getCanonicalName();
    protected Engine mEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEngine=Engine.getInstance(getApplicationContext());
    }

    protected User saveUserExam(final Exam exam){

        mEngine.setOnGetUserNowListener(new UsersManager.OnGetUserNowListener() {
            @Override
            public void onGetUserNow(User user) {
                if (user == null){
                    Toast.makeText(getApplicationContext(), R.string.usuario_incorrecto, Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(getApplicationContext(), "ERABILTZAILEA ONDO DAGO", Toast.LENGTH_SHORT).show();
                }

                if (user.getExams() == null) user.setExams(new ArrayList<Exam>());
                List<Exam> examList = user.getExams();

                boolean nuevoExamen = true;
                for (int n=0; n < examList.size(); n++){
                    Exam exam1 = examList.get(n);
                    if (exam1.getLevel().equals(exam.getLevel()) && exam1.getNumExams() == exam.getNumExams() && exam1.getTypeExam().equals(exam.getTypeExam())) {
                        nuevoExamen = false;
                        examList.set(n, exam);
                    }
                }
                if (nuevoExamen){
                    examList.add(exam);
                }

                user.setExams(examList);

                mEngine.setOnSaveUserListener(new UsersManager.OnSaveUserListener() {
                    @Override
                    public void onSaveUser(boolean isSaveUser) {
                        if (isSaveUser) {
                            System.out.println("Se ha guardado correctamente el usuario");
                        } else {
                            System.out.println("ERRORRRR!!!!");
                        }
                        finish();
                        mEngine.setOnSaveUserListener(null);
                        mEngine.setOnGetUserNowListener(null);
                    }
                });
                mEngine.saveUser();
            }
        });
        mEngine.getUserNow();

        return null;
    }

}

