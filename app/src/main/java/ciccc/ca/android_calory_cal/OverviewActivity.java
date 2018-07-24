package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;

import ciccc.ca.android_calory_cal.R;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;


public class OverviewActivity extends AppCompatActivity {
    TextView gotCalories;
    TextView lostCalories;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        PieView pieView = findViewById(R.id.pie_view);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();



        Intent intent = getIntent();
        if(intent.getStringExtra("sumOfCalories") != null) {
            String sumOfCalories = intent.getStringExtra("sumOfCalories");
            String sumOfMoveCal = intent.getStringExtra("sumOfMoveCal");
            String sumOfEatCal = intent.getStringExtra("sumOfEatCal");

            System.out.println("sumOfCalories :" + sumOfCalories);
            System.out.println("sumOfMoveCal :" + sumOfMoveCal);
            System.out.println("sumOfEatCal :" + sumOfEatCal);

            //to percentage
            int percentageOfEat = 100 * Integer.valueOf(sumOfEatCal) /
                    (Integer.valueOf(sumOfEatCal) + Integer.valueOf(sumOfMoveCal));
            int percentageOfMove = 100 * Integer.valueOf(sumOfMoveCal) /
                    (Integer.valueOf(sumOfEatCal) + Integer.valueOf(sumOfMoveCal));

            System.out.println(percentageOfEat);
            System.out.println(percentageOfMove);

            pieHelperArrayList.add(new PieHelper(percentageOfEat, Color.RED));
            pieHelperArrayList.add(new PieHelper(100 - percentageOfEat, Color.BLUE));


            pieView.setDate(pieHelperArrayList);
//        pieView.selectedPie(2); //optional
//        pieView.setOnPieClickListener(listener) //optional
            pieView.showPercentLabel(true); //optional


            gotCalories = findViewById(R.id.gotCalories);
            lostCalories = findViewById(R.id.lostCalories);

            gotCalories.setText(sumOfEatCal);
            lostCalories.setText(sumOfMoveCal);

        }
    }

    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }

    public void goToHistory(View view) {
        intent = new Intent(this, HistoryController.class);
        startActivity(intent);
    }
}
