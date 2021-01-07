package com.javalabs.tabatatimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.javalabs.tabatatimer.adapters.MySimpleRecycleViewAdapter;
import com.javalabs.tabatatimer.database.enities.Timer;
import com.javalabs.tabatatimer.utils.IVibration;
import com.javalabs.tabatatimer.utils.SimpleVibration;
import com.javalabs.tabatatimer.utils.VibrationBuild;
import com.javalabs.tabatatimer.viewmodel.TimerDetailViewModel;
import com.javalabs.tabatatimer.viewmodel.TimerViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static int itemId;
    TextView tvTimer;
    TextView currentTextTextView;
    Timer currentTimer;
    TimerDetailViewModel timerDetailViewModel;
    ImageButton btnStartTimer;
    ImageButton btnPrevTimer;
    ImageButton btnNextTimer;
    CountDownTimer countDownTimer;
    IVibration iVibration;
    Vibrator vibrator;

    SoundPool sp;

    public TimerFragment() {
        // Required empty public constructor

    }

    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(String param1) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putInt(param1, itemId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getInt("timerId");
        }
        timerDetailViewModel = ViewModelProviders.of(getActivity()).get(TimerDetailViewModel.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            iVibration = new VibrationBuild();
        } else {
            iVibration = new SimpleVibration();
        }
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment

        TimerViewModel timerViewModel = ViewModelProviders.of(getActivity()).get(TimerViewModel.class);
        timerViewModel.findById(itemId).observe(getViewLifecycleOwner(), timer -> {
            currentTimer = timer;
            initValues();
        });
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.e("D", "OnViewCreated");
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        sp.load(getContext(), R.raw.sound, 1);
        tvTimer = view.findViewById(R.id.tv);
        currentTextTextView = view.findViewById(R.id.current_text_text_view);
        btnStartTimer = view.findViewById(R.id.start_timer_button);
        btnStartTimer.setOnClickListener(view1 -> {
            if (!timerDetailViewModel.isInit){
                timerDetailViewModel.init(currentTimer.warmup);
                timerDetailViewModel.countStages(currentTimer);
                timerDetailViewModel.setSequence(currentTimer);
                currentTextTextView.setText(getString(timerDetailViewModel
                        .sequenceText.get(timerDetailViewModel.currentStage)));
                timerDetailViewModel.isInit = true;
            }
            if (timerDetailViewModel.isRunning) {
                timerDetailViewModel.isRunning = false;
                countDownTimer.cancel();
                timerDetailViewModel.currentTime = timerDetailViewModel.currentTickText.getValue() + 1;
                btnStartTimer.setImageResource(R.drawable.ic_play_72);
            }
            else {
                timerDetailViewModel.isRunning = true;
                startTimer(view);
                btnStartTimer.setImageResource(R.drawable.ic_pause_72);
            }
        });

        btnPrevTimer = view.findViewById(R.id.to_previous_stage);
        btnPrevTimer.setOnClickListener(view1 -> {
            if (timerDetailViewModel.currentStage != 0) {
                if (timerDetailViewModel.isRunning)
                    countDownTimer.cancel();
                timerDetailViewModel.currentStage -= 1;
                timerDetailViewModel.currentTime = timerDetailViewModel
                        .sequenceStages
                        .get(timerDetailViewModel.currentStage);
                timerDetailViewModel.currentText.setValue(getString(timerDetailViewModel
                        .sequenceText
                        .get(timerDetailViewModel.currentStage)));
                currentTextTextView.setText(timerDetailViewModel.currentText.getValue());
                tvTimer.setText(String.valueOf(timerDetailViewModel.currentTime));
                if (timerDetailViewModel.isRunning)
                    startTimer(view);
            }

        });

        btnNextTimer = view.findViewById(R.id.to_next_stage);
        btnNextTimer.setOnClickListener(view1 -> {
            if (timerDetailViewModel.currentStage < timerDetailViewModel.stages - 1) {
                if (timerDetailViewModel.isRunning)
                    countDownTimer.cancel();
                timerDetailViewModel.currentStage += 1;
                timerDetailViewModel.currentTime = timerDetailViewModel
                        .sequenceStages
                        .get(timerDetailViewModel.currentStage);
                timerDetailViewModel.currentText.setValue(getString(timerDetailViewModel
                        .sequenceText
                        .get(timerDetailViewModel.currentStage)));
                currentTextTextView.setText(timerDetailViewModel.currentText.getValue());
                tvTimer.setText(String.valueOf(timerDetailViewModel.currentTime));
                if (timerDetailViewModel.isRunning)
                    startTimer(view);
            }
        });



        RecyclerView recyclerView = view.findViewById(R.id.sequence_list);

        final MySimpleRecycleViewAdapter adapter = new MySimpleRecycleViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timerDetailViewModel.timerSequenceModels.observe(getViewLifecycleOwner(), adapter::setTimers);
    }

    protected void initValues(){
        timerDetailViewModel.init(currentTimer.warmup);
        timerDetailViewModel.countStages(currentTimer);
        timerDetailViewModel.setSequence(currentTimer);
        timerDetailViewModel.setTimerSequenceModels();
        currentTextTextView.setText(getString(timerDetailViewModel.sequenceText.get(timerDetailViewModel.currentStage)));
        tvTimer.setText(String.valueOf(timerDetailViewModel.sequenceStages.get(timerDetailViewModel.currentStage)));
        timerDetailViewModel.isInit = true;
    }

    private void startTimer(View view) {

        countDownTimer = new CountDownTimer(timerDetailViewModel.currentTime * 1000, 1000) {

            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                timerDetailViewModel.currentTickText.setValue(millisUntilFinished / 1000);
                if (millisUntilFinished / 1000 == 2 || millisUntilFinished / 1000 == 1 ||
                        millisUntilFinished / 1000 == 0)
                    iVibration.vibro(vibrator);
                tvTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                sp.play(1, 1.0F, 1.0F, 1, 0, 1.0F);
                timerDetailViewModel.currentStage += 1;
                if (timerDetailViewModel.currentStage < 0) {
                    timerDetailViewModel.currentStage = 0;
                }
                if (timerDetailViewModel.currentStage == timerDetailViewModel.stages) {
                    timerDetailViewModel.currentText.setValue((String) getText(R.string.complete));
                    currentTextTextView.setText(timerDetailViewModel.currentText.getValue());
                    btnStartTimer.setImageResource(R.drawable.ic_replay_72);
                    timerDetailViewModel.isFinished = true;
                    timerDetailViewModel.isInit = false;
                } else {
                    timerDetailViewModel.currentText.setValue(getString(timerDetailViewModel
                            .sequenceText
                            .get(timerDetailViewModel.currentStage)));
                    if (timerDetailViewModel.currentStage == timerDetailViewModel.stages - 1) {
                        timerDetailViewModel.nextText.setValue("");
                    }
                    else {
                        timerDetailViewModel.nextText
                                .setValue(getString(timerDetailViewModel
                                        .sequenceText.get(timerDetailViewModel.currentStage + 1)));
                    }
                    if (timerDetailViewModel.currentStage == 0) {
                        timerDetailViewModel.previousText.setValue("");
                    }
                    else {
                        timerDetailViewModel.previousText.setValue(getString(timerDetailViewModel
                                .sequenceText
                                .get(timerDetailViewModel.currentStage - 1)));
                    }
                    timerDetailViewModel.currentTime = (long) timerDetailViewModel.sequenceStages.get(timerDetailViewModel.currentStage);
                    currentTextTextView.setText(timerDetailViewModel.currentText.getValue());
                    startTimer(view);
                }
            }
        }.start();

    }
}