package ciccc.ca.android_calory_cal;

public class Move {
    String exercises;
    String calories;


    public Move() {
    }

    public Move(String exercises, String calories) {
        this.exercises = exercises;
        this.calories = calories;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
