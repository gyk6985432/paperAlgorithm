package paper.dataProcess;

import java.io.*;
import java.util.Random;

/**
 * Created by gyk on 2016/10/14.
 */
public class CreateData {
//    日变化
//    1	 2	3	4	5	6	7	8	9	10	11	12
//    35 28	21	10	0	4	20	31	49	63	69	65
//    13 14	15	16	17	18	19	20	21	22	23	24
//    52 48	53	70	78	75	68	82	98	80	50  42
    private static int[] dayPercent = {35,28,21,10,0 ,4 ,20,31,49,63,69,65,
                                       52,48,53,70,78,75,68,82,98,80,50,42};
//    周变化
//    1 	2	3	4	5	6	7
//    100	99	97	98	98	90	90
    private static int[] weekPercent = {100,99,97,98,98,94,94};

//    最大负荷5200，最小负荷3120，
    private static int MAXLOAD = 5200;
    private static int MINLOAD = 3120;

//    数据保存路径
    private String dataPath = "E:\\Demo\\luna-workspace\\paperAlgorithm\\paperAlgorithm\\data\\";

//    日负荷公式：DAYLOAD = MINLOAD + (MAXLOAD - MINLOAD)× (dayPercent + 5 - rand(5))
//    日负荷×周系数公式：DAYLOAD = DAYLOAD × (weekPercent + 5 - rand(5))
    private int week = 1;
    public int[] createDataForDay(){
        if (week > 7){
            week = 1;
        }
        Random rand = new Random();
        int load[] = new int[24];
        for (int i=1;i<=24;i++){
            load[i-1] =(int)( MINLOAD + (MAXLOAD-MINLOAD) * (dayPercent[i-1] + 5 - 5 * rand.nextDouble())/100);
            load[i-1] =(int)(load[i-1] * (weekPercent[week-1] + 5 - 5 * rand.nextDouble())/100);
        }
        week++;
        return load;
    }

//    写入文件，将新生成的负荷数据写入文件中，并把原来多余的删去
    public void writeDataForDay(int[] load) throws IOException {
        File file = new File(dataPath);
        String[] names = file.list();
        FileWriter fileWriter = new FileWriter(dataPath+names.length+".txt");
        for (int i=0;i<load.length;i++){
            fileWriter.append(load[i]+"\r\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException{
        CreateData cd = new CreateData();
        for (int i=0;i<60;i++){
            int[] dataForDay =  cd.createDataForDay();
            cd.writeDataForDay(dataForDay);
        }
    }

}
