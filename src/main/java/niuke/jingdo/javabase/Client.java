package niuke.jingdo.javabase;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Client client=new Client();
		client.client();
	}

	public void client() throws Exception{
		Socket socket=new Socket("localhost",10000);
		PrintWriter writer=new PrintWriter(socket.getOutputStream());

		StringBuilder builder=new StringBuilder();
		String temp;
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("E:\\Idea_Workplace\\hp-smart-whole\\src\\main\\resources\\tmp\\src")));
		while((temp=reader.readLine())!=null){
			builder.append(temp);
		}
		writer.write(builder.toString());
		writer.flush();
		writer.close();

	}
}
