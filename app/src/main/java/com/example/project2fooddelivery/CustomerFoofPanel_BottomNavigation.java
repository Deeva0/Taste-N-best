package com.example.project2fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.trusted.Token;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.project2fooddelivery.customerFoodPanel.CustomerCartFragmnet;
import com.example.project2fooddelivery.customerFoodPanel.CustomerHomeFragment;
import com.example.project2fooddelivery.customerFoodPanel.CustomerOrdersFragment;
import com.example.project2fooddelivery.customerFoodPanel.CustomerProfileFragment;
import com.example.project2fooddelivery.customerFoodPanel.CustomerTrackFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class CustomerFoofPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_foof_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
         FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("Homepage")) {
                loadFragment(new CustomerHomeFragment());
            } else if (name.equalsIgnoreCase("Preparingpage")) {
                loadFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("Preparedpage")) {
                loadFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("DeliverOrderpage")) {
                loadFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("ThankYoupage")) {
                loadFragment(new CustomerHomeFragment());
            }
        } else {
            loadFragment(new CustomerHomeFragment());
        }
    }


    private void UpdateToken() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isComplete()){
                    String token = task.getResult();
                    FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);



                }
            }
        });
//        String refreshToken = FirebaseInstanceId.getInstance().getToken();
//        Token token = new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;


        if(item.getItemId()==R.id.cust_Home){
            fragment=new CustomerHomeFragment();
        }
        else if(item.getItemId()==R.id.cart){
            fragment=new CustomerCartFragmnet();
        }
        else if(item.getItemId()==R.id.cust_profile){
            fragment=new CustomerProfileFragment();
        }
        else if(item.getItemId()==R.id.Cust_order){
            fragment=new CustomerOrdersFragment();
        }
        else if(item.getItemId()==R.id.track){
            fragment=new CustomerTrackFragment();
        }
        return loadFragment(fragment);

    }

    private boolean loadFragment(Fragment fragment) {

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}