package com.nuevaesperanza.demo.config;


import com.nuevaesperanza.demo.util.FiltroEstado;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateFilterConfig {

    @Bean
    public FiltroEstado filtroEstado() {
        return new FiltroEstado() {}; // clase anónima porque FiltroEstado es abstract
    }
}

