package com.example.booking.sportbooking.watched;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.Utility;
import com.example.booking.sportbooking.reservation.Reservation;

import java.util.Date;
import java.util.List;

/**
 * Created by Tomek on 01.05.2017.
 */

public class ObjectWatchedAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Reservation> mDataSource;

    public ObjectWatchedAdapter(Context context, List<Reservation> items) {
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
        ViewHolder holder = null;
        Reservation reservation = (Reservation) getItem(position);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.watched_list_item, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.firstLine);
            holder.subTitleTextView = (TextView) convertView.findViewById(R.id.secondLine);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.thirdLine);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(reservation.getReservationObjectName());
        holder.subTitleTextView.setText(reservation.getReservationObjectAddress());

        Date dateFrom = Utility.dateFromString(reservation.getDateFrom());

        if(dateFrom.before(new Date())){
            holder.descriptionTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        }
        else{
            holder.descriptionTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }

        holder.descriptionTextView.setText(String.format("%s - %s", reservation.getDateFrom().substring(0,reservation.getDateFrom().length()-2),
                reservation.getDateTo().substring(reservation.getDateTo().indexOf(" ")+1, reservation.getDateTo().length()-2)));

        return convertView;
    }

    public void removeItem(int position){
        mDataSource.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView descriptionTextView;
    }
}
