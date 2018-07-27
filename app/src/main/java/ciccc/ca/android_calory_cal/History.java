package ciccc.ca.android_calory_cal;

import java.util.Date;

public class History {
    private String id;
    private String date;
    private String item;
    private String totalCalories;

    @Override
    public String toString() {
        return item + " : " + totalCalories + " calories";
    }

    public History() {
    }

    public History(String id, String date, String item, String totalCalories) {
        this.id = id;
        this.date = date;
        this.item = item;
        this.totalCalories = totalCalories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
