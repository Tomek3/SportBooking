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
        View rowView = mInflater.inflate(R.layout.object_list_item, parent, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.firstLine);

        TextView subTitleTextView =
                (TextView) rowView.findViewById(R.id.secondLine);

        TextView descriptionTextView =
                (TextView) rowView.findViewById(R.id.thirdLine);

        ReservationObject reservationObject = (ReservationObject) getItem(position);
        titleTextView.setText(reservationObject.getName());
        subTitleTextView.setText(reservationObject.getAddress());
        descriptionTextView.setText(reservationObject.getInfo());

        return rowView;
    }
}
