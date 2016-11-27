package paper.distribute;

import java.util.List;

/**
 * Created by gyk on 2016/11/26.
 * 上层将符合条件的机组组合传给中间层，获得每个组合的煤耗量后，选择其中煤耗量最小的作为最佳启动计划。
 */
public class UpperLayer {
    private MiddleLayer middleLayer;
    private List<int[]> startPlans;
    private int[] bestPlan;

    public UpperLayer(MiddleLayer middleLayer,List<int[]> startPlans) {
        this.middleLayer = middleLayer;
        this.startPlans = startPlans;
        iterate();
    }

    public int[] getBestPlan() {
        return bestPlan;
    }

    private void iterate(){
        double minimumCost = 0;
        for (int[] startPlan : startPlans) {
            int[] plan = startPlan.clone();
            double cost = middleLayer.compute(plan);
            if (cost < minimumCost) {
                bestPlan = plan;
            }
        }
        System.out.println("上层问题计算完成。。。");
    }
}
