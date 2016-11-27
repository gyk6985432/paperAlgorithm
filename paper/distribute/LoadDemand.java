package paper.distribute;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by gyk on 2016/11/26.
 * 加载最新一天的负荷数据
 */
public class LoadDemand {
    private String dataPath = System.getProperty("user.dir")+"\\data\\";
    private int[] demands;

    public LoadDemand() throws IOException {
        demands = new int[24];
        loadDemand();
    }

    public int[] getDemands() {
        return demands;
    }

    private void loadDemand() throws IOException {
        File file = new File(dataPath);
        String[] names = file.list();
        String lastFilename = getLastFile(names);
        FileInputStream fis = new FileInputStream(dataPath+lastFilename);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        int j=0;
        String line;
        while ((line = br.readLine()) != null){
            demands[j++] = Integer.valueOf(line);
        }
        br.close();
        isr.close();
        fis.close();
        System.out.println("负荷数据加载完成。。。");
    }

    private String getLastFile(String[] names){
        String lastFile = names[0];
        int max = Integer.valueOf(lastFile.split("\\.")[0]);
        for (int i=1;i<names.length;i++){
            int temp = Integer.valueOf(names[i].split("\\.")[0]);
            if (temp>max){
                max = temp;
                lastFile = names[i];
            }
        }
        return lastFile;
    }

    public static void main(String[] args) throws IOException {
        LoadDemand ld = new LoadDemand();
        int[] demands = ld.getDemands();
        System.out.println(demands.length+"个时刻的负荷：");
        for (int demand:demands) {
            System.out.println(demand);
        }
    }
}
