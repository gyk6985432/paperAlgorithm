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
    //其他机组已经排好了顺序，因此这种情况下用插入insert排序法比较合适
    public void sort(){
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
    //http://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
    //http://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java
    public int[][] getArrange(int minNum, int maxNum){
        int n1 = 1 << minNum;
        int n2 = 1 << maxNum;
        int[][] table = new int[n2-n1][units.length];
        int count=0;
        for (int i=n1;i<n2;i++){
            int[] item = new int[units.length];
            for (int j=0;j<units.length;j++){
                int temp = i;
                if ((temp & (1 << j)) != 0){
                    item[j] = 1;
                }
            }
            table[count++] = item;
        }
        return table;
    }

    public static void main(String[] args) {
        Unit[] units = new Unit[5];
        units[0] = new Unit(1000,500,3);
        units[1] = new Unit(600,300,3);
        units[2] = new Unit(500,250,3);
        units[3] = new Unit(330,150,3);
        units[4] = new Unit(300,0,3);
        BetterSolution bs = new BetterSolution();
        bs.units = units;
        bs.maxLoad = 1400;
        bs.minLoad = 1000;
        bs.sort();
        int minNum = bs.getMinNum();
        int maxNum = bs.getMaxNum();
        int[][] result = bs.getArrange(minNum,maxNum);
        for (int i=0;i<result.length;i++){
            for (int j=0;j<5;j++){
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }

    }
}
