package com.easycoinbudget.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;

import com.easycoinbudget.Model.SR_SignUp;
import com.easycoinbudget.R;

import custom_font.MyEditText;
import custom_font.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Signup extends AppCompatActivity {
    MyTextView holliday, start;
    MyTextView signin;
    MyEditText et_Email, et_Password, et_Name, et_Cnum;
    String name, email, password, cnum;

    private ProgressDialog progressDialog;

    //    EditText eText_name, eText_name, eText_name, eText_name;
    EditText e_name, e_email, e_password, e_cnum;
    Button btn_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


//       getSupportActionBar().hide();


        e_name = (EditText) findViewById(R.id.et_Name);

        e_email = (EditText) findViewById(R.id.input_Email);

        e_password = (EditText) findViewById(R.id.tv_password);

        e_cnum = (EditText) findViewById(R.id.tv_cnum);


//
//
//        et_Email = (MyEditText) findViewById(R.id.email);
//        et_Password = (MyEditText) findViewById(R.id.password);
//        et_Name = (MyEditText) findViewById(R.id.name);
//        et_Cnum = (MyEditText) findViewById(R.id.contactnum);
//        signin = (MyTextView) findViewById(R.id.signin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("SignUp");
        progressDialog.setMessage("Please wait ....");

        //holliday = (TextView) findViewById(R.id.holliday);
//        start = (MyTextView) findViewById(R.id.getstartedSignup);
//
//
        btn_Next = (Button) findViewById(R.id.btnSignup_Next);
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                name = e_name.getText().toString().trim();
                email = e_email.getText().toString().trim();
                password = e_password.getText().toString().trim();
                cnum = e_cnum.getText().toString().trim();

                if (name.matches("")) {
                    e_name.setError("Please Enter User Name");
                }
//               else if(email.matches(emailPattern))
//                {
//                    Toast.makeText(MainActivity_Signup.this, "Okkk", Toast.LENGTH_SHORT).show();
//                }

                else if (email.matches("")) {
                    e_email.setError("Please Enter Email");
                } else if (password.matches("")) {
                    e_password.setError("Please Enter Your Password");
                } else if (cnum.matches("")) {
                    e_cnum.setError("Please Enter Mobile Number");
                } else if(email.matches(emailPattern)){
                    insertSignup(name, email, password, cnum);
                    progressDialog.show();
                    finish();

                }
                else if (email != (emailPattern))
                {
                    e_email.setError("Invalid Email Pattern");
                }


//
//                name = et_Name.getText().toString().trim();
//                email = et_Email.getText().toString().trim();
//                password = et_Password.getText().toString().trim();
//                cnum = et_Cnum.getText().toString().trim();
//

//                if (name.matches("")) {
//                    et_Name.setError("Please Enter User Name");
//                } else if (email.matches("")) {
//                    et_Email.setError("Please Enter Email");
//                } else if (password.matches("")) {
//                    et_Password.setError("Please Enter Your Password");
//                } else if (cnum.matches("")) {
//                    et_Cnum.setError("Please Enter Mobile Number");
//                } else {
//                    insertSignup(name, email, password, cnum);
//                    progressDialog.show();
//
//                }


            }
        });


//        Typeface custom_fonts = Typeface.createFromAsset(getAssets(), "fonts/ArgonPERSONAL-Regular.otf");
//        holliday.setTypeface(custom_fonts);
//
//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(getApplicationContext(), MainActivity_Login.class);
//                startActivity(i);
//
//
//            }
//        });

    }
//    public void sendMail(View view) {
//
//        try
//        {
//            LongOperation l=new LongOperation();
//            l.execute();  //sends the email in background
//            Toast.makeText(this, l.get(), Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e)
//
//        {
//            Log.e("SendMail", e.getMessage(), e);
//        }
//
//    }

    public void insertSignup(String name, String email, String password, String cnum) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_SignUp> call = apiClient.insert_SignUp(name, email, password, cnum);


            call.enqueue(new Callback<SR_SignUp>() {
                @Override
                public void onResponse(Call<SR_SignUp> call, Response<SR_SignUp> response) {
                    try {


                        SR_SignUp insert = response.body();

                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" SignUp Activity", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {


                            Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            progressDialog.dismiss();

                            finish();
                            Log.e(" SignUp Activity", "  id - " + insert.getMessage());

                        } else {
//                            Toast.makeText(getContext(), "inserted on response", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), "Record Not Inserted", Toast.LENGTH_SHORT).show();
                            //Log.e(" IncomeFragment", "  id - " + "Data not Inserted");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_SignUp> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" SignUp Activity", "  id - " + "Failure Method Call");

                    Log.e(" SignUp Activity", "  id - " + t.getMessage());
                    progressDialog.setMessage("Please Connect with you'r Mobile data/ Internet");
                    progressDialog.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
