package tmdtdemo.tmdt.controller.test;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.entity.ProductSku;
import tmdtdemo.tmdt.entity.ProductSpu;
import tmdtdemo.tmdt.service.ElasticService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final ElasticService elasticService;
//    @GetMapping("/matchAll")
//    public String matchAll(){
//        SearchResponse<Map> searchResponse =  elasticService.matchAllServices();
//        return searchResponse.hits().hits().toString();
//    }
//    @GetMapping("/fuzzySearch/{color}")
//    public String fuzzySearch(@PathVariable String color){
//        SearchResponse<Map> searchResponse = elasticService.fuzzySearch(color);
//
//        return searchResponse.hits().hits().toString();
//    }

    @GetMapping("/bool")
    public String boolSearch(@RequestParam String keyword){
        return elasticService.findProductsByBoolQuery(keyword);
    }
}
