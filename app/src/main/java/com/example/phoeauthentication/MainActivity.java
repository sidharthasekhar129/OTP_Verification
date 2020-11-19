package com.example.phoeauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
private EditText phone,otp;
private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone=(EditText)findViewById(R.id.ed1);
        otp=(EditText)findViewById(R.id.ed2);
        login=(Button)findViewById(R.id.login);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code="+91";
                String number=phone.getText().toString().trim();

                if(number.isEmpty() || number.length()<10)
                {
                    phone.setError("Valid Number is required");
                    phone.requestFocus();
                    return;
                }
                String phonenumber=code+number;

              Intent intent=new Intent(getApplicationContext(),Authentication.class);
              intent.putExtra("phonenumber",phonenumber);
              startActivity(intent);

            }
        });
    }

}
