// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: simple.proto

public final class Simple {
  private Simple() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MotorTypeOrBuilder extends
      // @@protoc_insertion_point(interface_extends:MotorType)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string objectId = 1;</code>
     * @return The objectId.
     */
    java.lang.String getObjectId();
    /**
     * <code>string objectId = 1;</code>
     * @return The bytes for objectId.
     */
    com.google.protobuf.ByteString
        getObjectIdBytes();

    /**
     * <code>string objectType = 2;</code>
     * @return The objectType.
     */
    java.lang.String getObjectType();
    /**
     * <code>string objectType = 2;</code>
     * @return The bytes for objectType.
     */
    com.google.protobuf.ByteString
        getObjectTypeBytes();

    /**
     * <code>string _path = 3;</code>
     * @return The path.
     */
    java.lang.String getPath();
    /**
     * <code>string _path = 3;</code>
     * @return The bytes for path.
     */
    com.google.protobuf.ByteString
        getPathBytes();
  }
  /**
   * Protobuf type {@code MotorType}
   */
  public static final class MotorType extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:MotorType)
      MotorTypeOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MotorType.newBuilder() to construct.
    private MotorType(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MotorType() {
      objectId_ = "";
      objectType_ = "";
      Path_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new MotorType();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MotorType(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              objectId_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              objectType_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              Path_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Simple.internal_static_MotorType_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Simple.internal_static_MotorType_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Simple.MotorType.class, Simple.MotorType.Builder.class);
    }

    public static final int OBJECTID_FIELD_NUMBER = 1;
    private volatile java.lang.Object objectId_;
    /**
     * <code>string objectId = 1;</code>
     * @return The objectId.
     */
    @java.lang.Override
    public java.lang.String getObjectId() {
      java.lang.Object ref = objectId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        objectId_ = s;
        return s;
      }
    }
    /**
     * <code>string objectId = 1;</code>
     * @return The bytes for objectId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getObjectIdBytes() {
      java.lang.Object ref = objectId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        objectId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int OBJECTTYPE_FIELD_NUMBER = 2;
    private volatile java.lang.Object objectType_;
    /**
     * <code>string objectType = 2;</code>
     * @return The objectType.
     */
    @java.lang.Override
    public java.lang.String getObjectType() {
      java.lang.Object ref = objectType_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        objectType_ = s;
        return s;
      }
    }
    /**
     * <code>string objectType = 2;</code>
     * @return The bytes for objectType.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getObjectTypeBytes() {
      java.lang.Object ref = objectType_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        objectType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int _PATH_FIELD_NUMBER = 3;
    private volatile java.lang.Object Path_;
    /**
     * <code>string _path = 3;</code>
     * @return The path.
     */
    @java.lang.Override
    public java.lang.String getPath() {
      java.lang.Object ref = Path_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        Path_ = s;
        return s;
      }
    }
    /**
     * <code>string _path = 3;</code>
     * @return The bytes for path.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getPathBytes() {
      java.lang.Object ref = Path_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        Path_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(objectId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, objectId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(objectType_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, objectType_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(Path_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, Path_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(objectId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, objectId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(objectType_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, objectType_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(Path_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, Path_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Simple.MotorType)) {
        return super.equals(obj);
      }
      Simple.MotorType other = (Simple.MotorType) obj;

      if (!getObjectId()
          .equals(other.getObjectId())) return false;
      if (!getObjectType()
          .equals(other.getObjectType())) return false;
      if (!getPath()
          .equals(other.getPath())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + OBJECTID_FIELD_NUMBER;
      hash = (53 * hash) + getObjectId().hashCode();
      hash = (37 * hash) + OBJECTTYPE_FIELD_NUMBER;
      hash = (53 * hash) + getObjectType().hashCode();
      hash = (37 * hash) + _PATH_FIELD_NUMBER;
      hash = (53 * hash) + getPath().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Simple.MotorType parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Simple.MotorType parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Simple.MotorType parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Simple.MotorType parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Simple.MotorType parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Simple.MotorType parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Simple.MotorType parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Simple.MotorType parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Simple.MotorType parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Simple.MotorType parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Simple.MotorType parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Simple.MotorType parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Simple.MotorType prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code MotorType}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:MotorType)
        Simple.MotorTypeOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Simple.internal_static_MotorType_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Simple.internal_static_MotorType_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Simple.MotorType.class, Simple.MotorType.Builder.class);
      }

      // Construct using Simple.MotorType.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        objectId_ = "";

        objectType_ = "";

        Path_ = "";

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Simple.internal_static_MotorType_descriptor;
      }

      @java.lang.Override
      public Simple.MotorType getDefaultInstanceForType() {
        return Simple.MotorType.getDefaultInstance();
      }

      @java.lang.Override
      public Simple.MotorType build() {
        Simple.MotorType result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public Simple.MotorType buildPartial() {
        Simple.MotorType result = new Simple.MotorType(this);
        result.objectId_ = objectId_;
        result.objectType_ = objectType_;
        result.Path_ = Path_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Simple.MotorType) {
          return mergeFrom((Simple.MotorType)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Simple.MotorType other) {
        if (other == Simple.MotorType.getDefaultInstance()) return this;
        if (!other.getObjectId().isEmpty()) {
          objectId_ = other.objectId_;
          onChanged();
        }
        if (!other.getObjectType().isEmpty()) {
          objectType_ = other.objectType_;
          onChanged();
        }
        if (!other.getPath().isEmpty()) {
          Path_ = other.Path_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Simple.MotorType parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Simple.MotorType) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object objectId_ = "";
      /**
       * <code>string objectId = 1;</code>
       * @return The objectId.
       */
      public java.lang.String getObjectId() {
        java.lang.Object ref = objectId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          objectId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string objectId = 1;</code>
       * @return The bytes for objectId.
       */
      public com.google.protobuf.ByteString
          getObjectIdBytes() {
        java.lang.Object ref = objectId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          objectId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string objectId = 1;</code>
       * @param value The objectId to set.
       * @return This builder for chaining.
       */
      public Builder setObjectId(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        objectId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string objectId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearObjectId() {
        
        objectId_ = getDefaultInstance().getObjectId();
        onChanged();
        return this;
      }
      /**
       * <code>string objectId = 1;</code>
       * @param value The bytes for objectId to set.
       * @return This builder for chaining.
       */
      public Builder setObjectIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        objectId_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object objectType_ = "";
      /**
       * <code>string objectType = 2;</code>
       * @return The objectType.
       */
      public java.lang.String getObjectType() {
        java.lang.Object ref = objectType_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          objectType_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string objectType = 2;</code>
       * @return The bytes for objectType.
       */
      public com.google.protobuf.ByteString
          getObjectTypeBytes() {
        java.lang.Object ref = objectType_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          objectType_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string objectType = 2;</code>
       * @param value The objectType to set.
       * @return This builder for chaining.
       */
      public Builder setObjectType(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        objectType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string objectType = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearObjectType() {
        
        objectType_ = getDefaultInstance().getObjectType();
        onChanged();
        return this;
      }
      /**
       * <code>string objectType = 2;</code>
       * @param value The bytes for objectType to set.
       * @return This builder for chaining.
       */
      public Builder setObjectTypeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        objectType_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object Path_ = "";
      /**
       * <code>string _path = 3;</code>
       * @return The path.
       */
      public java.lang.String getPath() {
        java.lang.Object ref = Path_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          Path_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string _path = 3;</code>
       * @return The bytes for path.
       */
      public com.google.protobuf.ByteString
          getPathBytes() {
        java.lang.Object ref = Path_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          Path_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string _path = 3;</code>
       * @param value The path to set.
       * @return This builder for chaining.
       */
      public Builder setPath(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        Path_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string _path = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearPath() {
        
        Path_ = getDefaultInstance().getPath();
        onChanged();
        return this;
      }
      /**
       * <code>string _path = 3;</code>
       * @param value The bytes for path to set.
       * @return This builder for chaining.
       */
      public Builder setPathBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        Path_ = value;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:MotorType)
    }

    // @@protoc_insertion_point(class_scope:MotorType)
    private static final Simple.MotorType DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Simple.MotorType();
    }

    public static Simple.MotorType getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MotorType>
        PARSER = new com.google.protobuf.AbstractParser<MotorType>() {
      @java.lang.Override
      public MotorType parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MotorType(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MotorType> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MotorType> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public Simple.MotorType getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MotorType_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MotorType_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014simple.proto\"@\n\tMotorType\022\020\n\010objectId\030" +
      "\001 \001(\t\022\022\n\nobjectType\030\002 \001(\t\022\r\n\005_path\030\003 \001(\t" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_MotorType_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_MotorType_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MotorType_descriptor,
        new java.lang.String[] { "ObjectId", "ObjectType", "Path", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
