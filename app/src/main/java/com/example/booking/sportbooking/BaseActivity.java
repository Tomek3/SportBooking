package com.example.booking.sportbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout mDrawerLayout;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle drawerToggle;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);

        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView login = (TextView)header.findViewById(R.id.drawerHeaderLogin);
        TextView name = (TextView)header.findViewById(R.id.drawerHeaderName);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        login.setText(settings.getString("Login", "").toString());
        name.setText(settings.getString("Name", "").toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.action_settings == item.getItemId())
        {
            navigatetoLoginActivity();
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    //navigation drawer - fragment
//    public void selectDrawerItem(MenuItem menuItem) {
//        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = null;
//        Class fragmentClass;
//        switch(menuItem.getItemId()) {
//            case R.id.navigation_item_object:
//                fragmentClass = ObjectListFragment.class;
//                break;
//            case R.id.navigation_item_reservation:
//                fragmentClass = ObjectListFragment.class;
//                break;
//            case R.id.navigation_item_notification:
//                fragmentClass = ObjectListFragment.class;
//                break;
//            default:
//                fragmentClass = ObjectListFragment.class;
//        }
//
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//
//        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
//        // Set action bar title
//        setTitle(menuItem.getTitle());
//        // Close the navigation drawer
//        mDrawerLayout.closeDrawers();
//
//        Toast.makeText(BaseActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
//    }

    public void selectDrawerItem(MenuItem menuItem) {

        //to prevent current item select over and over
        if (menuItem.isChecked()){
            mDrawerLayout.closeDrawers();
            return;
        }

        switch(menuItem.getItemId()) {
            case R.id.navigation_item_object:
                startActivity(new Intent(getApplicationContext(), ObjectActivity.class));
                break;
            case R.id.navigation_item_reservation:
                startActivity(new Intent(getApplicationContext(), ReservationActivity.class));
                break;
            case R.id.navigation_item_notification:
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            default:
                startActivity(new Intent(getApplicationContext(), ObjectActivity.class));
        }

        mDrawerLayout.closeDrawers();
        Toast.makeText(BaseActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void navigatetoLoginActivity(){
        Intent loginIntent = new Intent(getApplicationContext(),MainActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void hideMenu(View view){
        mDrawerLayout.closeDrawers();
    }


}
