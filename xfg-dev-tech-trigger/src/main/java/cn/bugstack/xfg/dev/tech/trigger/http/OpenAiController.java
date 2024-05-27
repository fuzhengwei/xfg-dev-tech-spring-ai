package cn.bugstack.xfg.dev.tech.trigger.http;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/openai/")
public class OpenAiController {

    @Resource
    private OpenAiChatClient chatClient;

    /**
     * curl http://localhost:8090/api/v1/openai/generate?message=1+1
     */
    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public ChatResponse generate(@RequestParam String message) {
        return chatClient.call(
                new Prompt(
                        message,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-4o")
                                .build()
                ));
    }

    /**
     * curl http://localhost:8090/api/v1/openai/generate_stream?message=1+1
     */
    @RequestMapping(value = "generate_stream", method = RequestMethod.GET)
    public Flux<ChatResponse> generateStream(@RequestParam String message) {
        return chatClient.stream(new Prompt(message));
    }

}
