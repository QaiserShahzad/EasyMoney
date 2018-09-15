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

import com.easycoinbudget.Adapters.rv_extraIncomeAdapter;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_InsertExtraIncome;
import com.easycoinbudget.Model.SR_getAllExtraIncome;
import com.easycoinbudget.Model.SR_getAllExtraIncomeList;
import com.easycoinbudget.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExtraIncomeEntry extends Fragment {

    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    EditText et_Extraamount, et_Extradate, et_Extranote;
    String amount, date, note, user_id, mEntry;
    String type;
    Button btnAdd_ExtraIncome;
    private OnFragmentInteractionListener mListener;
    private static final String ENTRY = "entry";
    private int mYear, mMonth, mDay, mHour, mMinute;

    private rv_extraIncomeAdapter adapter;
    private RecyclerView recyclerView;

    public ExtraIncomeEntry() {
        // Required empty public constructor
    }


    public static ExtraIncomeEntry newInstance(String entry) {
        ExtraIncomeEntry fragment = new ExtraIncomeEntry();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_extra_income_entry, container, false);
        pref = getContext().getSharedPreferences("MyPref", 0);
        user_id = pref.getString("id", null);


        rv_displayExtraIncome(user_id);


        et_Extraamount = (EditText) view.findViewById(R.id.inputExtra_IncomeAmount);
        et_Extradate = (EditText) view.findViewById(R.id.inputExtra_IncomeDate);

        et_Extradate.setOnClickListener(new View.OnClickListener() {
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

                                et_Extradate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        et_Extranote = (EditText) view.findViewById(R.id.inputExtra_IncomeNote);
        btnAdd_ExtraIncome = (Button) view.findViewById(R.id.btnAdd_ExtraIncome);

        btnAdd_ExtraIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = et_Extraamount.getText().toString();
                date = et_Extradate.getText().toString();
                note = et_Extranote.getText().toString();
                type = getActivity().getTitle().toString();


                if(amount.matches(""))
                {Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();}
                else if(date.matches(""))
                {Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();}
                else if(note.matches(""))
                {Toast.makeText(getContext(),"Missing Input",Toast.LENGTH_SHORT).show();}
                else {
                    insert_ExtraIncome(amount, date, note, type, user_id);

                }




                et_Extraamount.setText("");
                et_Extradate.setText("");
                et_Extradate.setText("");
                et_Extranote.setText("");
            }
        });


        return view;

    }

    private void rv_displayExtraIncome(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getAllExtraIncomeList> call = apiClient.get_ExtraIncomeDetails(user_id);


            call.enqueue(new Callback<SR_getAllExtraIncomeList>() {
                @Override
                public void onResponse(Call<SR_getAllExtraIncomeList> call, Response<SR_getAllExtraIncomeList> response) {
                    try {

//
                        if (response.body().getExtraIncomeDetails().isEmpty()) {
                            Toast.makeText(getContext(), "Plea" +
                                    "se insert Record", Toast.LENGTH_SHORT).show();

                        } else {
                            generateExtraIncomeList(response.body().getExtraIncomeDetails());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getAllExtraIncomeList> call, Throwable t) {
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

    private void generateExtraIncomeList(ArrayList<SR_getAllExtraIncome> empDataList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_Extraincome_list);

        adapter = new rv_extraIncomeAdapter(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    public void insert_ExtraIncome(String amount, String date, String note, String type, String user_id) {
        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_InsertExtraIncome> call = apiClient.insertExtraIncome(amount, date, note, type, user_id);


            call.enqueue(new Callback<SR_InsertExtraIncome>() {
                @Override
                public void onResponse(Call<SR_InsertExtraIncome> call, Response<SR_InsertExtraIncome> response) {
                    try {


                        SR_InsertExtraIncome insert = response.body();

                        int resp = insert.getSuccess();
//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();
                        Log.e(" RecuringExpFragment", "  id - " + (resp));

                        //check the status code
                        if (resp == 1) {
                            Toast.makeText(getContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<SR_InsertExtraIncome> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Extra Income ", "  id - " + "Failure Method Call");

                    Log.e(" Extra Income", "  id - " + t.getMessage());
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
