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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.easycoinbudget.R;

public class TransactionsFragmant extends Fragment {

    private OnFragmentInteractionListener mListener;

    private String[] data = {"General", "Groceries", "Restaurants", "Coffee", "Household", "Fuel", "Transportation",
            "Entertainment", "Health & Beauty", "Fitness", "Shopping", "Pets", "Education", "Children"
    };

    public TransactionsFragmant() {
        // Required empty public constructor
    }

    public static TransactionsFragmant newInstance() {
        TransactionsFragmant fragment = new TransactionsFragmant();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_fragmant, container, false);
        ListView lvTransactions = (ListView) view.findViewById(R.id.lvTransactions);
        lvTransactions.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.layout_lv_entry,R.id.tvEntryName, data));


        lvTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = RecurringExpenseEntry.newInstance(data[i]);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                getActivity().setTitle(data[i]);
            }
        });





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
