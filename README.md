# openIDEMCP

openIDEMCP is an open-source Model Control Platform (MCP) that lets clients interact with AI models through a single API.
It runs AI models locally or via cloud, processes tasks sequentially, and returns code snippets in JSON, enabling modular,
lightweight, and flexible code generation and automation

# Project Status
Now is Alfa period

## Project Philosophy & Idea

**openIDEMCP** is not just “an additional MCP for code.”  
It is a platform that gives you full control over code generation and lets you choose the level of abstraction for each task.

---

## Key Idea: *Choose, Don't Coerce*

Instead of forcing users to use AI to the fullest in every scenario, we provide a **three‑layer architecture**:

```
┌───────────────────────────────────────────────────────┐
│           Interaction Level                           │
├───────────────────────────────────────────────────────┤
│  Level 3: Autonomous AI → Autonomous mode             │
│  Level 2: Managed   → HTTP‑API → control + flexibility│
│  Level 1: Manual    → Direct calls → Maximum control  │
└───────────────────────────────────────────────────────┘
                          ↓
                 Unified generation engine
```

---

## Architectural Principles

### 1️ HTTP‑First Approach  
- **RESTful API** – the primary and only source of truth.  
- Supports any client: curl, Postman, IDE plugins, web interface.  
- Easy to test and debug.  
- Easily cached and load balanced using rented proxies.

### 2️ Server‑Side Prompt Logic  
- Plus that all prompts reside on the server:  
  - *Quality guaranteed* – templates undergo verification.  
  - *Coordinated updates* – new rules apply to everyone instantly.  
  - *Contextual adaptation* – real‑time project analysis.

### 3️ MCP as an Optional Adapter  
- **Independent layer** – can be omitted.  
- Converts MCP calls into HTTP requests.  
- Works with any AI models: ChatGPT, Claude, Llama, Grok, and more.

---

## Three Ways to Use

### Level 1 – Direct HTTP Calls (Maximum Control)

```bash
curl -X POST http://localhost:8080/api/ask_model \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "signature of fgets function",
    "model": "gemma3"
  }'
```

> **For:** Developers who need full parameter control.

### Level 2 – Smart CLI (Balance of Convenience and Control)

```bash
# Automatic mode: the server auto‑evaluates the required set of parameters
openIDEMCP generate --file src/main.rs --description "JSON parsing"

# Manual mode: precise configuration
openIDEMCP generate \
  --template function_senior \
  --language rust \
  --requirements "tests, documentation, error handling"
```

> **For:** Everyday development with quick feedback and quality control.

### Level 3 – Autonomous AI via MCP (Full Convenience)

*A user using a model such as gpt/claude/others sends the request to the MCP adapter → HTTP server → code generation.*

> **For:** Integration with code assistants and editors.

---

## Technical Implementation

### Core System: *Smart Prompt Engine*

```java
public class SmartPromptEngine {
    public String buildPrompt(GenerationContext ctx) {
        // 1. Base template (function, class, test, etc.)
        // 2. Language idioms (Pythonic, Rust‑like, etc.)
        // 3. Quality requirements (senior, production)
        // 4. Project context (code analysis)
        // 5. User requirements
        return assemblePrompt(ctx);
    }
}
```

### Configuration via Code and Files

```yaml
# .codex/config.yaml
project:
  language: rust
  framework: actix-web
  code_style:
    documentation: rustdoc
    error_handling: thiserror + anyhow
    testing: unit + integration

templates:
  function_senior:
    guarantees:
      - documentation
      - error_handling
      - unit_tests
      - performance_optimization
```

### MCP Adapter (Independent Component)

```java
public class McpAdapter {
    // Only started when needed
    // Makes a request to the HTTP API, adding dialog context
}
```

---

## Advantages of the Architecture

| For Who | What You Get |
|----------|--------------|
| Developers | **Continuous integration** – can hook into any point of the chain. |
| Projects | **Unified standards** – templates ensure high‑quality code. |
| Community | **Open standards** – HTTP API and MCP adapter work with any tools. |

---

## Technology Stack

| Layer | Technology | Purpose |
|-------|------------|---------|
| Backend | Javalin (Java) | High performance and type safety |
| Generation | Ollama / Llama.cpp (local) | Alternative to cloud APIs |
| Interfaces | RESTful HTTP + MCP (optional) | Universal interaction mechanism |
| Configuration | YAML + annotations | Flexible typing and convenience |
| Clients | CLI (Rust) for first, IDE plugins, web interface in future | Diverse ways to work |

---

## Roadmap

| Phase | Brief Description |
|-------|-------------------|
| **1 – Stable HTTP Core** | Full‑featured REST API, template library, CLI |
| **2 – Integration Ecosystem** | MCP adapter, IDE plugins, web UI |
| **3 – Advanced Analytics** | Contextual code analysis, ML models, collaborative editing |

---

## Development Principles

1. **HTTP‑First Priority** – all features are exposed through the API.  
2. **Configuration as Code** – settings live in the project.  
3. **Incremental Complexity** – start simple, progress to advanced.  
4. **Ecosystem Compatibility** – support existing tools.

---

## In Conclusion

**openIDEMCP** is not just a choice between control and convenience. It is an architecture that lets you enable **any level of code management**—from manual requests to autonomous AI—within a single technological base. Start with simple HTTP calls, move to the smart CLI, connect AI assistants—you can all do this without rewriting your existing workflow.

**openIDEMCP** gives you both control and convenience, tailored to your needs. Begin with direct HTTP calls, transition to the smart CLI, and integrate AI assistants—all on the same technology foundation.