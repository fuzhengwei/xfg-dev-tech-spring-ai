package cn.bugstack.xfg.dev.tech.test;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.huggingface.HuggingfaceChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HuggingfaceTest {

    @Resource
    private HuggingfaceChatClient huggingfaceChatClient;

    @Test
    public void test() {
        ChatResponse call = huggingfaceChatClient.call(new Prompt(
                "1+1",
                OpenAiChatOptions.builder()
                        .withModel("Xenova/gpt-4o")
                        .build()
        ));
        log.info("测试结果: {}", call);
    }


}
