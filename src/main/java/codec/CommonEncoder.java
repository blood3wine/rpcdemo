package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocol.RpcRequest;
import protocol.enumeration.PackageType;
import serializer.CommonSerializer;

/**
 * 编码拦截器
 *
 *
 * +---------------+---------------+-----------------+
 * |  Magic Number |  Package Type |  Data Length |
 * |    4 bytes    |    4 bytes    |   4 bytes    |
 * +---------------+---------------+-----------------+
 * |                     Data Bytes                  |
 * |              Length: ${Data Length}             |
 * +-------------------------------------------------+
 */
public class CommonEncoder extends MessageToByteEncoder {
    /**
     *  4 字节魔数，表识一个协议包
     *   Package Type，标明这是一个调用请求还是调用响应
     *   Data Length 就是实际数据的长度，设置这个字段防止粘包
     */
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

}
