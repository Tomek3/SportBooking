package com.example.booking.sportbooking.objectItem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.booking.sportbooking.R;
import com.example.booking.sportbooking.object.ReservationObject;
import com.example.booking.sportbooking.object.ReservationObjectAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomek on 21.04.2017.
 */

public class ReservationObjectItemAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ReservationObjectItem> mDataSource;

    public ReservationObjectItemAdapter(Context context, List<ReservationObjectItem> items) {
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
        ReservationObjectItemAdapter.ViewHolder holder = null;
        ReservationObjectItem reservationObjectItem = (ReservationObjectItem) getItem(position);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.object_item_list_item, parent, false);

            holder = new ReservationObjectItemAdapter.ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.firstLine);
            holder.subTitleTextView = (TextView) convertView.findViewById(R.id.secondLine);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.thirdLine);

            convertView.setTag(holder);
        }
        else{
            holder = (ReservationObjectItemAdapter.ViewHolder) convertView.getTag();
        }


        holder.titleTextView.setText(reservationObjectItem.getDateFrom().substring(0,reservationObjectItem.getDateFrom().length()-2));
        holder.subTitleTextView.setText(reservationObjectItem.getDateTo().substring(0,reservationObjectItem.getDateTo().length()-2));
        if(reservationObjectItem.getAvailable()){
            holder.descriptionTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            holder.descriptionTextView.setText(R.string.objectItemAvailable);
        }
        else{
            holder.descriptionTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            holder.descriptionTextView.setText(R.string.itemBusy);
        }


        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView descriptionTextView;
    }
}
