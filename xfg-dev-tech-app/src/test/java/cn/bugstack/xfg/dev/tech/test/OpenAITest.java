package cn.bugstack.xfg.dev.tech.test;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

/**
 * 官方文档地址：https://docs.spring.io/spring-ai/reference/1.0-SNAPSHOT/index.html
 * 官方开源地址：https://github.com/spring-projects/spring-ai
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenAITest {

    @Resource
    private OpenAiChatClient chatClient;
    @Resource
    private OpenAiImageClient openaiImageClient;

    @Test
    public void test_generate() {
        ChatResponse chatResponse = chatClient.call(
                new Prompt(
                        "1+1",
                        OpenAiChatOptions.builder()
                                .withModel("gpt-4o")
                                .build()
                ));

        log.info("测试结果：{}", JSON.toJSONString(chatResponse));
    }

    @Test
    public void test_generate_stream() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<ChatResponse> stream = chatClient.stream(new Prompt("1+1"));
        stream.subscribe(
                chatResponse -> {
                    AssistantMessage output = chatResponse.getResult().getOutput();
                    log.info("测试结果: {}", JSON.toJSONString(output));
                },
                Throwable::printStackTrace,
                () -> {
                    countDownLatch.countDown();
                    System.out.println("Stream completed");
                }
        );

        countDownLatch.await();
    }

    @Test
    public void test_generate_image() {
        ImageResponse imageResponse = openaiImageClient.call(
                new ImagePrompt("画个小狗",
                        OpenAiImageOptions.builder()
                                .withModel("dall-e-3")
                                .withQuality("hd")
                                .withN(1)
                                .withHeight(1024)
                                .withWidth(1024)
                                .build()
                )
        );

        log.info("测试结果: {}", JSON.toJSONString(imageResponse));
    }

}
