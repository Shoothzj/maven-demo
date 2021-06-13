package com.github.shoothzj.demo.bookkeeper;

import com.github.shoothzj.javatool.util.HexUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.proto.BookkeeperProtocol;

import java.util.Arrays;

/**
 * @author hezhangjian
 */
@Slf4j
public class BkCodecUtil {

    private static final int HEX_PREFIX = 2;

    private static final int BYTE_PREFIX = 4;

    public static BookkeeperProtocol.Response decodeRespNetwork(String hexStr) {
        final byte[] bytes = HexUtil.hexStrToByteArray(hexStr);
        byte[] content = Arrays.copyOfRange(bytes, 4, bytes.length);
        return decodeRespContent(content);
    }

    public static BookkeeperProtocol.Response decodeRespContent(String hexStr) {
        return decodeRespContent(HexUtil.hexStrToByteArray(hexStr));
    }

    public static BookkeeperProtocol.Response decodeRespNetwork(byte[] bytes) {
        byte[] content = Arrays.copyOfRange(bytes, 2, bytes.length);
        return decodeRespContent(content);
    }

    public static BookkeeperProtocol.Response decodeRespContent(byte[] bytes) {
        try {
            return BookkeeperProtocol.Response.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            log.error("invalid proto buf is ", e);
            return null;
        }
    }

    public static BookkeeperProtocol.Request decodeReqNetwork(String hexStr) {
        final byte[] bytes = HexUtil.hexStrToByteArray(hexStr);
        byte[] content = Arrays.copyOfRange(bytes, 4, bytes.length);
        return decodeReqContent(content);
    }

    public static BookkeeperProtocol.Request decodeReqContent(String hexStr) {
        return decodeReqContent(HexUtil.hexStrToByteArray(hexStr));
    }

    public static BookkeeperProtocol.Request decodeReqNetwork(byte[] bytes) {
        byte[] content = Arrays.copyOfRange(bytes, 2, bytes.length);
        return decodeReqContent(content);
    }

    public static BookkeeperProtocol.Request decodeReqContent(byte[] bytes) {
        try {
            return BookkeeperProtocol.Request.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            log.error("invalid proto buf is ", e);
            return null;
        }
    }

}
