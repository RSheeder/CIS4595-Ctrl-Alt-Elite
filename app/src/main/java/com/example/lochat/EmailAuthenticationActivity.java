package com.example.lochat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class EmailAuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailRegisterText;
    private EditText mPasswordRegisterText;
    private TextView mSignInLink;
    private Button mRegisterButton;
    private String mEmail;
    private String mPassword;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_authentication_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mSignInLink = (TextView) findViewById(R.id.sign_in_link);
        mEmailRegisterText = (EditText) findViewById(R.id.username_text_box);
        mPasswordRegisterText = (EditText) findViewById(R.id.password_text_box);

        mRegisterButton.setOnClickListener(this);
        mSignInLink.setOnClickListener(this);

    }

    private void registerUser(){
        mEmail = mEmailRegisterText.getText().toString().trim();
        mPassword = mPasswordRegisterText.getText().toString().trim();



            if (TextUtils.isEmpty(mEmail)) {
                //No Email entered
                mEmailRegisterText.setError("Email is required");
                mEmailRegisterText.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                mEmailRegisterText.setError("Please Enter a valid email");
                mEmailRegisterText.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(mPassword)) {
                mPasswordRegisterText.setError("Password Required");
                mPasswordRegisterText.requestFocus();
                return;
            }

            if (mPassword.length() < 8) {
                mPasswordRegisterText.setError("Password too short, enter minimum 8 characters!");
                mPasswordRegisterText.requestFocus();
                return;
            }

            //NEEDS EXTRA Validation on passwords (Check for Symbols)*************************************************************************************



            mProgressDialog.setMessage("Registering User...");
            mProgressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(EmailAuthenticationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mFirebaseAuth.getInstance().getCurrentUser()
                                        .sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Successful Register, Check Email For Verification", Toast.LENGTH_SHORT).show();
                                                    mEmailRegisterText.setText("");
                                                    mPasswordRegisterText.setText("");
                                                    Intent i = new Intent(EmailAuthenticationActivity.this, LogInActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);
                                                    finish();
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "Email Verification failed", Toast.LENGTH_SHORT).show();
                                                    mEmailRegisterText.setText("");
                                                    mPasswordRegisterText.setText("");
                                                }
                                            }
                                        });

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    mEmailRegisterText.setError("Email is already Registered");
                                    mEmailRegisterText.requestFocus();
                                    mEmailRegisterText.setText("");
                                    mPasswordRegisterText.setText("");
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    mEmailRegisterText.setText("");
                                    mPasswordRegisterText.setText("");
                                }
                            }

                            mProgressDialog.dismiss();
                        }

                    });
        }

    @Override
    public void onClick(View view){

        if(view == mRegisterButton){
            registerUser();
        }

        if(view == mSignInLink){
            startActivity(new Intent(EmailAuthenticationActivity.this, LogInActivity.class));
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressDialog.dismiss();

    }
}
