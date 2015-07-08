package io.github.rollenholt.mysql.to.elasticsearch.controller;

import com.rollenholt.pear.pojo.JsonV2;
import io.github.rollenholt.mysql.to.elasticsearch.model.BasePayload;
import io.github.rollenholt.mysql.to.elasticsearch.service.AbstractDataCollector;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author rollenholt
 */
@RestController
@RequestMapping(value = "/data")
public class DataCollectorController {

    @Resource
    private AbstractDataCollector abstractDataCollector;

    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public JsonV2 queryAll(){
        final Stream<BasePayload> basePayloadStream = abstractDataCollector.queryAll();
        final List<BasePayload> basePayloads = basePayloadStream.collect(Collectors.<BasePayload>toList());
        return new JsonV2<List>(0, "ok", basePayloads);
    }
}
