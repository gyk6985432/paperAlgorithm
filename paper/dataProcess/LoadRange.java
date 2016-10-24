package paper.dataProcess;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by gyk on 2016/10/14.
 */
public class LoadRange {
    //    两月内的负荷数据
    private int[] maxLoads = new int[60];
    private int[] minLoads = new int[60];

    private String dataPath = "E:\\Demo\\luna-workspace\\paperAlgorithm\\paperAlgorithm\\data\\";

    private void getLoadRange() throws IOException{
        File file = new File(dataPath);
        String[] names = sortNames(file.list());
        for (int i=0;i<60;i++){
            int[] loads = new int[24];
            InputStream fis = new FileInputStream(dataPath+names[i]);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            int j=0;
            String line;
            while ((line = br.readLine()) != null){
                loads[j++] = Integer.valueOf(line);
            }
            int[] range = getRange(loads);
            minLoads[i] = range[0];
            maxLoads[i] = range[1];
        }
    }

    private int[] getRange(int[] load){
        int max = load[0];
        int min = load[0];
        for (int i=0;i<load.length;i++){
            if (load[i]>max){
                max = load[i];
            }
            if (load[i]<min){
                min = load[i];
            }
        }
        return new int[]{min,max};
    }

    private String[] sortNames(String[] names){
        int i,j;
        String temp;
        for (i=1;i<names.length;i++){
            temp = names[i];
            j=i-1;
            while(j>=0 && getId(temp) < getId(names[j])){
                names[j+1]=names[j];
                j--;
            }
            names[j+1]=temp;
        }
        return names;
    }

    private int getId(String name){
        return Integer.valueOf(name.split("\\.")[0]);
    }

    public LoadRange() {
        try {
            getLoadRange();
        } catch (IOException e) {
            System.out.println("获取负荷范围数据失败！getLoadRange()");
            e.printStackTrace();
        }
    }

    public int[] getMaxLoads() {
        return maxLoads;
    }

    public int[] getMinLoads() {
        return minLoads;
    }

    public static void main(String[] args) throws IOException{
        LoadRange lr = new LoadRange();
        lr.getLoadRange();
        for (int i=0;i<60;i++){
            System.out.println(lr.maxLoads[i]+"  "+lr.minLoads[i]);
        }
    }
}
