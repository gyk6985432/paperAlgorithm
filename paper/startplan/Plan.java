package paper.startplan;

import paper.entity.Unit;

import java.util.List;

/**
 * Created by gyk on 2016/11/1.
 */
public class Plan {
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
    public List<int[]> getArrange(int minNum, int maxNum){
        int[] idArr = new int[units.length];
        for (int i=0;i<units.length;i++)
            idArr[i] = units[i].getId();
        PossiblePlans possiblePlans = new PossiblePlans(idArr,maxNum,minNum);
        List<int[]> table = possiblePlans.getPossiblePlans();
        DemandFilter demandFilter = new DemandFilter(units,table,maxLoad,minLoad);
        table = demandFilter.filter();
        TypeFilter typeFilter = new TypeFilter(units,table);
        table = typeFilter.filter();
        return table;
    }


    public static void main(String[] args) {
//      火电：1000，1000，660，600，600，350，330
//      水电：700，250，150
//      装机容量：4540 + 1100 = 5640
//      负荷需求范围：3120——5200
        Unit[] units = new Unit[10];
        units[0] = new Unit(1,1000,500,"thermal");
        units[1] = new Unit(2,1000,500,"thermal");
        units[2] = new Unit(3, 660,300,"thermal");
        units[3] = new Unit(4, 600,300,"thermal");
        units[4] = new Unit(5, 600,300,"thermal");
        units[5] = new Unit(6, 350,150,"thermal");
        units[6] = new Unit(7, 330,150,"thermal");
        units[7] = new Unit(8, 700,0,"hydro");
        units[8] = new Unit(9, 250,0,"hydro");
        units[9] = new Unit(10, 150,0,"hydro");
        Plan plan = new Plan();
        plan.units = units;
        plan.maxLoad = 5000;
        plan.minLoad = 3320;
        plan.sort();
        int minNum = plan.getMinNum();
        int maxNum = plan.getMaxNum();
        List<int[]> result = plan.getArrange(minNum,maxNum);
        for (int i=0;i<result.size();i++){
            for (int j=0;j<result.get(i).length;j++){
                System.out.print(result.get(i)[j]+" ");
            }
            System.out.println();
        }

    }
}
