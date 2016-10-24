package subProblems;

/**
 * Created by gyk on 2016/5/20.
 */
public class Arrangement {
    private Unit[] p;
    static int k =0;
    int rows;
    Unit[][] p_temp;

    public void setP(Unit[] p) {
        this.p = p;
    }

    public Arrangement(Unit[] p) {
        this.p = p;
        this.rows = getFactorial(p.length);
        p_temp = new Unit[rows][p.length];

    }

    //获得所有机组排列方式
    public Unit[][] getAllArrangement(){
        for(int i=0;i<p.length;i++) arrange(p, i, new Unit[0]);
//        for(int j=0;j<p_temp.length;j++){
//            for(int i=0;i<p_temp[j].length;i++){
//                System.out.print(p_temp[j][i]+" ");
//            }
//            System.out.println();
//        }
        return p_temp;
    }

    //正数的阶乘
    private int getFactorial(int i){
        if(i==1){
            return 1;
        }
        if(i<=0){
            return 0;
        }else{
            return i*getFactorial(i-1);
        }
    }

    //递归获得排列组合
    private void arrange(Unit[] q, int pos, Unit[] fromParent){
        if(q.length==0){
            return;
        }
        if(q.length==1){
            //System.out.println(q[0]);
            return;
        }

        Unit[] r;

        r=add(fromParent,q[pos]);

        Unit[] sub = kickOne(q,pos);

        if(sub.length == 1){
            r=add(r,sub);
            p_temp[k++]=r;
        }

        for(int i=0;i<sub.length;i++){
            arrange(sub,i,r);
        }
    }

    //int数组组合
    private Unit[] add(Unit[] fromParent, Unit e){
        Unit[] r = new Unit[fromParent.length+1];
        System.arraycopy(fromParent,0,r,0,fromParent.length);
        r[r.length-1]=e;
        return r;
    }
    //int数组组合重载
    private Unit[] add(Unit[] fromParent, Unit[] e){
        Unit[] r = new Unit[fromParent.length+e.length];
        System.arraycopy(fromParent,0,r,0,fromParent.length);
        System.arraycopy(e,0,r,fromParent.length,e.length);
        return r;
    }

    //踢掉pos位置的元素
    private Unit[] kickOne(Unit[] q, int pos){
        Unit[] sub = new Unit[q.length-1];
        if (pos==0){
            System.arraycopy(q,1,sub,0,sub.length);
            return sub;
        }
        System.arraycopy(q,0,sub,0,pos);
        System.arraycopy(q,pos+1,sub,pos,q.length-pos-1);

        return sub;
    }

}
