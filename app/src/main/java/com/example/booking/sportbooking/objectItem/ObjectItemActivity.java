package com.example.booking.sportbooking.objectItem;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.booking.sportbooking.BaseActivity;
import com.example.booking.sportbooking.R;

import java.util.Calendar;

public class ObjectItemActivity extends BaseActivity implements ObjectItemFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(0).setChecked(true);
        setTitle(R.string.nav_item_objects);

//        // pobieramy dane wysłane przez aktywność główną
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String objectId = extras.getString("objectId");
//        }

        ObjectItemFragment objectItemFragment = new ObjectItemFragment();
        objectItemFragment.setArguments(getIntent().getExtras());
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, objectItemFragment).commit();
    }


    public void onItemSelected(int position){
    }
}
