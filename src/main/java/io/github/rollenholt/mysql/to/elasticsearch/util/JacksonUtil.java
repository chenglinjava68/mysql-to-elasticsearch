package io.github.rollenholt.mysql.to.elasticsearch.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rollenholt
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    public static String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }
}
