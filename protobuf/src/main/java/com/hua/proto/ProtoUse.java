package com.hua.proto;

public class ProtoUse {
    public static void main(String[] args) {
        TestProto.Request.Builder builder = TestProto.Request.newBuilder();
        builder.setMsg("sss");
        builder.setMsgNo(2);

        System.out.println(builder);
        TestProto.Request build = builder.build();

        build.toByteString();

        System.out.println();
    }
}
