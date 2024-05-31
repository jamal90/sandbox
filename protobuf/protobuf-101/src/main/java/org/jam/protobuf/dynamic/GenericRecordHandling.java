package org.jam.protobuf.dynamic;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class GenericRecordHandling {

    // Ref - https://codeburst.io/using-dynamic-messages-in-protocol-buffers-in-scala-9fda4f0efcb3
    public static void main(String[] args) throws IOException, Descriptors.DescriptorValidationException {

        DescriptorProtos.FileDescriptorSet fileDescriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(
                GenericRecordHandling.class.getResourceAsStream("/proto/iot/motorTypeDescriptor.desc"));
        DescriptorProtos.FileDescriptorProto fileDescriptorProto = fileDescriptorSet.getFile(0);
        Descriptors.FileDescriptor fileDescriptor = Descriptors.FileDescriptor.buildFrom(fileDescriptorProto, new Descriptors.FileDescriptor[]{});
        Descriptors.Descriptor motorType = fileDescriptor.findMessageTypeByName("MotorType");

        MotorTypeOuterClass.MotorType message = getStandardMessage(); // using generated classes

        byte[] serializedMsg = message.toByteArray();
        System.out.println(Arrays.toString(serializedMsg));
        DynamicMessage dynamicMessage = DynamicMessage.parseFrom(motorType, serializedMsg);
        System.out.println("Json: " + JsonFormat.printer().preservingProtoFieldNames().print(dynamicMessage));
        System.out.println("Proto:" + dynamicMessage);

        // building message using dynamic message API
        DynamicMessage dynMessage = getDynamicMessage(motorType);

        byte[] dynMessageSerialized = dynMessage.toByteArray();
        DynamicMessage dynMessage2 = DynamicMessage.parseFrom(motorType, dynMessageSerialized);
        System.out.println("Dynamic Message Json: " + JsonFormat.printer().preservingProtoFieldNames().print(dynMessage2));
        System.out.println("Dynamic Message Proto:" + dynMessage2);

    }

    private static DynamicMessage getDynamicMessage(Descriptors.Descriptor motorType) {
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

    private static MotorTypeOuterClass.MotorType getStandardMessage() {
        // build message using generated class
        MotorTypeOuterClass.MotorType message = MotorTypeOuterClass.MotorType.newBuilder()
                .setObjectId("EQ1")
                .setObjectType("KT_PUMP")
                .addMeasurements(getMeasurement(10.93, 20.11))
                .addMeasurements(getMeasurement(20.93, 30.11))
                .build();
        return message;
    }

    private static MotorTypeOuterClass.MotorType.Measurement getMeasurement(double v1, double v2) {
        return MotorTypeOuterClass.MotorType.Measurement.newBuilder()
                .setGearRatio(v1)
                .setPitch(v2)
                .setTime(Instant.now().toEpochMilli())
                .setPath("/motionDevice/PowerTrain/Gear")
                .build();
    }
}
