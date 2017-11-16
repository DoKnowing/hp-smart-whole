package niuke.jingdo.javabase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
//题目描述
//		现分别有一个通过socket发送文件的客户端以及一个通过socket接收文件的服务端：
//		a) 客户端从/tmp/src.data文件中读取文件内容，通过网络socket将文件内容发给服务端
//		b) 服务端监听10000端口，当10000端口接收到客户端连接请求时，从连接读取文件内容，并写入/tmp/dst.data中
//
//		1. 试写出客户端和服务端的具体实现代码（15分）
//		2. 如果客户端需要知道服务端已经完全接收到所有文件数据并成功写入/tmp/dst.data，有何实现方式？请说出你的思路（5分）
public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server server=new Server();
		server.serve();
	}

	public void serve() throws IOException {
		ServerSocket serverSocket=new ServerSocket(10000);
		while(true){
			Socket socket=serverSocket.accept();
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder stringBuilder=new StringBuilder();
			String str;
			while((str=reader.readLine())!=null){
				stringBuilder.append(str+"\n");
			}
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\test\\dest.data")));
			writer.write(stringBuilder.toString());
			writer.flush();
			writer.close();
		}
	}
}
