package org.ispp4.cohabify.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebPageService {

    private final WebClient webClient;

    public WebPageService() {
        this.webClient = WebClient.create();
    }

    public Mono<String> getPageContent(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }
}