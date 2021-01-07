package com.javalabs.tabatatimer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.models.TimerSequenceModel;

import java.util.List;

public class MySimpleRecycleViewAdapter
        extends RecyclerView.Adapter<MySimpleRecycleViewAdapter.SimpleViewHolder>{

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<TimerSequenceModel> mValues;

    public MySimpleRecycleViewAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.timer_item, parent, false);
        return new MySimpleRecycleViewAdapter.SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        if (mValues != null){
            TimerSequenceModel current = mValues.get(position);
            holder.textView.setText(String.format("%d. %s: %d", current.getId(), mContext.getString(current.getName()), current.getStageTime()));
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else return 0;
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        private SimpleViewHolder(View itemView){
            super(itemView);
            textView =  itemView.findViewById(R.id.sequence_item_label);
        }
    }

    public void setTimers(List<TimerSequenceModel> timers){
        mValues = timers;
        notifyDataSetChanged();
    }
}
