package com.example.android_issue_2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class DeepLinkActivity extends AppCompatActivity {

    String deeplink_flag;
    TextView deeplink_notice_text;
    private FirebaseFirestore Firestore = FirebaseFirestore.getInstance();

    String script;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);

        deeplink_notice_text = (TextView)findViewById(R.id.deeplink_notice_text);
        webView = (WebView)findViewById(R.id.webview_content);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if (Intent.ACTION_VIEW.equalsIgnoreCase(action) && data != null) {
            final DocumentReference docRef = Firestore.collection("flag").document("flag_info");
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w("android_issue_2020", "Listen failed", error);
                    }

                    if (value != null && value.exists()) {
                        Map<String, Object> shot = value.getData();
                        deeplink_flag = String.valueOf(value.get("deeplink_flag"));
                    }
                    else {
                        //
                    }
                    script = data.getQuery();
                    webView.setWebViewClient(new WebViewClient());
                    webView.setWebChromeClient(new WebChromeClient());
                    webView.loadData(script, "text/html", "UTF-8");
                    webView.getSettings().setJavaScriptEnabled(true);
                    deeplink_notice_text.setText(deeplink_flag);

                }
            });
        }
        else {
            //
        }
    }
}