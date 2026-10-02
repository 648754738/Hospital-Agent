package com.hospital.app.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * @AiService 常用配置参数如下:
 * 1.chatModel ： 指定大模型名称
 * 2.wiringMode: 指定大模型参数的注入方式
 * 3.chatMemory : 指定大模型记忆
 * 4.chatMemoryProvider：指定大模型记忆提供者,其实就是隔离
 * 5.streamingChatModel: 指定流式大模型
 * 6.tools: 添加外挂工具，处理我们自己的项目的核心业务逻辑
 * 7. contentRetriever: 添加内容检索器，用于处理内容检索
 */
@AiService(streamingChatModel = "qwenStreamingChatModel"
        , wiringMode = AiServiceWiringMode.EXPLICIT,
        chatMemoryProvider = "chatMemoryProvider",
        tools = "hospitalTools",
        contentRetriever = "contentRetriever")
public interface Assistant {

    @SystemMessage(fromResource = "my-prompt-template.txt")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String question);
}
