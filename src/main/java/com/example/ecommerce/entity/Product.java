package com.example.ecommerce.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

@Document(indexName = "products") 
@Setting(settingPath = "elasticsearch/settings.json") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @Field(name = "product_id", type = FieldType.Keyword)
    private String productId;

    @Field(name = "seller_id", type = FieldType.Keyword)
    private String sellerId;

    @Field(name = "winner_score", type = FieldType.Float)
    private Float winnerScore;

    @Field(name = "price", type = FieldType.Integer)
    private Integer price;

    @MultiField(
        mainField = @Field(name = "master_name", type = FieldType.Text, 
        analyzer = "product_index_analyzer", searchAnalyzer = "product_search_analyzer"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword), 
            @InnerField(suffix = "chosung", type = FieldType.Text, analyzer = "chosung_index_analyzer", searchAnalyzer = "chosung_search_analyzer")
        }
    )
    private String masterName;

    @MultiField(
        mainField = @Field(name = "name", type = FieldType.Text, 
        analyzer = "product_index_analyzer", searchAnalyzer = "product_search_analyzer"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword), 
            @InnerField(suffix = "chosung", type = FieldType.Text, analyzer = "chosung_index_analyzer", searchAnalyzer = "chosung_search_analyzer")
        }
    )
    private String name;

    @MultiField(
        mainField = @Field(name = "keywords", type = FieldType.Text, 
        analyzer = "product_index_analyzer", searchAnalyzer = "product_search_analyzer"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword), 
            @InnerField(suffix = "chosung", type = FieldType.Text, analyzer = "chosung_index_analyzer", searchAnalyzer = "chosung_search_analyzer")
        }
    )
    private String keywords;

    @Field(name = "status", type = FieldType.Keyword)
    private String status;

    @Field(name = "popularity_score", type = FieldType.Float)
    private Float popularityScore;

    @Field(name = "review_score", type = FieldType.Float)
    private Float reviewScore;

    @Field(name = "options", type = FieldType.Nested)
    private List<ProductOption> options;

    /**
     * Nested 타입인 options를 위한 내부 클래스
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOption {
        @Field(type = FieldType.Keyword)
        private String name;

        @Field(type = FieldType.Keyword)
        private String value;
    }
}