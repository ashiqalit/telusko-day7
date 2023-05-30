package com.shortURL.shortURL;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class controller {
	private Map<String, String> urlMap = new HashMap<>();
	private Random random = new Random();
	
	@PostMapping("/generate")
	@ResponseBody
	public String generateShortUrl(@RequestParam("longUrl") String longUrl) {
		
		for(Map.Entry<String, String> entry : urlMap.entrySet()) {
			
			if(entry.getValue().equals(longUrl)) {
				return entry.getKey();
			}
			
		}
		
		String shortUrl = generateShortFromOg(longUrl);
		return shortUrl;
		
	}

	private String generateShortFromOg(String longUrl) {
		
		String chars = getChars(longUrl);
		int length = 6;
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0 ; i < length; i++) {
			
			int index = random.nextInt(chars.length());
			char randomChar = chars.charAt(index);
			sb.append(randomChar);
			
		}
		
		return "telus.ko/" + sb.toString();
	}

	private String getChars(String longUrl) {

		String rmHttp = longUrl.replaceFirst("^https://", "");
		
		String rmWww = longUrl.replaceFirst("^www", "");
		
		String characters = rmWww.replaceAll("[^a-zA-Z0-9]", "");
		
		if(characters.isEmpty()) {
			
			throw new IllegalArgumentException("invalid url");
			
		}
		
		return characters;
	}

	
	
}
