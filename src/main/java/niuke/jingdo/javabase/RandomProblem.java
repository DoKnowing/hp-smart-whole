package niuke.jingdo.javabase;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

public class RandomProblem {
    public static void main(String []args){
        TreeSet<Character> set=getRandom();
        for (char ch:set) {
            System.out.print(ch);
        }
    }

    public static TreeSet<Character> getRandom(){
        TreeSet<Character> treeSet=new TreeSet<>(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                if(o1>o2){
                    return -1;
                }else if(o1<o2){
                    return 1;
                }
                return 0;
            }
        });
        Random random=new Random();
        while(treeSet.size()<20){
            treeSet.add((char)(Math.abs(random.nextInt())%26+97));
        }

        return treeSet;
    }
}
