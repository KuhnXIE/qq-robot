package com.unfbx.chatgptsteamoutput.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 接收信息对象
 *
 * @author XIE
 */
@NoArgsConstructor
@Data
public class ReceiveMesVo {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("choices")
    private List<ChoicesDTO> choices;
    @JsonProperty("model")
    private String model;

    @NoArgsConstructor
    @Data
    public static class ChoicesDTO {
        @JsonProperty("text")
        private String text;
        @JsonProperty("index")
        private Integer index;
        @JsonProperty("logprobs")
        private Object logprobs;
        @JsonProperty("finish_reason")
        private Object finishReason;
    }
}
