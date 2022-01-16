package com.hua.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoUse {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        TestServer.Request.Builder builder = TestServer.Request.newBuilder();

        builder.setMsgNo(1);
        builder.setMsg("hahaha");

        TestServer.Request request = builder.build();

        // 对象转换正常byte数组
        byte[] requestBytes =  request.toByteArray();


        System.out.println(requestBytes.length);

        // byte数组转换成为对象
        TestServer.Request request2 = TestServer.Request.parseFrom(requestBytes);
        System.out.println(request2.getMsg());

    }
}
