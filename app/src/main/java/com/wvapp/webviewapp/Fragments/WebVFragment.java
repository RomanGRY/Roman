package com.wvapp.webviewapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.wvapp.webviewapp.R;

import static android.content.Context.MODE_PRIVATE;


public class WebVFragment extends Fragment {

    private View webFragmentView;
    private WebView webView;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webFragmentView = inflater.inflate(R.layout.fragment_web_v, container, false);
        context=getContext();

//        FirebaseApp.initializeApp(getContext());
        FirebaseDynamicLinks.getInstance().getDynamicLink(getActivity().getIntent())
                .addOnSuccessListener(getActivity(), new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Log.i("DeepLink","DeepLink Is here ");
                        Uri deepLink=null;
                        if (pendingDynamicLinkData!=null){
                            deepLink=pendingDynamicLinkData.getLink();
                        }
                        if (deepLink!=null){
                            Log.i("DeepLink","DeepLink Is : "+deepLink.toString());
                            Toast.makeText(context, deepLink.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("DeepLink","Couldn't get Link((( ");
            }
        });

        webView = webFragmentView.findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebClient());
        CookieManager.getInstance().setAcceptCookie(true);
        webView.loadUrl(getUrl());
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    SharedPreferences sp = getActivity().getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("SAVED_URL", "https://pin-up.games/");
                    editor.apply();
                    getActivity().finish();
                }
            }
        });

        return webFragmentView;
    }

    public void saveUrl(String url) {
        SharedPreferences sp = getActivity().getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("SAVED_URL", url);
        editor.apply();
    }

    public String getUrl() {
        SharedPreferences sp = getActivity().getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE);
        return sp.getString("SAVED_URL", "https://pin-up.games/");

    }

    private class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Save the last visited URL
            saveUrl(url);
        }
    }
}