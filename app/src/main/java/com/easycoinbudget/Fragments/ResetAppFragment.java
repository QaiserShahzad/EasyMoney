package com.easycoinbudget.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.easycoinbudget.Model.Utils;
import com.easycoinbudget.R;

public  class ResetAppFragment extends Fragment {
    private OnFragmentInteractionListener mListener;


//    String URL = "http://coinbudget.com/EasyCoinApp/reset_app.php";

    SharedPreferences sharedPreference;
    RequestQueue requestQueue;
    String userID = "";

    public ResetAppFragment() {
        // Required empty public constructor
    }

    public static ResetAppFragment newInstance() {
        ResetAppFragment fragment = new ResetAppFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        sharedPreference = getActivity().getSharedPreferences(Utils.SETTING_KEY, Context.MODE_PRIVATE);
        userID = sharedPreference.getString(Utils.SETTING_USER_ID, "null");
//        ResetApplication();


        return inflater.inflate(R.layout.fragment_reset_app, container, false);
    }


//   public  void ResetApplication() {
//        final String formatedUrl = String.format(URL, userID);
//      /// Toast.makeText(getContext(), "yesss oye", Toast.LENGTH_SHORT).show();
//
//               StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                   @Override
//                   public void onResponse(String response) {
//
////                       Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
//
//
//
//                       try {
//                           JSONObject   jsonObject = new JSONObject(response);
//
//                           String succes=jsonObject.getString("success");
//                           if (succes.equals("1"))
//                           {
//                               Toast.makeText(getActivity(), ""+jsonObject, Toast.LENGTH_SHORT).show();
//
//                           }
//
//                       } catch (JSONException e) {
//                           e.printStackTrace();
//                       }
//                   }
//               }, new Response.ErrorListener() {
//                   @Override
//                   public void onErrorResponse(VolleyError error) {
//
//                       Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
//                   }
//               })
//
//               {
//                   @Override
//                   protected Map<String, String> getParams() {
//
//
//                       Map<String, String> params = new HashMap<String, String>();
//
//                       params.put("f_id", userID);
//
//
//
//                       return params;
//                   }
//
//
//                   @Override
//                   public Map<String, String> getHeaders() throws AuthFailureError {
//                       Map<String, String> headers = new HashMap<>();
//                       headers.put("Content-Type", "application/x-www-form-urlencoded");
//
//                       return headers;
//                   }
//
//
////
////            @Override
////            public String getBodyContentType() {
////                // TODO Auto-generated method stub
////                return "application/json";
////            }
//
//               };
//
//
//       stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//
//
//        requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);
//
//    }










    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
