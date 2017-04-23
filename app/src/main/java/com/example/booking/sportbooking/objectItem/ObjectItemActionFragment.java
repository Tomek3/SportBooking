package com.example.booking.sportbooking.objectItem;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.service.ReservationService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;


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

        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);
        Integer userId = settings.getInt("UserId",-1);

        if (v == reserveButton) {
            createReservation(userId, reservationObjectItem.getId());
        }
        else if(v == watchButton){
            createWatch(userId, reservationObjectItem.getId());
        }
    }

    public void createReservation(Integer userId, Integer resId){
        final ProgressDialog prgDialog = new ProgressDialog(getActivity());
        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("userId", userId.toString());
        params.put("resId", resId.toString());

        client.get(ReservationService.getAbsoluteUrl("reservation/create"),params ,new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getBoolean("status")){
                        Toast.makeText(getContext(), R.string.reserveObjectInfo, Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(), R.string.reserveObjectNotAvailable, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), getResources().getString(R.string.jsonError), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                prgDialog.hide();
                if(statusCode == 404){
                    Toast.makeText(getContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                }
                else if(statusCode == 500){
                    Toast.makeText(getContext(), getResources().getString(R.string.http500), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), getResources().getString(R.string.httpError), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void createWatch(Integer userId, Integer resId){
        final ProgressDialog prgDialog = new ProgressDialog(getActivity());
        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("userId", userId.toString());
        params.put("resId", resId.toString());

        client.get(ReservationService.getAbsoluteUrl("watch/create"),params ,new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getBoolean("status")){
                        Toast.makeText(getContext(), R.string.watchObjectInfo, Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(), R.string.objectWatched, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), getResources().getString(R.string.jsonError), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                prgDialog.hide();
                if(statusCode == 404){
                    Toast.makeText(getContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                }
                else if(statusCode == 500){
                    Toast.makeText(getContext(), getResources().getString(R.string.http500), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), getResources().getString(R.string.httpError), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
