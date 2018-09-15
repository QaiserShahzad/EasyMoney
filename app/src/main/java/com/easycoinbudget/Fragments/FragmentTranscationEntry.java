package com.easycoinbudget.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_InsertRecuringExp;
import com.easycoinbudget.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTranscationEntry extends Fragment {
    private static final String ENTRY = "entry";

    String mEntry, amount, frequency, ddate, descp, rectype,id;
    EditText et_amount, et_freq, et_ddate, et_desc;
    Spinner spinn;
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    Button btn_Add;

    private OnFragmentInteractionListener mListener;

    public FragmentTranscationEntry() {
        // Required empty public constructor
    }


    public static FragmentTranscationEntry newInstance(String entry) {
        FragmentTranscationEntry fragment = new FragmentTranscationEntry();
        Bundle args = new Bundle();
        args.putString(ENTRY, entry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEntry = getArguments().getString(ENTRY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_transcation_entry, container, false);

        ((Spinner) view.findViewById(R.id.spinRecurringExpenseEntryFrequency)).setAdapter(new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Daily","Weekly","Every Two Weeks","Monthly" }));

        et_amount = (EditText) view.findViewById(R.id.inputRecurringTransactionExpenseEntryAmount);
        spinn = (Spinner) view.findViewById(R.id.spinRecurringExpenseTransacEntryFrequency);
        et_ddate = (EditText) view.findViewById(R.id.inputRecExpenseTransacEntryDate);
        et_desc = (EditText) view.findViewById(R.id.inputRecExpenseEntryDescription);
        btn_Add = (Button) view.findViewById(R.id.btnAddRecuringTransactionExpenseEntry);
        pref = getContext().getSharedPreferences("MyPref", 0);
        id=pref.getString("id",null);


        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                amount = et_amount.getText().toString();
                frequency = spinn.getSelectedItem().toString();
                ddate = et_ddate.getText().toString();
                descp = et_desc.getText().toString();
                rectype = getActivity().getTitle().toString();

                insertTranscation(amount, frequency, ddate, descp,rectype,id);
//                Toast.makeText(getContext(), rectype, Toast.LENGTH_SHORT).show();
//
//
//                Toast.makeText(getContext(), "Click Recuring Expenses", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    public void insertTranscation(String amount, String frequency, String ddate, String descp,String type,String id) {

        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertRecuringExp> call = apiClient.entryTranscation(amount, frequency, ddate, descp,rectype,id);


            call.enqueue(new Callback<SR_InsertRecuringExp>() {
                @Override
                public void onResponse(Call<SR_InsertRecuringExp> call, Response<SR_InsertRecuringExp> response) {
                    try {


                        SR_InsertRecuringExp insert = response.body();

                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" RecuringExpFragment", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {
                            Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            Log.e(" RecuringExpFragment", "  id - " + insert.getMessage());

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
                public void onFailure(Call<SR_InsertRecuringExp> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" RecuringExpFragment", "  id - " + "Failure Method Call");

                    Log.e(" RecuringExpFragment", "  id - " + t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }










    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
