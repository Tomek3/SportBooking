package com.example.booking.sportbooking.objectItem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.object.ReservationObject;
import com.example.booking.sportbooking.service.ApiClient;
import com.example.booking.sportbooking.service.ApiInterface;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectItemFragment extends Fragment implements
        View.OnClickListener {

    private View myFragmentView;

    Button btnDatePicker;
    TextView txtDate;
    ListView mListView;
    private int mYear, mMonth, mDay;
    private Integer objectId;

    private OnFragmentInteractionListener mListener;

    public ObjectItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            objectId = bundle.getInt("objectId");
        }

        myFragmentView = inflater.inflate(R.layout.fragment_object_item, container, false);
        btnDatePicker=(Button)myFragmentView.findViewById(R.id.btn_date);
        txtDate=(TextView)myFragmentView.findViewById(R.id.in_date);
        mListView = (ListView) myFragmentView.findViewById(R.id.objectItemListView);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);

        btnDatePicker.setOnClickListener(this);
        getObjectItem(objectId, mYear + "-" + (mMonth + 1) + "-" + mDay);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReservationObjectItem selectedObjectItem = (ReservationObjectItem)parent.getAdapter().getItem(position);
                mListener.onItemSelected(selectedObjectItem);
            }
        });

        return myFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemSelected(ReservationObjectItem reservationObjectItem);
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            getObjectItem(objectId, year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void getObjectItem(Integer objectId, String date){
        final ProgressDialog prgDialog = new ProgressDialog(getActivity());
        prgDialog.show();
        Call<List<ReservationObjectItem>> call = ApiClient.getClient().create(ApiInterface.class).doGetListReservationObjectItem(objectId.toString(), date);
        call.enqueue(new Callback<List<ReservationObjectItem>>() {
            @Override
            public void onResponse(Call<List<ReservationObjectItem>>call, Response<List<ReservationObjectItem>> response) {
                List<ReservationObjectItem> reservationObjectItems = response.body();

                ReservationObjectItemAdapter adapter = new ReservationObjectItemAdapter(getContext(), reservationObjectItems);
                mListView.setAdapter(adapter);
                prgDialog.hide();
            }

            @Override
            public void onFailure(Call<List<ReservationObjectItem>>call, Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                prgDialog.hide();
            }
        });
    }
}
