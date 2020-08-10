package rpcApi;

public class SayServiceImpl implements SayService {
    public String hello(String msg) {

        return msg != null ? msg + ":Hello" : "未接收到消息";
    }
    public String bye(String msg) {

        return msg != null ? msg + ":bye" : "未接收到消息";
    }
}
