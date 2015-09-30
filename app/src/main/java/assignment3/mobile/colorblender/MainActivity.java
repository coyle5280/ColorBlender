package assignment3.mobile.colorblender;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    static final int request_Button_Code = 1;
    static final int request_Button_Code2 = 2;
    TextView blendColorView;
    TextView color1View;
    TextView color2View;
    int colorOneInt, colorTwoInt;
    private double red1;
    private double blue1;
    private double green1;
    private double red2;
    private double blue2;
    private double green2;
    SeekBar blendBar;
    int start = 0;
    private double progressBarIndicator = 0;
    int blendedColor;
    boolean settingsBoolean = false;
    SettingsFragment settings;


    //  Menu
    ActionBar actionBar;
    //  Menu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar = getActionBar();
        //actionBar.show();

        setupButtons();
        setupTextViews();
        setupFragment();

    }

    private void setupFragment() {
        settings = new SettingsFragment();
    }

    private void setupTextViews() {

        blendColorView = (TextView) findViewById(R.id.blendColorTextView);
        color1View = (TextView) findViewById(R.id.color1TextView);
        color2View = (TextView) findViewById(R.id.color2TextView);
        setColor(-1, -1);

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Menu

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.settings:
                callSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }

    private void setColor(int kolor, int kolor2) {
        int k, k2;
        if (kolor == -1 && kolor2 == -1) {
            k = Color.parseColor("#0f3ddc");
            k2 = Color.parseColor("#3cff41");
        }else{
            k = kolor;
            k2 = kolor2;
        }

        blue1 = Color.blue(k);
        red1 = Color.red(k);
        green1 = Color.green(k);

        blue2 = Color.blue(k2);
        red2 = Color.red(k2);
        green2 = Color.green(k2);

        if (start == 0) {
            blendColorView.setBackgroundColor(k);
            start = 1;
        } else {
            blend(progressBarIndicator);
        }
    }

    public void blend(double progressBarIndicator){
        double t = progressBarIndicator;
        double inverse = t/100;
        double percent = 1-inverse;

        double newRed = (red1*percent) + (red2*inverse);
        double newBlue= blue1*percent + blue2*inverse;
        double newGreen =green1*percent + green2*inverse;

        int tempRed =  ((int)(newRed));
        int tempGreen= ((int) (newGreen));
        int tempBlue= ((int) (newBlue));

        blendedColor = Color.rgb(tempRed, tempGreen, tempBlue);
        blendColorView.setBackgroundColor(blendedColor);
    }



    private void setupButtons() {

        final Button button1 = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);
        blendBar = (SeekBar) findViewById(R.id.blendBar);
        blendBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBarIndicator = progress;
                blend(progressBarIndicator);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("Color.Finder.Assignment");
                intent.setFlags(0);
                intent.putExtra("calledByProgram", true);
                startActivityForResult(intent, request_Button_Code);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                Intent intent2 = pm.getLaunchIntentForPackage("Color.Finder.Assignment");
                intent2.setFlags(0);
                intent2.putExtra("calledByProgram", true);
                startActivityForResult(intent2, request_Button_Code2);

            }
        });
    }


    public void onActivityResult(int request_Code, int result_Code, Intent colorsData) {
        if (request_Code == 1) {
            if (result_Code == RESULT_OK) {
                colorOneInt = colorsData.getIntExtra("color", 0);
                color1View.setBackgroundColor(colorOneInt);
                red1=Color.red(colorOneInt);
                blue1= Color.blue(colorOneInt);
                green1=Color.green(colorOneInt);
                blend(progressBarIndicator);

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Error the ColorPicker Failed to Return the First Color");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


            }


        }

        if (request_Code == 2) {
            if (result_Code == RESULT_OK) {
                colorTwoInt = colorsData.getIntExtra("color", 0);
                color2View.setBackgroundColor(colorTwoInt);
                red2=Color.red(colorTwoInt);
                blue2=Color.blue(colorTwoInt);
                green2=Color.green(colorTwoInt);
                blend(progressBarIndicator);
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Error the ColorPicker Failed to Return the Second Color");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }
    }


    private void callSettings() {
        if(!settingsBoolean) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragHolder, settings);
            fragmentTransaction.commit();
            settingsBoolean = true;
        }else{
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(settings);
            fragmentTransaction.commit();
            settingsBoolean = false;
        }
    }

}