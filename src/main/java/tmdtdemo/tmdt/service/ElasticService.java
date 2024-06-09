package tmdtdemo.tmdt.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ElasticService {

    String findProductsByBoolQuery(String key);
}
