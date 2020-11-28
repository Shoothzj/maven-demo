package com.github.shoothzj.jpcap.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.TreeMap;

/**
 * @author hezhangjian
 */
@Slf4j
public class SortedJacksonUtil {

    private static final ObjectMapper SORTED_MAPPER = JsonMapper.builder()
            .nodeFactory(new SortingNodeFactory())
            .build();

    static {
        SORTED_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    public static ObjectNode createObjectNode() {
        return SORTED_MAPPER.createObjectNode();
    }

    static class SortingNodeFactory extends JsonNodeFactory {
        @Override
        public ObjectNode objectNode() {
            return new ObjectNode(this, new TreeMap<String, JsonNode>());
        }
    }


}
