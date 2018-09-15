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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easycoinbudget.Adapters.rv_bankAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_Pin;
import com.easycoinbudget.Model.SR_getAllBankDetails;
import com.easycoinbudget.Model.SR_getAllBankDetailsList;
import com.easycoinbudget.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinFragment extends Fragment {
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    EditText et_BankName, et_Pin, et_rePin;
    Button btnadd_Pin;
    String bank_name, bank_pin, re_pin, user_id;

    private OnFragmentInteractionListener mListener;
    private rv_bankAdapter adapter;
    private RecyclerView recyclerView;

    public PinFragment() {
        // Required empty public constructor
    }

    public static PinFragment newInstance(String param1, String param2) {
        PinFragment fragment = new PinFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pin, container, false);
        pref = getContext().getSharedPreferences("MyPref", 0);
        user_id = pref.getString("id", null);

        rv_displayPin(user_id);


        et_BankName = (EditText) view.findViewById(R.id.input_BankName);
        et_Pin = (EditText) view.findViewById(R.id.input_BankPin);
        et_rePin = (EditText) view.findViewById(R.id.input_ReBankPin);
        btnadd_Pin = (Button) view.findViewById(R.id.btnAdd_BankPin);


        btnadd_Pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank_name = et_BankName.getText().toString();
                bank_pin = et_Pin.getText().toString();
                re_pin = et_rePin.getText().toString();

                if (bank_name.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else if (bank_pin.matches("")) {

                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();

                } else if (re_pin.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else if (!bank_pin.equals(re_pin)) {
                    Toast.makeText(getContext(), "Pin doesnot Matched", Toast.LENGTH_SHORT).show();
                } else {

                    insert_Pin(bank_name, bank_pin, user_id);
                    rv_displayPin(user_id);
                    et_BankName.setText("");
                    et_Pin.setText("");
                    et_rePin.setText("");


                }
            }
        });


        return view;
    }


    private void rv_displayPin(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getAllBankDetailsList> call = apiClient.get_getAllBankDetails(user_id);


            call.enqueue(new Callback<SR_getAllBankDetailsList>() {
                @Override
                public void onResponse(Call<SR_getAllBankDetailsList> call, Response<SR_getAllBankDetailsList> response) {
                    try {


                        if (response.body().getBankinfo().isEmpty()) {
                            Toast.makeText(getContext(), "Please Insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateBankPinList((ArrayList<SR_getAllBankDetails
                                    >) response.body().getBankinfo());

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getAllBankDetailsList> call, Throwable t) {
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

    private void generateBankPinList(ArrayList<SR_getAllBankDetails> pinList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_Bank_list);

        adapter = new rv_bankAdapter(pinList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    public void insert_Pin(String bank_name, String pin, String id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_Pin> call = apiClient.insert_Pin(bank_name, pin, id);


            call.enqueue(new Callback<SR_Pin>() {
                @Override
                public void onResponse(Call<SR_Pin> call, Response<SR_Pin> response) {
                    try {


                        SR_Pin insert = response.body();

                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" Saving Fragement", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {
                            Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            Log.e(" Saving Fragement", "  id - " + insert.getMessage());

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
                public void onFailure(Call<SR_Pin> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Bank Pin", "  id - " + "Failure Method Call");

                    Log.e(" Bank Pin", "  id - " + t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }


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
        void onFragmentInteraction(Uri uri);
    }
}
