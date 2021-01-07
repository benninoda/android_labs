package com.javalabs.tabatatimer.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.database.enities.Timer;
import com.javalabs.tabatatimer.models.TimerSequenceModel;

import java.util.ArrayList;

public class TimerDetailViewModel extends ViewModel {
    public MutableLiveData<String> currentText = new MutableLiveData<>();
    public MutableLiveData<String> previousText = new MutableLiveData<>();
    public MutableLiveData<String> nextText = new MutableLiveData<>();

    public MutableLiveData<Long> currentTickText = new MutableLiveData<>();
    TimerSequenceModel timerSequenceModel;

    public ArrayList<Integer> sequenceStages;
    public ArrayList<Integer> sequenceText;
    public MutableLiveData<ArrayList<TimerSequenceModel>> timerSequenceModels = new MutableLiveData<>();
    private ArrayList<TimerSequenceModel> tempListTimerStages;
    public int stages;
    public int currentStage;

    public long currentTimeStatic;
    public long currentTime;
    public boolean isRunning;
    public boolean isFinished;
    public boolean isInit = false;

    public TimerDetailViewModel(){
    }

    public void init(long initTime){
        this.sequenceStages = new ArrayList<>();
        this.sequenceText = new ArrayList<>();
        this.tempListTimerStages = new ArrayList<TimerSequenceModel>();
//        this.timerSequenceModel = new TimerSequenceModel();
        this.isRunning = false;
        this.isFinished = false;
        this.currentTime = initTime;
        this.currentStage = 0;
    }

    public void countStages(Timer currentTimer){
        this.stages =  (currentTimer.cycle * 2 + 1) * currentTimer.repeat + 1;
    }

    public void setSequence(Timer currentTimer){
        sequenceStages.add(currentTimer.warmup);
        sequenceText.add(R.string.warm_up_text_view);
        for (int i = 0; i < currentTimer.repeat; i++){
            for (int j = 0; j < currentTimer.cycle; j++) {
                sequenceStages.add(currentTimer.workout);
                sequenceText.add(R.string.work_out_text_view);
                sequenceStages.add(currentTimer.rest);
                sequenceText.add(R.string.rest_text_view);
            }
            sequenceStages.add(currentTimer.cooldown);
            sequenceText.add(R.string.cooldown_text_view);
        }
    }

    public void setTimerSequenceModels(){
        for (int i = 0; i < stages; i++){
            Log.e("D", "i'm here");
//            Log.e("D", "it's firdt text in training" + sequenceText.get(i));
//            Log.e("D", timerSequenceModel.getName());
            timerSequenceModel = new TimerSequenceModel(i + 1, sequenceText.get(i), sequenceStages.get(i));
            tempListTimerStages.add(timerSequenceModel);
        }
        timerSequenceModels.setValue(tempListTimerStages);
    }

}
