package com.example.darkfellow.tummyfillers;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuAwal extends AppCompatActivity {
    private final int LOCATION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_awal);

        BottomNavigationView bottomNav = findViewById(R.id.nav_bot);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION_REQUEST_CODE);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;
                    switch(item.getItemId()){
                        case R.id.nav_com:
                            selectedFragment = new CsFragment();
                            break;
                        case R.id.nav_Help:
                            selectedFragment = new HelpFragment();
                            break;
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_tummycash:
                            selectedFragment = new TummycashFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont,selectedFragment).commit();

                    return true;
                }
            };

    private void askPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
            //gaada permission
            ActivityCompat.requestPermissions(this, new String[] {permission}, requestCode);
        }
        else{
            //ada permission
            Toast.makeText(this, "Permission is Already Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
