package paper.utils;

/**
 * Created by gyk on 2016/10/24.
 */
public class Constant {
    public static String dataPath = System.getProperty("user.dir")+"\\data\\";
    public static String imagePath = System.getProperty("user.dir")+"\\images\\";

    static {
        imagePath = imagePath.replaceAll("\\\\","/");
    }

    public static void main(String[] args) {
        System.out.println(Constant.imagePath);
    }
}
