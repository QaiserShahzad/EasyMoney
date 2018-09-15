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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easycoinbudget.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_resetpassword extends AppCompatActivity {
    EditText et_NewPassword, et_ConfirmPassword;
    Button btnUpdate_Password,btnUpdate_cancel;
    RequestQueue requestQueue;
    String newPassword, confirmPassword;
    String user_foreignId;


//    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resetpassword);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_foreignId = preferences.getString("user_id", "");

        et_NewPassword = (EditText) findViewById(R.id.input_NewPassword);
        et_ConfirmPassword = findViewById(R.id.input_ConfirmPassword);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Confirmation");
//        progressDialog.setMessage("Please wait ....");

        btnUpdate_Password = findViewById(R.id.btn_UpdatePassword);
        btnUpdate_cancel = findViewById(R.id.btnreset_cancel);

        btnUpdate_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity_Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnUpdate_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword = et_NewPassword.getText().toString().trim();
                confirmPassword = et_ConfirmPassword.getText().toString().trim();


                if (newPassword.matches("") && confirmPassword.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Input Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (newPassword.equals(confirmPassword))

                    {


                        updatePassword();
//                        progressDialog.show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Password not matched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void updatePassword() {


        String Url="http://coinbudget.com/EasyCoinApp/budget_updatePassword.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    if (success.equals("1"))
                    {
                        Toast.makeText(MainActivity_resetpassword.this, "Password Changed succesfuly", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),MainActivity_Login.class);
                        startActivity(i);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {






                Map<String, String> params = new HashMap<String, String>();
                params.put("f_id", MainActivity_SendPassword.U_id);
                params.put("update_password", et_NewPassword.getText().toString().trim());

//                params.put("name", editTextname.getText().toString().trim());
//                params.put("password", editTextPassword.getText().toString().trim());
//                params.put("cnum", editTextPhone.getText().toString().trim());

                // for hard code
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email","pasha@gmail.com");
//                params.put("name","qaiser");
//                params.put("password", "1234");
//                params.put("phone", "0333333");



                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

//
//            @Override
//            public String getBodyContentType() {
//                // TODO Auto-generated method stub
//                return "application/json";
//            }

        };



        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


}

