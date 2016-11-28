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
    private int[] outputs;

    public Sublayer(Unit[] units) {
        this.units = units;
        outputs = new int[units.length];
    }

    private void init(){
        sumOutput = 0;
        sumMaxOutput = 0;
        sumCost = 0;
        outputs = new int[units.length];
    }

    void setStartPlan(int[] startPlan) {
        this.startPlan = startPlan;
    }

    void setLambda(double lambda) {
        this.lambda = lambda;
    }

    void setMu(double mu) {
        this.mu = mu;
    }

    public void compute(){
        init();
        for (int index : startPlan) {
            int output = computeOutput(index);
            if (output > units[index-1].getMax()){
                output = units[index-1].getMax();
            }
            if (output < units[index-1].getMin()){
                output = units[index-1].getMin();
            }
            outputs[index-1] = output;
            sumOutput += output;
            sumCost += computeCost(index, output);
            sumMaxOutput += units[index-1].getMax();
        }
//        System.out.println("底层问题计算完成。。。");
    }

    private int computeOutput(int index){
        return (int)((lambda-units[index-1].getB())/2/units[index-1].getA());
    }

    private double computeCost(int index, int output){
        Unit unit = units[index-1];
        double a = unit.getA();
        double b = unit.getB();
        double c = unit.getC();
//        int maxOutput = units[index-1].getMax();
//        return a*output*output+b*output+c-lambda*output-mu*maxOutput;
        return a*output*output+b*output+c-lambda*output;
    }

    int getSumOutput() {
        return sumOutput;
    }

    int getSumMaxOutput() {
        return sumMaxOutput;
    }

    double getSumCost() {
        return sumCost;
    }

    public int[] getOutputs(){
        return outputs;
    }
}
