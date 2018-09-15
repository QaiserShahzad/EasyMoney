
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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easycoinbudget.Adapters.rv_estimatedentryAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.PasingData;
import com.easycoinbudget.Model.SR_InsertEstimatedExp;
import com.easycoinbudget.Model.SR_getAllEstExpenses;
import com.easycoinbudget.Model.SR_getAllEstExpensesList;
import com.easycoinbudget.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstimatedExpenseEntry extends Fragment {
    String a, b, e;
    CalendarView c;
    private static final String ENTRY = "entry";
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    TextView estExpEntry;
    PasingData obj;
    private String mEntry;
    DatePickerDialog picker;
    Button btnAdd, buttonEUpdate;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private rv_estimatedentryAdapter adapter;
    private RecyclerView recyclerView;


    String amount, frequency, ddate, descrip, estimated_type, f_id;
    EditText inputEstimatedExpEntryAmount, inputExpEntryDate, inputEstimatedExpEntryDescription;
    Spinner spinner;
    private EstimatedExpenseEntry.OnFragmentInteractionListener mListener;

    public EstimatedExpenseEntry() {
        // Required empty public constructor
    }

    public static EstimatedExpenseEntry newInstance() {
        EstimatedExpenseEntry fragment = new EstimatedExpenseEntry();
        return fragment;
    }


    public static EstimatedExpenseEntry newInstance(String entry) {


        EstimatedExpenseEntry fragment = new EstimatedExpenseEntry();
        Bundle args = new Bundle();
        args.putString(ENTRY, entry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_estimated_expense_entry, container, false);

        ((Spinner) view.findViewById(R.id.spinRecurringExpenseEntryFrequency)).setAdapter(new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Daily", "Weekly", "Every Two Weeks", "Monthly"}));

        buttonEUpdate = (Button) view.findViewById(R.id.btnExUpdate);
        buttonEUpdate.setVisibility(View.INVISIBLE);

        pref = getContext().getSharedPreferences("MyPref", 0);
        f_id = pref.getString("id", null);


        rv_displayEstimatedExp(f_id);


        inputEstimatedExpEntryAmount = (EditText) view.findViewById(R.id.inputEstimatedExpenseEntryAmount);

        inputExpEntryDate = (EditText) view.findViewById(R.id.inputEstimatedExpenseEntryDate);


        inputExpEntryDate.setOnClickListener(new View.OnClickListener() {
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

                                inputExpEntryDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        inputEstimatedExpEntryDescription = (EditText) view.findViewById(R.id.inputEstimatedExpenseEntryDescription);
        spinner = (Spinner) view.findViewById(R.id.spinRecurringExpenseEntryFrequency);


        btnAdd = (Button) view.findViewById(R.id.btn_AddEstimatedExpenseEntry);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = inputEstimatedExpEntryAmount.getText().toString();
                frequency = spinner.getSelectedItem().toString();


                ddate = inputExpEntryDate.getText().toString();
                descrip = inputEstimatedExpEntryDescription.getText().toString();

                estimated_type = getActivity().getTitle().toString();



                if (amount.matches("")) {
                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (frequency.matches("")) {
                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();

                } else if (ddate.matches("")) {

                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (descrip.matches("")) {

                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else if (estimated_type.matches("")) {

                    Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();
                } else {
                    InsertEstExp(amount, frequency, ddate, descrip, estimated_type, f_id);
                    rv_displayEstimatedExp(f_id);
                    inputEstimatedExpEntryAmount.setText("");
                    inputExpEntryDate.setText("");
                    inputEstimatedExpEntryDescription.setText("");

                }


                Log.e("EstimatedExpenseEntry", estimated_type);



            }
        });
        return view;
    }


    private void rv_displayEstimatedExp(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getAllEstExpensesList> call = apiClient.get_AllEstxpenses(user_id);


            call.enqueue(new Callback<SR_getAllEstExpensesList>() {
                @Override
                public void onResponse(Call<SR_getAllEstExpensesList> call, Response<SR_getAllEstExpensesList> response) {
                    try {

                        if (response.body().getEstimatedexp().isEmpty()) {
                            Toast.makeText(getContext(), "Please Insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateEstimatedExpList(response.body().getEstimatedexp());


                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getAllEstExpensesList> call, Throwable t) {
                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Estimated Expense", "  id - " + "Failure Method Call");

                    Log.e(" Estimated Expense", "  id - " + t.getMessage());
                }


            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void generateEstimatedExpList(ArrayList<SR_getAllEstExpenses> empDataList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_estimated_list);

        adapter = new rv_estimatedentryAdapter(getContext(),empDataList, inputEstimatedExpEntryAmount,inputEstimatedExpEntryDescription,btnAdd, buttonEUpdate);  //, inputExpEntryDate

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    public void InsertEstExp(String amount, String frequency, String ddate, String descrip, String estimated_type, String f_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertEstimatedExp> call = apiClient.insertEstimatedExpense(amount, frequency, ddate, descrip, estimated_type, f_id);


            call.enqueue(new Callback<SR_InsertEstimatedExp>() {
                @Override
                public void onResponse(Call<SR_InsertEstimatedExp> call, Response<SR_InsertEstimatedExp> response) {
                    try {


                        SR_InsertEstimatedExp insert = response.body();

                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" IncomeFragment", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {
                            // Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<SR_InsertEstimatedExp> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
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
