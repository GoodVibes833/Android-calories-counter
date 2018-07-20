package ciccc.ca.android_calory_cal;

import java.util.Date;

public class History {
    private String date;
    private String item;
    private String totalCalories;

    public History() {
    }

    public History(String date, String item, String totalCalories) {
        this.date = date;
        this.item = item;
        this.totalCalories = totalCalories;
    }

    @Override
    public String toString() {
        return date + " -> " + item + "," + totalCalories + "calories";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
