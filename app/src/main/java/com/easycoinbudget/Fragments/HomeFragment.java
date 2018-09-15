package com.easycoinbudget.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Model.SR_getAllRecord;
import com.easycoinbudget.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    SharedPreferences pref;
    TextView tv_tExpenses, tv_tRemIncome, tv_Income;
    String tincome, trec, test, gtotal, user_id;
    private OnFragmentInteractionListener mListener;
    int income, t_expenses, r_income;
    int total_income, t_resExp, t_estExp = 0;
    CardView secondCard;
    int  total_expenses=0;
    int rem_income = 0;
    int cv_tincome, cv_recexpense, cv_estexpense = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        try {
            tv_tRemIncome = (TextView) rootView.findViewById(R.id.tvBudgetFirst);

            tv_Income = (TextView) rootView.findViewById(R.id.tvAverageFirst);

            tv_tExpenses = (TextView) rootView.findViewById(R.id.tvDaysFirst);

            secondCard = (CardView) rootView.findViewById(R.id.cardSecond);


            pref = getContext().getSharedPreferences("MyPref", 0);
            user_id = pref.getString("id", null);

            getTotalIncome( user_id);

            Log.e("Remaining Income", String.valueOf(rem_income));

            Log.e("Remaining Income", String.valueOf(cv_estexpense));
            Log.e("Remaining Income", String.valueOf(cv_recexpense));
            Log.e("Remaining Income", String.valueOf(cv_tincome));



            secondCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),
                            "This Module is Available For Paid User", Toast.LENGTH_SHORT).show();

                }
            });
            pref = getContext().getSharedPreferences("MyPref", 0);

            // tv_tRemIncome.setText(String.valueOf(r_income));

            ((ImageButton) rootView.findViewById(R.id.iBtnAdd)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = ExtraIncomeFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    getActivity().setTitle("Extra Income");
                }
            });

            ((ImageButton) rootView.findViewById(R.id.iBtnRemove)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = TransactionsFragmant.newInstance();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    getActivity().setTitle("Transactions");
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;


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

                        cv_tincome=income.getTotalUserIncome()+income.getTotalExtraIncome();
                        cv_estexpense=income.getTotalEstExpense();
                        cv_recexpense=income.getTotalRecExpense();


                        total_expenses=cv_estexpense+cv_recexpense;
                        rem_income = cv_tincome - total_expenses;

                        Log.e("Remaining Income", String.valueOf(rem_income));

                        tv_tRemIncome.setText("$  "+String.valueOf(rem_income));

                        tv_Income.setText("$  "+String.valueOf(cv_tincome));
                        tv_tExpenses.setText("$  "+String.valueOf(cv_estexpense + cv_recexpense));

                       // t_estExp = 0;

                        total_expenses=0;
                        rem_income=0;
                        cv_tincome=0;





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