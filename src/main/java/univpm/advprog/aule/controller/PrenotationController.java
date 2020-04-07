package univpm.advprog.aule.controller;

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
import univpm.advprog.aule.services.PrenotationService;
import univpm.advprog.aule.services.PrenotationServiceDefault;

@RequestMapping("/prenotations")
@Controller
public class PrenotationController {
	
	private PrenotationService prenotationService;
	
	@Autowired
	public void setPrenotationService(PrenotationService prenotationService) {
		this.prenotationService = prenotationService;
	}
	
	
	@GetMapping(value = "/list")
	public String list(@RequestParam(value = "message", required=false) String message, Model uiModel) {

		System.out.println(prenotationService.toString());
		System.out.println("AAA");
		
		List<Prenotation> allPrenotations = this.prenotationService.findAll();
		
		System.out.println(allPrenotations.size());
		
		uiModel.addAttribute("prenotations", allPrenotations);
		//uiModel.addAttribute("numInstruments", allInstruments.size());
		
		// TODO ricevere un parametro via GET (es. per messaggio di esito operazione)
		//uiModel.addAttribute("message", message);
		
		return "prenotations/list";
	}
	
	
	
	
}
