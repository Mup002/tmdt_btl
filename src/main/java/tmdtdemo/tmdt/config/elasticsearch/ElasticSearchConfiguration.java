package tmdtdemo.tmdt.config.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class ElasticSearchConfiguration
{
    @Bean
    public RestClient getResClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic","VhCklKZqrv2I3bco09_0"));
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial((chain,authType) -> true)
                .build();
        RestClientBuilder restClient = RestClient.builder(
                        new HttpHost("localhost", 9200, "https")) // Change to HTTPS
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext)
                                .setDefaultIOReactorConfig(IOReactorConfig.custom()
                                        .setIoThreadCount(1)
                                        .setSoTimeout(60000)
                                        .build());
                    }
                });
        return restClient.build();
    }
    @Bean
    ElasticsearchTransport getElasticsearchTransport() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new RestClientTransport(
                getResClient(),new JacksonJsonpMapper()
        );
    }
    @Bean
    public ElasticsearchClient getElasticsearchClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }

}