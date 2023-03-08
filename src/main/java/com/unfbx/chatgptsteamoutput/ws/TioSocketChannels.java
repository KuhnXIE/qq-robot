package com.unfbx.chatgptsteamoutput.ws;


import org.tio.core.ChannelContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ThinkPad
 */
public class TioSocketChannels {

    private static Map<String, ChannelContext> channels = new HashMap<>();

    public static synchronized void put(String id, ChannelContext channel) {
        channels.put(id, channel);
    }

    public static synchronized ChannelContext getChannelById(String id) {
        return channels.get(id);
    }

    public static synchronized String getIdByChannel(ChannelContext channel) {

        for(Map.Entry<String, ChannelContext> entry : channels.entrySet()) {
            if(entry.getValue().equals(channel)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static synchronized void removeChannel(ChannelContext channel) {
        String id = getIdByChannel(channel);
        if(id != null) {
            channels.remove(id);
        }
    }

    public static Map<String, ChannelContext> getChannels() {
        return channels;
    }
}
