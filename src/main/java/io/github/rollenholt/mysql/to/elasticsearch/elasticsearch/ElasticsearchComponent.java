package io.github.rollenholt.mysql.to.elasticsearch.elasticsearch;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author rollenholt
 */
@Service
public class ElasticsearchComponent {

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.cluster.host}")
    private String clusterHost;

    @Value("${elasticsearch.cluster.port1}")
    private int clusterPort1;

    @Value("${elasticsearch.cluster.port2}")
    private int clusterPort2;

    @Value("${elasticsearch.cluster.port3}")
    private int clusterPort3;

    private Client client;

    @PostConstruct
    public void init(){
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
        client = new TransportClient(settings)
                .addTransportAddresses(new InetSocketTransportAddress(clusterHost, clusterPort1)
                ,new InetSocketTransportAddress(clusterHost, clusterPort2)
                ,new InetSocketTransportAddress(clusterHost, clusterPort3));
    }

    public IndexResponse addDocumentToIndex(String indexName, String indexType, String id, String source){
        return client.prepareIndex(indexName, indexType, id).setSource(source).execute().actionGet();
    }

    public SearchResponse queryAllDocuments(String indexName, String indexType, int from, int size){
        return client.prepareSearch(indexName).setTypes(indexType)
                .setExplain(true)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom(from)
                .setSize(size)
                .execute().actionGet();
    }

    public CountResponse queryDocumentCount(String indexName, String indexType){
        return client.prepareCount(indexName).setTypes(indexType).execute().actionGet();
    }

    public SearchResponse queryDocumentBySingleField(String indexName, String indexType, String field, Object value){
        return client.prepareSearch(indexName).setTypes(indexType).setQuery(QueryBuilders.termQuery(field, value)).execute().actionGet();
    }

    public SearchResponse queryDocumentBySingleFieldFuzzySearch(String indexName, String indexType, String field, Object value) {
        return client.prepareSearch(indexName).setTypes(indexType).setQuery(QueryBuilders.fuzzyQuery(field, value)).execute().actionGet();
    }
}
