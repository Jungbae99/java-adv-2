package network.myprogram;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static util.MyLogger.log;

public class ChatClient {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        try (Socket socket = new Socket("localhost", PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            log("소켓 연결: " + socket);

            // 메시지 큐를 생성 -> 메인 스레드와 쓰기 스레드 간 통신에 사용
            BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

            // 쓰기 핸들러는 큐에서 메시지를 가져와 서버로 전송
            WriteHandler writeHandler = new WriteHandler(output, messageQueue);
            Thread sendThread = new Thread(writeHandler);
            sendThread.setDaemon(true); // 메인 스레드가 종료되면 함께 종료
            sendThread.start();

            // 읽기 핸들러는 서버로부터 메시지를 계속 받아옴
            ReadHandler readHandler = new ReadHandler(input);
            Thread receiveThread = new Thread(readHandler);
            receiveThread.setDaemon(true); // 메인 스레드가 종료되면 함께 종료
            receiveThread.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("전송 문자 : ");
                String toSend = scanner.nextLine();

                if (toSend.equals("/exit")) {
                    break;
                }

                // 입력된 메시지를 큐에 추가
                messageQueue.put(toSend);
            }
        } catch (IOException e) {
            log(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("메시지 전송 중 인터럽트 발생: " + e);
        }
    }


    static class WriteHandler implements Runnable {
        private final DataOutputStream output;
        private final BlockingQueue<String> messageQueue;

        public WriteHandler(DataOutputStream output, BlockingQueue<String> messageQueue) {
            this.output = output;
            this.messageQueue = messageQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // 큐에서 메시지를 가져옴 (메시지가 없으면 대기)
                    String toSend = messageQueue.take();
                    // 서버에게 메시지 전송
                    output.writeUTF(toSend);
                    log("client -> server: " + toSend);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("쓰기 핸들러 인터럽트: " + e);
            } catch (IOException e) {
                log("서버로 메시지 전송 중 오류: " + e);
            }
        }
    }

    static class ReadHandler implements Runnable {
        private final DataInputStream inputStream;

        public ReadHandler(DataInputStream input) {
            this.inputStream = input;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // 서버로부터 메시지를 계속 받음
                    String received = inputStream.readUTF();
                    log("client <- server: " + received);
                }
            } catch (IOException e) {
                // 서버 연결이 끊어졌거나 다른 I/O 오류 발생
                log("서버로부터 메시지 수신 중 오류: " + e);
            }
        }
    }
}
