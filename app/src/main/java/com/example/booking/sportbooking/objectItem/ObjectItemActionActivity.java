package com.example.booking.sportbooking.objectItem;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.booking.sportbooking.BaseActivity;
import com.example.booking.sportbooking.R;

public class ObjectItemActionActivity extends BaseActivity {

    private String objectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(0).setChecked(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            objectName = extras.getString("objectName");
        }

        setTitle(objectName);

        ObjectItemActionFragment objectItemActionFragment = new ObjectItemActionFragment();
        objectItemActionFragment.setArguments(getIntent().getExtras());
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, objectItemActionFragment).commit();
    }
}
