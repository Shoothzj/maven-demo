package com.github.shoothzj.demo.bookkeeper;

import com.github.shoothzj.sdk.bookkeeper.util.BkCodecUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.proto.BookkeeperProtocol;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookKeeperRequestDecode {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        final String hexStr = "0a060803100818051000ca0610080010808090ceac0f1880e0c6bdcf03";
        final BookkeeperProtocol.Response response = BkCodecUtil.decodeRespContent(hexStr);
        System.out.println(response);
    }

}
