package com.easycoinbudget.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.easycoinbudget.Adapters.DataAdapter;
import com.easycoinbudget.R;

public class BankFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private static String TYPE = "type";
    private static int type;
    private ListView lvBank;
    private Class fragmentClass = null;
    private Fragment fragment = null;

    public BankFragment() {
        // Required empty public constructor
    }

    public static BankFragment newInstance() {
        BankFragment fragment = new BankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bank, container, false);
        lvBank = (ListView) rootView.findViewById(R.id.lvBank);
        lvBank.setAdapter(new DataAdapter(getContext(), R.layout.layout_lv_entry,
                new String[]{"Add Bank Account", "Add Loan Account", "Add Additional Account"}));


        //lvBank.setAdapter(new DataAdapter(getContext(), R.layout.layout_lv_entry,null));
        lvBank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View rootView, int i, long l) {

                try {
                    fragmentClass = Fragment_plaid.class;
                    loadFragment();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }


            }
        });


        return rootView;
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
