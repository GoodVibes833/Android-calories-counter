package ciccc.ca.android_calory_cal;

public class Eat {
    String food;
    String calories;

    public Eat() {
    }

    public Eat(String food, String calories) {
        this.food = food;
        this.calories = calories;
    };

    @Override
    public String toString() {
        return food+","+calories;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
