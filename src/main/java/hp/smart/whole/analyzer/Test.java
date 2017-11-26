package hp.smart.whole.analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Test {
    @org.junit.Test
    public void test() throws IOException {
        TreeMap c1=new TreeMap();
        c1.remove("");
//        Collection c2=new LinkedList();
//        System.out.println(c1.equals(c2));

//        Set st=new HashSet();
//        InetAddress ad=InetAddress.getByName("www.nba.com");
//        System.out.println(ad);
//        byte[] add=ad.getAddress();

        Socket socket=new Socket("internic.net",43);
        InputStream in=socket.getInputStream();
        OutputStream out=socket.getOutputStream();
        byte [] dns="osborne.com".getBytes();
        int c;
        out.write(dns);
        while ((c=in.read())!=-1){
            System.out.print(c);
        }
        out.close();
        in.close();
    }
}
