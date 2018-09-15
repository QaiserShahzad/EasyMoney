package com.easycoinbudget.Adapters;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.easycoinbudget.Model.SR_getSavingGoal;
        import com.easycoinbudget.R;

        import java.util.ArrayList;
public class rv_savingGoalAdapter extends  RecyclerView.Adapter<rv_savingGoalAdapter.EmployeeViewHolder>
{


    private ArrayList<SR_getSavingGoal> dataList;

    public rv_savingGoalAdapter(ArrayList<SR_getSavingGoal> dataList) {
        this.dataList = dataList;
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.saving_cardview, parent, false);
        return new EmployeeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

        holder.txt_Goal.setText("$"+dataList.get(position).getSavgoalTamount());
        holder.txt_GoalAmount.setText("$ "+dataList.get(position).getSavgoalAmount());
        holder.txt_GoalDate.setText(dataList.get(position).getSavgoalDate());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public   class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txt_Goal, txt_GoalAmount,txt_GoalDate;

        EmployeeViewHolder(View itemView) {
            super(itemView);

            txt_Goal = (TextView) itemView.findViewById(R.id.txt_savingGoal);
            txt_GoalAmount = (TextView) itemView.findViewById(R.id.txt_savingAmount);
            txt_GoalDate = (TextView) itemView.findViewById(R.id.txt_savingDate);
        }
    }
}










