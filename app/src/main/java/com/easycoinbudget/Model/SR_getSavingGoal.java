

package com.easycoinbudget.Model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getSavingGoal {

    @SerializedName("savgoal_Tamount")
    @Expose
    private String savgoalTamount;
    @SerializedName("savgoal_Amount")
    @Expose
    private String savgoalAmount;
    @SerializedName("savgoal_date")
    @Expose
    private String savgoalDate;

    public String getSavgoalTamount() {
        return savgoalTamount;
    }

    public void setSavgoalTamount(String savgoalTamount) {
        this.savgoalTamount = savgoalTamount;
    }

    public String getSavgoalAmount() {
        return savgoalAmount;
    }

    public void setSavgoalAmount(String savgoalAmount) {
        this.savgoalAmount = savgoalAmount;
    }

    public String getSavgoalDate() {
        return savgoalDate;
    }

    public void setSavgoalDate(String savgoalDate) {
        this.savgoalDate = savgoalDate;
    }

}