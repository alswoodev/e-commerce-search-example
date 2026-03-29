# e-commerce-search-example
Searching functionality for E-commerce domain

## Setting
1. Download nori analyzer plug-in
``` bash
docker compose up -d
docker exec -it elasticsearch bin/elasticsearch-plugin install analysis-icu
docker exec -it elasticsearch bin/elasticsearch-plugin install analysis-nori

#check
curl -u elastic:pwd -X GET "http://localhost:9200/_cat/plugins?v"
```

2. Run the application and check mapping
``` bash
curl -u elastic:pwd -X GET "http://localhost:9200/products?pretty"
curl -u elastic:pwd -X GET "http://localhost:9200/products/_mapping?pretty"
curl -u elastic:pwd -X GET "http://localhost:9200/products/_settings?pretty"
```

3. Analyzer test
``` bash
curl -u elastic:pwd -X POST "http://localhost:9200/products/_analyze?pretty" -H 'Content-Type: application/json' -d'
{
  "analyzer": "product_index_analyzer",
  "text": "삼성전자 갤럭시 S24 울트라"
}'

curl -u elastic:pwd -X POST "http://localhost:9200/products/_analyze?pretty" -H 'Content-Type: application/json' -d'
{
  "analyzer": "edge_ngram_analyzer",
  "text": "apple"
}'

curl -u elastic:pwd -X POST "http://localhost:9200/products/_analyze?pretty" -H 'Content-Type: application/json' -d'
{
  "field": "name",
  "text": "나이키 에어맥스 운동화"
}'

curl -u elastic:pwd -X GET "http://localhost:9200/products/_analyze" -H 'Content-Type: application/json' -d'
{
  "analyzer" : "product_search_analyzer",
  "text" : "airmax"
}'
```

4. Indexing and Search test
``` bash
curl -u elastic:pwd -X POST "http://localhost:9200/products/_doc" -H 'Content-Type: application/json' -d'
{
  "name":"나이키 에어맥스 운동화"
}'

curl -u elastic:pwd -X GET "http://localhost:9200/products/_search" -H 'Content-Type: application/json' -d'
{
  "query":{
    "match":{
      "name": "airmax"
    }
  }
}'
```

