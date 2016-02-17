package com;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MyController {

	private static final Logger logger = Logger.getLogger(MyController.class.getName());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		logger.info("wellcomePage");
		return "mail_form";
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public ModelAndView sendMailPage(
			@RequestParam(value = "from") String sendFrom, 
			@RequestParam(value = "to") String sendTo) {
		logger.info("sendMailPage");
		ModelAndView modelAndView = new ModelAndView("mail_form");
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sendFrom, "Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo, "Mr. User"));
			msg.setSubject("mail sent on " + new Date());

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("This is message body<br>otra lineallll");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			MimeBodyPart attachment = new MimeBodyPart();
			InputStream attachmentDataStream = new ByteArrayInputStream("toma toma toma".getBytes());
			attachment.setFileName("manual.pdf");
			attachment.setContent(attachmentDataStream, "application/pdf");
			multipart.addBodyPart(attachment);
			msg.setContent(multipart);
			Transport.send(msg);
			modelAndView.addObject("result", "ok");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.toString(), ex);
		}
		return modelAndView;
	}
}