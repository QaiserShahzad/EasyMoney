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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.easycoinbudget.Adapters.rv_reminderAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_InsertReminder;
import com.easycoinbudget.Model.SR_getAllReminder;
import com.easycoinbudget.Model.SR_getAllReminderList;
import com.easycoinbudget.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderFragment extends Fragment {
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    EditText et_Date, et_Note;
    String date, note, user_id;

    private rv_reminderAdapter adapter;
    private RecyclerView recyclerView;

    Button btn_addReminder;
    private OnFragmentInteractionListener mListener;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public ReminderFragment() {
        // Required empty public constructor
    }

    public static ReminderFragment newInstance() {
        ReminderFragment fragment = new ReminderFragment();
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
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        pref = getContext().getSharedPreferences("MyPref", 0);
        user_id = pref.getString("id", null);


        rv_displayReminder(user_id);


        et_Date = (EditText) view.findViewById(R.id.input_ReminderDate);


        et_Date.setOnClickListener(new View.OnClickListener() {
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

                                et_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        et_Note = (EditText) view.findViewById(R.id.input_ReminderNote);


        btn_addReminder = (Button) view.findViewById(R.id.btn_AddReminder);
        btn_addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = et_Date.getText().toString();
                note = et_Note.getText().toString();

                if (date.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else if (note.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else {

                    insertReminder(date, note, user_id);
                    rv_displayReminder(user_id);
                    et_Date.setText("");
                    et_Note.setText("");
                }


            }
        });


        return view;

    }


    private void rv_displayReminder(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getAllReminderList> call = apiClient.get_ReminderDetails(user_id);


            call.enqueue(new Callback<SR_getAllReminderList>() {
                @Override
                public void onResponse(Call<SR_getAllReminderList> call, Response<SR_getAllReminderList> response) {
                    try {


                        if (response.body().getReminder().isEmpty()) {
                            Toast.makeText(getContext(), "Please Insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateReminderList((ArrayList<SR_getAllReminder>) response.body().getReminder());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getAllReminderList> call, Throwable t) {
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

    private void generateReminderList(ArrayList<SR_getAllReminder> empDataList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_income_list);

        adapter = new rv_reminderAdapter(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    public void insertReminder(String date, String note, String id) {

        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertReminder> call = apiClient.insert_Reminder(date, note, id);


            call.enqueue(new Callback<SR_InsertReminder>() {
                @Override
                public void onResponse(Call<SR_InsertReminder> call, Response<SR_InsertReminder> response) {
                    try {


                        SR_InsertReminder insert = response.body();

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
                public void onFailure(Call<SR_InsertReminder> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Saving Reminder", "  id - " + "Failure Method Call");

                    Log.e(" Reminder", "  id - " + t.getMessage());
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
