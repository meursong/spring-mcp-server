package com.junie.mcp.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AiRequestDto {

    @NotBlank(message = "Prompt cannot be empty")
    @Size(min = 1, max = 4000, message = "Prompt must be between 1 and 4000 characters")
    private String prompt;
}