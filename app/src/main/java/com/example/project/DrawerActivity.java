package com.example.project;

import android.os.Bundle;

import com.example.project.Fragments.DashboardFragment;
import com.example.project.Fragments.WalletFragment;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.project.Fragments.WithdrawFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private MenuItem previousItem;
    private AppCompatTextView  navigationName, navigationEmail;
    private View navigationHeader;


    //Api Strings
    private String url, TAG = "USER_DRAWER_ACTIVITY";
    private OkHttpClient client;
    private Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        bindControls();
        bindListeners();
        toolbarSetting();
        setUpHomePage();

    }

    private void setUpHomePage() {
        Boolean refreshFragment;
        try {
            refreshFragment = getIntent().getExtras().getBoolean("refresh", false);
        } catch (Exception e) {
            refreshFragment = false;
            Log.e(TAG, "setUpHomePage: " + e);
        }
        if (refreshFragment) {
            navigationView.setCheckedItem(R.id.nav_dashboard);
            setUpFragment(new DashboardFragment());
        }
        else {
            navigationView.setCheckedItem(R.id.nav_wallet);
            setUpFragment(new WalletFragment());
        }
    }

    private void bindControls() {
        toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationHeader = navigationView.getHeaderView(0);
        navigationName = navigationHeader.findViewById(R.id.header_name);
        navigationEmail = navigationHeader.findViewById(R.id.header_email);

    }

    private void bindListeners() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    private void toolbarSetting() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (previousItem == item){
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        previousItem = item;

        int id = item.getItemId();

        String toolbarTitle = "";
        Boolean value = true;

        if (id == R.id.nav_dashboard) {
            fragment = new DashboardFragment();
            toolbarTitle = "Dashboard";
            value = true;
        } else if (id == R.id.nav_wallet) {
            fragment = new WalletFragment();
            toolbarTitle = "Your Wallet";
            value = false;
        } else if (id == R.id.nav_withdraw) {
            fragment = new WithdrawFragment();
            toolbarTitle = "Withdraw";
            value = false;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        setUpFragment(fragment);
        return true;
    }

    private void setUpFragment(Fragment frag) {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = frag;
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
