package assignment3.mobile.colorblender;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Button;

/**
 * Created by godzilla on 9/28/2015.
 */
public class SettingsFragment extends Fragment {

    CheckBox checkboxBackground;
    CheckBox checkboxText;
    TextView colorTextview;
    Button fragBackGroundButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.setting_frag, container, false);
        checkboxBackground = (CheckBox) view.findViewById(R.id.checkboxBackground);
        checkboxText = (CheckBox) view.findViewById(R.id.checkboxText);
        colorTextview = (TextView) view.findViewById(R.id.color1TextView);
        fragBackGroundButton = (Button) view.findViewById(R.id.fragBackGroundButton);
        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setupButtons();


    }

    private void setupButtons() {

        fragBackGroundButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    PackageManager pm = getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage("Color.Finder.Assignment");
                    intent.setFlags(0);
                    intent.putExtra("calledByProgram", true);
                    startActivityForResult(intent, request_Button_Code);
                }
        }
        );

    }


}
