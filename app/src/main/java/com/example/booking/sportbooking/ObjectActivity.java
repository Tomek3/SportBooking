package com.example.booking.sportbooking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking.sportbooking.service.ApiInterface;
import com.example.booking.sportbooking.service.ApiClient;
import com.example.booking.sportbooking.service.ReservationObject;
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
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().getItem(0).setChecked(true);
        setTitle(R.string.nav_item_objects);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final ListView listView = new ListView(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.flContent);
        frameLayout.addView(listView);

        //final List<ReservationObject> reservationObjectList = getReservationObjectList();

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
                prgDialog.hide();
            }
        });

//        if(reservationObjectList != null){
//            ReservationObjectAdapter adapter = new ReservationObjectAdapter(this, (ArrayList<ReservationObject>) reservationObjectList);
//            listView.setAdapter(adapter);
//        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        super.navigatetoLoginActivity();
    }

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

    private class ReservationObjectAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<ReservationObject> mDataSource;

        public ReservationObjectAdapter(Context context, List<ReservationObject> items) {
            mContext = context;
            mDataSource = items;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mInflater.inflate(R.layout.object_list_item, parent, false);

            TextView titleTextView =
                    (TextView) rowView.findViewById(R.id.firstLine);

            TextView subTitleTextView =
                    (TextView) rowView.findViewById(R.id.secondLine);

            ReservationObject reservationObject = (ReservationObject) getItem(position);
            titleTextView.setText(reservationObject.getName());
            subTitleTextView.setText(reservationObject.getInfo());

            return rowView;
        }
    }
}
