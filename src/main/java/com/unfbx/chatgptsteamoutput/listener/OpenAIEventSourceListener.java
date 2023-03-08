package com.unfbx.chatgptsteamoutput.listener;

import cn.hutool.json.JSONUtil;
import com.unfbx.chatgptsteamoutput.vo.ReceiveMesVo;
import com.unfbx.chatgptsteamoutput.ws.TioSocketChannels;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述：OpenAIEventSourceListener
 *
 * @author https:www.unfbx.com
 * @date 2023-02-22
 */
@Slf4j
public class OpenAIEventSourceListener extends EventSourceListener {

    private SseEmitter sseEmitter;

    private String uid;

    public OpenAIEventSourceListener(SseEmitter sseEmitter, String uid) {
        this.sseEmitter = sseEmitter;
        this.uid = uid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI建立sse连接...");
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("OpenAI返回数据：{}", data);
        if (data.equals("[DONE]")) {
            log.info("OpenAI返回数据结束了");
//            sendToq("[DONE]", "[DONE]");
            ChannelContext channelContext = TioSocketChannels.getChannelById(uid);
            if (channelContext != null){
                WsResponse wsResponse = WsResponse.fromText(AnswerMap.removeChannel(uid), "UTF-8");
                Tio.sendToUser(channelContext.tioConfig, channelContext.userid, wsResponse);
            }
            return;
        }

        ReceiveMesVo receiveMesVo = JSONUtil.toBean(data, ReceiveMesVo.class);

        String text = AnswerMap.getAnswerByIssue(uid);
        if (text == null){
            text = receiveMesVo.getChoices().get(0).getText();
        }else {
            text = text + receiveMesVo.getChoices().get(0).getText();
        }
        AnswerMap.put(uid, text);
//        sendToq(completionResponse.getId(), text);
    }

    private void sendToq(String s, String s2) throws IOException {
        sseEmitter.send(SseEmitter.event()
                .id(s)
                .data(s2)
                .reconnectTime(3000));
    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI关闭sse连接...");
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if(Objects.isNull(response)){
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", body.string(), t);
        } else {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
    }
}
