package org.jam.protobuf.basic;

import org.jam.protobuf.Simple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ProtobufSimple {

    public static void main(String[] args) throws IOException {
        Simple hello = Simple.newBuilder().setName("Hello").build();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        hello.writeTo(byteArrayOutputStream);
        byte[] serializedMsg = byteArrayOutputStream.toByteArray();

        Simple deserializedMsg = Simple.newBuilder().mergeFrom(serializedMsg).build();
        Simple deserializedMsg2 = Simple.parseFrom(serializedMsg);

        System.out.println(Arrays.toString(serializedMsg));
        System.out.println(deserializedMsg);
        System.out.println(deserializedMsg2);
    }
}
