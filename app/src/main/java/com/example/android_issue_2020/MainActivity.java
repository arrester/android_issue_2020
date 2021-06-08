package com.example.android_issue_2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button user_enumeration_button;
    Button root_detect_bypass_button;
    Button deeplink_button;
    EditText flag_text;
    Button flag_check_button;
    String flag_check;

    String root_flag;
    String deeplink_flag;
    String user_enu_flag;

    private FirebaseFirestore Firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_enumeration_button = (Button)findViewById(R.id.user_enumeration);
        root_detect_bypass_button = (Button)findViewById(R.id.root_detect_bypass);
        deeplink_button = (Button)findViewById(R.id.deeplink);
        flag_check_button = (Button)findViewById(R.id.flag_check_button);
        flag_text = (EditText)findViewById(R.id.flag_text);

        user_enumeration_button.setOnClickListener(this);
        root_detect_bypass_button.setOnClickListener(this);
        deeplink_button.setOnClickListener(this);
        flag_check_button.setOnClickListener(this);

        final DocumentReference docRef = Firestore.collection("flag").document("flag_info");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("android_issue_2020", "Listen failed", error);
                }

                if (value != null && value.exists()) {
                    Map<String, Object> shot = value.getData();
                    root_flag = String.valueOf(value.get("root_flag"));
                    deeplink_flag = String.valueOf(value.get("deeplink_flag"));
                    user_enu_flag = String.valueOf(value.get("user_enu_flag"));
                }
                else {
                    //
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.user_enumeration:
                Intent user_enumeration_intent = new Intent(MainActivity.this, UserEnumerationActivity.class);
                startActivity(user_enumeration_intent);
                break;
            case R.id.root_detect_bypass:
                Intent root_detect_bypass_intent = new Intent(MainActivity.this, RootDetectionBypassActivity.class);
                startActivity(root_detect_bypass_intent);
                break;
            case R.id.deeplink:
                Toast.makeText(MainActivity.this,"schema check!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.flag_check_button:
                flag_check = flag_text.getText().toString();
                if(user_enu_flag.equals(flag_check)) {
                    user_enumeration_button.setBackgroundColor(Color.GREEN);
                    Toast.makeText(MainActivity.this,"Success", Toast.LENGTH_SHORT).show();
                }
                else if(root_flag.equals(flag_check)) {
                    root_detect_bypass_button.setBackgroundColor(Color.GREEN);
                    Toast.makeText(MainActivity.this,"Success", Toast.LENGTH_SHORT).show();
                }
                else if(deeplink_flag.equals(flag_check)) {
                    deeplink_button.setBackgroundColor(Color.GREEN);
                    Toast.makeText(MainActivity.this,"Success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Fail, Try again!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}