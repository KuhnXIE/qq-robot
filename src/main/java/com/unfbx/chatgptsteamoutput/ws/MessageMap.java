package com.unfbx.chatgptsteamoutput.ws;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据消息获取ws连接id
 * @author XIE
 */
public class MessageMap {

    /**
     * messsage => userId
     */
    private static Map<String, String> message = new HashMap<>();

    public static synchronized void put(String mes, String userId) {
        message.put(mes, userId);
    }

    public static synchronized String getUserIdById(String mes) {
        return message.get(mes);
    }

    public static synchronized String getMesByUserId(String userId) {

        for(Map.Entry<String, String> entry : message.entrySet()) {
            if(entry.getValue().equals(userId)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static synchronized void removeChannel(String userId) {
        String id = getMesByUserId(userId);
        if(id != null) {
            message.remove(id);
        }
    }

    public static Map<String, String> getChannels() {
        return message;
    }
    
}
