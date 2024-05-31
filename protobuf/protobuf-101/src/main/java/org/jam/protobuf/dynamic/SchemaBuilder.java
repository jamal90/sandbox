package org.jam.protobuf.dynamic;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;

import static com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type.*;

public class SchemaBuilder {

    // Ref: https://stackoverflow.com/questions/68667180/protobuf-java-create-a-descriptor-from-descriptorproto

    // Check out protobuf-dynamic library for ref - https://github.com/os72/protobuf-dynamic
    // lib is only 3 classes - can probably have a similar builder locally

    public static void main(String[] args) throws Descriptors.DescriptorValidationException, InvalidProtocolBufferException {
        // build schema programmatically for MotorType.proto message
        Descriptors.Descriptor motorTypeSchema = buildDynamicSchema();

        DynamicMessage dynMessage = buildDynamicMessage(motorTypeSchema);
        byte[] dynMessageSerialized = dynMessage.toByteArray();
        DynamicMessage dynMessage2 = DynamicMessage.parseFrom(motorTypeSchema, dynMessageSerialized);
        System.out.println("Dynamic Message Json: " + JsonFormat.printer().preservingProtoFieldNames().print(dynMessage2));
        System.out.println("Dynamic Message Proto:" + dynMessage2);


    }

    private static DynamicMessage buildDynamicMessage(Descriptors.Descriptor motorType) {
        Descriptors.Descriptor measurementType = motorType.findFieldByName("measurements").getMessageType();
        DynamicMessage.Builder measurementBuilder = DynamicMessage.newBuilder(measurementType);
        DynamicMessage measurement = measurementBuilder.setField(measurementType.findFieldByName("_time"), Instant.now().toEpochMilli())
                .setField(measurementType.findFieldByName("_path"), "/motionDevice/PowerTrain/Gear")
                .setField(measurementType.findFieldByName("GearRatio"), new Random().nextDouble())
                .setField(measurementType.findFieldByName("Pitch"), new Random().nextDouble())
                .build();

        DynamicMessage.Builder messageBuilder = DynamicMessage.newBuilder(motorType);
        DynamicMessage dynMessage = messageBuilder.setField(motorType.findFieldByName("object_id"), "EQ1")
                .setField(motorType.findFieldByName("object_type"), "KT_PUMP")
                .addRepeatedField(motorType.findFieldByName("measurements"), measurement)
                .build();
        return dynMessage;
    }

    private static Descriptors.Descriptor buildDynamicSchema() throws Descriptors.DescriptorValidationException {
        DescriptorProtos.DescriptorProto measurementDescriptorProto = DescriptorProtos.DescriptorProto.newBuilder()
                .setName("Measurement")
                .addField(createField("_time", TYPE_INT64, 1))
                .addField(createField("_path", TYPE_STRING, 2))
                .addField(createField("GearRatio", TYPE_DOUBLE, 3))
                .addField(createField("Pitch", TYPE_DOUBLE, 4))
                .build();

        DescriptorProtos.DescriptorProto messageProto = DescriptorProtos.DescriptorProto.newBuilder()
                .setName("MotorType")
                .addField(createField("object_id", TYPE_STRING, 1))
                .addField(createField("object_type", TYPE_STRING, 2))
                .addNestedType(measurementDescriptorProto)
                .addField(DescriptorProtos.FieldDescriptorProto.newBuilder()
                        .setName("measurements")
                        .setLabel(DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED)
                        .setType(TYPE_MESSAGE)
                        .setTypeName("Measurement")
                        .setNumber(3)
                        .build())
                .build();

        DescriptorProtos.FileDescriptorProto fileDescriptorProto = DescriptorProtos.FileDescriptorProto.newBuilder()
                .setName("MotorType.proto")
                .addMessageType(messageProto)
                .build();

        // serialize schema to file & restore (e.g., saving to DB)
        byte[] protoDef = fileDescriptorProto.toByteArray();
        System.out.println("Schema File: " + new String(protoDef, StandardCharsets.UTF_8));

        try {
            fileDescriptorProto = DescriptorProtos.FileDescriptorProto.parseFrom(protoDef);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }

        Descriptors.FileDescriptor fileDescriptor = Descriptors.FileDescriptor.buildFrom(fileDescriptorProto, new Descriptors.FileDescriptor[]{});
        return fileDescriptor.findMessageTypeByName("MotorType");
    }

    private static DescriptorProtos.FieldDescriptorProto createField(String name, DescriptorProtos.FieldDescriptorProto.Type type, int tagNum) {
        return DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName(name)
                .setNumber(tagNum)
                .setType(type)
                .build();
    }
}
