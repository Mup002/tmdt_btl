package tmdtdemo.tmdt.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;

public class ElasticSearchUtil {
    public static Supplier<Query> supplier(){
        Supplier<Query> supplier = ()->Query.of(q->q.matchAll(matchAllQuery()));
        return supplier;
    }
    public static MatchAllQuery matchAllQuery(){
        val matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }
}
