package com.example.booking.sportbooking.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.booking.sportbooking.R;

import java.util.List;

/**
 * Created by Tomek on 09.04.2017.
 */

public class ReservationObjectAdapter extends BaseAdapter {
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
        ViewHolder holder = null;
        ReservationObject reservationObject = (ReservationObject) getItem(position);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.object_list_item, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.firstLine);
            holder.subTitleTextView = (TextView) convertView.findViewById(R.id.secondLine);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.thirdLine);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(reservationObject.getName());
        holder.subTitleTextView.setText(reservationObject.getAddress());
        holder.descriptionTextView.setText(reservationObject.getInfo());

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView descriptionTextView;
    }
}
