package com.example.lochat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener{


    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "Google";
    private EditText mLoginEditText;
    private EditText mPasswordEditText;
    private TextView mRegisterLink;
    private Button mLoginButton;
    private String mUsername;
    private String mPassword;

    FirebaseAuth mFirebaseAuth;
    ProgressDialog mProgressDialog;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterLink = (TextView) findViewById(R.id.create_login_link);
        mLoginEditText = (EditText) findViewById(R.id.login_username_editText);
        mPasswordEditText = (EditText) findViewById(R.id.login_password_editText);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        mRegisterLink.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        findViewById(R.id.login_google_button).setOnClickListener(this);
    }

    private void userLogin(){
        mUsername = mLoginEditText.getText().toString().trim();
        mPassword = mPasswordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(mUsername)){

            mLoginEditText.setError("Username is required");
            mLoginEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mUsername).matches()){
            mLoginEditText.setError("Please Enter a valid email or username");
            mLoginEditText.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(mPassword)){
            mPasswordEditText.setError("Password Required");
            mPasswordEditText.requestFocus();
            return;
        }

        mProgressDialog.setMessage("Logging on...");
        mProgressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(mUsername, mPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mFirebaseAuth.getCurrentUser().isEmailVerified()) {

                        Toast.makeText(getApplicationContext(), "You have Logged on", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, LocationActivity.class));
                        finish();

                    }else{
                        mLoginEditText.setError("Please Verify your Email address");
                        mLoginEditText.requestFocus();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                mProgressDialog.dismiss();
            }
        });

    }


    @Override
    public void onClick(View view){

        if(view == mLoginButton){
            userLogin();
        }

        if(view == mRegisterLink){
            startActivity(new Intent(LogInActivity.this, EmailAuthenticationActivity.class));
            finish();
        }

        if(view == findViewById(R.id.login_google_button)){
            signIn();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            startActivity(new Intent(LogInActivity.this, LocationActivity.class));
                            Toast.makeText(LogInActivity.this,"Authentication Success", Toast.LENGTH_LONG).show();
                            finish();
                            
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LogInActivity.this,"Authentication Failed", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }


}
