package com.example.project2fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.trusted.Token;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.project2fooddelivery.chefFoodPanel.ChefHomeFragment;
import com.example.project2fooddelivery.chefFoodPanel.ChefOrderFragment;
import com.example.project2fooddelivery.chefFoodPanel.ChefPendingOrderFragment;
import com.example.project2fooddelivery.chefFoodPanel.ChefProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class ChefFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_food_panel_bottom_navigation);

        BottomNavigationView navigationView = findViewById(R.id.chef_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        UpdateToken();

        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("Orderpage")) {
                loadcheffragment(new ChefPendingOrderFragment());
            } else if (name.equalsIgnoreCase("Confirmpage")) {
                loadcheffragment(new ChefOrderFragment());
            } else if (name.equalsIgnoreCase("AcceptOrderpage")) {
                loadcheffragment(new ChefHomeFragment());
            } else if (name.equalsIgnoreCase("Deliveredpage")) {
                loadcheffragment(new ChefHomeFragment());
            }
        } else {
            loadcheffragment(new ChefHomeFragment());
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
        if(item.getItemId()==R.id.chefHome)
        {fragment=new ChefHomeFragment();}
        else if(item.getItemId()==R.id.PendingOrders)
        {fragment=new ChefPendingOrderFragment();}
        else if(item.getItemId()==R.id.Orders)
        {fragment=new ChefOrderFragment();}
        else if(item.getItemId()==R.id.chefProfile)
        {fragment=new ChefProfileFragment();}
        return loadcheffragment(fragment);
    }

    private boolean loadcheffragment(Fragment fragment) {

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}