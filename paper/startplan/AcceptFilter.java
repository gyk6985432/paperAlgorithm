package paper.startplan;

import paper.entity.Unit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gyk on 2016/11/3.
 * 获得煤耗率最低的一组组合，本文默认机组容量越大，煤耗率越低，因此选择容量最大的一组组合
 */
class AcceptFilter {
    private Unit[] units;
    private List<int[]> table;
    private int[] bestPlan;

    AcceptFilter(Unit[] units, List<int[]> table) {
        this.units = units;
        this.table = table;
    }

    int[] filter(){
        int[] item;
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        Iterator<int[]> iterator = table.iterator();
        if (iterator.hasNext()){
            item = iterator.next();
            for (int i=0;i<item.length;i++){
                if (findType(item[i]) == "thermal"){
                    list1.add(find(item[i]).getMax());
                }
            }
            bestPlan = item;
            list1.sort(new MyComparator());
        }
        while (iterator.hasNext()){
            item = iterator.next();
            for (int i=0;i<item.length;i++){
                if (findType(item[i]) == "thermal"){
                    list2.add(find(item[i]).getMax());
                }
            }
            list2.sort(new MyComparator());
            for (int i=0;i<list1.size();i++){
                if (list2.get(i)>list1.get(i)) bestPlan = item;
            }
        }
        return bestPlan;
    }

    private String findType(int index){
        for (int i=0;i<units.length;i++){
            if (index == units[i].getId()){
                return units[i].getType();
            }
        }
        return "";
    }

    private Unit find(int index){
        for (int i=0;i<units.length;i++){
            if (units[i].getId() == index){
                return units[i];
            }
        }
        return null;
    }

    private class MyComparator implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            if (o1 > o2)
                return 1;
            else if (o1 < o2)
                return -1;
            else return 0;
        }
    }


}
