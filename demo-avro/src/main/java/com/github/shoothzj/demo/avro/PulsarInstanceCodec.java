package com.github.shoothzj.demo.avro;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.io.JsonEncoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarInstanceCodec {

    @SneakyThrows
    public byte[] serializeJson(PulsarInstance pulsarInstance) {
        final SpecificDatumWriter<PulsarInstance> writer = new SpecificDatumWriter<>(PulsarInstance.class);
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(pulsarInstance.getSchema(), stream);
        writer.write(pulsarInstance, jsonEncoder);
        jsonEncoder.flush();
        return stream.toByteArray();
    }

    @SneakyThrows
    public byte[] serializeBinary(PulsarInstance pulsarInstance) {
        final SpecificDatumWriter<PulsarInstance> writer = new SpecificDatumWriter<>(PulsarInstance.class);
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(stream, null);
        writer.write(pulsarInstance, binaryEncoder);
        binaryEncoder.flush();
        return stream.toByteArray();
    }

    @SneakyThrows
    public PulsarInstance deSerializeJson(byte[] data) {
        final SpecificDatumReader<PulsarInstance> reader = new SpecificDatumReader<>(PulsarInstance.class);
        final JsonDecoder decoder = DecoderFactory.get().jsonDecoder(PulsarInstance.getClassSchema(), new String(data));
        return reader.read(null, decoder);
    }

    @SneakyThrows
    public PulsarInstance deSerializeBinary(byte[] data) {
        final SpecificDatumReader<PulsarInstance> reader = new SpecificDatumReader<>(PulsarInstance.class);
        final BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);
        return reader.read(null, decoder);
    }

}
