package subProblemsTest;

import org.junit.Test;
import subProblems.Arrangement;
import subProblems.Unit;

/**
 * Created by gyk on 2016/5/20.
 */
public class ArrangementTest
{
    @Test
    public void testArrange(){
        Unit[] units = new Unit[4];
        for (int i = 0; i < units.length; i++) {
            units[i] = new Unit();
            units[i].setMax(i+1);
        }

        Arrangement a= new Arrangement(units);
        Unit[][] unitss = a.getAllArrangement();
        int[][] arr = new int[unitss.length][units.length];
        for(int j = 0; arr.length > j; j++){
            for(int i=0;i<arr[j].length;i++){
                arr[j][i] = unitss[j][i].getMax();
                System.out.print(arr[j][i]+" ");
            }
            System.out.println();
        }
        assert(arr.length == 24);
//        for(int j = 0; arr.length > j; j++){
//            for(int i=0;i<arr[j].length;i++){
//                System.out.print(arr[j][i]+" ");
//            }
//            System.out.println();
        //}
    }
}