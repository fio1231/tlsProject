package com.tls.server.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient() {

		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
				.doOnConnected(conn -> conn
						.addHandlerLast(new ReadTimeoutHandler(10))
						.addHandlerLast(new WriteTimeoutHandler(10)));

		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
				.build();

		ExchangeFilterFunction errorFilter = ExchangeFilterFunction
				.ofResponseProcessor(this::exchangeFilterResponseProcessor);

		return WebClient.builder()
				.clientConnector(connector)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.exchangeStrategies(exchangeStrategies)
				//.filter(errorFilter)
				.build();
	}

	private Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
//		HttpStatus status = (HttpStatus) response.statusCode();
//		if (status.is5xxServerError()) {
//			return response.bodyToMono(Object.class)
//					.flatMap(body -> Mono.just(new error4xx(status, body)));
//		}
//		if (status.is4xxClientError()) {
//			return response.bodyToMono(Object.class)
//					.flatMap(body -> Mono.error(new error5xx(status, body)));
//		}
		return Mono.just(response);
	}

	@Getter
	private static class error4xx extends RuntimeException {
		private final HttpStatus status;
		private final Object body;

		public error4xx(HttpStatus status, Object body) {
			this.status = status;
			this.body = body;
		}
	}

	@Getter
	private static class error5xx extends RuntimeException {
		private final HttpStatus status;
		private final Object body;

		public error5xx(HttpStatus status, Object body) {
			this.status = status;
			this.body = body;
		}
	}

}
