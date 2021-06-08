package com.example.android_issue_2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class MydataActivity extends AppCompatActivity implements View.OnClickListener {

    TextView user_id_text;
    TextView user_pw_text;
    TextView user_card_text;

    String db_id1;
    String db_pw1;
    String db_card1;

    String db_id2;
    String db_pw2;
    String db_card2;

    String db_id3;
    String db_pw3;
    String db_card3;

    static String now_id;

    Button mydata_button;

    private FirebaseFirestore Firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);

        user_id_text = (TextView)findViewById(R.id.user_id_text);
        user_pw_text = (TextView)findViewById(R.id.user_pw_text);
        user_card_text = (TextView)findViewById(R.id.user_card_text);

        mydata_button = (Button)findViewById(R.id.mydata_button);
        mydata_button.setOnClickListener(this);

        now_id = getIntent().getStringExtra("db_id");

        user_id_text.setText(now_id);

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
                    db_card1 = String.valueOf(value.get("card1"));

                    db_id2 = String.valueOf(value.get("id2"));
                    db_pw2 = String.valueOf(value.get("pw2"));
                    db_card2 = String.valueOf(value.get("card2"));

                    db_id3 = String.valueOf(value.get("id3"));
                    db_pw3 = String.valueOf(value.get("pw3"));
                    db_card3 = String.valueOf(value.get("card3"));
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
            case R.id.mydata_button:
                Toast.makeText(MydataActivity.this,"Update Success", Toast.LENGTH_SHORT).show();
                if(now_id.equals(db_id1)) {
                    user_id_text.setText(db_id1);
                    user_pw_text.setText(db_pw1);
                    user_card_text.setText(db_card1);
                }

                else if(now_id.equals(db_id2)) {
                    user_id_text.setText(db_id2);
                    user_pw_text.setText(db_pw2);
                    user_card_text.setText(db_card2);
                }

                else if(now_id.equals(db_id3)) {
                    user_id_text.setText(db_id3);
                    user_pw_text.setText(db_pw3);
                    user_card_text.setText(db_card3);
                }
                break;
        }
    }
}