package com.example.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem firstItem,secondItem;
    PagerAdapter adapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager=findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

        firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        userId=fAuth.getCurrentUser().getUid();

        adapter=new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTabLayout.getTabCount());
        pager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.menuTab){
            Intent view_pro = new Intent(MainActivity2.this,view_profile.class);
            startActivity(view_pro);
        }
        else if(item.getItemId()==R.id.menuTab1){
            Intent edit=new Intent(MainActivity2.this,EditProfile.class);
            edit.putExtra("fullName","Andriod");
            edit.putExtra("email","android@gmail.com");
            edit.putExtra("rollno","19202C1010");
            startActivity(edit);
        }
        else if(item.getItemId()==R.id.menuTab2){
            Intent i3= new Intent(MainActivity2.this,login.class);
            startActivity(i3);
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.menuTab3){
            Intent about = new Intent(MainActivity2.this,about_us.class);
            startActivity(about);
        }

        return false;
    }
}