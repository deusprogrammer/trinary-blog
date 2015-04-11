package com.trinary.test.controller.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/chat")
public class ChatController {
	@Autowired protected SimpMessagingTemplate template;
	
	@RequestMapping(value="/publish", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> sendMessage(@RequestBody String message) {
		template.convertAndSend("/topic/broadcast", message);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("status", "success");
		
		return responseMap;
	}
	
	@MessageMapping("/broadcast")
	public Map<String, String> broadcastMessage(MessageHeaders headers, Map<String, String> broadcast) {
		return broadcast;
	}
}