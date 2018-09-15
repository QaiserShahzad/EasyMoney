package com.easycoinbudget.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.easycoinbudget.R;

public class MainActivity_MatchPassword extends AppCompatActivity {
    Button btnpasswordMatches;
    EditText editText_MatchPassword;
    RequestQueue requestQueue;
    String matchPassword;
    String user_foreignId, user_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_matchpass);
        btnpasswordMatches = (Button) findViewById(R.id.btnpasswordmatched_next);
        //getSupportActionBar().hide();
        editText_MatchPassword = findViewById(R.id.input_Code);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_foreignId = preferences.getString("user_id", "");
        user_Password = preferences.getString("user_AutoPassword", "");


        btnpasswordMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchPassword = editText_MatchPassword.getText().toString().trim();
                if (matchPassword.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Insert Password Confirmation Code", Toast.LENGTH_SHORT).show();
                }
                else {
                    matchCode();
                }
            }
        });
    }

    private void matchCode()
    {


        if (matchPassword.equals(MainActivity_SendPassword.autoPass))
        {
            Toast.makeText(this, "Code Matched", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),MainActivity_resetpassword.class);
            startActivity(i);
            finish();

        }
        else
        {
            Toast.makeText(this, "Code not matched", Toast.LENGTH_SHORT).show();
        }
    }
}
