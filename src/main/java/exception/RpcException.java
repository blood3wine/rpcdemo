package exception;

import protocol.enumeration.RpcError;

/**
 * RPC调用异常
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}
