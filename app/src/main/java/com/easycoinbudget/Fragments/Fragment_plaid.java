package com.easycoinbudget.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.easycoinbudget.R;

import java.util.HashMap;


public class Fragment_plaid extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Class fragmentClass = null;
    private Fragment fragment = null;
    private WebView plaidLinkWebview;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Uri  linkInitializationUrl;
    private OnFragmentInteractionListener mListener;

    public Fragment_plaid() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //* @param param1 Parameter 1.
     * //* @param param2 Parameter 2.
     *
     * @return A new instance of fragment Fragment_plaid.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_plaid newInstance() {
        Fragment_plaid fragment = new Fragment_plaid();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_plaid, container, false);
        HashMap<String, String> linkInitializeOptions = new HashMap<String, String>();
        linkInitializeOptions.put("key", "ab0dd755143a742eae072bef81760f");
        linkInitializeOptions.put("product", "auth");
        linkInitializeOptions.put("apiVersion", "v2"); // set this to "v1" if using the legacy Plaid API
        linkInitializeOptions.put("env", "sandbox");
        linkInitializeOptions.put("clientName", "Test App");
        linkInitializeOptions.put("selectAccount", "true");
        linkInitializeOptions.put("webhook", "http://requestb.in");
        linkInitializeOptions.put("baseUrl", "https://cdn.plaid.com/link/v2/stable/link.html");

        linkInitializationUrl = generateLinkInitializationUrl(linkInitializeOptions);

        plaidLinkWebview = (WebView) view.findViewById(R.id.webview);

        WebSettings webSettings = plaidLinkWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            plaidLinkWebview.setWebContentsDebuggingEnabled(true);
        }

        plaidLinkWebview.loadUrl(linkInitializationUrl.toString());

        plaidLinkWebview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri parsedUri = Uri.parse(url);
                if (parsedUri.getScheme().equals("plaidlink")) {
                    String action = parsedUri.getHost();
                    HashMap<String, String> linkData = parseLinkUriData(parsedUri);

                    if (action.equals("connected")) {
                        Log.d("Public token: ", linkData.get("public_token"));
                        Log.d("Account ID: ", linkData.get("account_id"));
                        Log.d("Account name: ", linkData.get("account_name"));
                        Log.d("Institution type: ", linkData.get("institution_type"));
                        Log.d("Institution name: ", linkData.get("institution_name"));

                        plaidLinkWebview.loadUrl(linkInitializationUrl.toString());
                    } else if (action.equals("exit")) {

                        Log.d("EXIT", "Exit event");

                        fragmentClass = HomeFragment.class;
                        loadFragment();

                    } else {
                        Log.d("Link action detected: ", action);
                    }

                    return true;
                } else if (parsedUri.getScheme().equals("https") ||
                        parsedUri.getScheme().equals("http")) {

                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                    return true;
                } else {

                    return false;
                }
            }
        });


        return view;
    }

    private void loadFragment() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public Uri generateLinkInitializationUrl(HashMap<String, String> linkOptions) {
        Uri.Builder builder = Uri.parse(linkOptions.get("baseUrl"))
                .buildUpon()
                .appendQueryParameter("isWebview", "true")
                .appendQueryParameter("isMobile", "true");
        for (String key : linkOptions.keySet()) {
            if (!key.equals("baseUrl")) {
                builder.appendQueryParameter(key, linkOptions.get(key));
            }
        }
        return builder.build();
    }

    // Parse a Link redirect URL querystring into a HashMap for easy manipulation and access
    public HashMap<String, String> parseLinkUriData(Uri linkUri) {
        HashMap<String, String> linkData = new HashMap<String, String>();
        for (String key : linkUri.getQueryParameterNames()) {
            linkData.put(key, linkUri.getQueryParameter(key));
        }
        return linkData;
    }
}

























// old Code







