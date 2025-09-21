package br.com.rbr.agente_reneg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;

@Configuration
public class AgenteRenegConfig {

    @Bean
    public InMemoryChatMemoryRepository chatMemoryRepository() {
        return new InMemoryChatMemoryRepository();
    }
}