package com.example.csi.mReqAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.example.csi.R;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestListViewHolder> {

    private Context mContext;
    private ArrayList<RequestListItem> mRequestList;
    public ArrayList<RequestListItem> mConfirmRequestList = new ArrayList<>(); //now;
    public ArrayList<RequestListItem> mRejectRequestList = new ArrayList<>(); //now2;

    public RequestListAdapter(Context context, ArrayList<RequestListItem> requestList) {
        mContext = context;
        mRequestList = requestList;
    }

    @Override
    public RequestListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.request_list_item, parent, false);
        return new RequestListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RequestListViewHolder holder, final int position) {
        RequestListItem currentItem = mRequestList.get(position);

        String requestID = currentItem.getRequestID();
        String name = currentItem.getName();
        String date = currentItem.getDate();
        String timeSlots = currentItem.getTimeSlots();
        String reason = currentItem.getReason();
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation_recycler));
        holder.mRequestID.setText("Request ID: "+ requestID);
        holder.mName.setText("Name: "+ name);
        holder.mDate.setText("Date: " + date);
        holder.mTimeSlots.setText("Time Slots: " + timeSlots);
        holder.mReason.setText("Reason: " + reason);

        holder.mAcceptCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if(holder.mRejectCheck.isChecked()) {
                        holder.mRejectCheck.setChecked(false);
                        mRejectRequestList.remove(mRequestList.get(position));
                    }

                    mConfirmRequestList.add(mRequestList.get(position));

                } else {

                    mConfirmRequestList.remove(mRequestList.get(position));
                }

            }
        });

        holder.mRejectCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if(holder.mAcceptCheck.isChecked()) {
                        holder.mAcceptCheck.setChecked(false);
                        mConfirmRequestList.remove(mRequestList.get(position));
                    }

                    mRejectRequestList.add(mRequestList.get(position));

                } else {

                    mRejectRequestList.remove(mRequestList.get(position));
                }

            }
        });

        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chkAccept = (CheckBox) v.findViewById(R.id.chk_accept);
                CheckBox chkReject = (CheckBox) v.findViewById(R.id.chk_reject);

                if (chkAccept.isChecked())
                {
                    mConfirmRequestList.add(mRequestList.get(pos));
                }
                else if(!chkAccept.isChecked())
                {
                    mConfirmRequestList.remove(mRequestList.get(pos));
                }
                /*if (chkReject.isChecked())
                {
                    mRejectRequestList.add(mRequestList.get(pos));
                }
                else if(!chkReject.isChecked())
                {
                    mRejectRequestList.remove(mRequestList.get(pos));
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mRequestList.size();
    }

    public class RequestListViewHolder extends RecyclerView.ViewHolder{
        public TextView mRequestID;
        public TextView mName;
        public TextView mDate;
        public TextView mTimeSlots;
        public TextView mReason;
        public CheckBox mAcceptCheck;
        public CheckBox mRejectCheck;
        CardView container;
        ItemClickListener itemClickListener;

        public RequestListViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.box);
            mRequestID = itemView.findViewById(R.id.req_id);
            mName = itemView.findViewById(R.id.stud_name);
            mDate = itemView.findViewById(R.id.request_date);
            mTimeSlots = itemView.findViewById(R.id.request_slots);
            mReason = itemView.findViewById(R.id.reason);
            mAcceptCheck = itemView.findViewById(R.id.chk_accept);
            mRejectCheck = itemView.findViewById(R.id.chk_reject);

        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener = ic;
        }

//        @Override
//        public void onClick(View v) {
//            this.itemClickListener.onItemClick(v, getLayoutPosition());
//        }
    }
}