//package com.easymoney.Fragments;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Toast;
//
//import com.easymoney.Activities.HomeActivity;
//import com.easymoney.R;
//
//import java.util.HashMap;
//
//
//public class Fragment_plaid extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private Class fragmentClass = null;
//    private Fragment fragment = null;
//     WebView plaidLinkWebview;
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//     Uri  linkInitializationUrl;
//    private OnFragmentInteractionListener mListener;
//
//    public Fragment_plaid() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     * <p>
//     * //* @param param1 Parameter 1.
//     * //* @param param2 Parameter 2.
//     *
//     * @return A new instance of fragment Fragment_plaid.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Fragment_plaid newInstance() {
//        Fragment_plaid fragment = new Fragment_plaid();
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//          mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_fragment_plaid, container, false);
//        HashMap<String, String> linkInitializeOptions = new HashMap<String, String>();
//        linkInitializeOptions.put("key", "ab0dd755143a742eae072bef81760f");
//        linkInitializeOptions.put("product", "auth");
//        linkInitializeOptions.put("apiVersion", "v2"); // set this to "v1" if using the legacy Plaid API
//        linkInitializeOptions.put("env", "sandbox");
//        linkInitializeOptions.put("clientName", "Test App");
//        linkInitializeOptions.put("selectAccount", "true");
//        linkInitializeOptions.put("webhook", "http://requestb.in");
//        linkInitializeOptions.put("baseUrl", "https://cdn.plaid.com/link/v2/stable/link.html");
////        linkInitializeOptions.put("baseUrl", "https://sandbox.plaid.com");
//
//
//        // If initializing Link in PATCH / update mode, also provide the public_token
//        // linkInitializeOptions.put("public_token", "PUBLIC_TOKEN")
//
//        // Generate the Link initialization URL based off of the configuration options.
//      linkInitializationUrl = generateLinkInitializationUrl(linkInitializeOptions);
//
//        // Modify Webview settings - all of these settings may not be applicable
//        // or necesscary for your integration.
//          plaidLinkWebview = (WebView) view.findViewById(R.id.webview);
//        //final ImageView plaidLinkWebview=(ImageView)findViewById(R.id.webview);
//
//        WebSettings webSettings = plaidLinkWebview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            plaidLinkWebview.setWebContentsDebuggingEnabled(true);
//        }
//
//        // Initialize Link by loading the Link initiaization URL in the Webview
//        plaidLinkWebview.loadUrl(linkInitializationUrl.toString());
//
//        // Override the Webview's handler for redirects
//        // Link communicates success and failure (analogous to the web's onSuccess and onExit
//        // callbacks) via redirects.
//        plaidLinkWebview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // Parse the URL to determine if it's a special Plaid Link redirect or a request
//                // for a standard URL (typically a forgotten password or account not setup link).
//                // Handle Plaid Link redirects and open traditional pages directly in the  user's
//                // preferred browser.
//                Uri parsedUri = Uri.parse(url);
//                if (parsedUri.getScheme().equals("plaidlink")) {
//                    String action = parsedUri.getHost();
//                    HashMap<String, String> linkData = parseLinkUriData(parsedUri);
//
//                    if (action.equals("connected")) {
//                        // User successfully linked
//                        Log.d("Public token: ", linkData.get("public_token"));
//                        Log.d("Account ID: ", linkData.get("account_id"));
//                        Log.d("Account name: ", linkData.get("account_name"));
//                        Log.d("Institution type: ", linkData.get("institution_type"));
//                        Log.d("Institution name: ", linkData.get("institution_name"));
//
//                        // Reload Link in the Webview
//                        // You will likely want to transition the view at this point.
//                        plaidLinkWebview.loadUrl(linkInitializationUrl.toString());
//                    } else if (action == "exit") {
//
//
//                            fragmentClass = HomeFragment.class;
//                            loadFragment();
//
//
//                    } else {
//                        Log.d("Link action detected: ", action);
//                    }
//                    // Override URL loading
//                    return true;
//                } else if (parsedUri.getScheme().equals("https") ||
//                        parsedUri.getScheme().equals("http")) {
//                    // Open in browser - this is most  typically for 'account locked' or
//                    // 'forgotten password' redirects
//                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    // Override URL loading
//                    return true;
//                } else {
//                    // Unknown case - do not override URL loading
//                    return false;
//                }
//            }
//        });
//
//
//        return view;
//    }
//
//    private void loadFragment() {
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (java.lang.InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.mainContainer, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//
//    public Uri generateLinkInitializationUrl(HashMap<String, String> linkOptions) {
//        Uri.Builder builder = Uri.parse(linkOptions.get("baseUrl"))
//                .buildUpon()
//                .appendQueryParameter("isWebview", "true")
//                .appendQueryParameter("isMobile", "true");
//        for (String key : linkOptions.keySet()) {
//            if (!key.equals("baseUrl")) {
//                builder.appendQueryParameter(key, linkOptions.get(key));
//            }
//        }
//        return builder.build();
//    }
//
//    // Parse a Link redirect URL querystring into a HashMap for easy manipulation and access
//    public HashMap<String, String> parseLinkUriData(Uri linkUri) {
//        HashMap<String, String> linkData = new HashMap<String, String>();
//        for (String key : linkUri.getQueryParameterNames()) {
//            linkData.put(key, linkUri.getQueryParameter(key));
//        }
//        return linkData;
//    }
//}
