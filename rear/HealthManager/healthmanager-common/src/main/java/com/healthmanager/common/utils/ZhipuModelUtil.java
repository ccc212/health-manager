package com.healthmanager.common.utils;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZhipuModelUtil {

    private static String apiKey;
    private static String model;

    @Value("${zhipu.apiKey}")
    private String apiKeyValue;

    @Value("${zhipu.model}")
    private String modelValue;

    @PostConstruct
    public void init() {
        ZhipuModelUtil.apiKey = this.apiKeyValue;
        ZhipuModelUtil.model = this.modelValue;
    }

    public static String callZhipuModel(String input) {
        ClientV4 client = new ClientV4.Builder(apiKey).build();
        List<ChatMessage> messages = new ArrayList<>();

        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), input);
        messages.add(chatMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(model)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();

        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        return parseResult(JSON.toJSONString(invokeModelApiResp));
    }

    @SneakyThrows
    public static String parseResult(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(result);
        JsonNode contentNode = rootNode.path("data").path("choices").get(0).path("message").path("content");
        return contentNode.asText();
    }
}
