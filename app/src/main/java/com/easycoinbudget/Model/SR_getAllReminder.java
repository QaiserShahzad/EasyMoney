
package com.easycoinbudget.Model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SR_getAllReminder {

    @SerializedName("remTitle")
    @Expose
    private String remTitle;
    @SerializedName("remDate")
    @Expose
    private String remDate;
    @SerializedName("remDescription")
    @Expose
    private String remDescription;

    public String getRemTitle() {
        return remTitle;
    }

    public void setRemTitle(String remTitle) {
        this.remTitle = remTitle;
    }

    public String getRemDate() {
        return remDate;
    }

    public void setRemDate(String remDate) {
        this.remDate = remDate;
    }

    public String getRemDescription() {
        return remDescription;
    }

    public void setRemDescription(String remDescription) {
        this.remDescription = remDescription;
    }

}