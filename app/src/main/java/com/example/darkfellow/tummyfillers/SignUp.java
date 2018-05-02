package com.example.darkfellow.tummyfillers;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkfellow.tummyfillers.User.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignUp extends Activity {
    public TextView mloginBtn;
    public SignInButton signInButton;
    public Button mRegisterBtn;

    private EditText mPassEdit;
    private EditText mEmailEdit;
    private EditText mNameEdit;
    private TextView mTogglePass;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    private static long back_pressed;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mloginBtn = (TextView) findViewById(R.id.doLogin);

        mRegisterBtn = (Button) findViewById(R.id.registerBtn);

        mPassEdit = (EditText) findViewById(R.id.editPass);
        mEmailEdit = (EditText) findViewById(R.id.editEmail);
        mNameEdit = (EditText) findViewById(R.id.editName);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mTogglePass = (TextView) findViewById(R.id.toggleTextView);


        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dologin = new Intent(SignUp.this, SignIn.class);
                startActivity(dologin);
            }
        });


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = mEmailEdit.getText().toString().trim();
                final String password = mPassEdit.getText().toString().trim();
                final String name = mNameEdit.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
                                    DatabaseReference currentUserDB = mDatabase.child(auth.getCurrentUser().getUid());
                                    currentUserDB.child("name").setValue(name);
                                    currentUserDB.child("password").setValue(password);

                                    startActivity(new Intent(SignUp.this, SignIn.class));
                                    finish();
                                }
                            }
                        });

            }
        });

//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseAuthWithGoogle();
//            }
//        });

        mTogglePass.setVisibility(View.GONE);
        mPassEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mPassEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mPassEdit.getText().length()>0){
                    mTogglePass.setVisibility(View.VISIBLE);
                }
                else{
                    mTogglePass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTogglePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTogglePass.getText() == "SHOW"){
                    mTogglePass.setText("HIDE");
                    mPassEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassEdit.setSelection(mPassEdit.length());
                }
                else{
                    mTogglePass.setText("SHOW");
                    mPassEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassEdit.setSelection(mPassEdit.length());
                }
            }
        });

        setGooglePlusButtonText(signInButton, "Connect with Google");
    }
//    @Override
//    public void onBackPressed(){
//        final AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
//        builder.setMessage("Are you sure?");
//        builder.setCancelable(true);
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//                moveTaskToBack(true);
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

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


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
