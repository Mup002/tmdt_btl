package tmdtdemo.tmdt.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.utils.ElasticSearchUtil;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
}
