# Spring Boot AI MCP Server

## Overview
This project is a Spring Boot application that implements the Model-Controller-Presenter (MCP) architecture pattern for AI-powered applications. It provides a foundation for building AI-enhanced applications using Spring AI and OpenAI integration.

## Features
- Spring Boot 3.2.3 with Java 17
- Spring AI integration with OpenAI
- MCP architecture pattern
- RESTful API endpoints
- Exception handling
- Validation
- Unit testing

## Architecture
The project follows the MCP (Model-Controller-Presenter) architecture pattern:
- **Model**: Represents the data and business logic
- **Controller**: Handles HTTP requests and responses
- **Presenter**: Formats data for presentation

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── junie/
│   │           └── mcp/
│   │               ├── ai/
│   │               │   ├── config/
│   │               │   ├── controller/
│   │               │   ├── dto/
│   │               │   └── service/
│   │               └── common/
│   │                   ├── dto/
│   │                   ├── entity/
│   │                   └── exception/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── junie/
                └── mcp/
                    └── ai/
                        └── service/
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 8 or higher
- OpenAI API key

### Configuration
Set your OpenAI API key in the application.properties file or as an environment variable:
```
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

### Building the Project
```bash
./gradlew build
```

### Running the Project
```bash
./gradlew bootRun
```

## API Endpoints

### Generate AI Response
```
POST /api/v1/ai/generate
```
Request body:
```json
{
  "prompt": "Your prompt here"
}
```
Response:
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "content": "AI-generated response"
  },
  "timestamp": "2023-06-01T12:00:00"
}
```

## License
This project is licensed under the MIT License - see the LICENSE file for details.
