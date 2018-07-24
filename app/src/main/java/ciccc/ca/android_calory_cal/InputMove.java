package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InputMove extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_move);
    }

    public void goToHistory(View view) {
        intent = new Intent(this, HistoryController.class);
        startActivity(intent);
    }

    public void goToOverview(View view) {
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }

    public void addToList(View view) {
    }
}
