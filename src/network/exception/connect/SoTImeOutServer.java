package network.exception.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SoTImeOutServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(12345);

        Socket socket = serverSocket.accept();
        Thread.sleep(10000000); // 잠자는 서버를 만났다. 연결은 되었지만 응답을 안함
    }
}
