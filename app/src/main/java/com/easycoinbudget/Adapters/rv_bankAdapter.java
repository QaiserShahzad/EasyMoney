
package com.easycoinbudget.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easycoinbudget.Model.SR_getAllBankDetails;

import com.easycoinbudget.R;

import java.util.ArrayList;


public class rv_bankAdapter extends RecyclerView.Adapter<rv_bankAdapter.EmployeeViewHolder> {


    private ArrayList<SR_getAllBankDetails> dataList;

    public rv_bankAdapter(ArrayList<SR_getAllBankDetails> dataList) {
        this.dataList = dataList;
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bank_cardview, parent, false);
        return new EmployeeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

        holder.txtBankName.setText(dataList.get(position).getBankname()
        );
        holder.txtBankPin.setText(dataList.get(position).getBankpin());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtBankName, txtBankPin;

        EmployeeViewHolder(View itemView) {
            super(itemView);

            txtBankName = (TextView) itemView.findViewById(R.id.txt_BankName);
            txtBankPin = (TextView) itemView.findViewById(R.id.txt_BankPin);
        }
    }
}











