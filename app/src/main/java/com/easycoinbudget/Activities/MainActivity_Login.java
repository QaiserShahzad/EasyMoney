package com.easycoinbudget.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_Login;
import com.easycoinbudget.Model.SR_userInfo;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Login extends AppCompatActivity {
    TextView textView_forgetpassword;

    private static SR_userInfo userInfo;
   private static String key;
    private static String email_key;
    EditText et_email, et_password;
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    Button btnmain_login;
    String login_email, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__login);

        pref = getApplicationContext().getSharedPreferences(Utils.SETTING_KEY, 0);

        editor = pref.edit();


        //getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait ....");




//        et_email = (MyEditText) findViewById(R.id.email);
//        et_password = (MyEditText) findViewById(R.id.password);
        et_email = (EditText) findViewById(R.id.input_LoginEmail);
        et_password = (EditText) findViewById(R.id.et_password);
        btnmain_login = findViewById(R.id.btn_Login);
        textView_forgetpassword = findViewById(R.id.tv_forgot);



        btnmain_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_email = et_email.getText().toString().trim();
                login_password = et_password.getText().toString().trim();
                if (login_email.matches("")) {
                    et_email.setError("Please Enter Email Address");
                } else if (login_password.matches("")) {
                    et_password.setError("Please Enter Password");
                } else {


                    budgetlogin(login_email, login_password);
                    progressDialog.show();

                }

            }
        });


        textView_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity_SendPassword.class);
                startActivity(i);
                finish();
            }
        });





    }

    public void budgetlogin(String email, String password) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_Login> call = apiClient.budget_login(email, password);


            call.enqueue(new Callback<SR_Login>() {
                @Override
                public void onResponse(Call<SR_Login> call, Response<SR_Login> response) {
                    try {

                        email_key=et_email.getText().toString();
                        editor.putString(Utils.SETTING_USER_EMAIL,email_key);


                        SR_Login login = response.body();



                        int resp = login.getSuccess();
                        key = login.getUserId();


                        editor.putString(Utils.SETTING_USER_ID, login.getUserId());
                        editor.putString(email_key, null);
                        editor.commit();

//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" Login Activity", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {
                            //Toast.makeText(getApplicationContext(), button_shape_login_signup.getUserId(), Toast.LENGTH_SHORT).show();

                            // Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                            i.putExtra("name",email_key);
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();
                            Log.e(" Login Activity", "  id - " + login.getMessage());

                        } else {
//                            Toast.makeText(getContext(), "inserted on response", Toast.LENGTH_SHORT).show();
                             Toast.makeText(getApplicationContext(), "User does not exist or Incorrect Email and Password", Toast.LENGTH_LONG).show();
                            //Log.e(" IncomeFragment", "  id - " + "Data not Inserted");
                            progressDialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_Login> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Login Activity", "  id - " + "Failure Method Call");

                    Log.e(" Login Activity", "  id - " + t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
