package org.jam.protobuf.iot;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;

import static com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type.*;

public class InvalidFieldNames {

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
                .setField(measurementType.findFieldByName("pitch"), new Random().nextLong())
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
                // >> field name starting with number - not allowed when code generation is followed; such client could use a different name suitable for the language used
                .addField(createField("1aBc", TYPE_INT64, 6))
                .addField(createField("pitch", TYPE_INT64, 5))
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
