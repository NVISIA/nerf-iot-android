package com.nvisia.nerfremote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.nvisia.nerfremote.R;
import com.nvisia.nerfremote.model.NerfEvent;

public class NerfEventAdapter extends RecyclerView.Adapter<NerfEventAdapter.NerfEventViewHolder> {

    private static ArrayList<NerfEvent> eventList;


    public NerfEventAdapter(ArrayList<NerfEvent> aEventList) {
        eventList = aEventList;
    }

    public static class NerfEventViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.nerf_event_name)  TextView vName;
        @Bind(R.id.nerf_event_event) TextView vEvent;

        public NerfEventViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public NerfEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.nerf_event_item, parent, false);

        return new NerfEventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NerfEventViewHolder holder, int position) {
        NerfEvent ne = eventList.get(position);
        holder.vName.setText(ne.name);
        holder.vEvent.setText(ne.event);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addEvent(NerfEvent aNerfEvent) {
        eventList.add(aNerfEvent);
        notifyDataSetChanged();
    }

    public void clearData() {
        eventList.clear();
        notifyDataSetChanged();
    }
}
