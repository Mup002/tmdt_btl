package tmdtdemo.tmdt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;


import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.service.ElasticService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ElasticServiceImpl implements ElasticService {
    private final RestHighLevelClient restHighLevelClient;
    final private static String[] FETCH_FIELDS = { "@timestamp", "@message" };

    final private static String INDEX = "product_idx";

    @Override
    public String findProductsByBoolQuery(String key) {
        BoolQueryBuilder qb  = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("color",key))
                .should(QueryBuilders.matchQuery("type",key));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(qb).fetchSource(true);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if(searchResponse.getHits().getTotalHits().value == 0){
                FuzzyQueryBuilder fb = QueryBuilders.fuzzyQuery("type",key).fuzziness(Fuzziness.AUTO);
                searchSourceBuilder.query(fb).fetchSource(true);
                searchRequest.source(searchSourceBuilder);
                searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            }
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<String> hitJsonStrings = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            for (SearchHit hit : searchHits) {
                hitJsonStrings.add(hit.getSourceAsString());
            }
            return hitJsonStrings.toString();
        } catch (IOException e) {
            throw new BaseException(HttpStatus.BAD_REQUEST.toString(), "Có lỗi xảy ra khi tìm kiếm.");
        }

    }
}
