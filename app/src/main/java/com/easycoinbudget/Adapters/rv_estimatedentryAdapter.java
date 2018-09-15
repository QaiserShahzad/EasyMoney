package com.easycoinbudget.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easycoinbudget.Model.SR_getAllEstExpenses;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class rv_estimatedentryAdapter extends RecyclerView.Adapter<rv_estimatedentryAdapter.EmployeeViewHolder> {

    Context context;
    RequestQueue requestQueue;
    String userID = "";
    SharedPreferences sharedPreference;
    EditText editTextEAmount,editTextEDescrp, inputExpDate;
    Button buttonEAdd,buttonEUpdate;
    private ArrayList<SR_getAllEstExpenses> dataList;

    Object objectone;
    String oldExamount,oldExdes;
    String amvalue,devalue;


    public rv_estimatedentryAdapter(Context context,ArrayList<SR_getAllEstExpenses> dataList, EditText editTextEAmount,EditText editTextEDescrp, Button buttonEAdd,Button buttonEUpdate)   //EditText inputExpDate,
    {
        this.context=context;
        this.dataList = dataList;
        this.editTextEAmount=editTextEAmount;
        this.editTextEDescrp=editTextEDescrp;
        this.buttonEAdd=buttonEAdd;
        this.buttonEUpdate=buttonEUpdate;
//        this.inputExpDate=inputExpDate;



        sharedPreference=context.getSharedPreferences("MyPref",0);
//        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        userID = sharedPreference.getString(Utils.SETTING_USER_ID, "null");
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.estimatedexpenseentry_cardview, parent, false);
        return new EmployeeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EmployeeViewHolder holder,  final int position) {

        holder.txtEstDescription.setText(dataList.get(position).getEstdescription());
        holder.txtEstAmount.setText("$ " + dataList.get(position).getEstamount());

        holder.txtEstDate.setText(dataList.get(position).getEstdate());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                Toast.makeText(v.getContext(),"This Module is Available For Paid User",Toast.LENGTH_SHORT).show();
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater()
                        .inflate(R.menu.mainmenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                oldExamount=dataList.get(position).getEstamount();
                                oldExdes=dataList.get(position).getEstdescription();
                                getId();
                                editTextEAmount.setText(dataList.get(position).getEstamount());
                                editTextEDescrp.setText(dataList.get(position).getEstdescription());
//                                inputExpDate.setText(dataList.get(position).getEstdate());
//                                inputExpDate.setText("Current Date");

                                buttonEAdd.setVisibility(View.INVISIBLE);
                                buttonEUpdate.setVisibility(View.VISIBLE);

                                buttonEUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UpdateExEntry(position);
                                        Toast.makeText(context, "Updated Successfuly", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return true;




                            case R.id.delete:
                                oldExamount=dataList.get(position).getEstamount();
                                oldExdes=dataList.get(position).getEstdescription();

                                getId();
                                Toast.makeText(context, "Deleted Successfuly", Toast.LENGTH_SHORT).show();



                                new Handler().postDelayed(new Runnable() {


                                    @Override
                                    public void run() {

                                        DeleteEntry();

                                    }
                                }, 1000);



                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
//                return true;

            }
        });




    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtEstDescription, txtEstAmount, txtEstDate;
        public  View view;
        EmployeeViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txtEstDescription = (TextView) itemView.findViewById(R.id.txtEstentry_Description);
            txtEstAmount = (TextView) itemView.findViewById(R.id.txtEstentry_Amount);
            txtEstDate = (TextView) itemView.findViewById(R.id.txtEstentry_Date);


        }
    }






    public void UpdateExEntry( int position) {
        amvalue = editTextEAmount.getText().toString();
        devalue = editTextEDescrp.getText().toString();

        String Url = "http://coinbudget.com/edit_expense.php";




        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray Jarray =new JSONArray(response);
                            for (int i=0;i<Jarray.length();i++)
                            {
                                JSONObject jsonObject=(JSONObject)Jarray.get(i);
                                String code=jsonObject.getString("code");
                                if (code.equals("True"))
                                {
                                    Toast.makeText(context, "Please refresh", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Sorry try again", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {






                Map<String, String> params = new HashMap<String, String>();
                params.put("u_id",userID);
                params.put("ac_id",objectone.toString());
//                params.put("ac_id","31");
                params.put("amt",amvalue);
//                params.put("amt","40");
                params.put("des",devalue);
//                params.put("des","ttttttt");
//                params.put("Y-m-d","12-3-4");






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



        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);



    }





    public void DeleteEntry()
    {


        getId();
        String Url="http://coinbudget.com/delete_expense.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0; i<array.length();i++)
                    {
                        JSONObject jsonObject= (JSONObject) array.get(i);
                        String Success=jsonObject.getString("success");
                        if (Success.equals("True"))
                        {
                            Toast.makeText(context, "Please refresh", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, "Soory try again".toString()+Success, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() {






                Map<String, String> params = new HashMap<String, String>();
                params.put("userid",userID);
                params.put("ac_id",objectone.toString());





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



        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



    public void  getId() {


        String Url="http://coinbudget.com/getaccountidforexpense.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Object object=jsonObject.getString("success");
                    if(object.equals("1"))
                    {
                        objectone=jsonObject.getString("ac_id");

                    }
                    else
                    {
//                        Toast.makeText(context, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {






                Map<String, String> params = new HashMap<String, String>();
                params.put("u_id",userID);
                params.put("amt",oldExamount);
                params.put("des",oldExdes);





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



        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);



    }

}











