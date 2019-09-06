package com.example.csi.mAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.csi.R;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private ArrayList<ExampleItem> mExampleListFiltered;
    private OnItemClickedListener mListener;
    //Afif's Code
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key =  constraint.toString();
                if(key.isEmpty()){
                    mExampleListFiltered = mExampleList;
                }
                else {
                    ArrayList<ExampleItem> lstFiltered = new ArrayList<>();
                    for (ExampleItem row  : mExampleList){
                        if (row.getAgenda().toLowerCase().contains(key.toLowerCase()) || row.getDate().toLowerCase().contains(key.toLowerCase())|| row.getTime().toLowerCase().contains(key.toLowerCase())|| row.getCreator().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    mExampleListFiltered = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values= mExampleListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mExampleListFiltered = (ArrayList<ExampleItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    //End's Here hello
    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener) {
        mListener = listener;
    }

    public ExampleAdapter(Context context, ArrayList<ExampleItem> ExampleList) {
        mContext = context;
        mExampleList = ExampleList;
        this.mExampleListFiltered = mExampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exmple_item, viewGroup, false);

        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        ExampleItem currentItem = mExampleList.get(i);

        String agenda = currentItem.getAgenda();
        String date = currentItem.getDate();
        String time = currentItem.getTime();
        String creator = currentItem.getCreator();
        String points = currentItem.getPoints();
        //Edited By Afif
        exampleViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation_recycler));

        exampleViewHolder.mTextViewAgenda.setText("Agenda: " + mExampleListFiltered.get(i).getAgenda());
        exampleViewHolder.mTextViewDate.setText("Date: " + mExampleListFiltered.get(i).getDate());
        exampleViewHolder.mTextViewTime.setText("Time: " + mExampleListFiltered.get(i).getTime());
        exampleViewHolder.mTextViewCreator.setText("Creator: " + mExampleListFiltered.get(i).getCreator());
        exampleViewHolder.mTextViewPoints.setText("Points: " + points);
    }

    @Override
    public int getItemCount() {
        //Edited By Afif
        return mExampleListFiltered.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewAgenda, mTextViewDate, mTextViewTime, mTextViewCreator, mTextViewPoints;
        CardView container;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.box);
            mTextViewAgenda = itemView.findViewById(R.id.text_view_agenda);
            mTextViewDate = itemView.findViewById(R.id.text_view_date);
            mTextViewTime = itemView.findViewById(R.id.text_view_time);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewPoints = itemView.findViewById(R.id.text_view_points);

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
