package com.easycoinbudget.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easycoinbudget.Model.SR_getAllReminder;
import com.easycoinbudget.R;

import java.util.ArrayList;


public class rv_reminderAdapter extends RecyclerView.Adapter<rv_reminderAdapter.EmployeeViewHolder> {


    private ArrayList<SR_getAllReminder> dataList;

    public rv_reminderAdapter(ArrayList<SR_getAllReminder> dataList) {
        this.dataList = dataList;
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reminder_cardview, parent, false);
        return new EmployeeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

        holder.txtNote.setText(dataList.get(position).getRemDescription()
        );
        holder.txtDate.setText(dataList.get(position).getRemDate());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtNote, txtDate;

        EmployeeViewHolder(View itemView) {
            super(itemView);

            txtNote = (TextView) itemView.findViewById(R.id.txt_Note);
            txtDate = (TextView) itemView.findViewById(R.id.txt_Date);
        }
    }
}










