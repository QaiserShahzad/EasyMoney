package com.easycoinbudget.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_getAllRecord;
import com.easycoinbudget.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    String user_id;
    static int t_resExp = 0;
    static int t_estExp = 0;
    static int Grand_total = 0;
    static int exp = 0;
    Button btn_GetIncome;
    public int a, b, c;
    TextView t_totalincome, t_totalRecIncome, t_totalEstIncome, t_Total, tv_totalExpenses;
    public int res;
    private Class fragmentClass = null;
    int total_income, Rec_Expenses, Est_Expenses, total_expenses, Remaining_Income,extra_income;
    private Fragment fragment = null;

    public BudgetFragment() {
        // Required empty public constructor
    }

    public static BudgetFragment newInstance() {
        BudgetFragment fragment = new BudgetFragment();
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
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        try {


            pref = getContext().getSharedPreferences("MyPref", 0);
            user_id = pref.getString("id", null);
            t_totalincome = (TextView) view.findViewById(R.id.tvTotalIncome);
            t_totalRecIncome = (TextView) view.findViewById(R.id.tvTotalRecurringExpenses);
            t_totalEstIncome = (TextView) view.findViewById(R.id.tvTotalEstimatedExpenses);
            tv_totalExpenses = (TextView) view.findViewById(R.id.tv_TotalExpenses);
            t_Total = (TextView) view.findViewById(R.id.tvTotalRemaining);


            getTotalIncome(user_id);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

    private void loadFragment() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public void getTotalIncome(String user_id) {

        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_getAllRecord> call = apiClient.getAllAmount(user_id);


            call.enqueue(new Callback<SR_getAllRecord>() {
                @Override
                public void onResponse(Call<SR_getAllRecord> call, Response<SR_getAllRecord> response) {
                    try {


                        SR_getAllRecord income = response.body();

                        res = income.getTotalUserIncome();

                        String result1 = String.valueOf(res);
                        extra_income=income.getTotalExtraIncome();
                        total_income = res+extra_income;
                        res = 0;


                        t_resExp = income.getTotalRecExpense();
                        String result = String.valueOf(t_resExp);
                        Rec_Expenses = t_resExp;
                        t_resExp = 0;

                        t_estExp = income.getTotalEstExpense();
                        Est_Expenses = t_estExp;
                        t_estExp = 0;


                        total_expenses = Rec_Expenses + Est_Expenses;
//                        Thread.sleep(3000);
                        t_totalincome.setText("$  "+String.valueOf(total_income));
                        t_totalRecIncome.setText("$  "+String.valueOf(Rec_Expenses));

                        t_totalEstIncome.setText("$  "+String.valueOf(Est_Expenses));
                        tv_totalExpenses.setText("$  "+String.valueOf(total_expenses));
                        Remaining_Income = total_income - total_expenses;
                        t_Total.setText("$  "+String.valueOf(Remaining_Income));

                        total_income = 0;
                        Rec_Expenses = 0;
                        Est_Expenses = 0;
                        total_expenses = 0;
                        Remaining_Income = 0;


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_getAllRecord> call, Throwable t) {
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


//    public void getTotalRecExpenses(String user_id) {
//        try {
//            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);
//
//            Call<SR_getSingleValue> call = apiClient.recExpenseAmount(user_id);
//
//
//            call.enqueue(new Callback<SR_getSingleValue>() {
//                @Override
//                public void onResponse(Call<SR_getSingleValue> call, Response<SR_getSingleValue> response) {
//                    try {
//
//                        SR_getSingleValue value = response.body();
//
////                        for (int i = 0; i < insert.size(); i++) {
////
////                            t_resExp = t_resExp + Integer.parseInt(insert.get(i).getAmount());
////
////
////                        }
//
//                        t_resExp = value.getSuccess();
//                        String result = String.valueOf(t_resExp);
//                        Rec_Expenses = t_resExp;
//
//
//                        t_resExp = 0;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<SR_getSingleValue> call, Throwable t) {
////                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
////
////                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("Total Recuring Expenses", "  id - " + "Failure Method Call");
//
//                    Log.e("Total Recuring Expenses", "  id - " + t.getMessage());
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//
//    }
//
//    public void getTotalEstExpenses(String user_id) {
//        try {
//            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);
//
//            Call<List<SR_Tincome>> call = apiClient.get_EstExpenses(user_id);
//
//
//            call.enqueue(new Callback<List<SR_Tincome>>() {
//                @Override
//                public void onResponse(Call<List<SR_Tincome>> call, Response<List<SR_Tincome>> response) {
//                    try {
//
//
//                        List<SR_Tincome> insert = response.body();
//
//                        for (int i = 0; i < insert.size(); i++) {
//
//                            t_estExp = t_estExp + Integer.parseInt(insert.get(i).getAmount());
//
//
//                        }
//
//                        String result = String.valueOf(t_estExp);
//                        Est_Expenses = t_estExp;
//                        t_estExp = 0;
//
//                        total_expenses = Rec_Expenses + Est_Expenses;
//                        Thread.sleep(3000);
//                        t_totalincome.setText(String.valueOf(total_income));
//                        t_totalRecIncome.setText(String.valueOf(Rec_Expenses));
//
//                        t_totalEstIncome.setText(String.valueOf(Est_Expenses));
//                        tv_totalExpenses.setText(String.valueOf(total_expenses));
//                        Remaining_Income = total_income - total_expenses;
//                        t_Total.setText(String.valueOf(Remaining_Income));
//
//                        total_income = 0;
//                        Rec_Expenses = 0;
//                        Est_Expenses = 0;
//                        total_expenses = 0;
//                        Remaining_Income = 0;
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<SR_Tincome>> call, Throwable t) {
////                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();
////
////                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("Total Recuring Expenses", "  id - " + "Failure Method Call");
//
//                    Log.e("Total Recuring Expenses", "  id - " + t.getMessage());
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PinFragment.OnFragmentInteractionListener) {
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