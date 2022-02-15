package com.jpabook.jpashop.kafka;

public abstract class AbstractElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

    @Bean
    public abstract RestHighLevelClient elasticsearchClient();

    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter,
                                                           RestHighLevelClient elasticsearchClient) {

        ElasticsearchRestTemplate template = new ElasticsearchRestTemplate(elasticsearchClient, elasticsearchConverter);
        template.setRefreshPolicy(refreshPolicy());

        return template;
    }
}
