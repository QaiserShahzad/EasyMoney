package com.easycoinbudget.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easycoinbudget.ApiClient.RetrofitClient;
import com.easycoinbudget.ApiServer.RetrofitServer;
import com.easycoinbudget.Fragments.BankFragment;
import com.easycoinbudget.Fragments.BudgetFragment;
//import com.easymoney.Fragments.CurrencyFragment;
import com.easycoinbudget.Fragments.EstimatedExpenseEntry;
import com.easycoinbudget.Fragments.ExpensesFragment;
import com.easycoinbudget.Fragments.ExtraIncomeEntry;
import com.easycoinbudget.Fragments.ExtraIncomeFragment;
import com.easycoinbudget.Fragments.Fragment_plaid;
import com.easycoinbudget.Fragments.FrequencyFragment;
import com.easycoinbudget.Fragments.HomeFragment;
import com.easycoinbudget.Fragments.IncomeFragment;
import com.easycoinbudget.Fragments.PinFragment;
import com.easycoinbudget.Fragments.RecurringExpenseEntry;
import com.easycoinbudget.Fragments.ReminderFragment;
import com.easycoinbudget.Fragments.ResetAppFragment;
import com.easycoinbudget.Fragments.SavingsFragment;
import com.easycoinbudget.Fragments.TransactionsFragmant;
import com.easycoinbudget.Fragments.UserInfoFragment;
import com.easycoinbudget.Model.SR_userInfo;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        FrequencyFragment.OnFragmentInteractionListener,
        UserInfoFragment.OnFragmentInteractionListener,
