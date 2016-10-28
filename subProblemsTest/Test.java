package subProblemsTest;

import paper.entity.Unit;

/**
 * Created by gyk on 2016/10/24.
 */
public class Test {
    public static void main(String[] args) {
        Unit p=new Unit();
        String s = p.getClass().getResource("/").getFile().toString();
        System.out.printf(s);
    }
}
