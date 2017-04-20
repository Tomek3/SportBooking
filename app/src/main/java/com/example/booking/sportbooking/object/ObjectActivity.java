package com.example.booking.sportbooking.object;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.booking.sportbooking.BaseActivity;
import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.objectItem.ObjectItemActivity;
import com.example.booking.sportbooking.service.ReservationService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import cz.msebera.android.httpclient.Header;

public class ObjectActivity extends BaseActivity {

    private static final String TAG = ObjectActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(0).setChecked(true);
        setTitle(R.string.nav_item_objects);

        final ListView listView = new ListView(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.flContent);
        frameLayout.addView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReservationObject selectedObject = (ReservationObject)parent.getAdapter().getItem(position);
                Intent detailIntent = new Intent(getApplicationContext(), ObjectItemActivity.class);
                detailIntent.putExtra("objectId", selectedObject.getId());
                startActivity(detailIntent);
            }
        });

        prgDialog.show();

        Call<List<ReservationObject>> call = apiService.doGetListReservationObject();
        call.enqueue(new Callback<List<ReservationObject>>() {
            @Override
            public void onResponse(Call<List<ReservationObject>>call, Response<List<ReservationObject>> response) {
                List<ReservationObject> reservationObjects = response.body();
                Log.d(TAG, "Number of movies received: " + reservationObjects.size());

                ReservationObjectAdapter adapter = new ReservationObjectAdapter(getBaseContext(), reservationObjects);
                listView.setAdapter(adapter);
                prgDialog.hide();
            }

            @Override
            public void onFailure(Call<List<ReservationObject>>call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                prgDialog.hide();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }

    //example getReservationObjectList by AsyncHttpClient
    public List<ReservationObject> getReservationObjectList(){
        prgDialog.show();
        final List<ReservationObject>[] result = new List[1];
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ReservationService.getAbsoluteUrl("reservationObject"),new TextHttpResponseHandler(){
        //client.get(ReservationService.getAbsoluteUrl("reservationObject"),new RequestParams() ,new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ReservationObject>>(){}.getType();
                    result[0] = gson.fromJson(response, listType);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.jsonError), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                prgDialog.hide();
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.http404), Toast.LENGTH_LONG).show();
                }
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.http500), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.httpError), Toast.LENGTH_LONG).show();
                }
            }
        });

        return result[0];
    }
}
