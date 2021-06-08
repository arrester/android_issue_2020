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

import java.io.InputStream;
import java.util.Map;

public class UserEnumerationActivity extends AppCompatActivity implements View.OnClickListener{

    EditText user_id;
    EditText user_pw;

    String input_id;
    String input_pw;

    String db_id1;
    String db_pw1;
    //String db_card1;

    String db_id2;
    String db_pw2;
    //String db_card2;

    String db_id3;
    String db_pw3;
    //String db_card3;

    Button login_check;

    boolean check;

    private FirebaseFirestore Firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_enumeration);

        user_id = (EditText) findViewById(R.id.user_id);
        user_pw = (EditText) findViewById(R.id.user_pw);

        login_check = (Button) findViewById(R.id.login_check);
        login_check.setOnClickListener(this);

        final DocumentReference docRef = Firestore.collection("users").document("user_info");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("android_issue_2020", "Listen failed", error);
                }

                if (value != null && value.exists()) {
                    Map<String, Object> shot = value.getData();
                    db_id1 = String.valueOf(value.get("id1"));
                    db_pw1 = String.valueOf(value.get("pw1"));
                    //db_card1 = String.valueOf(value.get("card1"));

                    db_id2 = String.valueOf(value.get("id2"));
                    db_pw2 = String.valueOf(value.get("pw2"));
                    //db_card2 = String.valueOf(value.get("card2"));

                    db_id3 = String.valueOf(value.get("id3"));
                    db_pw3 = String.valueOf(value.get("pw3"));
                    //db_card3 = String.valueOf(value.get("card3"));
                }
                else {
                    //
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_check:
                input_id = user_id.getText().toString();
                input_pw = user_pw.getText().toString();

                if(db_id1.equals(input_id) && db_pw1.equals(input_pw)) {
                    // arrester id 미리 제공
                    Toast.makeText(UserEnumerationActivity.this,"Login Success!", Toast.LENGTH_SHORT).show();
                    Intent mydata_intent = new Intent(UserEnumerationActivity.this, MydataActivity.class);
                    mydata_intent.putExtra("db_id", db_id1);
                    startActivity(mydata_intent);
                }
                else if(db_id1.equals(input_id) && !db_pw1.equals(input_pw)) {
                    Toast.makeText(UserEnumerationActivity.this,"Password Incorrect!", Toast.LENGTH_SHORT).show();
                }
                else if(db_id2.equals(input_id) && db_pw2.equals(input_pw)) {
                    // arrester id 미리 제공
                    Toast.makeText(UserEnumerationActivity.this,"Login Success!", Toast.LENGTH_SHORT).show();
                    Intent mydata_intent = new Intent(UserEnumerationActivity.this, MydataActivity.class);
                    mydata_intent.putExtra("db_id", db_id2);
                    startActivity(mydata_intent);
                }
                else if(db_id2.equals(input_id) && !db_pw2.equals(input_pw)) {
                    Toast.makeText(UserEnumerationActivity.this,"Password Incorrect!", Toast.LENGTH_SHORT).show();
                }
                else if(db_id3.equals(input_id) && db_pw3.equals(input_pw)) {
                    // arrester id 미리 제공
                    Toast.makeText(UserEnumerationActivity.this,"Login Success!", Toast.LENGTH_SHORT).show();
                    Intent mydata_intent = new Intent(UserEnumerationActivity.this, MydataActivity.class);
                    mydata_intent.putExtra("db_id", db_id3);
                    startActivity(mydata_intent);
                }
                else if(db_id3.equals(input_id) && !db_pw3.equals(input_pw)) {
                    Toast.makeText(UserEnumerationActivity.this,"Password Incorrect!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UserEnumerationActivity.this,"This account does not exist!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}