package com.srii_systems.myrouteplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.srii_systems.myrouteplan.model.RoutePlanResponses;

import java.util.ArrayList;

/**
 * Created by User on 15.9.2015.
 */

public class RoutePlanResponsesAdapter extends ArrayAdapter<RoutePlanResponses> {
        ArrayList<RoutePlanResponses> RoutePlanResponsesList;
        LayoutInflater vi;
        int Resource;
        ViewHolder holder;

    public RoutePlanResponsesAdapter(Context context, int resource, ArrayList<RoutePlanResponses> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        RoutePlanResponsesList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.departureTime = (TextView) v.findViewById(R.id.departureTime);
            holder.arrivalTime = (TextView) v.findViewById(R.id.arrivalTime);
            holder.transportType = (TextView) v.findViewById(R.id.transportType);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.departureTime.setText("DepartureTime: " + RoutePlanResponsesList.get(position).getmDepartureTime());
        holder.arrivalTime.setText("ArrivalTime: " + RoutePlanResponsesList.get(position).getmArrivalTime());
        holder.transportType.setText("TransportType: " + RoutePlanResponsesList.get(position).getmTransportationType());
        return v;

    }

    static class ViewHolder {
        public TextView departureTime;
        public TextView arrivalTime;
        public TextView transportType;
         }

}
