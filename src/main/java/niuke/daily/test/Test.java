package niuke.daily.test;

/**
 * @author: SMA
 * @date: 2017-11-26 22:55
 * @explain:
 */
public class Test {
    public static void main(String[] args) {
        String s1=new String("h")+new String("ello");
//        s1.intern();
        String s2="hello";
        System.out.println(s1==s2);


    }
}
