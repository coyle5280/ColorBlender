package assignment3.mobile.colorblender;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * The Colorblender app, uses the color finder app to have the user select 2 colors and then
 * blend the color together. It has the ability to set text and background color of the app based
 * on the user choices.
 * @version 1.2
 * @author Josh Coyle
 * @author Robert Slavik
 */
public class MainActivity extends Activity implements SettingsFragment.settingsListener{
    //variables
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
    //upon start initialize to 0
    int start = 0;
    //set blend bar to 0
    private double progressBarIndicator = 0;
    int blendedColor;
    boolean settingsBoolean = false;
    SettingsFragment settings;
    RelativeLayout main;

    Button button1;
    Button button2;

    //  Menu
    ActionBar actionBar;
    //  Menu


    /**
     * Create the application, sets up the main activity
     * @param savedInstanceState - saved state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (RelativeLayout) findViewById(R.id.main);
        actionBar = getActionBar();
        //actionBar.show();

        setupButtons();
        setupTextViews();
        setupFragment();

    }

    /**
     * create a fragment for the settings feature
     */
    private void setupFragment() {
        settings = new SettingsFragment();
    }

    /**
     * setup all text views used in the application and set default color
     */
    private void setupTextViews() {

        blendColorView = (TextView) findViewById(R.id.blendColorTextView);
        color1View = (TextView) findViewById(R.id.color1TextView);
        color2View = (TextView) findViewById(R.id.color2TextView);
        setColor(-1, -1);

    }

    /**
     * Creates the menu bar at the top of the application
     * @param menu- the menu bar
     * @return the inflated menu
     */
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Menu

    /**
     * Used for the settings feature of the color blender application
     * @param item- the menu bar item that was selected
     * @return - return true if item exists or false if not available
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.settings:
                callSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }

    /**
     * sets the blended colors and their individual components, by taking 2 colors in
     * and parsing them, then calls blend function
     * @param kolor- first color to parse
     * @param kolor2- second color to parse
     */
    private void setColor(int kolor, int kolor2) {
        int k, k2;
        //check if colors have been choose
        if (kolor == -1 && kolor2 == -1) {
            k = Color.parseColor("#0f3ddc");
            k2 = Color.parseColor("#3cff41");
        }else{
            k = kolor;
            k2 = kolor2;
        }
        //parse the colors
        blue1 = Color.blue(k);
        red1 = Color.red(k);
        green1 = Color.green(k);

        blue2 = Color.blue(k2);
        red2 = Color.red(k2);
        green2 = Color.green(k2);

        //if app just opened, preset background
        if (start == 0) {
            blendColorView.setBackgroundColor(k);
            start = 1;
        } else {
            //blend parsed color
            blend(progressBarIndicator);
        }
    }

    /**
     * blend the selected colors based on slider bar position
     * @param progressBarIndicator- indicator for blend bar to determine how to blend color
     *                            from 0 to 100
     */
    public void blend(double progressBarIndicator){
        //copy parameter
        double t = progressBarIndicator;
        //determine the inverse
        double inverse = t/100;
        //determine the percent
        double percent = 1-inverse;
        //create a blended color parts
        double newRed = (red1*percent) + (red2*inverse);
        double newBlue= blue1*percent + blue2*inverse;
        double newGreen =green1*percent + green2*inverse;
        //change to ints
        int tempRed =  ((int)(newRed));
        int tempGreen= ((int) (newGreen));
        int tempBlue= ((int) (newBlue));
        //blend the color
        blendedColor = Color.rgb(tempRed, tempGreen, tempBlue);
        blendColorView.setBackgroundColor(blendedColor);
    }


    /**
     * Create the buttons used in the application and set listeners
     */
    private void setupButtons() {
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        blendBar = (SeekBar) findViewById(R.id.blendBar);
        blendBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Listener for the seekBar
             * @param seekBar - The seekBar
             * @param progress- current position of seekBar
             * @param fromUser- if change came from user
             */
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
            /**
             * Listener for button1 to see if it was clicked,
             * if clicked open the color finder application to
             * choose a color
             * @param v- the view
             */
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
            /**
             * Listener for button2to see if it was clicked,
             * if clicked open the color finder application to
             * choose a color
             * @param v- the view
             */
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

    /**
     * The return of the Activity requested (color finder)
     * @param request_Code - request code
     * @param result_Code- the result
     * @param colorsData- the color the user picked
     */
    public void onActivityResult(int request_Code, int result_Code, Intent colorsData) {
        //for button 1 requested
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
        //for button 2 requested
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

    /**
     * if the settings is clicked, create fragment and open or close if open
     */
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

    /**
     * if changes are made to the app from the settings menu
     * @param background- if background should be changed
     * @param text- if text should be changed
     * @param color- color to change text to
     */
    public void updateInterface(boolean background, boolean text, int color){
        if(background) {
            main.setBackgroundColor(blendedColor);
            callSettings();
        }else{
            main.setBackgroundColor(Color.BLACK);
            callSettings();
        }
        if(text) {
            if (color != -1) {
                button1.setTextColor(color);
                button2.setTextColor(color);
            }
        }else{
            button1.setTextColor(Color.WHITE);
            button2.setTextColor(Color.WHITE);
        }
    }

}