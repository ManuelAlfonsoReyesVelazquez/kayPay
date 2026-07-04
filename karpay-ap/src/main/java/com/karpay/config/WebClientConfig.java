package com.karpay.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
	
	@Value("${ach.simulator.url}")
	private String urlBase;

    @Bean
    public WebClient achWebClient() {

        HttpClient httpClient =
                HttpClient.create()
                        .option(
                                ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                3000)
                        .doOnConnected(connection ->
                                connection.addHandlerLast(
                                        new ReadTimeoutHandler(
                                                60,
                                                TimeUnit.SECONDS)));

        return WebClient.builder()
                .baseUrl(urlBase)
//                .baseUrl("http://localhost:8081")
                .clientConnector(
                        new ReactorClientHttpConnector(httpClient))
                .build();

    }

}