package eus.ehu.intel.tta.euskhazi.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import eus.ehu.intel.tta.euskhazi.Engine;
import eus.ehu.intel.tta.euskhazi.R;
import eus.ehu.intel.tta.euskhazi.services.UsersManager;
import eus.ehu.intel.tta.euskhazi.services.dataType.User;

public class TipoExamenActivity extends ScreenBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_examen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        TextView textLogin = (TextView)findViewById(R.id.tipo_examen_textView_level);
        textLogin.setText(intent.getStringExtra("level") + " MAILA");


    }

    public void chooseExamType(View view){

        int examType = Integer.parseInt(view.getTag().toString());
        switch (examType){
            case 1:
                Intent intent = new Intent(this, ScreenListaExamsAtarikoa.class);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(this, "Idatzizkoa sin implementar", Toast.LENGTH_SHORT).show();

                mEngine.setOnUserListener(new UsersManager.OnUserListener() {
                    @Override
                    public void onGetUser(User user) {

                    }

                    @Override
                    public void onGetUserNow(User user) {

                    }

                    @Override
                    public void onIsUser(boolean isUser) {
                        System.out.println("isUser: "+isUser);
                    }

                    @Override
                    public void onAddUser(boolean isAddUser) {
                        System.out.println("isAddUser: "+isAddUser);
                    }

                    @Override
                    public void onSaveUser(boolean isSaveUser) {

                    }
                });
                try {
                    mEngine.isUser("as","f");
                } catch (UsersManager.ExcepcionUser excepcionUser) {
                    excepcionUser.printStackTrace();
                }
                User user=new User();
                user.setPass("f");
                user.setName("as");
                try {
                    mEngine.addUser(user);
                } catch (UsersManager.ExcepcionUser excepcionUser) {
                    excepcionUser.printStackTrace();
                }

                try {
                    mEngine.isUser("as", "f");
                } catch (UsersManager.ExcepcionUser excepcionUser) {
                    excepcionUser.printStackTrace();
                }

                break;
            case 3:
                Toast.makeText(this, "Sinonimoak sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Berridazketak sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Entzumena sin implementar", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(this, "Ahozkoa sin implementar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
