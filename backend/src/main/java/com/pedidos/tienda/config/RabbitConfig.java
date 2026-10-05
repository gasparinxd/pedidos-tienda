package com.pedidos.tienda.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String COLA_PEDIDOS = "pedidos.creados";

	@Bean
	public Queue colaPedidos() {
		return new Queue(COLA_PEDIDOS, true);
	}
}
