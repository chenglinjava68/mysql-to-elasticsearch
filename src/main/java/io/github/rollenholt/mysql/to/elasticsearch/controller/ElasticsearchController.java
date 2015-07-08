package io.github.rollenholt.mysql.to.elasticsearch.controller;

import com.google.common.collect.Lists;
import com.rollenholt.pear.pojo.JsonV2;
import io.github.rollenholt.mysql.to.elasticsearch.elasticsearch.ElasticsearchComponent;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * rollenholt
 * 2015/6/29.
 */
@RestController
@RequestMapping(value = "/elasticsearch")
public class ElasticsearchController {

    @Value("${indexType}")
    private String indexType;

    @Value("${indexName}")
    private String indexName;

    @Resource
    private ElasticsearchComponent elasticsearchComponent;

    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public JsonV2 queryAll(@RequestParam("from") int from, @RequestParam("size") int size){
        SearchResponse searchResponse = elasticsearchComponent.queryAllDocuments(indexName, indexType, from, size);

        List<SearchHit> searchHits = Lists.newArrayList(searchResponse.getHits().getHits());
        Stream<String> stringStream = searchHits.stream().map(new Function<SearchHit, String>() {
            public String apply(SearchHit searchHitFields) {
                return searchHitFields.getSourceAsString();
            }
        });
        return new JsonV2(0, "ok", stringStream.collect(Collectors.toList()));
    }

    @RequestMapping(value = "queryCount", method = RequestMethod.GET)
    public JsonV2 queryCount(){
        CountResponse countResponse = elasticsearchComponent.queryDocumentCount(indexName, indexType);
        return new JsonV2(0, "ok", countResponse.getCount());
    }


}
