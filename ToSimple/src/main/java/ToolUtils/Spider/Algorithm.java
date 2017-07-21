package ToolUtils.Spider;

import java.io.IOException;

public class Algorithm {
	public static int getStringGap(String s,String t){
		
		int cost = 0;
		int n = s.length();
		int m = t.length();
		int[][] d = new int[n+1][m+1];
		if(n==0){return m;}
		if(m==0){return n;}

		for(int i=0;i<=n;i++){
			d[i][0] = i;
		}
		for(int j=0;j<=m;j++){
			d[0][j] = j;
		}
		for(int i=1;i<=n;i++){
			int s_i = s.charAt(i-1);
			for(int j=1;j<=m;j++){
				int t_j = t.charAt(j-1);
				if(s_i==t_j){
					cost=0;	
				}else{
					cost=1;
				}
				d[i][j]=min(d[i-1][j]+1,d[i][j-1]+1,d[i-1][j-1]+cost);
			}
		}
		return d[n][m];
	}
	
	public static int min(int a,int b,int c){
		int m;
		m = a;
		if(b<m){m=b;}
		if(c<m){m=c;}
		return m;
	}
	


	public static void main(String[] args) throws IOException   {
		System.out.println(getStringGap("手机对大学生的影响","王者荣耀对大学生手机依赖的影响调查"));
	}

}
