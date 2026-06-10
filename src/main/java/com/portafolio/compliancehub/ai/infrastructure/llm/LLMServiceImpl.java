package com.portafolio.compliancehub.ai.infrastructure.llm;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.portafolio.compliancehub.ai.application.internal.outboundservices.llm.LLMService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LLMServiceImpl implements LLMService {

    private final ChatModel chatModel;

    @Override
    public String generate(String prompt) {
        return chatModel.call(prompt);
    }
}
