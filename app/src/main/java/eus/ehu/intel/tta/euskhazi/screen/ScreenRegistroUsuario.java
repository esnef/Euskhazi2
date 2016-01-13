package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.Exam;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;

public class ScreenRegistroUsuario extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_registro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mCreateNewUserButton = (Button) findViewById(R.id.create_new_user_button);
        mCreateNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userNameEditText = (EditText) findViewById(R.id.userName_editText);
                final String userName = userNameEditText.getText().toString();
                EditText passwordEditText = (EditText) findViewById(R.id.password_editText);
                final String password = passwordEditText.getText().toString();



                mEngine.setOnIsUserListener(new UsersManager.OnIsUserListener() {
                    @Override
                    public void onIsUser(boolean isUser) {
                        if (isUser){
                            User usuarioNuevo = new User(userName, password, new ArrayList<Exam>());
                            try {
                                mEngine.setOnAddUserListener(new UsersManager.OnAddUserListener() {
                                    @Override
                                    public void onAddUser(boolean isAddUser) {
                                        if (isAddUser){
                                            Toast.makeText(getApplicationContext(),"Erabiltzailea ondo sortu da",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), ScreenLogin.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Errore bat gertatu da erabiltzailea sortzerakoan",Toast.LENGTH_SHORT).show();
                                        }
                                        mEngine.setOnAddUserListener(null);
                                    }
                                });
                                mEngine.addUser(usuarioNuevo);
                                mEngine.setOnIsUserListener(null);
                            } catch (UsersManager.ExcepcionUser excepcionUser) {
                                excepcionUser.printStackTrace();
                            }
                        }
                    }
                });
                try {
                    mEngine.isUser(userName);
                } catch (UsersManager.ExcepcionUser excepcionUser) {
                    excepcionUser.printStackTrace();
                }
            }
        });
    }

}
