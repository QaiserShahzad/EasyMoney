

package com.easycoinbudget.Model;

        import java.util.ArrayList;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllExtraIncomeList {

    @SerializedName("extraIncomeDetails")
    @Expose
    private ArrayList<SR_getAllExtraIncome> extraIncomeDetails = null;

    public ArrayList<SR_getAllExtraIncome> getExtraIncomeDetails() {
        return extraIncomeDetails;
    }

    public void setExtraIncomeDetails(ArrayList<SR_getAllExtraIncome> extraIncomeDetails) {
        this.extraIncomeDetails = extraIncomeDetails;
    }

}