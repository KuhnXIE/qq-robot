package com.unfbx.chatgptsteamoutput;

import com.unfbx.chatgpt.OpenAiStreamClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 描述：ChatgptSteamOutputApplication
 *
 * @author https:www.unfbx.com
 * @date 2023-02-28
 */
@SpringBootApplication
public class ChatgptSteamOutputApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatgptSteamOutputApplication.class, args);
    }

    @Value("${apikey}")
    private String apiKey;

    @Bean
    public OpenAiStreamClient openAiStreamClient() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 33210));
        OpenAiStreamClient openAiStreamClient = OpenAiStreamClient.builder()
                .connectTimeout(50)
                .readTimeout(50)
                .writeTimeout(50)
                .apiKey(apiKey)
                .proxy(proxy)
                .build();

        return openAiStreamClient;
    }

}
