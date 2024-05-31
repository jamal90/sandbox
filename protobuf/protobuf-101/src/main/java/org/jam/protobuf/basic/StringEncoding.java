package org.jam.protobuf.basic;

import com.google.protobuf.InvalidProtocolBufferException;
import org.jam.protobuf.Simple;

import java.util.Arrays;

public class StringEncoding {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        String unicode = "å∫∂œ£ñ"; //
//        String unicode = "ab"; // [10, 2, 97, 98]
        System.out.println("Unicode In: " + unicode);

        Simple unicodeString = Simple.newBuilder()
                .setName(unicode)
                .build();

        byte[] bytes = unicodeString.toByteArray();
        System.out.println("Serialized: " + Arrays.toString(bytes));

        Simple deserializedUnicode = Simple.parseFrom(bytes);
        System.out.println("Deserialized: " + deserializedUnicode.getName());
    }
}
