package com.example.booking.sportbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutProgramActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(3).setChecked(true);
        setTitle(R.string.nav_item_about_program);
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }
}
