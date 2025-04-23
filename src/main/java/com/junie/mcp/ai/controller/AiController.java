package com.junie.mcp.ai.controller;

import com.junie.mcp.ai.dto.AiRequestDto;
import com.junie.mcp.ai.dto.AiResponseDto;
import com.junie.mcp.ai.service.AiService;
import com.junie.mcp.common.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public CommonResponse<AiResponseDto> generateResponse(@Valid @RequestBody AiRequestDto request) {
        log.info("Received AI generation request: {}", request);
        String response = aiService.generateResponse(request.getPrompt());
        return CommonResponse.success(new AiResponseDto(response));
    }
}