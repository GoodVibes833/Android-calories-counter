package ciccc.ca.android_calory_cal;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import java.util.ArrayList;

import ciccc.ca.android_calory_cal.R;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;


public class OverviewActivity extends AppCompatActivity {
    TextView gotCalories;
    TextView lostCalories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        PieView pieView = findViewById(R.id.pie_view);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();

        pieHelperArrayList.add(new PieHelper(50,Color.RED));
        pieHelperArrayList.add(new PieHelper(50,Color.BLUE));


        pieView.setDate(pieHelperArrayList);
//        pieView.selectedPie(2); //optional
//        pieView.setOnPieClickListener(listener) //optional
        pieView.showPercentLabel(true); //optional


        gotCalories = findViewById(R.id.gotCalories);
        lostCalories = findViewById(R.id.lostCalories);





    }
}
