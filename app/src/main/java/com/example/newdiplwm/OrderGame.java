package com.example.newdiplwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrderGame extends AppCompatActivity implements View.OnClickListener{

    private static final String MISSPOINTS = "MISSPOINTS";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String TOTALPOINTS = "TOTALPOINTS";
    private static final String TRUECOUNTER = "TRUECOUNTER";
    private static final String TOTALSPEED = "TOTALSPEED";
    private static final String STARTTIME = "STARTTIME";
    private static final String ENDTIME = "ENDTIME";
    private static final String STARTSPEED = "STARTSPEED";
    private static final String ENDSPEED = "ENDSPEED";
    private static final String CURRENTROUND = "CURRENTROUND";
    private static final String CURRENTDIFFICULTY = "CURRENTDIFFICULTY";
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String MATCH = "MATCH";
    private static final String IMAGEVIEWS = "IMAGEVIEWS";
    private static final String CLICK = "CLICK";
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
    private static final String PICKEDIMAGES = "PICKEDIMAGES";
    private static final String LISTSELECTION = "LISTSELECTION";
    private static final String RIGHTPICK = "RIGHTPICK";
    private static final String CASEMISSOBJ = "CASEMISSOBJ";
    private static final String FAlSEPICK = "FAlSEPICK";
    private static final String CLEANLIST = "CLEANLIST";
    private static final String ELECTRONICSLIST = "ELECTRONICSLIST";
    private static final String SWEETSLIST = "SWEETSLIST";
    private static final String FRUITSlIST = "FRUITSlIST";
    private static final String JANKFOODLIST = "JANKFOODLIST";
    private static final String CLOCK = "CLOCK";

    private static  final int THRESHOLD_EASY = 120;
    private static  final int THRESHOLD_ALL = 180;

    private ImageView imagebutton1, imagebutton2, imagebutton3, imagebutton4, imagebutton5 ,exit ,replayTutorial;
    private MaterialButton startButton, missingObj;
    private TextView textRounds, textTimer, animPointsText, textMsg, textMsgTime;
    private LinearLayout logoLinear, textsLinear;

    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;

    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";

    private ArrayList<Integer> cleanList = new ArrayList<>();
    private ArrayList<Integer> electonicsList = new ArrayList<>();
    private ArrayList<Integer> sweetsList = new ArrayList<>();
    private ArrayList<Integer> fruitsList = new ArrayList<>();
    private ArrayList<Integer> jankfoodList = new ArrayList<>();

    private ArrayList<Integer> pickedImages = new ArrayList<>();

    private SparseArray listselection = new SparseArray(5);
    private CountDownTimer Timer , nextRoundTimer;
    private static final long START_TIME_IN_MILLIS = 2000;
    private long mTimeLeftInMillis = 0, timeLeftInMillisNextRound =0;

    private SparseIntArray imageIDS =new SparseIntArray(5);
    private ArrayList<Integer> imageviews = new ArrayList<Integer>();

    private HashMap<String,Integer> pointsHashMap = new HashMap<String, Integer>();
    private int user_id, game_id, currentRound=0 , TotalRounds=0;
    private int hit = 0 , miss = 0 , totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;
    private String menuDifficulty,currentDifficulty;

    private int click=0;
    private int rightpick=0;
    private int caseMissingObj = 0;

    private  boolean falsepick =false;

    private Vibrator vibe;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    private ArrayList<Integer> helperwhenRotate = new ArrayList<>();
    private static final String HELPERWHENROTATE = "HELPERWHENROTATE";
    private static final String RANDLIST = "RANDLIST";
    private static final String GAMEINIT = "GAMEINIT";
    private int randlist;
    private boolean gameInit =false;

    private Session  session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        session = new Session(getApplicationContext());
        assignAllButtons();

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        if (savedInstanceState != null)
        {

            gameInit = savedInstanceState.getBoolean(GAMEINIT);
            startButton.setVisibility(View.INVISIBLE);
            user_id = savedInstanceState.getInt(USER_ID);
            game_id = savedInstanceState.getInt(GAME_ID);
            imageIDS = (SparseIntArray) savedInstanceState.getParcelable(MATCH);
            imageviews = savedInstanceState.getIntegerArrayList(IMAGEVIEWS);
            pickedImages = savedInstanceState.getIntegerArrayList(PICKEDIMAGES);
            cleanList = savedInstanceState.getIntegerArrayList(CLEANLIST);
            electonicsList = savedInstanceState.getIntegerArrayList(ELECTRONICSLIST);
            sweetsList = savedInstanceState.getIntegerArrayList(SWEETSLIST);
            fruitsList = savedInstanceState.getIntegerArrayList(FRUITSlIST);
            jankfoodList = savedInstanceState.getIntegerArrayList(JANKFOODLIST);
            helperwhenRotate = savedInstanceState.getIntegerArrayList(HELPERWHENROTATE);
            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
            mTimeLeftInMillis = savedInstanceState.getLong(CLOCK);
            randlist = savedInstanceState.getInt(RANDLIST);
            menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
            TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
            click =savedInstanceState.getInt(CLICK);
            caseMissingObj =savedInstanceState.getInt(CASEMISSOBJ);
            rightpick =savedInstanceState.getInt(RIGHTPICK);
            hit =savedInstanceState.getInt(HIT);
            miss =savedInstanceState.getInt(MISS);
            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
            totalspeed = savedInstanceState.getDouble(TOTALSPEED);
            missPoints = savedInstanceState.getBoolean(MISSPOINTS);
            falsepick = savedInstanceState.getBoolean(FAlSEPICK);
            startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
            endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
            startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
            endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
            currentRound = savedInstanceState.getInt(CURRENTROUND);
            matchlists();
            if (gameInit){
                logoLinear.setVisibility(View.GONE);
                textRounds.setText(currentRound+"/"+TotalRounds);
                Timer = userViewModel.getTimer();
                if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                {

                    if (mTimeLeftInMillis>1000)
                    {
                        unclickable();
                        Timer.cancel();
                        for (int i = 0; i<pickedImages.size();i++)
                        {
                            ImageView iv = findViewById(imageviews.get(i));
                            iv.setImageResource(pickedImages.get(i));
                        }
                        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                            @Override
                            public void onTick(long l) {
                                mTimeLeftInMillis=l;
                                textTimer.setText("Απομένουν "+ mTimeLeftInMillis/1000+" δευτερόλεπτα");
                            }

                            @Override
                            public void onFinish() {
                                textTimer.setText(getResources().getString(R.string.textQuestionOG));
                                mTimeLeftInMillis=0;
                                setTimerobjAdv((ArrayList<Integer>) listselection.get(randlist));

                            }
                        }.start();
                        userViewModel.saveTimer(Timer);
                    }
                    else
                    {
                        textTimer.setText(getResources().getString(R.string.textQuestionOG));

                        for (int i=0 ; i<imageIDS.size();i++)
                        {
                            ImageView iv= findViewById(imageIDS.keyAt(i));
                            iv.setImageResource(imageIDS.valueAt(i));
                        }

                        missingObj.setVisibility(View.VISIBLE);

                        if (!helperwhenRotate.isEmpty())
                        {
                            for (int imgv :helperwhenRotate)
                            {
                                ImageView iv = findViewById(imgv);
                                iv.setClickable(false);
                                iv.setColorFilter(Color.GREEN,PorterDuff.Mode.LIGHTEN);
                            }

                        }
                    }

                }
                else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                {

                    if (mTimeLeftInMillis>1000)
                    {
                        unclickable();
                        Timer.cancel();
                        imagebutton1.setImageResource(pickedImages.get(0));
                        imagebutton2.setImageResource(pickedImages.get(1));
                        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                            @Override
                            public void onTick(long l) {
                                mTimeLeftInMillis=l;
                                textTimer.setText("Απομένουν "+ mTimeLeftInMillis/1000+" δευτερόλεπτα");
                            }

                            @Override
                            public void onFinish() {
                                mTimeLeftInMillis=0;
                                textTimer.setText(getResources().getString(R.string.textQuestionOG));
                                setTimerobjmed((ArrayList<Integer>) listselection.get(randlist));

                            }
                        }.start();
                        userViewModel.saveTimer(Timer);
                    }
                    else
                    {
                        textTimer.setText(getResources().getString(R.string.textQuestionOG));

                        for (int i=0 ; i<imageIDS.size();i++)
                        {
                            ImageView iv= findViewById(imageIDS.keyAt(i));
                            iv.setImageResource(imageIDS.valueAt(i));
                        }

                        if (!helperwhenRotate.isEmpty())
                        {
                            for (int imgv :helperwhenRotate)
                            {
                                ImageView iv = findViewById(imgv);
                                iv.setClickable(false);
                                iv.setColorFilter(Color.GREEN,PorterDuff.Mode.LIGHTEN);
                            }

                        }
                    }
                }
                else
                {
                    if (mTimeLeftInMillis>1000)
                    {
                        Timer.cancel();
                        unclickable();
                        imagebutton2.setImageResource(pickedImages.get(0));
                        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                            @Override
                            public void onTick(long l) {
                                mTimeLeftInMillis=l;
                                textTimer.setText("Απομένουν "+ mTimeLeftInMillis/1000+" δευτερόλεπτα");
                            }

                            @Override
                            public void onFinish() {
                                mTimeLeftInMillis=0;
                                textTimer.setText(getResources().getString(R.string.textQuestionOG));
                                setTimerobjez((ArrayList<Integer>) listselection.get(randlist));

                            }
                        }.start();
                        userViewModel.saveTimer(Timer);
                    }
                    else
                    {
                        textTimer.setText(getResources().getString(R.string.textQuestionOG));
                        for (int i=0 ; i<imageIDS.size();i++)
                        {
                            ImageView iv= findViewById(imageIDS.keyAt(i));
                            iv.setImageResource(imageIDS.valueAt(i));
                        }

                    }

                }
            }
            else
            {
                if (currentRound == 0) {
                    startButton.setVisibility(View.VISIBLE);
                    logoLinear.setVisibility(View.VISIBLE);
                    unclickable();
                }
                else
                {
                    unclickable();
                    textRounds.setText(currentRound+"/"+TotalRounds);
                    startButton.setText(getResources().getString(R.string.nextRound));
                    startButton.setVisibility(View.VISIBLE);
                }
            }
        }
        else{

            user_id = session.getUserIdSession();
            game_id = session.getGameIdSession();
            menuDifficulty = session.getModeSession();
            matchlists();
            initialiseLists();
            fillListImageview();
            unclickable();
        }


        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);





        if (!session.getPlayAgainVideo() && currentRound == 0) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialOrderGame");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightpick=0;
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialOrderGame");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }
                logoLinear.setVisibility(View.GONE);
                gameInit = true;
                createRound();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onbackAndExitCode();
            }
        });

        replayTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTutorialPopUp();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        onbackAndExitCode();
    }

    private void onbackAndExitCode(){
        if (Timer != null)
        {
            Timer.cancel();
        }
        if (nextRoundTimer != null)
        {
            nextRoundTimer.cancel();
        }
        if (currentRound == 0 || click ==0 )
        {
            startTime = new Timestamp(System.currentTimeMillis());
            endTime = new Timestamp(System.currentTimeMillis());
            GameEvent gameEvent = new GameEvent(game_id, user_id, 0, 0, 1, 0, 0, 0, 0, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }
        else
        {
            if (startspeed == null || endspeed==null)
            {
                totalspeed +=0;
            }
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            if (totalPlayInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
            {
                totalPlayInSeconds = THRESHOLD_EASY;
            }
            else if (totalPlayInSeconds > THRESHOLD_ALL)
            {
                totalPlayInSeconds = THRESHOLD_ALL;
            }
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss , 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }
    }

    private void createRound(){
        startButton.setVisibility(View.INVISIBLE);

        Random rand = new Random();
        randlist = rand.nextInt(listselection.size());

        ArrayList<Integer> randomPickedLis = (ArrayList<Integer>) listselection.get(randlist);
        Collections.shuffle(randomPickedLis);

        if (currentRound == 0)
        {
            startTime = new Timestamp(System.currentTimeMillis());
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))){
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameEz(randomPickedLis);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMedium(randomPickedLis);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds=3;
            displayGameAdv(randomPickedLis);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
        {
            TotalRounds = 4;

            if (currentRound<=1){
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz(randomPickedLis);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium(randomPickedLis);
            }
        }
        else
        {
            TotalRounds =5;
            if (currentRound<1)
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz(randomPickedLis);
            }
            else if (currentRound>=1 && currentRound<=2)
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium(randomPickedLis);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv(randomPickedLis);
            }
        }
        currentRound++;

        textRounds.setText(currentRound+"/"+TotalRounds);

    }

    private void displayGameEz(final ArrayList<Integer> randomPickedList)
    {
        unclickable();
        Random rand = new Random();
        int randpickImagefromList = rand.nextInt(3);

        int pickedImage = randomPickedList.get(randpickImagefromList);

        imagebutton2.setImageResource(pickedImage);

        pickedImages.add(pickedImage);

        mTimeLeftInMillis = 4000;

        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                textTimer.setText("Απομένουν "+ mTimeLeftInMillis/1000+" δευτερόλεπτα");
            }

            @Override
            public void onFinish() {
                enableReplayTut();
                textTimer.setText(getResources().getString(R.string.textQuestionOG));
                mTimeLeftInMillis=0;
                setTimerobjez(randomPickedList);

            }
        }.start();
        disableReplayTut();
        userViewModel.saveTimer(Timer);

    }

    private void setTimerobjez(ArrayList<Integer> randomPickedList){
        imagebutton2.setImageResource(0);

        for (int i=0;i<3;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(randomPickedList.get(i));
            imageIDS.put(imageviews.get(i),randomPickedList.get(i));


        }
        clickable();
        startspeed = new Timestamp(System.currentTimeMillis());

    }


    private void displayGameMedium(final ArrayList<Integer> randomPickedList){
        unclickable();
        Random rand = new Random();
        int randpick1 = rand.nextInt(2);
        int randpick2 = rand.nextInt(2)+2;
        int picked1  = randomPickedList.get(randpick1);
        int picked2  = randomPickedList.get(randpick2);
        imagebutton1.setImageResource(picked1);
        imagebutton2.setImageResource(picked2);

        pickedImages.add(randomPickedList.get(randpick1));
        pickedImages.add(randomPickedList.get(randpick2));
        mTimeLeftInMillis = 6000;


        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                textTimer.setText("Απομένουν "+ mTimeLeftInMillis/1000+" δευτερόλεπτα");
            }

            @Override
            public void onFinish() {
                enableReplayTut();
                mTimeLeftInMillis=0;

                textTimer.setText(getResources().getString(R.string.textQuestionOG));
                setTimerobjmed(randomPickedList);
            }
        }.start();
        disableReplayTut();
        userViewModel.saveTimer(Timer);
    }

    private void setTimerobjmed(ArrayList<Integer> randomPickedList)
    {
        imagebutton1.setImageResource(0);
        imagebutton2.setImageResource(0);

        for (int i=0;i<4;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(randomPickedList.get(i));
            imageIDS.put(imageviews.get(i),randomPickedList.get(i));
        }
        clickable();
        startspeed = new Timestamp(System.currentTimeMillis());
    }

    private void displayGameAdv(final ArrayList<Integer> randomPickedList){

        unclickable();
        Random rand = new Random();
        caseMissingObj = rand.nextInt(2);
        ArrayList<Integer> randomNums = new ArrayList<Integer>();
        for (int i = 0 ; i<5; i++)
        {
            randomNums.add(i);

        }
        Collections.shuffle(randomNums);

        for (int i=0;i<3;i++)
        {
            ImageView v = findViewById(imageviews.get(i));

            v.setImageResource(randomPickedList.get(randomNums.get(i)));
            pickedImages.add(randomPickedList.get(randomNums.get(i)));
        }

        if (caseMissingObj == 1) {
            Collections.shuffle(pickedImages);
            ListIterator  itr = randomPickedList.listIterator();
            while (itr.hasNext())
            {
                int temp = (int) itr.next();
                if (temp == pickedImages.get(0))
                {
                    itr.remove();
                    break;
                }
            }
        }
        mTimeLeftInMillis= 9000;

        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                textTimer.setText("Απομένουν "+ mTimeLeftInMillis/1000+" δευτερόλεπτα");
            }

            @Override
            public void onFinish() {
                enableReplayTut();
                mTimeLeftInMillis=0;
                textTimer.setText(getResources().getString(R.string.textQuestionOG));

                setTimerobjAdv(randomPickedList);

            }
        }.start();
        disableReplayTut();
        userViewModel.saveTimer(Timer);

    }
    private void setTimerobjAdv(ArrayList<Integer> randomPickedList){

        for (int i=0;i<3;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(0);
        }

        for (int i=0;i<5;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(randomPickedList.get(i));
            imageIDS.put(imageviews.get(i),randomPickedList.get(i));
        }
        clickable();
        startspeed = new Timestamp(System.currentTimeMillis());
        missingObj.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View view) {
        Timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                for (int imageviewId : imageviews) {
                    ImageView v = findViewById(imageviewId);
                    v.setImageResource(0);
                    v.setColorFilter(0);
                }
                Timer = null;
                mTimeLeftInMillis=0;
                falsepick = false;
                textTimer.setText("");
                helperwhenRotate.clear();
                imageIDS.clear();
                pickedImages.clear();
//                startButton.setText(getResources().getString(R.string.nextRound));
//                startButton.setVisibility(View.VISIBLE);
                missingObj.setVisibility(View.INVISIBLE);
                missingObj.setText(getResources().getString(R.string.MissingObjects));


                if (currentRound == TotalRounds)
                {
                    startButton.setVisibility(View.INVISIBLE);
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    if (totalPlayInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                    {
                        totalPlayInSeconds = THRESHOLD_EASY;
                    }
                    else if (totalPlayInSeconds > THRESHOLD_ALL)
                    {
                        totalPlayInSeconds = THRESHOLD_ALL;
                    }
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/click,totalPlayInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();
                }
                gameInit = false;
            }
        };

        click++;
        int vibeduration = 1000;

        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        if (missingObj.getId() == view.getId() && caseMissingObj == 1)
        {
            unclickable();
            startAnimation();
            MaterialButton iv = (MaterialButton)findViewById(view.getId());
            iv.setText("Σωστά");
            hit++;
            trueCounter++;
            countPoints();
            Timer.start();
            nextRound();
        }
        else if (!(imageIDS.indexOfKey(view.getId())<0))
        {

            if (pickedImages.contains(imageIDS.get(view.getId())))
            {
                view.setClickable(false);
                rightpick++;
                ImageView iv = (ImageView)findViewById(view.getId());
                iv.setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);
                helperwhenRotate.add(view.getId());

            }
            else
            {
                falsepick = true;
            }


            if (rightpick == pickedImages.size())
            {
                unclickable();
                startAnimation();
                hit++;
                trueCounter++;
                countPoints();
                Timer.start();
                nextRound();
            }
            else if (falsepick)
            {
                unclickable();
                startAnimation();
                ImageView iv = (ImageView)findViewById(view.getId());
                iv.setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
                Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
                view.startAnimation(animShake);
                vibe.vibrate(vibeduration);
                miss++;
                missPoints = true;
                countPoints();
                Timer.start();
                nextRound();
            }

        }
        else
        {
            unclickable();
            startAnimation();
            MaterialButton iv = (MaterialButton)findViewById(view.getId());
            iv.setText("Λαθος");
            Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            view.startAnimation(animShake);
            vibe.vibrate(vibeduration);
            miss++;
            missPoints = true;
            countPoints();
            Timer.start();
            nextRound();

        }
    }


    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);
        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
        disableReplayTut();

        nextRoundTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillisNextRound = l;

                if (currentRound == TotalRounds)
                {
                    textMsgTime.setText("");
                }
                else
                {
                    textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                }


            }

            @Override
            public void onFinish() {

                timeLeftInMillisNextRound = 0;

                if (currentRound == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                    disableReplayTut();
                }
                else
                {
                    enableReplayTut();
                    nextRoundTimer = null;
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    rightpick=0;
                    gameInit = true;
                    createRound();
                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }

    private void enableReplayTut(){
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }
    private void disableReplayTut(){
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(OrderGame.this,R.raw.tutorial_ordergame,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialOrderGame");
    }


    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,OrderGame.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "OrderGame");
    }

    private void countPoints() {

        int currentPoints = 0;

        if (!missPoints && trueCounter == 1) {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win);
        } else if (!missPoints && trueCounter == 2) {
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win1);
        } else if (!missPoints && trueCounter >= 3) {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win2);
        } else if (missPoints) {
            currentPoints += 0;
            trueCounter = 0;
            missPoints = false;
            textMsg.setText(R.string.lose);
        }
 //       msgHelper = textMsg.getText().toString();
        totalPoints += currentPoints;
        animPointsText.setText("+ " + currentPoints);
        if (currentPoints == 0) {
            animPointsText.setTextColor(Color.RED);
        } else
            animPointsText.setTextColor(Color.GREEN);
    }


    private void startAnimation(){
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(animPointsText,"y",500f,-500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha =  ObjectAnimator.ofFloat(animPointsText,View.ALPHA,1.0f,0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY,alpha);
        animatorSet.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MENUDIFFICULTY,menuDifficulty);
        outState.putString(CURRENTDIFFICULTY,currentDifficulty);
        outState.putInt(GAME_ID,game_id);
        outState.putInt(USER_ID,user_id);
        outState.putIntegerArrayList(IMAGEVIEWS,imageviews);
        outState.putIntegerArrayList(PICKEDIMAGES,pickedImages);
        outState.putIntegerArrayList(ELECTRONICSLIST,electonicsList);
        outState.putIntegerArrayList(FRUITSlIST,fruitsList);
        outState.putIntegerArrayList(SWEETSLIST,sweetsList);
        outState.putIntegerArrayList(JANKFOODLIST,jankfoodList);
        outState.putIntegerArrayList(CLEANLIST,cleanList);
        outState.putIntegerArrayList(HELPERWHENROTATE,helperwhenRotate);

        outState.putParcelable(MATCH, new OrderGame.SparseIntArrayParcelable(imageIDS));

        outState.putInt(TOTALROUNDS,TotalRounds);
        outState.putInt(CURRENTROUND,currentRound);
        outState.putInt(RIGHTPICK,rightpick);
        outState.putInt(CASEMISSOBJ,caseMissingObj);
        outState.putInt(RANDLIST,randlist);
        outState.putBoolean(MISSPOINTS,missPoints);
        outState.putBoolean(FAlSEPICK,falsepick);
        outState.putBoolean(GAMEINIT,gameInit);
        outState.putInt(HIT,hit);
        outState.putInt(MISS,miss);
        outState.putInt(TOTALPOINTS,totalPoints);
        outState.putInt(TRUECOUNTER,trueCounter);
        outState.putInt(CLICK,click);
        outState.putDouble(TOTALSPEED,totalspeed);
        outState.putSerializable(STARTTIME,startTime);
        outState.putSerializable(ENDTIME,endTime);
        outState.putSerializable(STARTSPEED,startspeed);
        outState.putSerializable(ENDSPEED,endspeed);
        outState.putLong(CLOCK,mTimeLeftInMillis);


    }

    private  void fillListImageview(){
        imageviews.add(R.id.imageView1OG);
        imageviews.add(R.id.imageView2OG);
        imageviews.add(R.id.imageView3OG);
        imageviews.add(R.id.imageView4OG);
        imageviews.add(R.id.imageView5OG);
    }


    private void matchlists(){
        listselection.put(0,cleanList);
        listselection.put(1,electonicsList);
        listselection.put(2,sweetsList);
        listselection.put(3,fruitsList);
        listselection.put(4,jankfoodList);
    }

    private void assignAllButtons(){

        imagebutton1 = findViewById(R.id.imageView1OG);
        imagebutton2 = findViewById(R.id.imageView2OG);
        imagebutton3 = findViewById(R.id.imageView3OG);
        imagebutton4 = findViewById(R.id.imageView4OG);
        imagebutton5 = findViewById(R.id.imageView5OG);

        exit = findViewById(R.id.ExitOG);
        replayTutorial = findViewById(R.id.ReplayTutorialOG);
        startButton = findViewById(R.id.startButtonOG);
        missingObj = findViewById(R.id.missingObjectsOG);
        missingObj.setVisibility(View.INVISIBLE);
        textRounds = findViewById(R.id.textRoundsOG);
        textTimer = findViewById(R.id.textTimerOG);
        animPointsText = findViewById(R.id.AnimTextPointsOG);
        logoLinear = findViewById(R.id.imageLogoDisplayOG);
        textsLinear = findViewById(R.id.textsOG);
        textMsg = findViewById(R.id.msgOG);
        textMsgTime = findViewById(R.id.msgOG1);

        imagebutton1.setOnClickListener(this);
        imagebutton2.setOnClickListener(this);
        imagebutton3.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
        imagebutton5.setOnClickListener(this);
        missingObj.setOnClickListener(this);
    }

    private void clickable(){
        imagebutton1.setClickable(true);
        imagebutton2.setClickable(true);
        imagebutton3.setClickable(true);
        imagebutton4.setClickable(true);
        imagebutton5.setClickable(true);
        missingObj.setClickable(true);
    }
    private  void unclickable(){
        imagebutton1.setClickable(false);
        imagebutton2.setClickable(false);
        imagebutton3.setClickable(false);
        imagebutton4.setClickable(false);
        imagebutton5.setClickable(false);
        missingObj.setClickable(false);
    }

    private void initialiseLists(){
        cleanList.add(R.drawable.og_h_broom);
        cleanList.add(R.drawable.og_h_mop);
        cleanList.add(R.drawable.og_h_pills);
        cleanList.add(R.drawable.og_h_shampoo);
        cleanList.add(R.drawable.og_h_sponge);
        cleanList.add(R.drawable.og_h_spray);
        cleanList.add(R.drawable.og_h_toothbrush);
        cleanList.add(R.drawable.og_h_toothpaste);

        electonicsList.add(R.drawable.og_e_camera);
        electonicsList.add(R.drawable.og_e_headset);
        electonicsList.add(R.drawable.og_e_keyboard);
        electonicsList.add(R.drawable.og_e_mouse);
        electonicsList.add(R.drawable.og_e_smartphone);
        electonicsList.add(R.drawable.og_e_speakers);
        electonicsList.add(R.drawable.og_e_tv);
        electonicsList.add(R.drawable.og_e_usb);

        sweetsList.add(R.drawable.og_c_cake);
        sweetsList.add(R.drawable.og_c_candy);
        sweetsList.add(R.drawable.og_c_coffee);
        sweetsList.add(R.drawable.og_c_donut);
        sweetsList.add(R.drawable.og_c_icecream);
        sweetsList.add(R.drawable.og_c_juice);
        sweetsList.add(R.drawable.og_c_milkshake);
        sweetsList.add(R.drawable.og_c_muffin);

        fruitsList.add(R.drawable.og_f_apple);
        fruitsList.add(R.drawable.og_f_banana);
        fruitsList.add(R.drawable.og_f_cherry);
        fruitsList.add(R.drawable.og_f_grape);
        fruitsList.add(R.drawable.og_f_mellon);
        fruitsList.add(R.drawable.og_f_pineapple);
        fruitsList.add(R.drawable.og_f_strawberry);
        fruitsList.add(R.drawable.og_f_watermellon);

        jankfoodList.add(R.drawable.og_sn_burger);
        jankfoodList.add(R.drawable.og_sn_chicken);
        jankfoodList.add(R.drawable.og_sn_eggs);
        jankfoodList.add(R.drawable.og_sn_fries);
        jankfoodList.add(R.drawable.og_sn_hotdog);
        jankfoodList.add(R.drawable.og_sn_pizza);
        jankfoodList.add(R.drawable.og_sn_salad);
        jankfoodList.add(R.drawable.og_sn_steak);
    }


    public class SparseIntArrayParcelable extends SparseIntArray implements Parcelable {
        public  Creator<OrderGame.SparseIntArrayParcelable> CREATOR = new Creator<OrderGame.SparseIntArrayParcelable>() {
            @Override
            public OrderGame.SparseIntArrayParcelable createFromParcel(Parcel source) {
                OrderGame.SparseIntArrayParcelable read = new OrderGame.SparseIntArrayParcelable();
                int size = source.readInt();

                int[] keys = new int[size];
                int[] values = new int[size];

                source.readIntArray(keys);
                source.readIntArray(values);

                for (int i = 0; i < size; i++) {
                    read.put(keys[i], values[i]);
                }

                return read;
            }

            @Override
            public OrderGame.SparseIntArrayParcelable[] newArray(int size) {
                return new OrderGame.SparseIntArrayParcelable[size];
            }
        };

        private SparseIntArrayParcelable() {

        }

        private SparseIntArrayParcelable(SparseIntArray sparseIntArray) {
            for (int i = 0; i < sparseIntArray.size(); i++) {
                this.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            int[] keys = new int[size()];
            int[] values = new int[size()];

            for (int i = 0; i < size(); i++) {
                keys[i] = keyAt(i);
                values[i] = valueAt(i);
            }

            dest.writeInt(size());
            dest.writeIntArray(keys);
            dest.writeIntArray(values);
        }
    }
}
