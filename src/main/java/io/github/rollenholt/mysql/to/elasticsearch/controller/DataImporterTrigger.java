package io.github.rollenholt.mysql.to.elasticsearch.controller;

import com.rollenholt.pear.pojo.JsonV2;
import io.github.rollenholt.mysql.to.elasticsearch.elasticsearch.ElasticsearchComponent;
import io.github.rollenholt.mysql.to.elasticsearch.model.BasePayload;
import io.github.rollenholt.mysql.to.elasticsearch.service.AbstractDataCollector;
import io.github.rollenholt.mysql.to.elasticsearch.util.JacksonUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author rollenholt
 */
@RestController
@RequestMapping(value = "/trigger")
public class DataImporterTrigger {

    @Resource
    private AbstractDataCollector abstractDataCollector;

    @Resource
    private ElasticsearchComponent elasticsearchComponent;

    private final Logger logger = LoggerFactory.getLogger(DataImporterTrigger.class);

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public JsonV2 startImport(){

        final Stream<BasePayload> basePayloadStream = abstractDataCollector.queryAll();
        final List<BasePayload> basePayloads = basePayloadStream.collect(Collectors.<BasePayload>toList());
        logger.info("需要导入elasticsearch中的数据量为:{}", basePayloads.size());

        basePayloads.forEach(new Consumer<BasePayload>() {
            public void accept(BasePayload basePayload) {
                String json = JacksonUtil.toJson(basePayload);
                final String id = String.valueOf(basePayload.getId());
                IndexResponse indexResponse = elasticsearchComponent.addDocumentToIndex("test", "user", id, json);
                logger.info("数据[id: {}] 导入elasticsearch 结果: {}", id, indexResponse.isCreated());
            }
        });

        return new JsonV2<String>(0, "ok", "数据已经导入elasticsearch");
    }
}
