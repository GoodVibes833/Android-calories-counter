package ciccc.ca.android_calory_cal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class InputEat extends AppCompatActivity {

    EditText foodName;
    EditText caloriesPerGram;
    private InputStream inputStream;
    private OutputStream OutputStream;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private List<String> MoveArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_eat);
        foodName = findViewById(R.id.foodName);
        caloriesPerGram = findViewById(R.id.caloriesPerGram);

        String newFoodName = String.valueOf(foodName.getText());
        String newFoodCalories = String.valueOf(caloriesPerGram.getText());


        TextView reading = findViewById(R.id.reading);


        // 1. read text file
        inputStream = getResources().openRawResource(R.raw.write_test);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        MoveArray = new ArrayList<String>();

        try {
            String line;
            while (true) {
                line = (bufferedReader.readLine());
                if (line == null)
                    break;
                MoveArray.add(line);

            }
            reading.setText(MoveArray.get(2));
            bufferedReader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        // 2. write text file
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File("/Users/jin-tak.han/Code/AndroidDay50/Android-calories-calculator/app/src/main/res/raw/write_test.txt")));
            String line;
            while(true) {
            line = (bufferedReader.readLine());
            if (line == null)
                break;
        }
        bufferedWriter.write("what you did");

        bufferedWriter.close();
        inputStream.close();

    } catch (IOException e) {
        e.printStackTrace();
    }


}
    public void addToList(View view) {

    }
}
