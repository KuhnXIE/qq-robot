package com.unfbx.chatgptsteamoutput.listener;

import java.util.HashMap;
import java.util.Map;

public class AnswerMap {

    /**
     *  issue => answer
     */
    private static Map<String, String> answer = new HashMap<>();

    public static synchronized void put(String issue, String ans) {
        answer.put(issue, ans);
    }

    public static synchronized String getAnswerByIssue(String issue) {
        return answer.get(issue);
    }

    public static synchronized String getIssueByAnswer(String ans) {

        for(Map.Entry<String, String> entry : answer.entrySet()) {
            if(entry.getValue().equals(ans)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static synchronized String removeChannel(String ans) {
        if(ans != null) {
            return answer.remove(ans);
        }
        return "";
    }

    public static Map<String, String> getChannels() {
        return answer;
    }


}
