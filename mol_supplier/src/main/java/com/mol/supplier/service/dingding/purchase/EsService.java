package com.mol.supplier.service.dingding.purchase;

import com.mol.supplier.entity.dingding.solr.fyPurchase;
import com.mol.supplier.util.StatusUtils;
import entity.PageBean;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

@Service
public class EsService {
    @Value("${spring.elasticsearch.course.index}")
    private String index;

    @Value("${spring.elasticsearch.course.type}")
    private String type;

    @Value("${spring.elasticsearch.course.source_field}")
    private String sourceField;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    public PageBean list(int page, int size, String keyword) {

        if (keyword == null) {
            return null;
        }
        PageBean pb=new PageBean();

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] split = sourceField.split(",");
        searchSourceBuilder.fetchSource(split,new String[]{});
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "goods_type", "goods_name", "goods_specs", "title", "apply_cause", "remarks")
                .minimumShouldMatch("70%");
        boolQueryBuilder.must(multiMatchQueryBuilder);
        searchSourceBuilder.query(multiMatchQueryBuilder);
        if (page<=0){
            page=1;
        }
        if (size<=0){
            size=6;
        }
        int start =(page-1)*size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = search.getHits();
        SearchHit[] searchHits = hits.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        List<fyPurchase> purList=new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            fyPurchase pur=new fyPurchase();
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            pur.setTitle((String) sourceAsMap.get("title"));
            pur.setId((String) sourceAsMap.get("id"));
           // pur.setOrderNumber((String) sourceAsMap.get("order_number"));
            pur.setGoodsType((String) sourceAsMap.get("goods_type"));
            pur.setStatus((String) sourceAsMap.get("status"));
            pur.setCreateTime((String) sourceAsMap.get("create_time"));
            pur.setOrderNumber((String) sourceAsMap.get("order_number"));
            pur.setBuyChannelId((Integer) sourceAsMap.get("buy_channel_id"));
            System.out.println(pur);
            pur = StatusUtils.getStatusIntegerToString(pur);
            purList.add(pur);
        }
        //ServiceResult sr=new ServiceResult(true,"200","搜索成功",purList);
        pb.setTotalCount(Math.toIntExact(totalHits));
        pb.setList(purList);
        return pb;
    }
}

