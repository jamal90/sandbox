package org.jam.protobuf.basic;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.jam.protobuf.Nested;

import java.util.Arrays;

public class NestedSample {

    private static Nested.Inner createNewInner(String propOne, String propTwo) {
        return Nested.Inner.newBuilder()
                .setPropOne(propOne)
                .setPropTwo(propTwo)
                .build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        Nested build = Nested.newBuilder()
                .setInner(createNewInner("abc", "def"))
                .setOuterString("outer")
                .addRepeatedInner(createNewInner("inner-1", "inner-2"))
                .addRepeatedInner(createNewInner("inner-2", "inner-3"))
                .build();

        System.out.println(build);

        // serialize
        byte[] serialized = build.toByteArray();
        System.out.println(Arrays.toString(serialized));

        // deserialize
        System.out.println(Nested.parseFrom(serialized));

        // serialize to json
        String jsonStr = JsonFormat.printer().omittingInsignificantWhitespace().print(build);
        System.out.println(jsonStr);

        // deserialize from json

        Nested.Builder builder = Nested.newBuilder();
        JsonFormat.parser().merge(jsonStr, builder);
        Nested build1 = builder.build();
        System.out.println(build1);
    }

}
