package com.hospital.app.config;

import com.hospital.app.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AssistantConfig {

    // 持久化记忆自定义类实例化
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;


    @Bean
    ChatMemoryProvider chatMemoryProvider () {
        return memoryId -> MessageWindowChatMemory.builder ()
                .id(memoryId)  // 每个会话的记忆ID,必须是唯一的
                .maxMessages(10) // 每个会话的记忆长度
                .chatMemoryStore(mongoChatMemoryStore) // 使用持久化记忆
                .build() ;
    }

    @Bean
    public EmbeddingStore  store() {
        //加载文档
        List<Document> documents = ClassPathDocumentLoader
                .loadDocuments("docs");
        //构建内存向量数据库操作对象
        InMemoryEmbeddingStore store=new InMemoryEmbeddingStore();
        //构建文档分割器对象
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .build();
        //批量向量化并保存进内存向量数据库
        ingestor.ingest(documents);
        return store;
    }
    //构建向量数据库检索对象
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store) {
       return  EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store) // 设置向量数据库
                .minScore(0.5)// 设置最小匹配分数
                .maxResults(3)// 设置最大匹配数量
                .build();

    }
}
