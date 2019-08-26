package com.example.newdiplwm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class Tutorial extends DialogFragment implements SharedPreferences.OnSharedPreferenceChangeListener, MediaPlayer.OnCompletionListener {

    private Context context;
    private int resId , position;
    private VideoView videoView;
    private String packageName;
    private CheckBox checkBox;
    private Session session;
    private View content;

    public Tutorial() { }

    public Tutorial(Context context, int resId, String packageName) {

        this.context = context;
        this.resId = resId;
        this.packageName = packageName;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        session = new Session(getActivity().getApplicationContext());
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = requireActivity().getLayoutInflater();
         content = factory.inflate(R.layout.custom_tutorial_popup, null);
        videoView = (VideoView) content.findViewById(R.id.videoview);
        checkBox = (CheckBox) content.findViewById(R.id.notAgain);

        fillViews();
        builder.setView(content)
                .setPositiveButton("Κατάλαβα", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        getDialog().dismiss();

                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d("ONSTOPPPPPP","ONSTOPPPPP");
        position = videoView.getCurrentPosition();
        videoView.stopPlayback();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume","onResume");
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
//       // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        videoView.setOnCompletionListener(this);

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        boolean y = checkBox.isChecked();
        session.setPlayAgainVideo(y);
        videoView.stopPlayback();
    }



//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d("ONDESTROYVIEW","ONDESTROYVIEW");
//
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("ONDESTROYT","ONDESTROY");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.d("ONDTACHHHHH","ONDTACHHHHH");
//
//
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    private void fillViews() {
        Uri uri = Uri.parse("android.resource://"+packageName+"/"+resId);
        videoView.setVideoURI(uri);
        videoView.start();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("testing"))
        {
            session.setPlayAgainVideo(sharedPreferences.getBoolean("testing",false));
        }
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        getDialog().dismiss();
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("RESID",resId);
//        outState.putInt("RESID",position);
//    }
}
