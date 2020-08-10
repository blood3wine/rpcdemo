import netty.server.NettyServer;
import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;
import rpcApi.SayService;
import rpcApi.SayServiceImpl;

/**
 * 服务端
 */
public class NettyTestServer {

    public static void main(String[] args) {
        SayService helloService = new SayServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}
