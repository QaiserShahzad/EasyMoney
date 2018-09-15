package com.easycoinbudget.Model;

        import java.util.ArrayList;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllReminderList {

    @SerializedName("reminder")
    @Expose
    private ArrayList<SR_getAllReminder> reminder = null;

    public List<SR_getAllReminder> getReminder() {
        return reminder;
    }

    public void setReminder(ArrayList<SR_getAllReminder> reminder) {
        this.reminder = reminder;
    }

}