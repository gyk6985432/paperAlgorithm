package paper.entity;

/**
 * Created by gyk on 2016/5/20.
 */
public class Unit {
    private int id;
    private int max;
    private int min;
    private double rate;

    public Unit(int id, int max, int min, double rate) {
        this.id = id;
        this.max = max;
        this.min = min;
        this.rate = rate;
    }

    public Unit(int max, int min, double rate) {
        this.max = max;
        this.min = min;
        this.rate = rate;
    }

    public Unit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
