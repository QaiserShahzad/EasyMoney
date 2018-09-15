
package com.easycoinbudget.Model;

        import java.util.ArrayList;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getSavingGoalList {

    @SerializedName("savinggoal")
    @Expose
    private ArrayList<SR_getSavingGoal> savinggoal = null;

    public List<SR_getSavingGoal> getSavinggoal() {
        return savinggoal;
    }

    public void setSavinggoal(ArrayList<SR_getSavingGoal> savinggoal) {
        this.savinggoal = savinggoal;
    }

}