package paper.startplan;

import paper.entity.Unit;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by gyk on 2016/11/2.
 * 找出水电最多，火电最少的机组组合
 */
class TypeFilter {
    private Unit[] units;
    private List<int[]> table;

    TypeFilter(Unit[] units, List<int[]> table) {
        this.units = units;
        this.table = table;
    }

    List<int[]> filter(){
        int minThermalCount=0, maxHydroCount=0;
        int[] item;
        int[] counts;
        Iterator<int[]> iterator = table.iterator();
        if (iterator.hasNext()){
            item = iterator.next();
            counts = getTypeCounts(item);
            minThermalCount = counts[0];
            maxHydroCount = counts[1];
        }
        while (iterator.hasNext()){//获得最大最小火水电机组的数目
            item = iterator.next();
            counts = getTypeCounts(item);
            if (counts[0] <= minThermalCount){
                minThermalCount = counts[0];
                if (counts[1] >= maxHydroCount){
                    maxHydroCount = counts[1];
                }
            }
        }
        iterator = table.iterator();
        LinkedList<int[]> temp = new LinkedList<>();
        while (iterator.hasNext()){//找出最小火电机组，最大水电机组数目的组合
            item = iterator.next();
            counts = getTypeCounts(item);
            if (counts[0] == minThermalCount){
                if (counts[1] == maxHydroCount){
                    temp.add(item);
                }
            }
        }
        return temp;
    }

    private int[] getTypeCounts(int[] item){
        int[] counts = new int[2];
        for (int anItem : item) {
            String type = findType(anItem);
            if (Objects.equals(type, "thermal")) {
                counts[0]++;
            }
            if (Objects.equals(type, "hydro")) {
                counts[1]++;
            }
        }
        return counts;
    }

    private String findType(int index){
        for (Unit unit : units) {
            if (index == unit.getId()) {
                return unit.getType();
            }
        }
        return "";
    }
}
