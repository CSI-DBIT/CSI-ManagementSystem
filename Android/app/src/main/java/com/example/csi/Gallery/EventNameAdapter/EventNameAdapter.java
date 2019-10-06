package com.example.csi.Gallery.EventNameAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.csi.R;

import java.util.ArrayList;

public class EventNameAdapter extends RecyclerView.Adapter<EventNameAdapter.EventViewHolder> {

    private Context mContext;
    private ArrayList<EventItem> mEventList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public EventNameAdapter(Context context, ArrayList<EventItem> eventList) {
        mContext = context;
        mEventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_item, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int position) {
        EventItem currentItem = mEventList.get(position);

        String eventName = currentItem.getEventName();

        eventViewHolder.mEventName.setText(eventName);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView mEventName;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            mEventName = itemView.findViewById(R.id.event_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
