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

import com.easycoinbudget.Adapters.DataAdapter;
import com.easycoinbudget.R;

public class ExtraIncomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private String[] data = {"Extra Income", "Investment Return", "Sale", "Bonus"};

    public ExtraIncomeFragment() {
        // Required empty public constructor
    }

    public static ExtraIncomeFragment newInstance() {
        ExtraIncomeFragment fragment = new ExtraIncomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_extra_income, container, false);
        ListView lvExtraIncome = (ListView) rootView.findViewById(R.id.lvExtraIncome);
        lvExtraIncome.setAdapter(new DataAdapter(getContext(), R.layout.layout_lv_entry, data));

        lvExtraIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = ExtraIncomeEntry.newInstance(data[i]);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                getActivity().setTitle(data[i]);
            }
        });




        return rootView;
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
