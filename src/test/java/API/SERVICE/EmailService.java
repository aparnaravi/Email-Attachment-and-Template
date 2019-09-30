package API.SERVICE;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import API.DTO.MailRequest;
import API.DTO.MailResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;
	@Autowired
	private Configuration config;
	
	public MailResponse sendEmail(MailRequest request,Map<String,Object> model) {
		MailResponse response = new MailResponse();
		MimeMessage message =sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name()); 
			helper.addAttachment("image.png", new ClassPathResource("image.png"));
			helper.addAttachment("Logo.fw.png", new ClassPathResource("Logo.fw.png"));
			Template t =config.getTemplate("email-template.ftl");
			String html =FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			helper.setTo(request.getTo());
			helper.setText(html,true);
			helper.setSubject(request.getSubject());
			helper.setFrom(request.getFrom());
			sender.send(message);
			response.setMessage("mail sent to " +request.getTo());
			response.setStatus(Boolean.TRUE);
			
		}catch(MessagingException e ) {
			response.setMessage("mail not delivered " +e.getMessage());
			response.setStatus(Boolean.FALSE);
		}catch( IOException e) {
			response.setMessage("mail not delivered " +e.getMessage());
			response.setStatus(Boolean.FALSE);
		}catch( TemplateException e) {
			response.setMessage("mail not delivered " +e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		return response;
		
	}
	
	
}
