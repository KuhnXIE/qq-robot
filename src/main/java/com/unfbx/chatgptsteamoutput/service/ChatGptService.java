package com.unfbx.chatgptsteamoutput.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author XIE
 */
public interface ChatGptService {

    SseEmitter sendMessage(String uid, String message) throws IOException;
}
