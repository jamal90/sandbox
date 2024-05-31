package org.jam.protobuf.iot;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class MultiStructTypeHandling {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        RobotSystemTypeProtos.RobotSystemType measurements = RobotSystemTypeProtos.RobotSystemType.newBuilder()
                .setObjectId("EQ1")
                .setObjectType("PUMP_MODEL_1")
                .addMeasurements(
                        RobotSystemTypeProtos.RobotSystemType.Measurement.newBuilder()
                                .setPath("/path1")
                                .setTime(Instant.now().toEpochMilli())
                                .setGearType(RobotSystemTypeProtos.RobotSystemType.GearType.newBuilder()
                                        .setGearRatio(new Random().nextDouble())
                                        .setPitch(new Random().nextDouble())
                                        .build())
                                .build()
                )
                .addMeasurements(
                        RobotSystemTypeProtos.RobotSystemType.Measurement.newBuilder()
                                .setPath("/path1")
                                .setTime(Instant.now().toEpochMilli())
                                .setMotorType(RobotSystemTypeProtos.RobotSystemType.MotorType.newBuilder()
                                        .setBreakReleased(new Random().nextDouble())
                                        .setEffectiveLoadRate(new Random().nextDouble())
                                        .setMotorTemperature(new Random().nextDouble())
                                        .build())
                                .build()
                ).build();

        System.out.println("Binary Serialized: " + Arrays.toString(measurements.toByteArray()));

        System.out.println("Json Serialized: " + JsonFormat.printer().preservingProtoFieldNames().print(measurements));
    }
}
