package com.javalabs.tabatatimer.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.database.enities.Timer;
import com.javalabs.tabatatimer.helper.ItemTouchHelperAdapter;
import com.javalabs.tabatatimer.helper.ItemTouchHelperViewHolder;
import com.javalabs.tabatatimer.viewmodel.TimerViewModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Timer).
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTimerSequenceRecyclerViewAdapter
        extends RecyclerView.Adapter<MyTimerSequenceRecyclerViewAdapter.TimerViewHolder>
        implements ItemTouchHelperAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Timer> mValues;
    TimerViewModel timerViewModel;

    public MyTimerSequenceRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        timerViewModel = ViewModelProviders.of((FragmentActivity) context).get(TimerViewModel.class);
    }

    @Override
    public TimerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_timer_item, parent, false);
        return new TimerViewHolder(itemView);
    }

    @SuppressLint({"ResourceAsColor"})
    @Override
    public void onBindViewHolder(TimerViewHolder holder, int position) {
        if (mValues != null) {
            Timer current = mValues.get(position);
            holder.timerItemView.setText(current.timer_name);
//            holder.idTimerItemView.setText(String.valueOf(current.uid));
            Log.e("D", String.valueOf(position));
            int clr = 0;
            if (current.color != null){
                clr = Color.parseColor(current.color);
            }
            else{
                clr = Color.parseColor("#0000FF");
            }
            holder.solidColorBackground.setColorFilter(clr, PorterDuff.Mode.SRC_ATOP);
            holder.btnPlay.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("timerId", current.uid);
                Navigation.findNavController(view)
                        .navigate(R.id.action_timerSequenceFragment_to_timerFragment, bundle);
            });

            holder.btnViewOptions.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(mContext, holder.btnViewOptions);
                popup.inflate(R.menu.options_menu);
                popup.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()){
                        case R.id.edit_item:
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("mode", "edit");
                            bundle.putInt("itemId", current.uid);
                            Navigation.findNavController(view).navigate(R.id.action_timerSequenceFragment_to_editTimerFragment, bundle);
                            break;
                        case R.id.read_item:
                            Dialog dialog = new Dialog(mContext);
                            dialog.setTitle(current.timer_name);
                            dialog.setContentView(R.layout.dialog_view);
                            TextView textView = dialog.findViewById(R.id.dialog_content);
                            StringBuilder content = new StringBuilder();
                            content.append(mContext.getString(R.string.repeats_read)).append(current.repeat);
                            content.append(mContext.getString(R.string.cycles_read)).append(current.cycle);
                            content.append(mContext.getString(R.string.warmup_read)).append(current.warmup);
                            content.append(mContext.getString(R.string.workout_read)).append(current.workout);
                            content.append(mContext.getString(R.string.rest_read)).append(current.rest);
                            content.append(mContext.getString(R.string.cooldown_read)).append(current.cooldown);
                            content.append(mContext.getString(R.string.all_time_read)).append(current.workout * current.cycle * current.repeat);
                            textView.setText(content);
                            dialog.show();
                            break;
                        default:
                            break;
                    }
                    return false;
                });
                popup.show();
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.timerItemView.setText("No Timer");
        }

    }

    public void setWords(List<Timer> timers){
        mValues = timers;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else return 0;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Timer prev = mValues.remove(fromPosition);
        mValues.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }


    static class TimerViewHolder extends RecyclerView.ViewHolder
        implements ItemTouchHelperViewHolder {
//        private final TextView idTimerItemView;
        private final TextView timerItemView;
        private final LinearLayout linearLayoutItem;
        private final Drawable solidColorBackground;
        private final ImageButton btnPlay;
        private final TextView btnViewOptions;

        private TimerViewHolder(View itemView) {
            super(itemView);
//            idTimerItemView = itemView.findViewById(R.id.item_number);
            timerItemView = itemView.findViewById(R.id.content);
            linearLayoutItem = itemView.findViewById(R.id.list_number);
            solidColorBackground = itemView.getBackground();
            btnPlay = itemView.findViewById(R.id.button_play);
            btnViewOptions = itemView.findViewById(R.id.textViewOptions);
        }

        @Override
        public void onItemSelected() {
            Log.e("D", "ON ITEM SELECTED");
        }

        @Override
        public void onItemClear() {
            Log.e("D", "ON ITEM CLEAR");
        }

    }
}