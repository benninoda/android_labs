package com.javalabs.tabatatimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.javalabs.tabatatimer.activities.SettingsActivity;
import com.javalabs.tabatatimer.adapters.MyTimerSequenceRecyclerViewAdapter;
import com.javalabs.tabatatimer.database.enities.Timer;
import com.javalabs.tabatatimer.helper.SimpleItemTouchHelperCallback;
import com.javalabs.tabatatimer.viewmodel.TimerViewModel;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class TimerSequenceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
//    public TimerSequenceFragment() {
//    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
//    public static TimerSequenceFragment newInstance(int columnCount) {
//        TimerSequenceFragment fragment = new TimerSequenceFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.timer_list);

        TimerViewModel timerViewModel = ViewModelProviders.of(getActivity()).get(TimerViewModel.class);

        final MyTimerSequenceRecyclerViewAdapter adapter = new MyTimerSequenceRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);

        ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                        .setTitle(getString(R.string.qsn_delete))
                        .setMessage(getString(R.string.delete_message))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.deleteItem), R.drawable.ic_delete_24, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                timerViewModel.deleteTimer(Objects.requireNonNull(timerViewModel
                                        .getAllTimers()
                                        .getValue())
                                        .get(viewHolder.getAdapterPosition()));
                                dialogInterface.dismiss();
                                adapter.notifyDataSetChanged();
                            }

                        })
                        .setNegativeButton(getString(R.string.dialog_cancel), R.drawable.ic_cancel_24, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
            }

        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);

        mItemTouchHelper.attachToRecyclerView(recyclerView);


        timerViewModel.getAllTimers().observe(getViewLifecycleOwner(), new Observer<List<Timer>>() {
            @Override
            public void onChanged(@Nullable final List<Timer> timers) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(timers);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putCharSequence("mode", "create");
            Navigation.findNavController(view1).navigate(R.id.action_timerSequenceFragment_to_editTimerFragment, bundle);
        });

        ImageButton btnToSettings = view.findViewById(R.id.to_settings_button);
        btnToSettings.setOnClickListener(view1 -> {
            Intent v = new Intent(getActivity(), SettingsActivity.class);
            getActivity().finish();
            startActivity(v);
        });


    }
}