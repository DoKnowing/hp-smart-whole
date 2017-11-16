package niuke.wangyi.programming;

import java.util.Scanner;
//С��������һ���³��������Ϸ,��������Ϸ��һ����άƽ�����,С��������ԭ��(0,0),ƽ������nֻ����,ÿ�����������ڵ�����(x[i], y[i])��С�׽���һ��������x���y����(��������ԭ��)�Ĺ���һ��������
//С���������Ϸ��VIP���,��ӵ��������Ȩ����:
//1����ƽ���ڵ����й���ͬʱ������ͬһ�����ƶ�����ͬһ����
//2����ƽ���ڵ����й���ͬʱ����С��(0,0)��ת����ͬһ�Ƕ�
//С��Ҫ����һ�������С���ڽ������ǰ,����ʹ����������Ȩ��������Ρ�
//С����֪�����������ʱ��������ͬʱ�������ֻ����,������С�ס�
//

public class ShootGame {
	public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int num=sc.nextInt();
        int[] x=new int[num];
        int[] y=new int[num];
        for(int i=0;i<num;i++){
            x[i]=sc.nextInt();
        }
        for(int i=0;i<num;i++){
            y[i]=sc.nextInt();
        }
        int result=wipeOut(x,y);
        System.out.println(result);
    }
    
    public static int wipeOut(int[] x,int[] y){
        int max=0;
        int Ax,Ay,Bx,By,Cx,Cy,Dx,Dy,ABx,ABy,ADx,ADy,CDx,CDy;
        
        if(x.length<=3)
        	return x.length;
        
        for(int i=0;i<x.length;i++){
            Ax=x[i];
            Ay=y[i];
            for(int j=i+1;j<x.length;j++){
                Bx=x[j];
                By=y[j];
                ABx=Ax-Bx;
                ABy=Ay-By;
                for(int k=0;k<x.length;k++){
                    int temp=3;
                    Cx=x[k];
                    Cy=y[k];
                    
                    if(k!=i && k!=j){
                        for(int l=0;l<x.length;l++){
                            Dx=x[l];
                            Dy=y[l];
                            CDx=Cx-Dx;
                            CDy=Cy-Dy;
                            ADx=Ax-Dx;
                            ADy=Ay-Dy;
                            if(l!=i && l!=j && l!=k && (ABx*CDx+ABy*CDy==0  || ADy*ABx-ADx*ABy==0)){
                                temp++;
                            }
                        }
                    }
                    if(temp>max){
                        max=temp;
                    }
                }
            }
        }
        return max;
    }
}
