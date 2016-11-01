package paper.startplan;

import paper.entity.Unit;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gyk on 2016/11/1.
 * 筛掉不符合最大最小限制的机组组合
 */
public class Filter {
    private Unit[] units;
    private List<int[]> table;
    private int maxLoad,minLoad;

    public Filter(Unit[] units, List<int[]> table, int maxLoad, int minLoad) {
        this.units = units;
        this.table = table;
        this.maxLoad = maxLoad;
        this.minLoad = minLoad;
    }

    public List<int[]> filter(){
        int sum_max,sum_min;
        Iterator iterator = table.iterator();
        while (iterator.hasNext()) {
            int[] item = (int[])iterator.next();
            sum_max = 0;
            sum_min = 0;
            for (int i = 0; i < item.length; i++) {
                Unit u = find(item[i]);
                sum_max += u.getMax();               //机组最大容量求和
                sum_min += u.getMin();               //机组最小容量求和
            }
            if (sum_max < maxLoad || sum_min > minLoad) {        //开机机组最大容量之和小于当天最大负荷
                iterator.remove();                   //不符合
            }
        }
        return table;
    }

    private Unit find(int index){
        for (int i=0;i<units.length;i++){
            if (units[i].getId() == index){
                return units[i];
            }
        }
        return null;
    }
}
