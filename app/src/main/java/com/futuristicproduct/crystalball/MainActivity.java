package com.futuristicproduct.crystalball;

import android.graphics.drawable.AnimationDrawable;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CrystalBall mCrystalBall = new CrystalBall();
    private  TextView mAnswerLabel;
    //private Button mGetAnswerButton;
    private  ImageView mCrystalballImage;


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare our View variables and assign them the Views from the layout file
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
        //mGetAnswerButton = (Button) findViewById(R.id.button1);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleNewAnswer();
            }
        });

       /*mGetAnswerButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
             handleNewAnswer();
            }
       });*/

        Toast makeToast = Toast.makeText(this, "I was expecting you", Toast.LENGTH_LONG);
        makeToast.setGravity(Gravity.TOP,0,0);
        makeToast.show();

        Log.d(TAG, "MainActivity OnCreate() is launched!");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,mAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause ()
    {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }

    private void handleNewAnswer() {
        String answer = mCrystalBall.getAnAnswer();

        // Update the label with our dynamic answer
        mAnswerLabel.setText(answer);
        animateCrystalBall ();
        animateAnswer();
        playSound();
    }

    public void animateCrystalBall ()
    {
        mCrystalballImage =  (ImageView) findViewById(R.id.imageView1);
        mCrystalballImage.setImageResource(R.drawable.ball_animation);
        AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalballImage.getDrawable();

        if (ballAnimation.isRunning())
            ballAnimation.stop();

        ballAnimation.start();
    }

    public void animateAnswer()
    {
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0,1);
        fadeInAnimation.setDuration(1500);
        fadeInAnimation.setFillAfter(true);

        mAnswerLabel.setAnimation(fadeInAnimation);

    }

    public void playSound()
    {
        MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

    }
}
