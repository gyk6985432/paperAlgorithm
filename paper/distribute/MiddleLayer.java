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

    public MiddleLayer(Sublayer sublayer, int demand, int reserve) {
        this.sublayer = sublayer;
        this.lambda = 300;
        this.mu = 1;
        this.g_lambda = 1;
        this.g_mu = 1;
        this.theta = 0.04;
        this.rho = 1;
        this.demand = demand;
        this.reserve = reserve;
        this.reserve = demand / 20;        //暂设旋转备用R=D×0.05
    }

    double compute(int[] plan){
        double cost = 0;
        sublayer.setStartPlan(plan);
        int count = 0;
        while (Math.abs(g_lambda) > 0.000001 || Math.abs(g_mu) > 0.000001){
            cost = iterate();
            count++;
            System.out.println(count);
        }
        System.out.println("中间层问题计算完成。。。");
        return cost;
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
//        if (lambda<0) lambda=0;
        mu = mu + theta * g_mu;
//        if (mu<0) mu=0;
        theta = theta * rho;
        System.out.println("g_lambda:" + g_lambda+ " g_mu:"+g_mu);
        return cost;
    }

    private double systemSum(){
        return lambda * demand + mu * (demand + reserve);
    }

}
