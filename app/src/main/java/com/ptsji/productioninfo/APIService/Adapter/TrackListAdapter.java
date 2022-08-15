package com.ptsji.productioninfo.APIService.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ptsji.productioninfo.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ptsji.productioninfo.APIService.GetterSetter.TrackListGetSet;
import com.ptsji.productioninfo.ResultActivity;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {
    private ArrayList<TrackListGetSet> trackArrayList;
    private Context context;

    public TrackListAdapter(List<TrackListGetSet> trackArrayList, Context context) {
        this.trackArrayList = (ArrayList<TrackListGetSet>) trackArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return trackArrayList.size();
    }

    public TrackListGetSet getItem(int position){
        return trackArrayList.get(position);
    }

    @Override
    public TrackListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_history, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackListAdapter.ViewHolder holder, int position) {
        if (position == 0 ) {
            holder.line1.setVisibility(View.GONE);
            holder.circle.setColorFilter(ContextCompat.getColor(context, R.color.main));
            holder.line.setColorFilter(ContextCompat.getColor(context, R.color.main));
            holder.status.setColorFilter(ContextCompat.getColor(context, R.color.teal_200));
            holder.title.setTypeface(holder.title.getTypeface(), Typeface.BOLD);
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.main));
            holder.scan.setTextColor(ContextCompat.getColor(context, R.color.main));
            holder.scan.setTypeface(holder.scan.getTypeface(), Typeface.BOLD);
            holder.by.setTypeface(holder.by.getTypeface(), Typeface.BOLD);
            holder.by.setTextColor(ContextCompat.getColor(context, R.color.main));
        } else if (position == 1) {
            holder.line1.setColorFilter(ContextCompat.getColor(context, R.color.main));
            holder.circle.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
            holder.line.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
            holder.status.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
        } else {
            holder.line1.setVisibility(View.VISIBLE);
            holder.circle.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
            holder.line.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
            holder.line1.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
            holder.status.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey));
        }

        if (position+1 == trackArrayList.size()) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.title.setText(trackArrayList.get(position).getTitle());
        holder.date.setText(trackArrayList.get(position).getDate());
        holder.time.setText(trackArrayList.get(position).getTime());
        holder.scan.setText(trackArrayList.get(position).getScan());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView circle,line,status,line1;
        TextView title, date, time, scan,by;
        public ViewHolder(@NonNull View v) {
            super(v);
            circle = v.findViewById(R.id.circle);
            line = v.findViewById(R.id.line);
            line1 = v.findViewById(R.id.line1);
            status = v.findViewById(R.id.status);
            title = v.findViewById(R.id.title);
            date = v.findViewById(R.id.date);
            time = v.findViewById(R.id.time);
            scan = v.findViewById(R.id.scan_by);
            by = v.findViewById(R.id.by);
        }
    }
}
