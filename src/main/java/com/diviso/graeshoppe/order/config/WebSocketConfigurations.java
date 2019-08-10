package com.diviso.graeshoppe.order.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigurations implements WebSocketMessageBrokerConfigurer{
	private final JHipsterProperties jHipsterProperties;

	public WebSocketConfigurations(JHipsterProperties jHipsterProperties) {
		this.jHipsterProperties = jHipsterProperties;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		String[] allowedOrigins = Optional.ofNullable(jHipsterProperties.getCors().getAllowedOrigins())
				.map(origins -> origins.toArray(new String[0])).orElse(new String[0]);
		registry.addEndpoint("/order-notifications").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app")
		.enableSimpleBroker("/queue/notification","/topic");
		
		//registry.setUserDestinationPrefix("/secured/user");
	}
}
