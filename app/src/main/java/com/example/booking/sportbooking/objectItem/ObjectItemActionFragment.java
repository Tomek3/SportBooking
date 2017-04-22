package com.example.booking.sportbooking.objectItem;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking.sportbooking.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectItemActionFragment extends Fragment implements View.OnClickListener {

    private ReservationObjectItem reservationObjectItem;
    private View myFragmentView;

    TextView dateFrom, dateTo;
    Button reserveButton, watchButton;

    public ObjectItemActionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            reservationObjectItem = (ReservationObjectItem)bundle.getSerializable("reservationObjectItem");
        }

        myFragmentView = inflater.inflate(R.layout.fragment_object_item_action, container, false);

        dateFrom=(TextView)myFragmentView.findViewById(R.id.dateFromTextView);
        dateTo=(TextView)myFragmentView.findViewById(R.id.dateToTextView);
        reserveButton=(Button)myFragmentView.findViewById(R.id.reserveButton);
        watchButton=(Button)myFragmentView.findViewById(R.id.watchButton);

        dateFrom.setText(getString(R.string.dateFromLabel) + " " + reservationObjectItem.getDateFrom().substring(0,reservationObjectItem.getDateFrom().length()-2));
        dateTo.setText(getString(R.string.dateToLabel) + " " + reservationObjectItem.getDateTo().substring(0,reservationObjectItem.getDateTo().length()-2));

        reserveButton.setOnClickListener(this);
        watchButton.setOnClickListener(this);

        if(!reservationObjectItem.getAvailable()){
            reserveButton.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return myFragmentView ;
    }

    @Override
    public void onClick(View v) {

        if (v == reserveButton) {
            Toast.makeText(getContext(), R.string.reserveObjectInfo, Toast.LENGTH_LONG).show();
        }
        else if(v == watchButton){
            Toast.makeText(getContext(), R.string.watchObjectInfo, Toast.LENGTH_LONG).show();
        }
    }

}
