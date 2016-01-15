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
                final String userName = userNameEditText.getText().toString().trim();
                EditText passwordEditText = (EditText) findViewById(R.id.password_editText);
                final String password = passwordEditText.getText().toString().trim();

                if (userName.equals("")||password.equals("")){
                    Toast.makeText(getApplicationContext(),"Datu guztiak bete behar dituzu",Toast.LENGTH_SHORT).show();
                    return;
                }

                mEngine.setOnAddUserListener(new UsersManager.OnAddUserListener() {
                    @Override
                    public void onAddUser(boolean isAddUser) {
                        Toast.makeText(getApplicationContext(),"addUser eventoa",Toast.LENGTH_SHORT).show();
                        if (isAddUser){
                            Toast.makeText(getApplicationContext(),"Erabiltzailea ondo sortu da",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ScreenLogin.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),"Errore bat gertatu da erabiltzailea sortzerakoan",Toast.LENGTH_SHORT).show();
                        }
                        mEngine.setOnAddUserListener(null);
                        mEngine.setOnIsUserListener(null);
                    }
                });

                mEngine.setOnIsUserListener(new UsersManager.OnIsUserListener() {
                    @Override
                    public void onIsUser(boolean isUser) {
                        if (!isUser){
                            User usuarioNuevo = new User(userName, password, null);
                            try {
                                Toast.makeText(getApplicationContext(),"addUser eventoari deituko zaio ",Toast.LENGTH_SHORT).show();
                                boolean bo = mEngine.addUser(usuarioNuevo);
                                Toast.makeText(getApplicationContext(),"addUser = "+ String.valueOf(bo),Toast.LENGTH_SHORT).show();
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
