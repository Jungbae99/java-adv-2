package network.myprogram;

import network.tcp.SocketCloseUtil;
import network.tcp.v6.SessionManagerV6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.MyLogger.log;

public class ChatSession implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final ChatSessionManager sessionManager;
    private boolean closed = false;
    public String username;

    public ChatSession(Socket socket, ChatSessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();
                log("client -> server: " + received);

                if (received.equals("/exit")) {
                    break;
                }

                // 클라이언트에게 문자 보내기
                sendMessage(output, received);

                log("client <- server에게로 전송 완료.");
            }

        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }

    }

    private void sendMessage(DataOutputStream output, String received) throws IOException {
        if (received.startsWith("/join|{")) {
            username = received.substring(7, received.length() - 1);
            output.writeUTF(username + "사용자가 서버에 입장합니다.");
        } else if (received.startsWith("/change|{")) {
            if (username == null) {
                throw new IllegalArgumentException("이름을 먼저 생성하세요");
            }
            username = received.substring(9, received.length() - 1);
        } else if (received.startsWith("/users")) {
            if (username == null) {
                throw new IllegalArgumentException("이름을 먼저 생성하세요");
            }
            List<ChatSession> sessions = sessionManager.getSessions();
            List<String> answer = new ArrayList<>();
            for (ChatSession session : sessions) {
                answer.add(session.getUsername());
            }
            output.writeUTF(Arrays.toString(answer.toArray()));
        } else if (received.startsWith("/message|{")) {
            if (username == null) {
                throw new IllegalArgumentException("이름을 먼저 생성하세요");
            }

            String message = received.substring(10, received.length() - 1);

            List<ChatSession> sessions = sessionManager.getSessions();
            for (ChatSession session : sessions) {
                session.output.writeUTF(this.username + ": " + message);
            }
        }
    }

    // 세션 종료시, 서버 종료시 동시에 호출될 수 있다.
    public synchronized void close() {
        if (closed) {
            return;
        }
        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }

    public String getUsername() {
        return username;
    }
}

