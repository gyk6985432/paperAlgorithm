package subProblems;

public class Arrangement2 {
	private static int[] p;
	static int k =0;
	int rows;
	int[][] p_temp;

	public void setP(int[] p) {
		this.p = p;
	}

	public Arrangement2(int[] p) {
		this.p = p;
		this.rows = getFactorial(p.length);
		p_temp = new int[rows][p.length];
	}

	//获得所有机组排列方式
	public int[][] getAllArrangement(){
		for(int i=0;i<p.length;i++) arrange(p, i, new int[0]);
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
	private void arrange(int[] q, int pos, int[] fromParent){
		if(q.length==0){
			return;
		}
		if(q.length==1){
			//System.out.println(q[0]);
			return;
		}

		int[] r=new int[p.length];

		r=add(fromParent,q[pos]);

		int[] sub = kickOne(q,pos);

		if(sub.length == 1){
			r=add(r,sub);
//          for(int i=0;i<r.length;i++){
//              System.out.print(r[i]+" ");
//          }
//          System.out.println();
			p_temp[k++]=r;
		}

		for(int i=0;i<sub.length;i++){
			arrange(sub,i,r);
		}
	}

	//int数组组合
	private int[] add(int[] fromParent, int e){
		int[] r = new int[fromParent.length+1];
		for(int i=0;i<fromParent.length;i++){
			r[i]=fromParent[i];
		}
		r[r.length-1]=e;
		return r;
	}
	//int数组组合重载
	private int[] add(int[] fromParent, int[] e){
		int[] r = new int[fromParent.length+e.length];
		for(int i=0;i<fromParent.length;i++){
			r[i]=fromParent[i];
		}
		for(int i=0;i<e.length;i++){
			r[i+fromParent.length]=e[i];
		}
		return r;
	}

	//踢掉pos位置的元素
	private int[] kickOne(int[] q, int pos){
		int[] sub = new int[q.length-1];
		for(int i=0;i<sub.length;i++){
			if(i < pos ){
				sub[i]=q[i];
			}else{
				sub[i] = q[i+1];
			}
		}
		return sub;
	}


}