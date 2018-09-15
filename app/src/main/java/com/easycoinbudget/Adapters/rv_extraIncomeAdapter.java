package com.easycoinbudget.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easycoinbudget.Model.SR_getAllExtraIncome;
import com.easycoinbudget.R;

import java.util.ArrayList;
public class rv_extraIncomeAdapter extends  RecyclerView.Adapter<rv_extraIncomeAdapter.EmployeeViewHolder>
{


    private ArrayList<SR_getAllExtraIncome> dataList;

    public rv_extraIncomeAdapter(ArrayList<SR_getAllExtraIncome> dataList) {
        this.dataList = dataList;
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.extraincome_cardview, parent, false);
        return new EmployeeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

        holder.txt_Note.setText(dataList.get(position).getExtDescrp());
        holder.txt_Amount.setText("$ "+dataList.get(position).getExtAmount());
        holder.txt_Date.setText(dataList.get(position).getExtDate());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public   class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txt_Note, txt_Amount,txt_Date;

        EmployeeViewHolder(View itemView) {
            super(itemView);

            txt_Note = (TextView) itemView.findViewById(R.id.txt_NNote);
            txt_Amount = (TextView) itemView.findViewById(R.id.txt_Amount);
            txt_Date = (TextView) itemView.findViewById(R.id.txt_extDate);
        }
    }
}










