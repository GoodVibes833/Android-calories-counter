package ciccc.ca.android_calory_cal;

import java.util.Date;

public class History {
    String today;
    String item;
    String totalCalories;

    public History(String today, String item, String totalCalories) {
        this.today = today;
        this.item = item;
        this.totalCalories = totalCalories;
    }

    @Override
    public String toString() {
        return today + " -> " + item + "," + totalCalories + "calories";
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }
}
