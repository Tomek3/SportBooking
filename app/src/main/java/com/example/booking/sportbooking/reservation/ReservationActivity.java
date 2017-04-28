package com.example.booking.sportbooking.reservation;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.booking.sportbooking.BaseActivity;
import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.service.ReservationService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends BaseActivity {

    private int selectedPosition = -1;
    private Reservation selectedReservation = null;
    private ListView listView = null;
    private ReservationAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(1).setChecked(true);
        setTitle(R.string.nav_item_reservation);

        listView = new ListView(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.flContent);
        frameLayout.addView(listView);

        listView.setSelector(R.drawable.bg_key);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Reservation selectedReservation = (Reservation)parent.getAdapter().getItem(position);
                //selectedPosition = position;
                deleteButton.setVisible(false);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedReservation = (Reservation)parent.getAdapter().getItem(position);
                selectedPosition = position;

                deleteButton.setVisible(true);
                view.setSelected(true);

                return true;
            }
        });

        loadAdapterData();
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.action_delete == item.getItemId())
        {
            deleteButton.setVisible(false);
            listView.setItemChecked(selectedPosition,false);
            deleteReservation(getSharedPreferences("UserInfo", 0).getInt("UserId",-1), selectedReservation.getId());

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void loadAdapterData(){
        prgDialog.show();
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        Integer userId = settings.getInt("UserId",-1);

        Call<List<Reservation>> call = apiService.doGetListUserReservation(userId.toString());
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>>call, Response<List<Reservation>> response) {
                List<Reservation> reservations = response.body();

                adapter = new ReservationAdapter(getBaseContext(), reservations);
                listView.setAdapter(adapter);
                prgDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Reservation>>call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                prgDialog.hide();
            }
        });
    }

    public void deleteReservation(Integer userId, Integer resId){

        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("userId", userId.toString());
        params.put("resId", resId.toString());

        client.get(ReservationService.getAbsoluteUrl("reservation/delete"),params ,new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getBoolean("status")){
                        Toast.makeText(getBaseContext(), R.string.reservationDeleted, Toast.LENGTH_LONG).show();
                        removeItemFromAdapter();
                    }
                    else{
                        Toast.makeText(getBaseContext(), R.string.reservationDeletedError, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.jsonError), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                prgDialog.hide();
                if(statusCode == 404){
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                }
                else if(statusCode == 500){
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.http500), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.httpError), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void removeItemFromAdapter(){
        adapter.removeItem(selectedPosition);
    }
}
