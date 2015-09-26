package assignment3.mobile.colorblender;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity{

    static final int request_Button_Code = 1;
    static final int request_Button_Code2 = 2;
    TextView blendColor;
    TextView color1;
    TextView color2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
        setupTextViews();


    }

    private void setupTextViews() {

        blendColor = (TextView) findViewById(R.id.blendColorTextView);
        color1 = (TextView) findViewById(R.id.color1TextView);
        color2 = (TextView) findViewById(R.id.color2TextView);

    }

    private void setupButtons() {

        final Button button1 = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage("Color.Finder.Assignment");
                intent.putExtra("calledByProgram", true);
                startActivityForResult(intent, request_Button_Code);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                Intent intent2 = pm.getLaunchIntentForPackage("Color.Finder.Assignment");
                intent2.putExtra("calledByProgram", true);
                startActivityForResult(intent2, request_Button_Code2);

            }
        });
    }





    public void onActivityResult(int request_Code, int result_Code, Intent colorsData){
        if(request_Code == 1){
            if(result_Code == RESULT_OK){
                int colorOne = colorsData.getIntExtra("color", 0);
                color1.setBackgroundColor(colorOne);
            }else{
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

        if(request_Code == 2){
            if(result_Code == RESULT_OK){
                int colorTwo = colorsData.getIntExtra("color", 0);
                color2.setBackgroundColor(colorTwo);
            }else{
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




}