//        CurrencyFragment.OnFragmentInteractionListener,
        ReminderFragment.OnFragmentInteractionListener,
        PinFragment.OnFragmentInteractionListener,
        ResetAppFragment.OnFragmentInteractionListener,
        BankFragment.OnFragmentInteractionListener,
        BudgetFragment.OnFragmentInteractionListener,
        IncomeFragment.OnFragmentInteractionListener,
        ExpensesFragment.OnFragmentInteractionListener,
        SavingsFragment.OnFragmentInteractionListener,
        EstimatedExpenseEntry.OnFragmentInteractionListener,
        RecurringExpenseEntry.OnFragmentInteractionListener,
        ExtraIncomeFragment.OnFragmentInteractionListener,
        TransactionsFragmant.OnFragmentInteractionListener,
        ExtraIncomeEntry.OnFragmentInteractionListener,
        Fragment_plaid.OnFragmentInteractionListener {


    private BottomNavigationViewEx mBottomNavigationView;
    String URL = "http://coinbudget.com/EasyCoinApp/reset_app.php";
    RequestQueue requestQueue;
    SharedPreferences pref;  // 0 - for private mode
    SharedPreferences.Editor editor;
    private Fragment fragment = null;
    private Class fragmentClass = null;
    private String fragmentTitle = null;
    View headerView;
    NavigationView navigationView;
    AlertDialog.Builder builder;
        SharedPreferences sharedPreference;
    String userID = "";
    String userEmail = "";

    String user_id;
    //    String user_email;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            fragmentTitle = String.valueOf(item.getTitle());

            if (id == R.id.bnav_Home) {
                fragmentClass = HomeFragment.class;
                loadFragment();
            } else if (id == R.id.bnav_budget) {
                fragmentClass = BudgetFragment.class;
                loadFragment();
            } else if (id == R.id.bnav_income) {
                fragmentClass = IncomeFragment.class;
                loadFragment();

            } else if (id == R.id.bnav_expenses) {

                new ExpensesDialog().show(getFragmentManager(), null);
            } else if (id == R.id.bnav_savings) {
                fragmentClass = SavingsFragment.class;
                loadFragment();
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        builder = new AlertDialog.Builder(this);

        mBottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve);
        mBottomNavigationView.enableAnimation(false);
        mBottomNavigationView.enableShiftingMode(false);
        mBottomNavigationView.enableItemShiftingMode(false);
        mBottomNavigationView.setTextSize(12);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView;



        sharedPreference = getApplicationContext().getSharedPreferences(Utils.SETTING_KEY, Context.MODE_PRIVATE);
        userID = sharedPreference.getString(Utils.SETTING_USER_ID, "null");
        userEmail=sharedPreference.getString(Utils.SETTING_USER_EMAIL,"null");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
         headerView = navigationView.getHeaderView(0);

        Navheader_displayUserInfo(userID);
        Navheader_displayUserInfo(userEmail);

        TextView navUserEmail = (TextView)headerView.findViewById(R.id.textView_UserEmail);
        navUserEmail.setText(userEmail);

        navigationView.setNavigationItemSelectedListener(this);


        fragmentClass = HomeFragment.class;
        fragmentTitle = "Easy Coin Budget";
        loadFragment();

    }


    public void Navheader_displayUserInfo(String user_id) {


        try {
            RetrofitServer apiClient = RetrofitClient.getClient().create(RetrofitServer.class);

            Call<SR_userInfo> call = apiClient.Navheader_UserInfo(user_id);


            call.enqueue(new Callback<SR_userInfo>() {
                @Override
                public void onResponse(Call<SR_userInfo> call, Response<SR_userInfo> response) {
                    try {


                        SR_userInfo user = response.body();

                        int resp = user.getSuccess();


//                       Toast.makeText(getContext(), String.valueOf(insert.getSuccess()), Toast.LENGTH_SHORT).show();


                        //check the status code
                        if (resp == 1) {

                            TextView navUsername = (TextView) headerView.findViewById(R.id.textView_UserName);
                            navUsername.setText(user.getUserName());
//                            TextView navUserEmail = (TextView) headerView.findViewById(R.id.textView_UserEmail);
//                            navUserEmail.setText(user.getUserEmail());



                        } else {
//                            Toast.makeText(getContext(), "inserted on response", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Record Not Inserted", Toast.LENGTH_SHORT).show();
                            //Log.e(" IncomeFragment", "  id - " + "Data not Inserted");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }

                @Override
                public void onFailure(Call<SR_userInfo> call, Throwable t) {
//                    Toast.makeText(getContext(), "FAILURE METHOD CALL", Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" Navigation Header", "  id - " + "Failure Method Call");

                    Log.e(" Navigation Header", "  id - " + t.getMessage());
                    //progressDialog.setMessage("Please Connect with you'r Mobile data/ Internet");
                    //progressDialog.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentTitle = String.valueOf(item.getTitle());

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
        } else if (id == R.id.nav_bank) {
            fragmentClass = BankFragment.class;
        } else if (id == R.id.nav_reminder) {
            fragmentClass = ReminderFragment.class;
        } else if (id == R.id.nav_pin) {
            fragmentClass = PinFragment.class;
        } else if (id == R.id.nav_reset) {

            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
            alertDialog.setTitle("\t Reset App");
            alertDialog.setIcon(R.drawable.ic_recur_50);
            alertDialog.setMessage("Are You Sure You Want To Reset The App");
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.color);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                    ResetApplication();
//                    fragment = ResetAppFragment.newInstance();
//
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                    fragmentTransaction.replace(R.id.mainContainer, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                }
            });
            alertDialog.show();

//            fragmentClass = ResetAppFragment.class;
//            new ResetApp().show(getFragmentManager(), null);
        } else if (id == R.id.nav_logut) {
            Intent i = new Intent(getApplicationContext(), MainActivity_Login.class);
            startActivity(i);
            finish();
        }

        loadFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (fragmentTitle != null) {
            setTitle(fragmentTitle);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @SuppressLint("ValidFragment")
    public class ExpensesDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.layout_expenses_dialog, null);
            view.findViewById(R.id.btnRecurringExpenses).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExpensesDialog.this.getDialog().cancel();
                    fragment = ExpensesFragment.newInstance(1);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.mainContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("Recurring Expenses");
                }
            });
            view.findViewById(R.id.btnEstimatedExpenses).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExpensesDialog.this.getDialog().cancel();
                    fragment = ExpensesFragment.newInstance(2);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.replace(R.id.mainContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("Estimated Expenses");
                }
            });

            builder.setView(view)
                    // Add action buttons
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ExpensesDialog.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }


//    @SuppressLint("ValidFragment")
//    public  class ResetApp extends DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            // Get the layout inflater
//
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            final View view = inflater.inflate(R.layout.fragment_reset_app_fragement, null);
//            view.findViewById(R.id.btnResetApp).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getActivity(), "Application Reset Successfully", Toast.LENGTH_SHORT).show();
//                    fragment = HomeFragment.newInstance();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                    fragmentTransaction.replace(R.id.mainContainer, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                    //setTitle("Estimated Expenses");
//                }
//            });
//            // Inflate and set the layout for the dialog
//            // Pass null as the parent view because its going in the dialog layout
//            builder.setView(view)
//                    // Add action buttons
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            ResetApp.this.getDialog().cancel();
//                        }
//                    });
//            return builder.create();
//        }
//    }
//
//


    public  void ResetApplication() {
//        final String formatedUrl = String.format(URL, userID);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String succes=jsonObject.getString("success");
                    if (succes.equals("1"))
                    {
//                        Toast.makeText(getApplicationContext(), ""+jsonObject, Toast.LENGTH_SHORT).show();
                        Toast.makeText(HomeActivity.this, "App Reset Successfully", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("f_id", userID);



                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                return headers;
            }


//
//            @Override
//            public String getBodyContentType() {
//                // TODO Auto-generated method stub
//                return "application/json";
//            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));




        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }




}

