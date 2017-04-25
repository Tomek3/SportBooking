package com.example.booking.sportbooking.reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.object.ReservationObject;
import com.example.booking.sportbooking.object.ReservationObjectAdapter;

import java.util.List;

/**
 * Created by Tomek on 25.04.2017.
 */

public class ReservationAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Reservation> mDataSource;

    public ReservationAdapter(Context context, List<Reservation> items) {
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
        ReservationAdapter.ViewHolder holder = null;
        Reservation reservation = (Reservation) getItem(position);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.reservation_list_item, parent, false);

            holder = new ReservationAdapter.ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.firstLine);
            holder.subTitleTextView = (TextView) convertView.findViewById(R.id.secondLine);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.thirdLine);
            holder.detailsTextView = (TextView) convertView.findViewById(R.id.fourthLine);

            convertView.setTag(holder);
        }
        else{
            holder = (ReservationAdapter.ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(reservation.getReservationObjectName());
        holder.subTitleTextView.setText(reservation.getReservationObjectAddress());
        holder.descriptionTextView.setText(String.format("%s - %s", reservation.getDateFrom().substring(0,reservation.getDateFrom().length()-2),
                reservation.getDateTo().substring(reservation.getDateTo().indexOf(" "), reservation.getDateTo().length()-2)));
        holder.detailsTextView.setText(String.format("%s %s", mContext.getString(R.string.reservationNumber), reservation.getId()));

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView descriptionTextView;
        public TextView detailsTextView;
    }
}
