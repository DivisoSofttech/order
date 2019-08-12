package com.diviso.graeshoppe.order.web.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationSocketController {

	

	@Autowired
	private  SimpMessagingTemplate template;
	
	@MessageMapping("/send/message")
    public void send(Principal principal) {
    	System.out.println("User is #################################### "+principal.getName());
    	template.convertAndSendToUser(principal.getName(), "/queue/notification", "Message from "+principal.getName());
    
    	
	}
}
