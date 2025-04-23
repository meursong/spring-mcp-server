package com.junie.mcp.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;

    /**
     * Generate a response from the AI model using a simple prompt
     *
     * @param userPrompt The user's input prompt
     * @return The AI-generated response
     */
    public String generateResponse(String userPrompt) {
        log.debug("Generating AI response for prompt: {}", userPrompt);
        Message userMessage = new UserMessage(userPrompt);
        Prompt prompt = new Prompt(List.of(userMessage));
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * Generate a response from the AI model using a system prompt template and user input
     *
     * @param systemPromptTemplate The system prompt template
     * @param parameters The parameters to fill in the template
     * @param userPrompt The user's input prompt
     * @return The AI-generated response
     */
    public String generateResponseWithTemplate(String systemPromptTemplate, 
                                              Map<String, Object> parameters, 
                                              String userPrompt) {
        log.debug("Generating AI response with template for prompt: {}", userPrompt);
        SystemPromptTemplate template = new SystemPromptTemplate(systemPromptTemplate);
        Message systemMessage = template.createMessage(parameters);
        Message userMessage = new UserMessage(userPrompt);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }
}
