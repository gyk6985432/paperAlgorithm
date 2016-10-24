package subProblems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyk on 2016/10/9.
 */
public class BetterSolution {
    Unit[] units;
    int maxLoad,minLoad;
    //1.对数组从大到小排序
    //由于机组数量一般不超过数十台，而且一般机组启停较少，当有新机组加入时，
    // 其他机组已经排好了顺序，因此这种情况下用插入排序法比较合适
    public void insert(){
        int i,j;
        Unit temp;
        for (i=1;i<units.length;i++) {
            temp = units[i];
            j=i-1;
            while(j>=0 && temp.getMax() > units[j].getMax()){
                units[j+1]=units[j];
                j--;
            }
            units[j+1]=temp;
        }
    }
    //2.根据最大负荷和最小负荷，求出最少需要多少台，最多需要多少台
    public int getMaxNum(){
        int hold = 0;
        int count = 0;
        for (int i=units.length-1;i>=0;i--){
            hold+=units[i].getMax();
            count++;
            if (hold>=maxLoad){
                return count;
            }
        }
        return 0;
    }
    public int getMinNum(){
        int hold = 0;
        int count = 0;
        for (int i=0;i<units.length;i++){
            hold+=units[i].getMax();
            count++;
            if (hold>=maxLoad){
                return count;
            }
        }
        return 0;
    }
    //3.列出台数范围内的所有可能，筛掉不符合最小负荷的组合
    public int[][] getArrange(int minNum, int maxNum){
        int n1 = 1 << minNum;
        int n2 = 1 << maxNum;
        int[][] table = new int[n2-n1][units.length];
        int count=0;
        for (int i=n1;i<n2;i++){
            int[] item = new int[units.length];
            for (int j=0;j<maxNum;j++){
                int temp = i;
                if ((temp & (1 << j)) != 0){
                    item[j] = 1;
                }
            }
            table[count++] = item;
        }
        return table;
    }
}
