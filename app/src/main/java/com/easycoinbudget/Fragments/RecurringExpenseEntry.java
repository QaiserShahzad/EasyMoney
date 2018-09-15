package com.easycoinbudget.Fragments;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.easycoinbudget.Adapters.rv_recuringentryAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_InsertRecuringExp;
import com.easycoinbudget.Model.SR_getAllRecExpenses;
import com.easycoinbudget.Model.SR_getAllRecExpensesList;
import com.easycoinbudget.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecurringExpenseEntry extends Fragment {
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    private static final String ENTRY = "entry";

    String mEntry, amount, frequency, ddate, descp, rectype, user_id;
    EditText et_amount, et_freq, et_ddate, et_desc;
    Spinner spinn;
    EditText selectDate;
    Button btn_Add,btn_RecUpdate;
    private rv_recuringentryAdapter adapter;
    private RecyclerView recyclerView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private OnFragmentInteractionListener mListener;

    public RecurringExpenseEntry() {
        // Required empty public constructor
    }

    public static RecurringExpenseEntry newInstance(String entry) {
        RecurringExpenseEntry fragment = new RecurringExpenseEntry();
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
        View view = inflater.inflate(R.layout.fragment_recurring_expense_entry, container, false);

        ((Spinner) view.findViewById(R.id.spinRecurringExpenseEntryFrequency)).setAdapter(new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Daily", "Weekly", "Every Two Weeks", "Monthly"}));

        btn_RecUpdate = (Button) view.findViewById(R.id.btnRecUpdate);
        btn_RecUpdate.setVisibility(View.INVISIBLE);

        pref = getContext().getSharedPreferences("MyPref", 0);
        user_id = pref.getString("id", null);


        rv_displayrecuringExp(user_id);


        et_amount = (EditText) view.findViewById(R.id.inputRecurringExpenseEntryAmount);
        spinn = (Spinner) view.findViewById(R.id.spinRecurringExpenseEntryFrequency);
        et_ddate = (EditText) view.findViewById(R.id.inputRecExpenseEntryDate);


        et_desc = (EditText) view.findViewById(R.id.inputRecExpenseEntryDescription);
        btn_Add = (Button) view.findViewById(R.id.btnAddRecuringExpenseEntry);


        et_ddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                et_ddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                amount = et_amount.getText().toString();
                frequency = spinn.getSelectedItem().toString();
                ddate = et_ddate.getText().toString();
                descp = et_desc.getText().toString();
                rectype = getActivity().getTitle().toString();

                if (amount.matches(""))
                {
                Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (frequency.matches("")) {
                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (ddate.matches("")) {
                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (descp.matches("")) {
                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (rectype.matches("")) {
                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else {

                    insertRecuringExpenses(amount, frequency, ddate, descp, rectype, user_id);
                    rv_displayrecuringExp(user_id);
                    et_amount.setText("");
                    et_ddate.setText("");
                    et_desc.setText("");
                }
            }
        });


        return view;
    }


    private void rv_displayrecuringExp(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getAllRecExpensesList> call = apiClient.get_AllRecExpenses(user_id);


            call.enqueue(new Callback<SR_getAllRecExpensesList>() {
                @Override
                public void onResponse(Call<SR_getAllRecExpensesList> call, Response<SR_getAllRecExpensesList> response) {
                    try {


                        if (response.body().getRecuringexp().isEmpty()) {
                            Toast.makeText(getContext(), "Please Insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateRecuringExp(response.body().getRecuringexp());
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getAllRecExpensesList> call, Throwable t) {
                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" RecuringExpensesList", "  id - " + "Failure Method Call");

                    Log.e(" RecuringExpensesList", "  id - " + t.getMessage());
                }


            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void generateRecuringExp(ArrayList<SR_getAllRecExpenses> recDataList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_income_list);

        adapter = new rv_recuringentryAdapter(getContext(),recDataList,et_amount, et_desc, btn_Add,btn_RecUpdate);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    public void insertRecuringExpenses(String amount, String frequency, String ddate, String descp, String rectype, String user_id) {

        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertRecuringExp> call = apiClient.insertRecuringExpense(amount, frequency, ddate, descp, rectype, user_id);


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
                            Toast.makeText(getContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();


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
