package com.javalabs.tabatatimer;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.javalabs.tabatatimer.database.enities.Timer;
import com.javalabs.tabatatimer.viewmodel.TimerViewModel;

import java.util.ArrayList;
import java.util.Random;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTimerFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private static String mode;
    private static int itemId;
    private Timer timer;
    String name_tmr = "";
    String color_tmr = "";
    ArrayList<String> colors;
    ImageButton btnWorkupMinus;
    ImageButton btnWorkupPlus;
    ImageButton btnWorkoutMinus;
    ImageButton btnWorkoutPlus;
    ImageButton btnRestMinus;
    ImageButton btnRestPlus;
    ImageButton btnCooldownMinus;
    ImageButton btnCooldownPlus;
    ImageButton btnCycleMinus;
    ImageButton btnCyclePlus;
    ImageButton btnRepeatMinus;
    ImageButton btnRepeatPlus;
    EditText editTextWorkup;
    EditText editTextWorkout;
    EditText editTextRest;
    EditText editTextCooldown;
    EditText editTextRepeat;
    EditText editTextCycle;
    TextView pageLabel;


    private TimerViewModel timerViewModel;

    Toolbar toolbar;
    private ImageButton btnNameEdit;
    private ImageButton btnColorEdit;
    private ImageButton btnOk;

    public EditTimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTimerFragment newInstance(String param1, String param2) {
        EditTimerFragment fragment = new EditTimerFragment();
        Bundle args = new Bundle();
        args.putString(param1, mode);
        args.putInt(param2, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getString("mode");
            itemId = getArguments().getInt("itemId");
        }
        Log.e("D", "ON CREATE EDIT FRAGMENT");
        Log.e("D", String.valueOf(itemId));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container,savedInstanceState);
        timerViewModel = ViewModelProviders.of(getActivity()).get(TimerViewModel.class);
        return inflater.inflate(R.layout.fragment_edit_timer, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNameEdit = view.findViewById(R.id.edit_name_button);
        btnNameEdit.setOnClickListener(view12 -> showInputDialog());

        btnColorEdit = view.findViewById(R.id.edit_color_button);
        btnColorEdit.setOnClickListener(view13 -> showColorPicker());

        btnOk = view.findViewById(R.id.ok_button);
        btnOk.setOnClickListener(view1 -> createNewTimer());

        setupColors();

        btnWorkupMinus = view.findViewById(R.id.workup_minus_button);
        btnWorkupPlus = view.findViewById(R.id.workup_plus_button);
        btnWorkoutMinus = view.findViewById(R.id.workout_minus_button);
        btnWorkoutPlus = view.findViewById(R.id.workout_plus_button);
        btnRestMinus = view.findViewById(R.id.rest_minus_button);
        btnRestPlus = view.findViewById(R.id.rest_plus_button);
        btnCooldownMinus = view.findViewById(R.id.cooldown_minus_button);
        btnCooldownPlus = view.findViewById(R.id.cooldown_plus_button);
        btnCycleMinus = view.findViewById(R.id.cycle_minus_button);
        btnCyclePlus = view.findViewById(R.id.cycle_plus_button);
        btnRepeatMinus = view.findViewById(R.id.repeat_minus_button);
        btnRepeatPlus = view.findViewById(R.id.repeat_plus_button);

        editTextWorkup = view.findViewById(R.id.warm_up_edit_text);
        editTextWorkout = view.findViewById(R.id.workout_edit_text);
        editTextRest = view.findViewById(R.id.rest_edit_text);
        editTextCooldown = view.findViewById(R.id.cooldown_edit_text);
        editTextCycle = view.findViewById(R.id.cycle_edit_text);
        editTextRepeat = view.findViewById(R.id.repeat_edit_text);
        pageLabel = view.findViewById(R.id.edit_fragment_label);

        btnWorkupMinus.setOnClickListener(view1 -> changeEditTextMinus(editTextWorkup));
        btnWorkupPlus.setOnClickListener(view1 -> changeEditTextPlus(editTextWorkup));
        btnWorkoutMinus.setOnClickListener(view1 -> changeEditTextMinus(editTextWorkout));
        btnWorkoutPlus.setOnClickListener(view1 -> changeEditTextPlus(editTextWorkout));
        btnRestMinus.setOnClickListener(view1 -> changeEditTextMinus(editTextRest));
        btnRestPlus.setOnClickListener(view1 -> changeEditTextPlus(editTextRest));
        btnCooldownMinus.setOnClickListener(view1 -> changeEditTextMinus(editTextCooldown));
        btnCooldownPlus.setOnClickListener(view1 -> changeEditTextPlus(editTextCooldown));
        btnCycleMinus.setOnClickListener(view1 -> changeEditTextMinus(editTextCycle));
        btnCyclePlus.setOnClickListener(view1 -> changeEditTextPlus(editTextCycle));
        btnRepeatMinus.setOnClickListener(view1 -> changeEditTextMinus(editTextRepeat));
        btnRepeatPlus.setOnClickListener(view1 -> changeEditTextPlus(editTextRepeat));

        if (mode.equals("edit")){
            timerViewModel.findById(itemId).observe(getViewLifecycleOwner(), timer1 -> {
                pageLabel.setText(getText(R.string.edit_fragment_toolbar_title));
                editTextWorkup.setText(String.valueOf(timer1.warmup));
                editTextWorkout.setText(String.valueOf(timer1.workout));
                editTextRest.setText(String.valueOf(timer1.rest));
                editTextCooldown.setText(String.valueOf(timer1.cooldown));
                editTextCycle.setText(String.valueOf(timer1.cycle));
                editTextRepeat.setText(String.valueOf(timer1.repeat));
                color_tmr = timer1.color;
                name_tmr = timer1.timer_name;
                btnOk.setOnClickListener(view1 -> {
                    updateTimer(timer1);
                });
            });

        }
    }

    protected void changeEditTextMinus(EditText editText){
        int seconds = Integer.parseInt(String.valueOf(editText.getText()));
        seconds -= 1;
        if ((editText.equals(editTextWorkout) || editText.equals(editTextCycle) ||
                editText.equals(editTextRepeat)) && seconds == 0){
            seconds = 1;
        }
        else if (seconds == -1){
            seconds = 0;
        }
        editText.setText(String.valueOf(seconds));
    }

    protected void changeEditTextPlus(EditText editText){
        int seconds = Integer.parseInt(String.valueOf(editText.getText()));
        seconds += 1;
        editText.setText(String.valueOf(seconds));
    }

    protected void showColorPicker(){
        ColorPicker colorPicker = new ColorPicker(getActivity());
        colorPicker
                .setDefaultColorButton(Color.parseColor(color_tmr))
                .setColors(colors)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                color_tmr = colors.get(position);
            }

            @Override
            public void onCancel(){

            }
        });
        colorPicker.show();
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.dialog_ok, (dialog, id) -> name_tmr = String.valueOf(editText.getText()))
                .setNegativeButton(R.string.dialog_cancel,
                        (dialog, id) -> dialog.cancel());

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void createNewTimer(){
        name_tmr = name_tmr.equals("") ? getString(R.string.newTimer) : name_tmr;
        timer = new Timer(name_tmr, color_tmr, Integer.parseInt(String.valueOf(editTextWorkup.getText())),
                Integer.parseInt(String.valueOf(editTextWorkout.getText())),
                Integer.parseInt(String.valueOf(editTextRest.getText())),
                Integer.parseInt(String.valueOf(editTextCooldown.getText())),
                Integer.parseInt(String.valueOf(editTextRepeat.getText())),
                Integer.parseInt(String.valueOf(editTextCycle.getText())));
        timerViewModel.insert(timer);
        Navigation.findNavController(requireView()).navigate(R.id.action_editTimerFragment_to_timerSequenceFragment);
    }

    protected void updateTimer(Timer timer1){
        timer1.updateAll(name_tmr, color_tmr, Integer.parseInt(String.valueOf(editTextWorkup.getText())),
                Integer.parseInt(String.valueOf(editTextWorkout.getText())),
                Integer.parseInt(String.valueOf(editTextRest.getText())),
                Integer.parseInt(String.valueOf(editTextCooldown.getText())),
                Integer.parseInt(String.valueOf(editTextRepeat.getText())),
                Integer.parseInt(String.valueOf(editTextCycle.getText())));
        timerViewModel.update(timer1);
        Navigation.findNavController(requireView()).navigate(R.id.action_editTimerFragment_to_timerSequenceFragment);
    }

    protected void setupColors(){
        colors = new ArrayList<>();
        colors.add("#82B926");
        colors.add("#a276eb");
        colors.add("#6a3ab2");
        colors.add("#666666");
        colors.add("#FFFF00");
        colors.add("#3C8D2F");
        colors.add("#FA9F00");
        colors.add("#44BDFF");
        colors.add("#FF0000");
        colors.add("#76F689");
        Random r = new Random();
        color_tmr = colors.get(r.nextInt(10));
    }
}