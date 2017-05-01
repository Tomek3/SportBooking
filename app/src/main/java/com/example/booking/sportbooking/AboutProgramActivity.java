package com.example.booking.sportbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AboutProgramActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(3).getSubMenu().getItem(0).setChecked(true);
        setTitle(R.string.nav_item_about_program);

        final TextView mTextView = new TextView(this);
        mTextView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setSingleLine(false);
        mTextView.setText("SportBooking\nAutor: Tomasz Fielek 2017");


        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.flContent);
        frameLayout.addView(mTextView);

    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }
}
