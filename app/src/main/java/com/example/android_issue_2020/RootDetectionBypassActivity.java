package com.example.android_issue_2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.util.Map;

public class RootDetectionBypassActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ROOT_PATH = Environment.
            getExternalStorageDirectory() + "";
    public static final String ROOTING_PATH_1 = "/system/bin/su";
    public static final String ROOTING_PATH_2 = "/system/xbin/su";
    public static final String ROOTING_PATH_3 = "/system/app/Superuser.apk";
    public static final String ROOTING_PATH_4 = "/data/data/com.noshufou.android.su";
    public static final String ROOTING_PATH_5 = "/system/xbin/daemonsu";
    public static final String ROOTING_PATH_6 = "/system/etc/init.d/99SuperSUDaemon";
    public static final String ROOTING_PATH_7 = "/system/bin/.ext/.su";
    public static final String ROOTING_PATH_8 = "/system/etc/.has_su_daemon";
    public static final String ROOTING_PATH_9 = "/system/etc/.installed_su_daemon";
    public static final String ROOTING_PATH_10 = "/dev/com.koushikdutta.superuser.daemon/";

    public String[] RootFilesPath = new String[]{
            ROOT_PATH + ROOTING_PATH_1 ,
            ROOT_PATH + ROOTING_PATH_2 ,
            ROOT_PATH + ROOTING_PATH_3 ,
            ROOT_PATH + ROOTING_PATH_4 ,
            ROOT_PATH + ROOTING_PATH_5 ,
            ROOT_PATH + ROOTING_PATH_6 ,
            ROOT_PATH + ROOTING_PATH_7 ,
            ROOT_PATH + ROOTING_PATH_8 ,
            ROOT_PATH + ROOTING_PATH_9 ,
            ROOT_PATH + ROOTING_PATH_10
    };

    String msg_title = "";
    String msg_contents = "";

    String flag_title = "flag";
    String flag_contents = "flag{????}";

    Button root_msg;
    Button root_button;

    boolean isRootingFlag = false;

    private FirebaseFirestore Firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_detection_bypass);

        root_msg = (Button)findViewById(R.id.root_msg);
        root_button = (Button)findViewById(R.id.root_button);
        root_button.setEnabled(false); // 버튼 비활성화

        root_msg.setOnClickListener(this);
        root_button.setOnClickListener(this);

        // su 명령 검증
        try {
            Runtime.getRuntime().exec("su");
            isRootingFlag = true;
            msg_title = "Root Detected";
            msg_contents = "Root Detected!";
            DocumentReference docRef = Firestore.collection("flag").document("flag_info");
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w("android_issue_2020", "Listen failed", error);
                    }

                    if (value != null && value.exists()) {
                        Map<String, Object> shot = value.getData();
                        flag_contents = String.valueOf(value.get("root_flag"));
                    }
                    else {
                        //
                    }
                }
            });
            finish();
            root_button.setEnabled(true);
        } catch ( Exception e) {
            // Exception 나면 루팅 false;
            isRootingFlag = false;
            msg_title = "Normal";
            msg_contents = "일반 사용자 권한 휴대폰 확인되었습니다. (General user rights device confirmed)";
        }

        // su 명령 검증 통과 후 파일 경로 검증

        if(!isRootingFlag){
            isRootingFlag = checkRootingFiles(createFiles(RootFilesPath));
            if (isRootingFlag == true) {
                msg_title = "Root Detected";
                msg_contents = "Root Detected!";
                DocumentReference docRef = Firestore.collection("flag").document("flag_info");
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("android_issue_2020", "Listen failed", error);
                        }

                        if (value != null && value.exists()) {
                            Map<String, Object> shot = value.getData();
                            flag_contents = String.valueOf(value.get("root_flag"));
                        }
                        else {
                            //
                        }
                    }
                });
                finish();
                root_button.setEnabled(true);
            }
        }
        Log.d("test", "isRootingFlag = " + isRootingFlag);

        // test_keys 검증
        if(sub_check() == true) {
            msg_title = "Root Detected";
            msg_contents = "Root Detected!";
            DocumentReference docRef = Firestore.collection("flag").document("flag_info");
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w("android_issue_2020", "Listen failed", error);
                    }

                    if (value != null && value.exists()) {
                        Map<String, Object> shot = value.getData();
                        flag_contents = String.valueOf(value.get("root_flag"));
                    }
                    else {
                        //
                    }
                }
            });
            finish();
            root_button.setEnabled(true);
        }
    }

    // test_keys 검증 함수
    public static boolean sub_check() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    /**
     * 루팅파일 의심 Path를 가진 파일들을 생성 한다.
     */
    private File[] createFiles(String[] sfiles){
        File[] rootingFiles = new File[sfiles.length];
        for(int i=0 ; i < sfiles.length; i++){
            rootingFiles[i] = new File(sfiles[i]);
        }
        return rootingFiles;
    }

    /**
     * 루팅파일 여부를 확인 한다.
     */
    private boolean checkRootingFiles(File... file){
        boolean result = false;
        for(File f : file){
            if(f != null && f.exists() && f.isFile()){
                result = true;
                break;
            }else{
                result = false;
            }
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.root_msg:
                AlertDialog.Builder builder = new AlertDialog.Builder(RootDetectionBypassActivity.this);
                builder.setTitle(RootDetectionBypassActivity.this.msg_title);
                builder.setMessage(RootDetectionBypassActivity.this.msg_contents);
                builder.setPositiveButton("check",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RootDetectionBypassActivity.this,"check ok :)", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
            case R.id.root_button:
                finish();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(RootDetectionBypassActivity.this);
                builder2.setTitle(RootDetectionBypassActivity.this.flag_title);
                builder2.setMessage(RootDetectionBypassActivity.this.flag_contents);
                builder2.setPositiveButton("flag check",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RootDetectionBypassActivity.this,"flag check good :)", Toast.LENGTH_SHORT).show();
                    }
                });
                builder2.show();
                break;
            default:
                break;
        }
    }
}