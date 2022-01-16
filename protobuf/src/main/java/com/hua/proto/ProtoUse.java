package com.hua.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoUse {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        TestProto.Request.Builder builder = TestProto.Request.newBuilder();

        builder.setMsgNo(1);
        builder.setMsg("hahaha");

        TestProto.Request request = builder.build();

        // 对象转换正常byte数组
        byte[] requestBytes =  request.toByteArray();


        System.out.println(requestBytes.length);

        // byte数组转换成为对象
        TestProto.Request request2 = TestProto.Request.parseFrom(requestBytes);
        System.out.println(request2.getMsg());

    }
}
