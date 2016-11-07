package paper.startplan;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gyk on 2016/11/1.
 *获得最大至最小启动的机组数的所有排列组合
 */
class PossiblePlans {
    private int maxNum,minNum;//最大最小机组启动数
    private int[] arr;//所有机组id
    private KCombination kCombination;

    PossiblePlans(int[] arr, int maxNum, int minNum){
        this.arr = arr;
        this.maxNum = maxNum;
        this.minNum = minNum;
    }

    List<int[]> getPossiblePlans(){
        kCombination = new KCombination();
        List<int[]> plans = new LinkedList<>();
        for (int i=minNum;i<=maxNum;i++){
            plans.addAll(kCombination.getCombinations(arr, i));
        }
        return plans;
    }
}
