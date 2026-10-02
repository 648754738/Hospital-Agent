package com.hospital.app.controller;

import com.hospital.app.assistant.Assistant;
import com.hospital.app.domain.ChatMessages;
import com.hospital.app.domain.Department;
import com.hospital.app.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/agent")
@CrossOrigin
public class HospitalController {
    @Autowired
    private Assistant assistant;
    @Autowired
    private IDepartmentService departmentService;
    @PostMapping(value = "/chat",  produces = "text/html;charset=utf8")
    public Flux<String> chat(@RequestBody ChatMessages chatMessages) {
        return assistant.chat(chatMessages.getMemoryId(), chatMessages.getMessages());
    }



}
