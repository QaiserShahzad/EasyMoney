package com.easycoinbudget.Model;

        import java.util.ArrayList;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllEstExpensesList {

    @SerializedName("estimatedexp")
    @Expose
    private ArrayList<SR_getAllEstExpenses> estimatedexp = null;

    public ArrayList<SR_getAllEstExpenses> getEstimatedexp() {
        return estimatedexp;
    }

    public void setEstimatedexp(ArrayList<SR_getAllEstExpenses> estimatedexp) {
        this.estimatedexp = estimatedexp;
    }

}