package com.hospital.app.store;

import com.hospital.app.domain.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        //创建查询条件
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        //创建查询对象
        Query query = new Query(criteria);
        //查询
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        if (chatMessages == null)
            return new LinkedList<>();
        //将json字符串转为List<ChatMessage>
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getMessages());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        //创建查询条件   update chat_messages  set  content = ? where memoryId = ?
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        //创建查询对象
        Query query = new Query(criteria);
        //创建更新对象
        Update update = new Update();
        //设置更新内容
        update.set("messages", ChatMessageSerializer.messagesToJson(messages));
        //根据query条件能查询出文档，则修改文档；否则新增文档
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        //创建查询条件  delete chat_messages where memoryId = ?
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        //创建查询对象
        Query query = new Query(criteria);
        //删除
        mongoTemplate.remove(query, ChatMessages.class);

    }
}
