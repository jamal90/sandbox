/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package parquet.org.jam;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class User extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -4726065146524528686L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"parquet.org.jam\",\"fields\":[{\"name\":\"userId\",\"type\":\"int\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"homeAddress\",\"type\":{\"type\":\"record\",\"name\":\"AddressRecord\",\"fields\":[{\"name\":\"number\",\"type\":\"int\"},{\"name\":\"street\",\"type\":\"string\"}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int userId;
  @Deprecated public java.lang.CharSequence name;
  @Deprecated public parquet.org.jam.AddressRecord homeAddress;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public User() {}

  /**
   * All-args constructor.
   * @param userId The new value for userId
   * @param name The new value for name
   * @param homeAddress The new value for homeAddress
   */
  public User(java.lang.Integer userId, java.lang.CharSequence name, parquet.org.jam.AddressRecord homeAddress) {
    this.userId = userId;
    this.name = name;
    this.homeAddress = homeAddress;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return userId;
    case 1: return name;
    case 2: return homeAddress;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: userId = (java.lang.Integer)value$; break;
    case 1: name = (java.lang.CharSequence)value$; break;
    case 2: homeAddress = (parquet.org.jam.AddressRecord)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'userId' field.
   * @return The value of the 'userId' field.
   */
  public java.lang.Integer getUserId() {
    return userId;
  }

  /**
   * Sets the value of the 'userId' field.
   * @param value the value to set.
   */
  public void setUserId(java.lang.Integer value) {
    this.userId = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'homeAddress' field.
   * @return The value of the 'homeAddress' field.
   */
  public parquet.org.jam.AddressRecord getHomeAddress() {
    return homeAddress;
  }

  /**
   * Sets the value of the 'homeAddress' field.
   * @param value the value to set.
   */
  public void setHomeAddress(parquet.org.jam.AddressRecord value) {
    this.homeAddress = value;
  }

  /**
   * Creates a new User RecordBuilder.
   * @return A new User RecordBuilder
   */
  public static parquet.org.jam.User.Builder newBuilder() {
    return new parquet.org.jam.User.Builder();
  }

  /**
   * Creates a new User RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new User RecordBuilder
   */
  public static parquet.org.jam.User.Builder newBuilder(parquet.org.jam.User.Builder other) {
    return new parquet.org.jam.User.Builder(other);
  }

  /**
   * Creates a new User RecordBuilder by copying an existing User instance.
   * @param other The existing instance to copy.
   * @return A new User RecordBuilder
   */
  public static parquet.org.jam.User.Builder newBuilder(parquet.org.jam.User other) {
    return new parquet.org.jam.User.Builder(other);
  }

  /**
   * RecordBuilder for User instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<User>
    implements org.apache.avro.data.RecordBuilder<User> {

    private int userId;
    private java.lang.CharSequence name;
    private parquet.org.jam.AddressRecord homeAddress;
    private parquet.org.jam.AddressRecord.Builder homeAddressBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(parquet.org.jam.User.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.userId)) {
        this.userId = data().deepCopy(fields()[0].schema(), other.userId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.homeAddress)) {
        this.homeAddress = data().deepCopy(fields()[2].schema(), other.homeAddress);
        fieldSetFlags()[2] = true;
      }
      if (other.hasHomeAddressBuilder()) {
        this.homeAddressBuilder = parquet.org.jam.AddressRecord.newBuilder(other.getHomeAddressBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing User instance
     * @param other The existing instance to copy.
     */
    private Builder(parquet.org.jam.User other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.userId)) {
        this.userId = data().deepCopy(fields()[0].schema(), other.userId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.homeAddress)) {
        this.homeAddress = data().deepCopy(fields()[2].schema(), other.homeAddress);
        fieldSetFlags()[2] = true;
      }
      this.homeAddressBuilder = null;
    }

    /**
      * Gets the value of the 'userId' field.
      * @return The value.
      */
    public java.lang.Integer getUserId() {
      return userId;
    }

    /**
      * Sets the value of the 'userId' field.
      * @param value The value of 'userId'.
      * @return This builder.
      */
    public parquet.org.jam.User.Builder setUserId(int value) {
      validate(fields()[0], value);
      this.userId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'userId' field has been set.
      * @return True if the 'userId' field has been set, false otherwise.
      */
    public boolean hasUserId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'userId' field.
      * @return This builder.
      */
    public parquet.org.jam.User.Builder clearUserId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.CharSequence getName() {
      return name;
    }

    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public parquet.org.jam.User.Builder setName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public parquet.org.jam.User.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'homeAddress' field.
      * @return The value.
      */
    public parquet.org.jam.AddressRecord getHomeAddress() {
      return homeAddress;
    }

    /**
      * Sets the value of the 'homeAddress' field.
      * @param value The value of 'homeAddress'.
      * @return This builder.
      */
    public parquet.org.jam.User.Builder setHomeAddress(parquet.org.jam.AddressRecord value) {
      validate(fields()[2], value);
      this.homeAddressBuilder = null;
      this.homeAddress = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'homeAddress' field has been set.
      * @return True if the 'homeAddress' field has been set, false otherwise.
      */
    public boolean hasHomeAddress() {
      return fieldSetFlags()[2];
    }

    /**
     * Gets the Builder instance for the 'homeAddress' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public parquet.org.jam.AddressRecord.Builder getHomeAddressBuilder() {
      if (homeAddressBuilder == null) {
        if (hasHomeAddress()) {
          setHomeAddressBuilder(parquet.org.jam.AddressRecord.newBuilder(homeAddress));
        } else {
          setHomeAddressBuilder(parquet.org.jam.AddressRecord.newBuilder());
        }
      }
      return homeAddressBuilder;
    }

    /**
     * Sets the Builder instance for the 'homeAddress' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public parquet.org.jam.User.Builder setHomeAddressBuilder(parquet.org.jam.AddressRecord.Builder value) {
      clearHomeAddress();
      homeAddressBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'homeAddress' field has an active Builder instance
     * @return True if the 'homeAddress' field has an active Builder instance
     */
    public boolean hasHomeAddressBuilder() {
      return homeAddressBuilder != null;
    }

    /**
      * Clears the value of the 'homeAddress' field.
      * @return This builder.
      */
    public parquet.org.jam.User.Builder clearHomeAddress() {
      homeAddress = null;
      homeAddressBuilder = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public User build() {
      try {
        User record = new User();
        record.userId = fieldSetFlags()[0] ? this.userId : (java.lang.Integer) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.CharSequence) defaultValue(fields()[1]);
        if (homeAddressBuilder != null) {
          record.homeAddress = this.homeAddressBuilder.build();
        } else {
          record.homeAddress = fieldSetFlags()[2] ? this.homeAddress : (parquet.org.jam.AddressRecord) defaultValue(fields()[2]);
        }
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}