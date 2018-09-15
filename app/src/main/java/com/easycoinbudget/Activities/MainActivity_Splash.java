package com.easycoinbudget.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.easymoney.Fragments.ResetAppFragement;
import com.easycoinbudget.R;

public class MainActivity_Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Button btn_splashSignup, btn_splashlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().hide();

        btn_splashSignup = (Button) findViewById(R.id.btnSplash_Signup);
        btn_splashlogin = (Button) findViewById(R.id.btnSplash_Login);

        try {
            btn_splashSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signup = new Intent(getApplicationContext(), MainActivity_Signup.class);
                    startActivity(signup);
                    //finish();

                }
            });
        }
        catch (NullPointerException n)
        {n.printStackTrace();}
        try {
            btn_splashlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent login = new Intent(getApplicationContext(), MainActivity_Login.class);
                    startActivity(login);
         //           finish();
                }
            });
        }
        catch (NullPointerException nu)
        {nu.printStackTrace();}


    }

}
