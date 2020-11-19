package com.example.phoeauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Authentication extends AppCompatActivity {
private EditText otpgater;
private Button enter;
    private ProgressBar progressBar;
    private String verificationid;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        enter=(Button)findViewById(R.id.enter);
        otpgater=(EditText)findViewById(R.id.otpgater);

        final String phonenumber=getIntent().getStringExtra("phonenumber");
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendVerificationcode(phonenumber);

                String code1=otpgater.getText().toString();
                if (code1.isEmpty() || code1.length()<6)
                {
                    otpgater.setError("Enter valid code");
                    otpgater.requestFocus();
                    return;
                }

                verifycode(code1);
            }
        });

    }
    private void sendVerificationcode(String number)
    {           progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,60, TimeUnit.SECONDS, this,
                mCallbacks
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationid=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if (code!=null)
            {
                otpgater.setText(code);
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };
    private void verifycode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,code);
        signinwithcredential(credential);

    }

    private void signinwithcredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {  finish();
                            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
