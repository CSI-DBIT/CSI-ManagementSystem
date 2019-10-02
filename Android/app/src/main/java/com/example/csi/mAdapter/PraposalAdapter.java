package com.example.csi.mAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.csi.R;

import java.util.ArrayList;

public class PraposalAdapter extends RecyclerView.Adapter<PraposalAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<PraposalItem> mPraposalList;
    private OnItemClickedListener mListener;
    private String AS="Approved By SBC";
    private String RS="Rejected By SBC";
    private String AH="Approved By HOD";
    private String RH="Rejected By HOD";


    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener) {
        mListener = listener;
    }

    public PraposalAdapter(Context context, ArrayList<PraposalItem> PraposalList) {
        mContext = context;
        mPraposalList = PraposalList;

    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exmple_item, viewGroup, false);

        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        PraposalItem currentItem = mPraposalList.get(i);

        String Eid = currentItem.getmEid();
        String date = currentItem.getDate();
        String Name = currentItem.getmName();
        String status = currentItem.getmStatus();
        String extra = currentItem.getmExtra();
        //Edited By Afif
        exampleViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation_recycler));

        exampleViewHolder.mTextViewAgenda.setText(Name);
        exampleViewHolder.mTextViewDate.setText(date);
        exampleViewHolder.mTextViewTime.setText(extra);
        Log.i("color status",status);
        if(status.equals("1"))
        {exampleViewHolder.mTextViewE1.setText(AS); exampleViewHolder.ll.setBackgroundColor(Color.parseColor("#4000FF00"));} //light green
        else if(status.equals("2"))
        {exampleViewHolder.mTextViewE1.setText(AH);exampleViewHolder.ll.setBackgroundColor(Color.parseColor("#8000FF00"));} //dark green
        else if(status.equals("-1"))
        {exampleViewHolder.mTextViewE1.setText(RS);exampleViewHolder.ll.setBackgroundColor(Color.parseColor("#40FF0000")); }//light red
        else if(status.equals("-2"))
        {exampleViewHolder.mTextViewE1.setText(RH);exampleViewHolder.ll.setBackgroundColor(Color.parseColor("#99FF0000"));} //dark red
        else if(status.equals("0"))
        {exampleViewHolder.mTextViewE1.setText("Latest Submitted");exampleViewHolder.ll.setBackgroundColor(Color.parseColor("#80ffffff"));} //white
        else {
            exampleViewHolder.mTextViewE1.setVisibility(View.INVISIBLE);exampleViewHolder.ll.setBackgroundColor(Color.parseColor("#80ffffff"));//white ,this one is for creative and technical recycler
        }
        exampleViewHolder.mTextViewPoints.setText(extra );
    }

    @Override
    public int getItemCount() {
        //Edited By Afif
        return mPraposalList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewAgenda, mTextViewDate, mTextViewTime, mTextViewPoints,mTextViewE1;
        LinearLayout ll;
        CardView container;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.box);
            mTextViewAgenda = itemView.findViewById(R.id.text_view_agenda);
            mTextViewDate = itemView.findViewById(R.id.text_view_date);
            mTextViewTime = itemView.findViewById(R.id.text_view_time);
            mTextViewPoints = itemView.findViewById(R.id.text_view_points);
            mTextViewE1=itemView.findViewById(R.id.text_view_creator);
            ll=itemView.findViewById(R.id.example_item_LL);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
