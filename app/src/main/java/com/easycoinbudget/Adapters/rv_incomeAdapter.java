package com.easycoinbudget.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.easymoney.Activities.view;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easycoinbudget.Activities.MainActivity_SendPassword;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.Model.rv_getIncome;
import com.easycoinbudget.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class rv_incomeAdapter extends RecyclerView.Adapter<rv_incomeAdapter.EmployeeViewHolder> {
    CardView cardView;
    Context context;
    RequestQueue requestQueue;
    String userID = "";
    SharedPreferences sharedPreference;

    private ArrayList<rv_getIncome> dataList;
    EditText editTextAmount,editTextDescrp;
    Button buttonAdd,buttonUpdate;
String oldamount,olddes;


    Object objectone;



String amvalue,frevalue,devalue;

    public rv_incomeAdapter(Context context, ArrayList<rv_getIncome> dataList, EditText editTextAmount, EditText editTextDescrp,Button buttonAdd,Button buttonUpdate) {
        this.context=context;
        this.dataList = dataList;
        this.editTextAmount=editTextAmount;
        this.editTextDescrp=editTextDescrp;
        this.buttonAdd=buttonAdd;
        this.buttonUpdate=buttonUpdate;



        sharedPreference=context.getSharedPreferences("MyPref",0);
        userID = sharedPreference.getString(Utils.SETTING_USER_ID, "null");

    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.income_cardview, parent, false);
        return new EmployeeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final EmployeeViewHolder holder, final int position) {



        holder.txtIncDescription.setText(dataList.get(position).getDescription());
        holder.txtIncAmount.setText("$ " + dataList.get(position).getAmount());


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
                                oldamount=dataList.get(position).getAmount();
                                olddes=dataList.get(position).getDescription();
                                getId();
                                editTextAmount.setText(dataList.get(position).getAmount());
                                editTextDescrp.setText(dataList.get(position).getDescription());
                                buttonAdd.setVisibility(View.INVISIBLE);
                                buttonUpdate.setVisibility(View.VISIBLE);

                                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       UpdateEntry(position);
                                        Toast.makeText(context, "Edited Successfuly", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return true;




                            case R.id.delete:
                                oldamount=dataList.get(position).getAmount();
                                olddes=dataList.get(position).getDescription();

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
//

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public static View view;
        private  CardView cardView;
        //public Item currentItem;
        TextView txtIncDescription, txtIncAmount;
 Spinner spinner;
        EmployeeViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            cardView = (CardView) itemView.findViewById(R.id.incomecardv);
            txtIncDescription = (TextView) itemView.findViewById(R.id.txt_description);
            txtIncAmount = (TextView) itemView.findViewById(R.id.txt_amount);


            //cardView.setOnClickListener((View.OnClickListener) itemView.getContext());
        }

    }


    public void DeleteEntry()
    {


        getId();
        String Url="http://coinbudget.com/delete_income.php";

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
                            Toast.makeText(context, "Treueee", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, "False".toString()+Success, Toast.LENGTH_SHORT).show();
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




    public void UpdateEntry( int position)
    {
                amvalue=editTextAmount.getText().toString();
                devalue=editTextDescrp.getText().toString();

        String Url="http://coinbudget.com/edit_income.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0; i<array.length();i++)
                    {
                        JSONObject jsonObject= (JSONObject) array.get(i);
                        String Code=jsonObject.getString("code");
                        if (Code.equals("True"))
                        {

                            Toast.makeText(context, "Treueee", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, "False".toString()+Code, Toast.LENGTH_SHORT).show();
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
                params.put("u_id",userID);
                params.put("ac_id",objectone.toString());
                params.put("amt",amvalue);
                params.put("des",devalue);






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


         String Url="http://coinbudget.com/getaccountidforincone.php";

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
                        Toast.makeText(context, "Some thing went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("amt",oldamount);
                params.put("des",olddes);





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










