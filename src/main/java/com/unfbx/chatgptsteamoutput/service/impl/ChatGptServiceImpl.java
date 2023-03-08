package com.unfbx.chatgptsteamoutput.service.impl;

import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgptsteamoutput.listener.OpenAIEventSourceListener;
import com.unfbx.chatgptsteamoutput.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ChatGptServiceImpl implements ChatGptService {

    @Autowired
    private OpenAiStreamClient openAiStreamClient;

    @Override
    public SseEmitter sendMessage(String uid, String message) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(-1L);
        sseEmitter.send(SseEmitter.event().id("root").name("连接成功！！！！").data(LocalDateTime.now()).reconnectTime(3000));
        sseEmitter.onCompletion(() -> {
            System.out.println(LocalDateTime.now() + ", uid#" + uid + ", on completion");
        });
        sseEmitter.onTimeout(() -> System.out.println(LocalDateTime.now() + ", uid#" + uid + ", on timeout#" + sseEmitter.getTimeout()));
        sseEmitter.onError(throwable -> System.out.println(LocalDateTime.now() + ", uid#" + uid + ", on error#" + throwable.toString()));
        OpenAIEventSourceListener openAIEventSourceListener = new OpenAIEventSourceListener(sseEmitter, uid);
        openAiStreamClient.streamCompletions(message,openAIEventSourceListener);
        return sseEmitter;
    }
}
