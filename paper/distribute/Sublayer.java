package paper.distribute;


import paper.entity.Unit;

/**
 * Created by gyk on 2016/11/26.
 * 中间层将lambda和mu传给底层，并计算出各台机组的出力和煤耗，返回给中间层。
 */
public class Sublayer {

    private Unit[] units;
    private int[] startPlan;
    private double lambda,mu;
    private int sumOutput, sumMaxOutput;
    private double sumCost;

    public Sublayer(Unit[] units) {
        this.units = units;
        compute();
    }

    public void setStartPlan(int[] startPlan) {
        this.startPlan = startPlan;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }

    private void compute(){
        sumOutput = 0;
        sumMaxOutput = 0;
        sumCost = 0;
        for (int index : startPlan) {
            int output = computeOutput(index);
            sumOutput += output;
            sumCost += computeCost(index, output);
            sumMaxOutput += units[index].getMax();
        }
    }

    private int computeOutput(int index){
        return (int)((lambda-units[index].getB())/2/units[index].getA());
    }

    private double computeCost(int index, int output){
        Unit unit = units[index];
        double a = unit.getA();
        double b = unit.getB();
        double c = unit.getC();
        int maxOutput = units[index].getMax();
        return a*output*output+b*output+c-lambda*output-mu*maxOutput;
    }

    public int getSumOutput() {
        return sumOutput;
    }

    public int getSumMaxOutput() {
        return sumMaxOutput;
    }

    public double getSumCost() {
        return sumCost;
    }
}
