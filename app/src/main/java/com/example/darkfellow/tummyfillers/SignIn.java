package com.example.darkfellow.tummyfillers;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends Activity {
    SignInButton signInButton1;
    Button mLoginBtn;
    TextView forgotPass;
    TextView mTogglePass1;
    TextView mRegister;

    EditText mLogEmail, mlogPass;
    FirebaseAuth auth1;
    ProgressBar progressBar1;

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInButton1 = (SignInButton) findViewById(R.id.SignInGoogle);
        mTogglePass1 = (TextView) findViewById(R.id.toggleShow);
        forgotPass = (TextView) findViewById(R.id.forgotPass);
        mLogEmail = (EditText) findViewById(R.id.logEmail);
        mlogPass = (EditText) findViewById(R.id.logPass);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar2);
        mLoginBtn = (Button) findViewById(R.id.LoginBtn);
        mRegister = (TextView) findViewById(R.id.doRegister);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        //Get Firebase auth instance
        auth1 = FirebaseAuth.getInstance();

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, ForgotPassword.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLogEmail.getText().toString();
                final String password = mlogPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);

                //authenticate user
                auth1.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar1.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        mlogPass.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(SignIn.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(SignIn.this, MenuAwal.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

        mTogglePass1.setVisibility(View.GONE);
        mlogPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mlogPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mlogPass.getText().length()>0){
                    mTogglePass1.setVisibility(View.VISIBLE);
                }
                else{
                    mTogglePass1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTogglePass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTogglePass1.getText() == "SHOW"){
                    mTogglePass1.setText("HIDE");
                    mlogPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mlogPass.setSelection(mlogPass.length());
                }
                else{
                    mTogglePass1.setText("SHOW");
                    mlogPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mlogPass.setSelection(mlogPass.length());
                }
            }
        });

        setGooglePlusButtonText(signInButton1, "Connect with Google");
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }


    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Search all the views inside SignInButton for TextView
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            // if the view is instance of TextView then change the text SignInButton
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }
}
