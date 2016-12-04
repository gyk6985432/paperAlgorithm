package paper.distribute;

/**
 * Created by gyk on 2016/11/26.
 * 中间层在启停计划确定的情况下，更新拉格朗日乘子，并计算最终的煤耗量cost，返回给上层upperLayer。
 */
public class MiddleLayer {

    private Sublayer sublayer;
    private double lambda,mu;//拉格朗日乘子
    private double g_lambda,g_mu;//拉格朗日乘子的次梯度
    private double theta,rho;//theta,rho初值选取需要调整
    private int demand;//某一时刻的负荷数据
    private int reserve;//某一时刻的旋转备用
    private int[] output;
    public static int count;

    public MiddleLayer(Sublayer sublayer, int demand, int reserve) {
        this.sublayer = sublayer;
        this.demand = demand;
        this.reserve = reserve;
        this.reserve = demand / 20;        //暂设旋转备用R=D×0.05
        init();
    }

    private void init(){
        this.lambda = 13;
        this.mu = 1;
        this.g_lambda = 1;
        this.g_mu = 1;
        this.theta = 3;
        this.rho = 0.995;
    }

    double compute(int[] plan){
        init();
        double cost;
        double maxCost = 0;
        sublayer.setStartPlan(plan);
//        while (Math.abs(g_lambda) > 0.000001 || Math.abs(g_mu) > 0.000001){
        while (Math.abs(g_lambda) > 0.000001){
            cost = iterate();
            if (cost>maxCost){
                maxCost = cost;
                output = sublayer.getOutputs();
            }
            count++;
        }
//        System.out.println("中间层问题计算完成。。。");
        return maxCost;
    }

    private double iterate(){
        sublayer.setLambda(lambda);
        sublayer.setMu(mu);
        sublayer.compute();
        double Li = sublayer.getSumCost();
        double cost = Li + systemSum();
        //计算次梯度
        g_lambda = demand - sublayer.getSumOutput();
        g_mu = demand + reserve - sublayer.getSumMaxOutput();
        //更新拉格朗日乘子
        lambda = lambda + theta * g_lambda;
        if (lambda<0) lambda=0;
        mu = mu + theta * g_mu;
        if (mu<0) mu=0;
        theta = theta * rho;
//        System.out.println("lambda:" + lambda + " g_lambda:" + g_lambda);
        return cost;
    }

    private double systemSum(){
//        return lambda * demand + mu * (demand + reserve);
        return lambda * demand;
    }

    public int[] getOutput() {
        return output;
    }
}
