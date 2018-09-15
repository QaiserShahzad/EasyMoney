

package com.easycoinbudget.Model;

        import java.util.ArrayList;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllRecExpensesList {

    @SerializedName("recuringexp")
    @Expose
    private ArrayList<SR_getAllRecExpenses> recuringexp = null;

    public ArrayList<SR_getAllRecExpenses> getRecuringexp() {
        return recuringexp;
    }

    public void setRecuringexp(ArrayList<SR_getAllRecExpenses> recuringexp) {
        this.recuringexp = recuringexp;
    }

}