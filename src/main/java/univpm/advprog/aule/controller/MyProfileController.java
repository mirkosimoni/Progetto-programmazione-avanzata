package univpm.advprog.aule.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import univpm.advprog.aule.model.dao.*;
import univpm.advprog.aule.model.entities.*;
import univpm.advprog.aule.services.MyProfileService;
import univpm.advprog.aule.services.PrenotationService;
import univpm.advprog.aule.services.PrenotationServiceDefault;


@RequestMapping("/myprofile")
@Controller
public class MyProfileController {
private MyProfileService myprofileservice;
	
	@Autowired
	public void setMyProfileService(MyProfileService myprofileService) {
		this.myprofileservice = myprofileService;
	}
	
	@GetMapping(value = "/profile")
	public String profile() {

		return "myprofile";
	}
	
}


	