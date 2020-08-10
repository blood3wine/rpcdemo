import netty.client.NettyClient;
import proxy.RpcClientProxy;
import rpcApi.SayService;

/**
 * 客户端
 */
public class NettyTestClient {

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);

        SayService rpcHello = rpcClientProxy.getProxy(SayService.class);
        System.out.println(rpcHello.hello("1say"));

        SayService rpcBye = rpcClientProxy.getProxy(SayService.class);
        System.out.println(rpcBye.bye("2say"));

    }

}
