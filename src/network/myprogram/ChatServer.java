package network.myprogram;

import network.tcp.v6.ServerV6;
import network.tcp.v6.SessionManagerV6;
import network.tcp.v6.SessionV6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static util.MyLogger.log;

public class ChatServer {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ChatSessionManager sessionManager = new ChatSessionManager();
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT); // ShutdownHook 등록이 필요함

        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));

        try {
            while (true) {
                Socket socket = serverSocket.accept(); // 블로킹 메서드
                log("소켓 연결: " + socket);

                ChatSession session = new ChatSession(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();

            }
        } catch (IOException e) {
            log("서버 소켓 종료: " + e);
        }

    }

    static class ShutdownHook implements Runnable {

        private final ServerSocket serverSocket;
        private final ChatSessionManager sessionManager;

        public ShutdownHook(ServerSocket serverSocket, ChatSessionManager sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdown hook 실행");
            try {
                sessionManager.closeAll();
                serverSocket.close();

                Thread.sleep(1000); // 자원 정리 대기 (로그도 남겨야하고...이것 저것 할 시간은 줘야지)
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }
        }
    }

}
