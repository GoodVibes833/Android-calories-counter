package ciccc.ca.android_calory_cal;

public class Overview {
    private int sumOfMoveCal;
    private int sumOfEatCal;


    public Overview() {
    }

    public Overview(int sumOfMoveCal, int sumOfEatCal) {
        this.sumOfMoveCal = sumOfMoveCal;
        this.sumOfEatCal = sumOfEatCal;
    }

    public int getSumOfMoveCal() {
        return sumOfMoveCal;
    }

    public void setSumOfMoveCal(int sumOfMoveCal) {
        this.sumOfMoveCal = sumOfMoveCal;
    }

    public int getSumOfEatCal() {
        return sumOfEatCal;
    }

    public void setSumOfEatCal(int sumOfEatCal) {
        this.sumOfEatCal = sumOfEatCal;
    }
}
