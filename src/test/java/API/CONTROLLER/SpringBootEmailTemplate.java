package API.CONTROLLER;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import API.DTO.MailRequest;
import API.DTO.MailResponse;
import API.SERVICE.EmailService;




@SpringBootApplication
@RestController
@ComponentScan("API.")
public class SpringBootEmailTemplate {
	@Autowired
	private EmailService service;
	
	@PostMapping(path="/sendingmail",consumes = "application/json")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		Map<String,Object> model=new HashMap<>();
		model.put("Name",request.getName());
		model.put("Phone",request.getPhone());
		return service.sendEmail(request, model);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailTemplate.class, args);
}


}
