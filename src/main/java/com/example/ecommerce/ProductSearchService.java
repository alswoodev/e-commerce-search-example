package com.example.ecommerce;

import com.example.ecommerce.entity.Product;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchService {
    private final ElasticsearchOperations esOps;

    public ProductSearchService(@Autowired ElasticsearchOperations ops){
        this.esOps = ops;
    }

    public List<ProductScore> searchByNameWithScore(String keyword) {
        
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                    .match(m -> m
                        .field("name")
                        .query(keyword)
                    )
                )
                .build();

        SearchHits<Product> searchHits = esOps.search(query, Product.class);

        return searchHits.stream()
                .map(hit -> new ProductScore(hit.getContent(), hit.getScore()))
                .collect(Collectors.toList());
    }

    public record ProductScore(Product product, float score) {}

    @PostConstruct
    public void init() {
        var indexOps = esOps.indexOps(Product.class);

        if (!indexOps.exists()) {
            indexOps.createWithMapping();
        }else{
            System.out.println("dfasdfadf");
        }
    }
}