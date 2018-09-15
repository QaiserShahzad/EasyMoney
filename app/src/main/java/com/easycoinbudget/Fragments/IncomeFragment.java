package com.easycoinbudget.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import com.easymoney.Activities.times;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easycoinbudget.Adapters.rv_incomeAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;

import com.easycoinbudget.Model.PasingData;
import com.easycoinbudget.Model.SR_InsertIncome;
import com.easycoinbudget.Model.SR_Login;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.Model.rv_getIncome;
import com.easycoinbudget.Model.rv_getIncomeList;
import com.easycoinbudget.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeFragment extends Fragment {
    EditText inputAmount, inputDescription;
    Button btn_AddIncome, buttonUpdate;
    String amount, user_id;
    String frequency, descrip;
    String incomeID = "";
    SharedPreferences sharedPreference;
    String foreign_Id;
    Spinner spinner;
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    String abc;
    private OnFragmentInteractionListener mListener;
    SR_Login login;
    PasingData data;
    private rv_incomeAdapter adapter;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;

    public IncomeFragment() {
        // Required empty public constructor
    }

    public static IncomeFragment newInstance() {
        IncomeFragment fragment = new IncomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_income, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        ((Spinner) view.findViewById(R.id.spinFrequency)).setAdapter(new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Daily", "Weekly", "Every Two Weeks", "Monthly"}));

        pref = getContext().getSharedPreferences("MyPref", 0);
        abc = pref.getString("id", null);
        user_id = pref.getString("id", null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_income_list);



        sharedPreference = getActivity().getSharedPreferences(Utils.SETTING_KEY, Context.MODE_PRIVATE);
        incomeID = sharedPreference.getString(Utils.SETTING_INCOME_ID, "null");



        inputAmount = (EditText) view.findViewById(R.id.inputAmount);
        inputDescription = (EditText) view.findViewById(R.id.inputDescription);
        data = new PasingData();
        btn_AddIncome = (Button) view.findViewById(R.id.btnAdd);
        buttonUpdate = (Button) view.findViewById(R.id.btnUpdate);
        buttonUpdate.setVisibility(View.INVISIBLE);

        spinner = (Spinner) view.findViewById(R.id.spinFrequency);
        login = new SR_Login();
        frequency = spinner.getSelectedItem().toString();
        rv_displayincome(user_id);









        btn_AddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = inputAmount.getText().toString();
               // frequency = spinner.getSelectedItem().toString();
                descrip = inputDescription.getText().toString();
                foreign_Id = login.getUserId();

                if (amount.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else if (descrip.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else {

                    insertIncome(amount, frequency, descrip, abc);
                    rv_displayincome(user_id);


                }

                inputAmount.setText("");
                inputDescription.setText("");
            }
        });

        return view;


    }


    private void insertIncome(String amount, String frequency, String descrip, String foreign_Id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertIncome> call = apiClient.insertIncome(amount, frequency, descrip, foreign_Id);


            call.enqueue(new Callback<SR_InsertIncome>() {
                @Override
                public void onResponse(Call<SR_InsertIncome> call, Response<SR_InsertIncome> response) {
                    try {

                        Toast.makeText(getContext(), "Ress"+response, Toast.LENGTH_SHORT).show();

                        SR_InsertIncome insert = response.body();

                        // display_income(response.body());
                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" IncomeFragment", "  id - " + (resp));


                        //check the status code


                        if (resp == 1) {


                            Toast.makeText(getContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                            Log.e(" IncomeFragment", "  id - " + insert.getMessage());

                        } else {
//                            Toast.makeText(getContext(), "inserted on response", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            //Log.e(" IncomeFragment", "  id - " + "Data not Inserted");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_InsertIncome> call, Throwable t) {
                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" IncomeFragment", "  id - " + "Failure Method Call");

                    Log.e(" IncomeFragment", "  id - " + t.getMessage());
                }


            });


        } catch (Exception e) {
            e.printStackTrace();

        }

    }





    private void rv_displayincome(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<rv_getIncomeList> call = apiClient.get_IncomeDetails(user_id);


            call.enqueue(new Callback<rv_getIncomeList>() {
                @Override
                public void onResponse(Call<rv_getIncomeList> call, Response<rv_getIncomeList> response) {
                    try {



//                        rv_getIncomeList incomeIDD=response.body();

//                        editor.putString(Utils.SETTING_USER_ID, incomeID.getUserIncome());


                        if (response.body().getUserIncome().isEmpty()) {
                            Toast.makeText(getContext(), "Please Insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateEmployeeList(response.body().getUserIncome());

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<rv_getIncomeList> call, Throwable t) {
                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();

                    Log.e(" IncomeFragment", "  id - " + "Failure Method Call");

                    Log.e(" IncomeFragment", "  id - " + t.getMessage());
                }


            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }




    private void generateEmployeeList(ArrayList<rv_getIncome> empDataList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_income_list);

        adapter = new rv_incomeAdapter(getContext(),empDataList,inputAmount,inputDescription,btn_AddIncome,buttonUpdate);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);




    }

}




