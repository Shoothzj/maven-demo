package com.github.shoothzj.demo.beam;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TypeDescriptors;

import java.net.URL;
import java.util.Arrays;

/**
 * @author Apache Beam
 */
@Slf4j
public class WordCount {

    public static void main(String[] args) {
        final URL data = Resources.getResource("");

        String inputsDir = data.getPath() + "data/*";
        log.info("input dir is [{}]", inputsDir);
        String outputsPrefix = data.getPath() + "outputs/part";
        log.info("outputs");

        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
        Pipeline pipeline = Pipeline.create(options);
        pipeline
                .apply("Read lines", TextIO.read().from(inputsDir))
                .apply("Find words", FlatMapElements.into(TypeDescriptors.strings())
                        .via((String line) -> Arrays.asList(line.split("[^\\p{L}]+"))))
                .apply("Filter empty words", Filter.by((String word) -> !word.isEmpty()))
                .apply("Count words", Count.perElement())
                .apply("Write results", MapElements.into(TypeDescriptors.strings())
                        .via((KV<String, Long> wordCount) ->
                                wordCount.getKey() + ": " + wordCount.getValue()))
                .apply(TextIO.write().to(outputsPrefix));
        pipeline.run();
    }

}