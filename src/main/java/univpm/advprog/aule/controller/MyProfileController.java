package univpm.advprog.aule.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import univpm.advprog.aule.model.dao.*;
import univpm.advprog.aule.model.entities.*;
import univpm.advprog.aule.services.MyProfileService;
import univpm.advprog.aule.services.PrenotationService;
import univpm.advprog.aule.services.PrenotationServiceDefault;


@RequestMapping("/myprofile")
@Controller
@PropertySource("classpath:path.properties")
public class MyProfileController {
	
	private MyProfileService myprofileService;
	@Value("${path.path}")
	private String UPLOADED_FOLDER;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Autowired
	public void setMyProfileService(MyProfileService myprofileService) {
		this.myprofileService = myprofileService;
	}
	
	@GetMapping(value = "/profile")
	public String profile(Model uiModel) {
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.myprofileService.findByUsername(auth.getName());
		uiModel.addAttribute("user", user);
		String dataNascita = user.getProfile().getDataNascita().getDayOfMonth() + "-" + user.getProfile().getDataNascita().getMonthValue() +"-"+ user.getProfile().getDataNascita().getYear();
		uiModel.addAttribute("dataNascita", dataNascita);

		return "myprofile";
	}
	
	
	@PostMapping("/upload")
	public String multiFileUpload(@RequestParam("file") MultipartFile file,
	                             	Model uiModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = myprofileService.findByUsername(auth.getName());
		boolean trovato=true;
		int i = file.getOriginalFilename().length()-1;
		while(trovato & i>=0) {
			char temp = file.getOriginalFilename().charAt(i);
			if (temp=='\\' || i==0) {
				trovato=false;
			}
			i--;
		}
		try {
			byte[] bytes = file.getBytes();
			String name_image= file.getOriginalFilename().substring(i+1);
	        Path path_disk = Paths.get(UPLOADED_FOLDER + "/user_img/" + name_image);
	        String path_image = "/media/user_img/"+ name_image;
	        Files.write(path_disk, bytes);
	        System.out.println(path_image);
	        user.getProfile().setImmagine(path_image);
	        myprofileService.update(user);
		} catch(IOException e) {
			 e.printStackTrace();
		}
		
		return "redirect:/myprofile/profile";

	}
	
}


	