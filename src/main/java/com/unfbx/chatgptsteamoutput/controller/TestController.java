package com.unfbx.chatgptsteamoutput.controller;

import com.unfbx.chatgptsteamoutput.listener.AnswerMap;
import com.unfbx.chatgptsteamoutput.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-03-01
 */
@Controller
public class TestController {

    @Autowired
    private ChatGptService chatGptService;

    @GetMapping("/test/sse")
    @CrossOrigin
    public SseEmitter sseEmitter(@RequestParam("uid") String uid, @RequestParam("message") String message) throws IOException {
        SseEmitter sseEmitter = chatGptService.sendMessage(uid, message);
        return sseEmitter;
    }

    @GetMapping("/test/print")
    public void print(){
        Map<String, String> channels = AnswerMap.getChannels();
        channels.forEach((key, value) -> {
            System.out.println("问题id:" + key + "问题答案:" + value);
        });
    }
}
