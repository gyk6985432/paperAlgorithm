package subProblemsTest;

import org.junit.Test;
import subProblems.RangeLoads;
import subProblems.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gyk on 2016/5/20.
 */
public class RangeLoadsTest {
    @Test
    public void getResultTest() throws Exception {
        Unit[] units = new Unit[8];
        for (int i = 0; i < units.length; i++) {
            units[i] = new Unit();
        }
        units[0].setMax(1000);
        units[0].setMin(500);
        units[1].setMax(660);
        units[1].setMin(300);
        units[2].setMax(600);
        units[2].setMin(300);
        units[3].setMax(600);
        units[3].setMin(300);
        units[4].setMax(330);
        units[4].setMin(150);
        units[5].setMax(330);
        units[5].setMin(150);
        units[6].setMax(200);
        units[6].setMin(100);
        units[7].setMax(150);
        units[7].setMin(60);
        RangeLoads rl= new RangeLoads();
        rl.setDemand_max(3200);
        rl.setDemand_min(1890);
        List<Unit[]> result = rl.getResults(units);

        List<Integer> max = new ArrayList<>();
        List<Integer> min = new ArrayList<>();
        for (Unit[] u: result) {
            int h=0;
            int l=0;
            for (int i = 0; i <u.length; i++) {
                System.out.print(u[i].getMax()+", ");
                h += u[i].getMax();
                l += u[i].getMin();
            }
            System.out.print(h+"   "+l+"  "+(h-l));
            System.out.println();
            max.add(h);
            min.add(l);
        }
        System.out.println(result.size());
        Iterator i = max.iterator();
        Iterator j = min.iterator();
        int temp1=(int)i.next();
        int temp2=(int)i.next();
        while (i.hasNext()){
            if ((int)i.next()<temp1){
                temp1=(int)i.next();
            }
        }
        while (j.hasNext()){
            if ((int)j.next()<temp2){
                temp2=(int)j.next();
            }
        }
        System.out.println(temp1+"  "+temp2);
    }


    @Test
    public void sortTest() throws Exception {
        Unit[] units = new Unit[5];
        for (int i = 0; i < units.length; i++) {
            units[i] = new Unit();
            units[i].setMax(i+3);
            units[i].setMin(i);
        }
        RangeLoads rl= new RangeLoads();
        rl.setDemand_max(13);
        rl.setDemand_min(5);
        List<Unit[]> result = rl.getResults(units);
        Unit[] r = rl.sort(result.get(1),7);

        for (Unit re: r) {
                System.out.print(re.getMax());

        }
    }


    @Test
    public void arrayEqual(){
        String [] array1 = {"1","2","3"};
        String [] array2 = {"3","2","1"};
        Arrays.sort(array1);
        Arrays.sort(array2);
        if (Arrays.equals(array1, array2)) {
            System.out.println(true+"两个数组中的元素值相同");
        } else {
            System.out.println(false+"两个数组中的元素值不相同");
        }
    }
}
