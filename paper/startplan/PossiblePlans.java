package paper.startplan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gyk on 2016/11/1.
 *
 */
public class PossiblePlans {
    private int maxNum,minNum;//最大最小机组启动数
    private int[] arr;//所有机组id
    KCombination kCombination;

    public PossiblePlans(int[] arr, int maxNum, int minNum){
        this.arr = arr;
        this.maxNum = maxNum;
        this.minNum = minNum;
    }

    public List<int[]> getPossiblePlans(){
        kCombination = new KCombination();
        List<int[]> plans = new LinkedList<>();
        for (int i=minNum;i<=maxNum;i++){
            plans.addAll(kCombination.getCombinations(arr, i));
        }
        return plans;
    }
}
