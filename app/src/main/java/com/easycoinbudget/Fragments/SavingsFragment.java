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

//import com.easymoney.Activities.view;
import com.easycoinbudget.Adapters.rv_savingGoalAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_InsertSavingGoal;
import com.easycoinbudget.Model.SR_getSavingGoal;
import com.easycoinbudget.Model.SR_getSavingGoalList;
import com.easycoinbudget.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingsFragment extends Fragment {
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    String savingGoal, savingAmount, savingGDate, user_id;
    EditText et_savingGoalAmount, et_sAmount, et_sGDate;
    Button btnAdd;
    private OnFragmentInteractionListener mListener;
    EditText selectDate;
    private RecyclerView recyclerView;
    private rv_savingGoalAdapter adapter;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public SavingsFragment() {
        // Required empty public constructor
    }

    public static SavingsFragment newInstance() {
        SavingsFragment fragment = new SavingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_savings, container, false);

        selectDate = (EditText) view.findViewById(R.id.inputSavingsGoalDate);

        selectDate.setOnClickListener(new View.OnClickListener() {
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

                                selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        pref = getContext().getSharedPreferences("MyPref", 0);
        user_id = pref.getString("id", null);

        rv_displaySavingGoal(user_id);

        et_savingGoalAmount = (EditText) view.findViewById(R.id.inputSavingsGoalTitle);

        et_sAmount = (EditText) view.findViewById(R.id.inputSavingsGoalAmount);
        et_sGDate = (EditText) view.findViewById(R.id.inputSavingsGoalDate);


        btnAdd = (Button) view.findViewById(R.id.btn_AddSavingEntry);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingGoal = et_savingGoalAmount.getText().toString();
                savingAmount = et_sAmount.getText().toString();
                savingGDate = et_sGDate.getText().toString();

                if (savingGoal.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();

                } else if (savingAmount.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else if (savingGDate.matches("")) {
                    Toast.makeText(getContext(), "Missing Input", Toast.LENGTH_SHORT).show();
                } else {

                    savingGoal(savingGoal, savingAmount, savingGDate, user_id);
                    rv_displaySavingGoal(user_id);

                    et_savingGoalAmount.setText("");
                    et_sAmount.setText("");
                    et_sGDate.setText("");

                }


            }
        });


        return view;
    }


    public void savingGoal(String savingGoal, String savingAmount, String savingGDate, String user_id) {
        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertSavingGoal> call = apiClient.insertsavingGoal(savingGoal, savingAmount, savingGDate, user_id);


            call.enqueue(new Callback<SR_InsertSavingGoal>() {
                @Override
                public void onResponse(Call<SR_InsertSavingGoal> call, Response<SR_InsertSavingGoal> response) {
                    try {


                        SR_InsertSavingGoal insert = response.body();

                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" Saving Fragement", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {
                            Toast.makeText(getContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<SR_InsertSavingGoal> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Saving Fragement", "  id - " + "Failure Method Call");

                    Log.e(" Saving Fragement", "  id - " + t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void rv_displaySavingGoal(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getSavingGoalList> call = apiClient.get_savingGoal(user_id);


            call.enqueue(new Callback<SR_getSavingGoalList>() {
                @Override
                public void onResponse(Call<SR_getSavingGoalList> call, Response<SR_getSavingGoalList> response) {
                    try {


                        if (response.body().getSavinggoal().isEmpty()) {
                            Toast.makeText(getContext(), "Please Insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateEmployeeList((ArrayList<SR_getSavingGoal>) response.body().getSavinggoal());
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getSavingGoalList> call, Throwable t) {
                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" SavingGoalFragement", "  id - " + "Failure Method Call");

                    Log.e(" SavingGoalFragement", "  id - " + t.getMessage());
                }


            });


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void generateEmployeeList(ArrayList<SR_getSavingGoal> empDataList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_Savingincome_list);

        adapter = new rv_savingGoalAdapter(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

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
        void onFragmentInteraction(Uri uri);
    }
}
