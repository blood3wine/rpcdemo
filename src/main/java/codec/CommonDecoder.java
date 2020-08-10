package codec;

import exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocol.RpcRequest;
import protocol.RpcResponse;
import protocol.enumeration.PackageType;
import protocol.enumeration.RpcError;
import serializer.CommonSerializer;
import serializer.JsonSerializer;

import java.util.List;

/**
 * 解码拦截器
 */
public class CommonDecoder extends ByteToMessageDecoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();
        if (magic != MAGIC_NUMBER) {
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packageCode = in.readInt();
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        CommonSerializer serializer =new JsonSerializer();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, packageClass);
        out.add(obj);
    }
}
