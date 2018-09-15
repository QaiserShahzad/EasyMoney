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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easycoinbudget.Model.SR_getAllRecExpenses;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class rv_recuringentryAdapter extends RecyclerView.Adapter<rv_recuringentryAdapter.EmployeeViewHolder> {


    Context context;
    RequestQueue requestQueue;
    String userID = "";
    SharedPreferences sharedPreference;
    EditText editTextRecAmount,editTextRecDescrp, inputRecDate;
    Button buttonRecAdd,buttonRecUpdate;

    private ArrayList<SR_getAllRecExpenses> dataList;

    Object objectone;
    String oldRecamount,oldRecdes;
    String amvalue,devalue;

    public rv_recuringentryAdapter(Context context,ArrayList<SR_getAllRecExpenses> dataList, EditText editTextRecAmount,EditText editTextRecDescrp,Button buttonRecAdd,Button buttonRecUpdate) {
        this.context=context;
        this.dataList = dataList;
        this.editTextRecAmount=editTextRecAmount;
        this.editTextRecDescrp=editTextRecDescrp;
        this.buttonRecAdd=buttonRecAdd;
        this.buttonRecUpdate=buttonRecUpdate;

        sharedPreference=context.getSharedPreferences("MyPref",0);
//        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        userID = sharedPreference.getString(Utils.SETTING_USER_ID, "null");
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recuringexpenseentry_cardview
                , parent, false);
        return new EmployeeViewHolder(view);

    }


    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, final int position) {

        holder.txtRecDescription.setText(dataList.get(position).getRecdescription());
        holder.txtRecAmount.setText("$ " + dataList.get(position).getRecamount());

        holder.txtRecDate.setText(dataList.get(position).getRecdate());

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
                                oldRecamount=dataList.get(position).getRecamount();
                                oldRecdes=dataList.get(position).getRecdescription();
                                getId();
                                editTextRecAmount.setText(dataList.get(position).getRecamount());
                                editTextRecDescrp.setText(dataList.get(position).getRecdescription());
//                                inputExpDate.setText(dataList.get(position).getEstdate());

                                buttonRecAdd.setVisibility(View.INVISIBLE);
                                buttonRecUpdate.setVisibility(View.VISIBLE);

                                buttonRecUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UpdateRecEntry(position);
                                        Toast.makeText(context, "Updated Successfuly", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return true;




                            case R.id.delete:
                                oldRecamount=dataList.get(position).getRecamount();
                                oldRecdes=dataList.get(position).getRecdescription();

                                getId();
                                Toast.makeText(context, "Deleted Successfuly", Toast.LENGTH_SHORT).show();



                                new Handler().postDelayed(new Runnable() {


                                    @Override
                                    public void run() {

                                        DeleteRecEntry();

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

        TextView txtRecDescription, txtRecAmount, txtRecDate;
        public  View view;
        EmployeeViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txtRecDescription = (TextView) itemView.findViewById(R.id.txtrecentry_Description);
            txtRecAmount = (TextView) itemView.findViewById(R.id.txtrecentry_Amount);
            txtRecDate = (TextView) itemView.findViewById(R.id.txtrecentry_Date);


        }
    }




    public void UpdateRecEntry( int position) {
        amvalue = editTextRecAmount.getText().toString();
        devalue = editTextRecDescrp.getText().toString();

        String Url = "http://coinbudget.com/edit_recexpense.php";




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
                            Toast.makeText(context, "Please Refresh", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Soory try again", Toast.LENGTH_SHORT).show();
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



    public void DeleteRecEntry()
    {


        getId();
        String Url="http://coinbudget.com/delete_Recexpense.php";

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


        String Url="http://coinbudget.com/getaccountidforrecexpense.php";

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
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("amt",oldRecamount);
                params.put("des",oldRecdes);





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











