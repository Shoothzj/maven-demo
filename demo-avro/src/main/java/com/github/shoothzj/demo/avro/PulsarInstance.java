/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.github.shoothzj.demo.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class PulsarInstance extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -8668911545534396740L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PulsarInstance\",\"namespace\":\"com.github.shoothzj.demo.avro\",\"fields\":[{\"name\":\"cluster\",\"type\":\"string\"},{\"name\":\"url\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<PulsarInstance> ENCODER =
      new BinaryMessageEncoder<PulsarInstance>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PulsarInstance> DECODER =
      new BinaryMessageDecoder<PulsarInstance>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<PulsarInstance> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<PulsarInstance> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<PulsarInstance> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<PulsarInstance>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this PulsarInstance to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a PulsarInstance from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a PulsarInstance instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static PulsarInstance fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence cluster;
   private java.lang.CharSequence url;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PulsarInstance() {}

  /**
   * All-args constructor.
   * @param cluster The new value for cluster
   * @param url The new value for url
   */
  public PulsarInstance(java.lang.CharSequence cluster, java.lang.CharSequence url) {
    this.cluster = cluster;
    this.url = url;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return cluster;
    case 1: return url;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: cluster = (java.lang.CharSequence)value$; break;
    case 1: url = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'cluster' field.
   * @return The value of the 'cluster' field.
   */
  public java.lang.CharSequence getCluster() {
    return cluster;
  }


  /**
   * Sets the value of the 'cluster' field.
   * @param value the value to set.
   */
  public void setCluster(java.lang.CharSequence value) {
    this.cluster = value;
  }

  /**
   * Gets the value of the 'url' field.
   * @return The value of the 'url' field.
   */
  public java.lang.CharSequence getUrl() {
    return url;
  }


  /**
   * Sets the value of the 'url' field.
   * @param value the value to set.
   */
  public void setUrl(java.lang.CharSequence value) {
    this.url = value;
  }

  /**
   * Creates a new PulsarInstance RecordBuilder.
   * @return A new PulsarInstance RecordBuilder
   */
  public static com.github.shoothzj.demo.avro.PulsarInstance.Builder newBuilder() {
    return new com.github.shoothzj.demo.avro.PulsarInstance.Builder();
  }

  /**
   * Creates a new PulsarInstance RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PulsarInstance RecordBuilder
   */
  public static com.github.shoothzj.demo.avro.PulsarInstance.Builder newBuilder(com.github.shoothzj.demo.avro.PulsarInstance.Builder other) {
    if (other == null) {
      return new com.github.shoothzj.demo.avro.PulsarInstance.Builder();
    } else {
      return new com.github.shoothzj.demo.avro.PulsarInstance.Builder(other);
    }
  }

  /**
   * Creates a new PulsarInstance RecordBuilder by copying an existing PulsarInstance instance.
   * @param other The existing instance to copy.
   * @return A new PulsarInstance RecordBuilder
   */
  public static com.github.shoothzj.demo.avro.PulsarInstance.Builder newBuilder(com.github.shoothzj.demo.avro.PulsarInstance other) {
    if (other == null) {
      return new com.github.shoothzj.demo.avro.PulsarInstance.Builder();
    } else {
      return new com.github.shoothzj.demo.avro.PulsarInstance.Builder(other);
    }
  }

  /**
   * RecordBuilder for PulsarInstance instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PulsarInstance>
    implements org.apache.avro.data.RecordBuilder<PulsarInstance> {

    private java.lang.CharSequence cluster;
    private java.lang.CharSequence url;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.github.shoothzj.demo.avro.PulsarInstance.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.cluster)) {
        this.cluster = data().deepCopy(fields()[0].schema(), other.cluster);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.url)) {
        this.url = data().deepCopy(fields()[1].schema(), other.url);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing PulsarInstance instance
     * @param other The existing instance to copy.
     */
    private Builder(com.github.shoothzj.demo.avro.PulsarInstance other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.cluster)) {
        this.cluster = data().deepCopy(fields()[0].schema(), other.cluster);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.url)) {
        this.url = data().deepCopy(fields()[1].schema(), other.url);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'cluster' field.
      * @return The value.
      */
    public java.lang.CharSequence getCluster() {
      return cluster;
    }


    /**
      * Sets the value of the 'cluster' field.
      * @param value The value of 'cluster'.
      * @return This builder.
      */
    public com.github.shoothzj.demo.avro.PulsarInstance.Builder setCluster(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.cluster = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'cluster' field has been set.
      * @return True if the 'cluster' field has been set, false otherwise.
      */
    public boolean hasCluster() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'cluster' field.
      * @return This builder.
      */
    public com.github.shoothzj.demo.avro.PulsarInstance.Builder clearCluster() {
      cluster = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'url' field.
      * @return The value.
      */
    public java.lang.CharSequence getUrl() {
      return url;
    }


    /**
      * Sets the value of the 'url' field.
      * @param value The value of 'url'.
      * @return This builder.
      */
    public com.github.shoothzj.demo.avro.PulsarInstance.Builder setUrl(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.url = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'url' field has been set.
      * @return True if the 'url' field has been set, false otherwise.
      */
    public boolean hasUrl() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'url' field.
      * @return This builder.
      */
    public com.github.shoothzj.demo.avro.PulsarInstance.Builder clearUrl() {
      url = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PulsarInstance build() {
      try {
        PulsarInstance record = new PulsarInstance();
        record.cluster = fieldSetFlags()[0] ? this.cluster : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.url = fieldSetFlags()[1] ? this.url : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PulsarInstance>
    WRITER$ = (org.apache.avro.io.DatumWriter<PulsarInstance>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PulsarInstance>
    READER$ = (org.apache.avro.io.DatumReader<PulsarInstance>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.cluster);

    out.writeString(this.url);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.cluster = in.readString(this.cluster instanceof Utf8 ? (Utf8)this.cluster : null);

      this.url = in.readString(this.url instanceof Utf8 ? (Utf8)this.url : null);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.cluster = in.readString(this.cluster instanceof Utf8 ? (Utf8)this.cluster : null);
          break;

        case 1:
          this.url = in.readString(this.url instanceof Utf8 ? (Utf8)this.url : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










