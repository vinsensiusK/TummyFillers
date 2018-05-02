package com.example.darkfellow.tummyfillers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.darkfellow.tummyfillers.User.User;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by darkfellow on 4/10/18.
 */

public class ProfileFragment extends Fragment {
    private TextView email,nama;
    public MaterialEditText namadia;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Button signout;
    private Firebase mRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        email = view.findViewById(R.id.useremail);
        namadia = view.findViewById(R.id.editName);
        nama = view.findViewById(R.id.userName);
        signout = (Button) view.findViewById(R.id.signOut);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        String uid = user.getUid();
        mRef = new Firebase("https://tummyfillers-922e6.firebaseio.com/User/"+uid+"/name");

        mRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                nama.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),SignUp.class));
                getActivity().finish();
            }
        });
        
        return view;

    }


    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText(user.getEmail());

    }

}
