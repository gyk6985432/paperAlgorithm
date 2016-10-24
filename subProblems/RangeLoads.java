package subProblems;

import java.util.*;

/**
 * Created by gyk on 2016/5/20.
 */
public class RangeLoads {
    private int demand_max;     //当天最大负荷
    private int demand_min;     //当天最小负荷

    public void setDemand_min(int demand_min) {
        this.demand_min = demand_min;
    }

    public void setDemand_max(int demand_max) {
        this.demand_max = demand_max;
    }

    /**
     * 找出一个符合不等式条件的机组组合
     */
    private Unit[] isAccord(Unit[] ps){
        Unit[] units;
        int[] ds_max = new int[ps.length];
        int[] ds_min = new int[ps.length];
        for (int i = 0; i < ps.length; i++) {
            ds_max[i]=ps[i].getMax();
            ds_min[i]=ps[i].getMin();
        }
        int sum_max=0;
        int sum_min=0;
        int k = 0;
        for (int i = 0; i < ps.length; i++) {
            sum_max += ds_max[i];               //机组最大容量求和
            sum_min += ds_min[i];               //机组最小容量求和
            k++;
            if (sum_max >= demand_max) {        //开机机组最大容量之和大于当天最大负荷
                if (sum_min > demand_min) {     //开机机组最小容量之和大于当天最小负荷
                    k = 0;                      //不符合，返回空数组
                    break;
                }else {                         //符合不等式条件，返回长度为k的数组
                    break;
                }
            }
        }
        units = new Unit[k];
        System.arraycopy(ps,0,units,0,k);       //将ps前k个Unit拷贝给units，并返回
        return units;
    }

    /**
     * 将机组组合按最大出力进行排序
     * 计数排序法，O(n)，要求k大于最大的元素值
     */
    public Unit[] sort(Unit[] units, int k) throws Exception {
        if (units.length<=0){
            return new Unit[0];
        }

        Unit[] us = new Unit[units.length];
        int[] counter= new int[k+1]; //计数器

        if(units.length<=1){
            return units;
        }
        for (int i = 0; i < units.length; i++) {
            int value = units[i].getMax();
            if (value < 0 || value > k){
                throw new Exception("元素不在0~k的范围内");
            }
            counter[value] ++;
        }
        for (int i = 1; i < counter.length; i++) {          //对计数器加和
            counter[i] += counter[i-1];
        }
        for (int i = units.length-1;i>=0;i--){              //将元素放入新数组中
            Unit u = units[i];
            int value = u.getMax();
            int position = counter[value]-1;

            us[position] = u;
            counter[value] --;
        }
        return  us;
    }

    /**
     * 获得所有符合条件的机组组合，并将相同的元素筛去
     */
    public List<Unit[]> getResults(Unit[] units) throws Exception {
        List<Unit[]> results = new ArrayList<>();       //用于放不重复的机组组合
        Arrangement a = new Arrangement(units);
        Unit[][] arrs = a.getAllArrangement();          //获得所有机组组合的全排列
        for (int i = 0; i <arrs.length; i++) {
            Unit[] rs = sort(isAccord(arrs[i]),1100);      //将机组组合全排列中的每一项按机组容量排序
            if (rs.length > 0 ){
                boolean b =true;
                for (Unit[] u:results) {
                    if (Arrays.equals(u, rs)){          //筛去含有相同机组组合的项
                        b = false;
                        break;
                    }
                }
                if (b){
                    results.add(rs);
                }
            }

        }
        return results;
    }
}
