package was.httpserver;

import java.io.IOException;

public interface HttpServlet {

    // Http, Server, Applet 의 줄임말. 애플릿은 작은 자바 프로그램을 뜻한다.
    void service(HttpRequest request, HttpResponse response) throws IOException;

}
