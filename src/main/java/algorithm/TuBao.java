package algorithm;

import org.junit.Test;

import java.util.*;

public class TuBao {
    class Point{
        int x;
        int y;
        public Point(int x,int y){
            this.x=x;
            this.y=y;
        }

        @Override
        public String toString() {
            return x+" "+y;
        }
    }

    public void sortPolarAngle(Point p,List points){
        for(int i=1;i<points.size();i++){
            int j=i-1;
            Point temp=(Point)points.get(i);
            while(j>=0){
                if(nonLeftTurn(p,(Point)points.get(j),temp)<0){
                    points.set(j+1,points.get(j));
                    j--;
                }else if(nonLeftTurn(p,(Point)points.get(j),temp)==0){
                    if(temp.y<((Point)points.get(j)).y){
                        points.remove((Point)points.get(j+1));
                        temp=(Point)points.get(j);
                    }else{
                        points.remove((Point)points.get(j));
                    }
                    j--;
                }else{
                    break;
                }

            }
            points.set(j+1,temp);
        }
    }

    public double nonLeftTurn(Point p0,Point p1,Point p2){
        return (p2.y-p0.y)*(p1.x-p0.x)-(p1.y-p0.y)*(p2.x-p0.x);
    }

    public Point findMinY(List points){
        Point point=(Point)points.get(0);
        for(int i=1;i<points.size();i++){
            if(((Point)points.get(i)).y<point.y){
                point=(Point)points.get(i);
            }
        }
        return point;
    }
    public Stack grahamScan(List points){
        Stack stack=new Stack();
        Point startPoint=findMinY(points);
        points.remove(startPoint);
        stack.push(startPoint);
        sortPolarAngle(startPoint,points);
        stack.push(points.get(0));
        for(int i=1;i<points.size();i++){
            Point temp=(Point)points.get(i);
            while(stack.size()>=2 && nonLeftTurn((Point)stack.get(stack.size()-1),(Point)stack.get(stack.size()-2),temp)>0){
                stack.pop();
            }
            stack.push(temp);
        }
        return stack;
    }

    @Test
    public void test(){
        int []arr0={3,5,4,3,2,3,1,7};
        int []arr1={4,3,7,1,6,2,4,4};
        List l=new LinkedList();
        for(int i=0;i<arr0.length;i++){
            l.add(new Point(arr0[i],arr1[i]));
        }
        Stack s=grahamScan(l);
        for(int i=0;i<s.size();i++){
            System.out.println(s.get(i));
        }
    }
}
