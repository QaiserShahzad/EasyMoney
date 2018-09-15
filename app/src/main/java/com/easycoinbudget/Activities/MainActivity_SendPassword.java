package com.easycoinbudget.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity_SendPassword extends AppCompatActivity {
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    EditText et_Email;
    RequestQueue requestQueue;
    Button btn_forgetPassword;
    String email;
    public static String autoPass;
    public static String U_id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__send_password);
        et_Email = (EditText) findViewById(R.id.input_SendEmail);

        btn_forgetPassword = (Button) findViewById(R.id.btnreset_cancel);


//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Reset Password");
//        progressDialog.setMessage("Please wait ....");


        btn_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_Email.getText().toString().trim();
                if (email.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                } else {
                    forgotpass();
//                    progressDialog.show();
                }
            }


        });


    }

    public void forgotpass() {



        String Url="http://coinbudget.com/EasyCoinApp/budget_forgetPassword.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    autoPass=jsonObject.getString("auto_generatedPass");
                    U_id=jsonObject.getString("user_Id");
                    String success=  jsonObject.getString("success");
                    if (success.equals("1"))
                    {
                        Toast.makeText(MainActivity_SendPassword.this, "Code sent to Email", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplication(),MainActivity_MatchPassword.class);
                        startActivity(intent);
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
                params.put("uemail", et_Email.getText().toString().trim());
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

