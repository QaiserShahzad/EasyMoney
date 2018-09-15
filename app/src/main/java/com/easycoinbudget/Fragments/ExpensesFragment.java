package com.easycoinbudget.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.easycoinbudget.Adapters.DataAdapter;
import com.easycoinbudget.Model.PasingData;
import com.easycoinbudget.R;

public  class ExpensesFragment extends Fragment {
    private static String TYPE = "type";
    private static int type;
    TextView title;
    private ListView lvExpenses;
    PasingData pdata;

    private String[] dataRecur = {"Mortgage / Rent", "Auto Payments", "Utilities: Gas", "Utilities: Electric", "Utilities: Water",
            "Trash", "Phone", "Credit Card", "Loan", "Insurance", "Transportatoin: Bus / Train / Taxi Fare", "Television / Internet",
            "Gym", " Saving Goal", "Prescriptions", "Subscriptions", "Personal", "Alimony / Child Support", "Child Care", "Children",
            "Investment Account", "Retirement Account", "Education"
    };

    private String[] dataEstimate = {"Groceries", "Auto Maintenance", "Fuel", "Household", "Food: Breakfast", "Food: Lunch",
            "Food: Dinner", "Alcohol / Drinks", "Coffee", "Shopping", "Entertainment", "Music / Movies / Books", "Toys", "Pets",
            "Health & Beauty", "Personal", "Other"
    };

    private OnFragmentInteractionListener mListener;

    public ExpensesFragment() {
        // Required empty public constructor
    }

    public static ExpensesFragment newInstance(int type) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        switch (type) {

            case 1:

                lvExpenses = (ListView) view.findViewById(R.id.lvExpenses);
                lvExpenses.setAdapter(new DataAdapter(getContext(), R.layout.layout_lv_entry, dataRecur));
                lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Fragment fragment = RecurringExpenseEntry.newInstance(dataRecur[i]);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        getActivity().setTitle(dataRecur[i]);
                    }
                });
                break;
            case 2:
                lvExpenses = (ListView) view.findViewById(R.id.lvExpenses);
                lvExpenses.setAdapter(new DataAdapter(getContext(), R.layout.layout_lv_entry, dataEstimate));
                lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Fragment fragment = EstimatedExpenseEntry.newInstance(dataEstimate[i]);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        getActivity().setTitle(dataEstimate[i]);
                    }
                });
                break;
        }
        return view;
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
