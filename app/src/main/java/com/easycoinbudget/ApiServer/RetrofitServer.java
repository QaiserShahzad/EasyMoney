package com.easycoinbudget.ApiServer;


import com.easycoinbudget.Model.SR_InsertEstimatedExp;
import com.easycoinbudget.Model.SR_InsertExtraIncome;
import com.easycoinbudget.Model.SR_InsertIncome;
import com.easycoinbudget.Model.SR_InsertRecuringExp;
import com.easycoinbudget.Model.SR_InsertReminder;
import com.easycoinbudget.Model.SR_InsertSavingGoal;
import com.easycoinbudget.Model.SR_Login;
import com.easycoinbudget.Model.SR_Pin;
import com.easycoinbudget.Model.SR_SignUp;
import com.easycoinbudget.Model.SR_Tincome;
import com.easycoinbudget.Model.SR_UpdatePassword;
import com.easycoinbudget.Model.SR_forgotPassword;
import com.easycoinbudget.Model.SR_getAllBankDetailsList;
import com.easycoinbudget.Model.SR_getAllEstExpensesList;
import com.easycoinbudget.Model.SR_getAllExtraIncomeList;
import com.easycoinbudget.Model.SR_getAllRecExpensesList;
import com.easycoinbudget.Model.SR_getAllRecord;
import com.easycoinbudget.Model.SR_getAllReminderList;
import com.easycoinbudget.Model.SR_getSavingGoalList;
import com.easycoinbudget.Model.SR_getSingleValue;
import com.easycoinbudget.Model.SR_userInfo;
import com.easycoinbudget.Model.rv_getIncomeList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dell ins on 4/8/2018.
 */

public interface RetrofitServer {

    // <Admininseert>  is the Model class in which we get JSON Response from server
    // insertUser() is the function that receive arguments from Client side and send towards server by using the function
    //of retrofit


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_income.php")
    Call<SR_InsertIncome> insertIncome(
            @Field("amount") String amo,
            @Field("frequency") String freq,
            @Field("descrip") String des,
            @Field("foreign_Id") String id);


    // For Income


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_GetSal.php")
    Call<List<SR_Tincome>> get_Income(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_GetIncome.php")
    Call<rv_getIncomeList> get_IncomeDetails(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_GetRecExpenses.php")
    Call<List<SR_Tincome>> get_RecExpenses(

            @Field("uid") String id);
// Api that Get only One Value from Database


    @FormUrlEncoded
    @POST("EasyCoinApp/rec_totalAmount.php")
    Call<SR_getSingleValue> recExpenseAmount(

            @Field("uid") String id);

    @FormUrlEncoded
    @POST("EasyCoinApp/getAllAmount.php")
    Call<SR_getAllRecord> getAllAmount(

            @Field("uid") String id);



    @FormUrlEncoded
    @POST("EasyCoinApp/budget_GetEstExpenses.php")
    Call<List<SR_Tincome>> get_EstExpenses(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_getSavingGoal.php")
    Call<SR_getSavingGoalList> get_savingGoal(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_getAllRecExpens" +
            "es.php")
    Call<SR_getAllRecExpensesList> get_AllRecExpenses(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_getAllEstExpenses.php")
    Call<SR_getAllEstExpensesList> get_AllEstxpenses(

            @Field("uid") String id);



    @FormUrlEncoded
    @POST("EasyCoinApp/budget_getAllReminder.php")
    Call<SR_getAllReminderList> get_ReminderDetails(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_getAllBankInfo.php")
    Call<SR_getAllBankDetailsList> get_getAllBankDetails(

            @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_GetAllExtraIncomeDetails.php")
    Call<SR_getAllExtraIncomeList> get_ExtraIncomeDetails(

            @Field("uid") String id);




    @FormUrlEncoded
    @POST("EasyCoinApp/budget_RecuringExpenses.php")
    Call<SR_InsertRecuringExp> insertRecuringExpense(
            @Field("amount") String amo,
            @Field("frequency") String freq,
            @Field("ddate") String date,
            @Field("descrip") String des,
            @Field("rectype") String type,
            @Field("user_id") String uid);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_EstimatedExpenses.php")
    Call<SR_InsertEstimatedExp> insertEstimatedExpense
            (
                    @Field("amount") String amo,
                    @Field("frequency") String freq,
                    @Field("ddate") String date,
                    @Field("descrip") String des,
                    @Field("esttype") String type,
                    @Field("fid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_savingGoal.php")
    Call<SR_InsertSavingGoal> insertsavingGoal
            (
                    @Field("sgamount") String sgamo,
                    @Field("amount") String amount,
                    @Field("gdate") String gdate,
                    @Field("u_id") String uid);

    @FormUrlEncoded
    @POST("EasyCoinApp/budget_reminder.php")
    Call<SR_InsertReminder> insert_Reminder
            (
                    @Field("date") String date,
                    @Field("note") String note,
                    @Field("u_id") String uid);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_BankDetails.php")
    Call<SR_Pin> insert_Pin
            (
                    @Field("b_name") String bname,
                    @Field("pin") String pin,
                    @Field("u_id") String uid);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_transaction.php")
    Call<SR_InsertRecuringExp> entryTranscation
            (
                    @Field("amount") String amo,
                    @Field("frequency") String freq,
                    @Field("ddate") String date,
                    @Field("descrip") String des,
                    @Field("type") String type,
                    @Field("fid") String id);

    @FormUrlEncoded
    @POST("EasyCoinApp/budget_insertExtraIncome.php")
    Call<SR_InsertExtraIncome> insertExtraIncome
            (@Field("amount") String amo,
             @Field("date") String date,
             @Field("note") String des,
             @Field("type") String type,
             @Field("uid") String id);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_SignUp.php")
    Call<SR_SignUp> insert_SignUp
            (
                    @Field("name") String sgamo,
                    @Field("email") String amount,
                    @Field("password") String gdate,
                    @Field("cnum") String cnum);

    @FormUrlEncoded
    @POST("EasyCoinApp/budget_login.php")
    Call<SR_Login> budget_login
            (
                    @Field("email") String Email,
                    @Field("password") String Password);


    @FormUrlEncoded
//    @POST("EasyCoinApp/thanks.php")
//    @POST("EasyCoinApp/budget_forgetPassword.php")
    Call<SR_forgotPassword> budget_forgotPass
            (
                    @Field("uemail") String email);


    @FormUrlEncoded
    @POST("EasyCoinApp/budget_updatePassword.php")
    Call<SR_UpdatePassword> update_newPassword
            (
                    @Field("f_id") String fid,
                    @Field("new_password") String newPass,
                    @Field("old_password") String oldPass,
                    @Field("old_Email") String oldEmail);



    @FormUrlEncoded
    @POST("EasyCoinApp/budget_userinfo.php")
    Call<SR_userInfo> Navheader_UserInfo
            (
                    @Field("uid") String userId);











}
