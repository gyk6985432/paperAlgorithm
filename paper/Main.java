package paper;

import paper.distribute.LoadDemand;
import paper.distribute.MiddleLayer;
import paper.distribute.Sublayer;
import paper.distribute.UpperLayer;
import paper.entity.Unit;
import paper.predict.Prediction;
import paper.startplan.Plan;

import java.io.IOException;
import java.util.List;

/**
 * Created by gyk on 2016/11/26.
 */
public class Main {
//    多电源电力系统多目标优化调度研究
//    机组容量    600 600 600 360 360 360 360 150 150 200
//    负荷下限    300 300 300 150 150 150 150 60  60  100
//    煤耗系数a   0.0028  0.0139  0.0083  0.0162  0.0025  0.0152  0.0611  0.0333  0.0494  0.0208
//    煤耗系数b   273.5   258.5   268.5   294.25  307.75  296.5   278.5   311.75  312     303.75
//    煤耗系数c   13700   14500   14100   6810    6737.5  6765    6350    4635.42 5040    5416.67

    public static void main(String[] args) throws IOException {
        Unit[] units = new Unit[10];
        units[0] = new Unit(1,1000, 500, "thermal");
        units[1] = new Unit(2,1000, 500, "thermal");
        units[2] = new Unit(3, 660, 300, "thermal");
        units[3] = new Unit(4, 600, 300, "thermal");
        units[4] = new Unit(5, 600, 300, "thermal");
        units[5] = new Unit(6, 350, 150, "thermal");
        units[6] = new Unit(7, 330, 150, "thermal");
        units[7] = new Unit(8, 330, 150, "thermal");
        units[8] = new Unit(9, 250, 130, "thermal");
        units[9] = new Unit(10, 150, 70, "thermal");

        Prediction prediction = new Prediction();
        int[] range = prediction.getPredicts();
        int maxLoad = range[0];
        int minLoad = range[1];

        Plan plan = new Plan(units,maxLoad,minLoad);

        List<int[]> startPlans = plan.getArrange();

        LoadDemand ld = new LoadDemand();
        int[] demands = ld.getDemands();

        for (int t=0;t<24;t++){
            Sublayer sublayer = new Sublayer(units);
            MiddleLayer middleLayer = new MiddleLayer(sublayer,demands[t],0);
            UpperLayer upperLayer = new UpperLayer(middleLayer,startPlans);
            upperLayer.getBestPlan();
        }


    }
}
