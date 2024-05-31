package org.jam.protobuf.jmh;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import org.jam.protobuf.dynamic.MotorTypeOuterClass;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.Instant;
import java.util.Random;

import static com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type.*;

public class SerDeBenchmark {

    @State(Scope.Benchmark)
    public static class JMHStateSpec {
        MotorTypeOuterClass.MotorType fixedMessage;
        Descriptors.Descriptor motorTypeDescriptor;
        DynamicMessage dynamicMessage;
        byte[] serializedFixedMessage;
        byte[] serializedDynMessage;


        public JMHStateSpec() {
            fixedMessage = getFixedMessage();
            try {
                motorTypeDescriptor = buildDynamicSchema();
                dynamicMessage = buildDynamicMessage(motorTypeDescriptor);
                serializedFixedMessage = fixedMessage.toByteArray();
                serializedDynMessage = dynamicMessage.toByteArray();
            } catch (Descriptors.DescriptorValidationException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) throws RunnerException {
        Options jmhConfigOpts = new OptionsBuilder()
                .forks(2)
                .include(SerDeBenchmark.class.getSimpleName())
                .build();

        new Runner(jmhConfigOpts).run();
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public byte[] serializeFixedMessage(JMHStateSpec jmhStateSpec) {
        return jmhStateSpec.fixedMessage.toByteArray();
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public MotorTypeOuterClass.MotorType deserializeFixedMessage(JMHStateSpec jmhStateSpec) throws InvalidProtocolBufferException {
        return MotorTypeOuterClass.MotorType.parseFrom(jmhStateSpec.serializedFixedMessage);
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public byte[] serializeDynamicMessage(JMHStateSpec jmhStateSpec) {
        return jmhStateSpec.dynamicMessage.toByteArray();
    }

    @Benchmark
    @Warmup(iterations = 3)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.Throughput)
    public DynamicMessage deserializeDynamicMessage(JMHStateSpec jmhStateSpec) throws InvalidProtocolBufferException {
        return DynamicMessage.parseFrom(jmhStateSpec.motorTypeDescriptor, jmhStateSpec.serializedDynMessage);
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
        return messageBuilder.setField(motorType.findFieldByName("object_id"), "EQ1")
                .setField(motorType.findFieldByName("object_type"), "KT_PUMP")
                .addRepeatedField(motorType.findFieldByName("measurements"), measurement)
                .build();
    }

    private static MotorTypeOuterClass.MotorType getFixedMessage() {
        // build message using generated class
        MotorTypeOuterClass.MotorType message = MotorTypeOuterClass.MotorType.newBuilder()
                .setObjectId("EQ1")
                .setObjectType("KT_PUMP")
                .addMeasurements(MotorTypeOuterClass.MotorType.Measurement.newBuilder()
                        .setGearRatio(new Random().nextDouble())
                        .setPitch(new Random().nextDouble())
                        .setTime(Instant.now().toEpochMilli())
                        .setPath("/motionDevice/PowerTrain/Gear")
                        .build())
                .build();
        return message;
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
                .setName("synthetic_file.proto")
                .addMessageType(messageProto)
                .build();

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
