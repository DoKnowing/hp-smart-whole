package algorithm;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ArtPoint {
    private static final int WHITE=0;
    private static final int RED=1;
    private static final int BLACK=2;
    private int[] low;
    private int[] visited;
    Set arts=new HashSet();

    public void searchArtPoint(int[][] graph){
        visited=new int[graph.length];
        low=new int[graph.length];
        int childNum=0;
        visited[0]=1;
        for(int i=1;i<graph[0].length;i++){
            if(graph[0][i]==1 && visited[i]==0){
                graph[i][0]=0;
                articular(graph,i);
                childNum++;
            }
        }
        if(childNum>=2){
            arts.add(0);
        }
    }

    public int min(int a,int b){
        return a<=b?a:b;
    }

    public void articular(int[][] graph,int point){
        low[point]=point;
        visited[point]=1;
        for(int i=0;i<low.length;i++){
            if(graph[point][i]==1){
                if(visited[i]==0){
                    graph[i][point]=0;
                    articular(graph,i);
                    if(low[i]>=point){
                        arts.add(point);
                    }else{
                        low[point]=min(low[i],low[point]);
                    }
                }else{
                    low[point]=min(low[point],i);
                }

            }

        }

    }

    @Test
    public void test(){
        int[][] graph={{0,1,0,0,1,1,0,0,0,0},
                {1,0,1,0,0,1,0,0,0,0},
                {0,1,0,1,0,1,1,0,0,0},
                {0,0,1,0,0,0,0,1,0,0},
                {1,0,0,0,0,0,0,0,1,1},
                {1,1,1,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,1,0,0},
                {0,0,0,1,0,0,1,0,0,0},
                {0,0,0,0,1,0,0,0,0,1},
                {0,0,0,0,1,0,0,0,1,0}};
        searchArtPoint(graph);
        System.out.println(arts.toString());
    }
}
