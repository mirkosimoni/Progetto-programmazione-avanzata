package univpm.advprog.aule.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import univpm.advprog.aule.services.AulaService;
import univpm.advprog.aule.services.PrenotationService;
import univpm.advprog.aule.services.PrenotationServiceDefault;

@RequestMapping("/prenotations")
@Controller
public class PrenotationController {
	
	private PrenotationService prenotationService;
	private AulaService aulaService;
	
	@Autowired
	public void setPrenotationService(PrenotationService prenotationService) {
		this.prenotationService = prenotationService;
	}
	
	@Autowired
	public void setAulaService(AulaService aulaService) {
		this.aulaService = aulaService;
	}
	
	
	
	@GetMapping(value = "/list")
	public String list(@RequestParam(value = "message", required=false) String message, Model uiModel) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		List<Prenotation> allPrenotations = this.prenotationService.findAll();
		
		System.out.println(allPrenotations.size());
		
		
		
		uiModel.addAttribute("formatter", formatter);
		uiModel.addAttribute("prenotations", allPrenotations);
		
		return "prenotations/list";
	}
	
	@PostMapping(value = "/search")
	public String search(@RequestParam(value = "prof_surname", required=false) String surname, 
						@RequestParam(value = "prof_name", required=false) String name,
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "aula", required=false) String aula,
						@RequestParam(value = "data", required=false) String data,
						@RequestParam(value = "ora_inizio", required=false) String oraInizio,
						@RequestParam(value = "ora_fine", required=false) String oraFine,
						@RequestParam(value = "error", required = false) String error, 
						Model uiModel) {
		
		//int int_quota = Integer.parseInt(quota);
		
		//System.out.println("Quota");
		//System.out.println(quota);
		
		//List<Aula> aule = aulaService.findAule(int_quota, -1, null);
		
		//System.out.println("Lunghezza aule");
		//System.out.println(aule.size());
		System.out.println(data);
		System.out.println(oraInizio);
		System.out.println(oraFine);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		
		error = null;
		
		if(!data.equals("") && ((!oraInizio.equals("Scegli") || !oraFine.equals("Scegli")) || (oraInizio.equals("Scegli") || oraFine.equals("Scegli")))) {
			if(!oraInizio.equals("Scegli")) {
				String data_orainizio = data + ' ' + oraInizio;
				DateTime dt_inizio = formatter.parseDateTime(data_orainizio);
				System.out.println(dt_inizio.toString());
			}else {
				String data_orainizio = data + ' ' + "01:00";
				DateTime dt_inizio = formatter.parseDateTime(data_orainizio);
				System.out.println(dt_inizio.toString());
			}
		
			if(!oraFine.equals("Scegli")) {
				String data_orafine = data + ' ' + oraFine;
				DateTime dt_fine = formatter.parseDateTime(data_orafine);
				System.out.println(dt_fine.toString());
			}else {
				String data_orafine = data + ' ' + "23:00";
				DateTime dt_fine = formatter.parseDateTime(data_orafine);
				System.out.println(dt_fine.toString());
			}
		} 
		if(data.equals("") && (!oraInizio.equals("Scegli") || !oraFine.equals("Scegli"))) {
			error = "Scegliere un giorno nel calendario";
		}
		
		uiModel.addAttribute("errorMessageData",error);
		
		return "prenotations/list";
	}
	
	
	
}
