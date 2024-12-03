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
import java.math.BigDecimal;
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

    // 健身计划
    public static String buildFitnessPlanMessage(String initialStatus, String gender, int age, BigDecimal height, BigDecimal weight, String goals, String exerciseList) {
        StringBuilder message = new StringBuilder();
        message.append("现在需要你根据我的身体状况以及目标来给我制定健身计划,")
                .append("我的身体状况是 " + initialStatus)
                .append(",我的性别是 " + gender)
                .append(",我的年龄是 " + age)
                .append(",我的身高是 " + height)
                .append(",我的体重是 " + weight)
                .append(",我的目标是 " + goals)
                .append(",请在以下的健身项目给我指定健身计划,并给我推荐合适的强度和时长," + exerciseList)
                .append(".在:前的是健身项目id,在:后是健身项目名,")
                .append("你返回结果按我的格式进行返回,无需任何多余的文字描述,按以下格式返回:")
                .append("健身项目id:强度:时间。")
                .append("用换行符分隔,不需要健身项目名,其中强度0表示低,1表示中,2表示高;时间的单位是分钟;不用返回所有健身项目,只用返回需要的");
        return message.toString();
    }

    // 心理咨询
    public static String buildPsychologyCounseling(String question) {
        StringBuilder message = new StringBuilder();
        message.append("现在需要你根据我的问题来给我回复心理咨询,我的心理问题是 " + question);
        return message.toString();
    }
}
