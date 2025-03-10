package network.tcp.autocloseable;

public class ResourceV1 {

    private String name;

    public ResourceV1(String name) {
        this.name = name;
    }

    // 정상로직
    public void call() {
        System.out.println(name + " call");
    }

    // 비정상로직
    public void callEx() throws CallException {
        System.out.println(name + " callEx");
        throw new CallException(name + " ex");
    }

    // 정상종료
    public void close() {
        System.out.println(name + " close");
    }

    // 비정상종료
    public void closeEx() throws CloseException {
        System.out.println(name + " closeEx");
        throw new CloseException(name + " ex");
    }

}
