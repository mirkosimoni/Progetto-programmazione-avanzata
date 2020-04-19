package univpm.advprog.aule.controller;


import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import univpm.advprog.aule.model.dao.*;
import univpm.advprog.aule.model.entities.*;
import univpm.advprog.aule.services.AulaService;
import univpm.advprog.aule.services.MyProfileService;
import univpm.advprog.aule.services.PrenotationService;
import univpm.advprog.aule.services.PrenotationServiceDefault;

@RequestMapping("/prenotations")
@Controller
public class PrenotationController {
	
	private PrenotationService prenotationService;
	private AulaService aulaService;
	private MyProfileService profileService;
	
	
	@Autowired
	public void setPrenotationService(PrenotationService prenotationService) {
		this.prenotationService = prenotationService;
	}
	
	@Autowired
	public void setAulaService(AulaService aulaService) {
		this.aulaService = aulaService;
	}
	
	@Autowired
	public void setProfileRepository(MyProfileService userRepository) {
		this.profileService = userRepository;
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
						@RequestParam(value = "aula", required=false) String nome_aula,
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
		
		SimpleDateFormat formatter_view = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		error = null;
		
		if(surname == "") surname = null;
		if(name == "") name = null;
		if(quota == "") quota = null;
		if(nome_aula == "") nome_aula = null;
		
		if(!data.equals("") && ((!oraInizio.equals("Scegli") || !oraFine.equals("Scegli")))) {
			DateTime dt_inizio = new DateTime();
			DateTime dt_fine = new DateTime();
			if(!oraInizio.equals("Scegli")) {
				String data_orainizio = data + ' ' + oraInizio;
				dt_inizio = formatter.parseDateTime(data_orainizio);
				System.out.println(dt_inizio.toString());
			}else {
				String data_orainizio = data + ' ' + "01:00";
				dt_inizio = formatter.parseDateTime(data_orainizio);
				System.out.println(dt_inizio.toString());
			}
		
			if(!oraFine.equals("Scegli")) {
				String data_orafine = data + ' ' + oraFine;
				dt_fine = formatter.parseDateTime(data_orafine);
				System.out.println(dt_fine.toString());
			}else {
				String data_orafine = data + ' ' + "23:00";
				dt_fine = formatter.parseDateTime(data_orafine);
				System.out.println(dt_fine.toString());
			}
	
			List<Prenotation> prenotations = this.prenotationService.findPrenotationsRange(surname, name, quota, nome_aula, dt_inizio, dt_fine);
			System.out.println("prenotations size");
			System.out.println(prenotations.size());
			uiModel.addAttribute("prenotations",prenotations);
		} 
		
		if(!data.equals("") && (oraInizio.equals("Scegli") && oraFine.equals("Scegli"))) {
			String data_0 = data + ' ' + "00:00";
			DateTime data_datetime = formatter.parseDateTime(data_0);
			List<Prenotation> prenotations = this.prenotationService.findPrenotationsData(surname, name, quota, nome_aula, data_datetime);
			uiModel.addAttribute("prenotations",prenotations);
		}
		
		
		if(data.equals("")) {
			List <Prenotation> prenotations = this.prenotationService.findPrenotations(surname, name, quota, nome_aula);
			uiModel.addAttribute("prenotations", prenotations);
		}
		
		
		//if(data.equals("") && (!oraInizio.equals("Scegli") || !oraFine.equals("Scegli"))) {
		//	error = "Scegliere un giorno nel calendario se si desidera effettuare una ricerca per fascia oraria";
		//	List<Prenotation> allPrenotations = prenotationService.findAll();
		//	uiModel.addAttribute("prenotations", allPrenotations);
		//}
		
		uiModel.addAttribute("formatter",formatter_view);
		uiModel.addAttribute("errorMessageData",error);
		
		return "prenotations/list";
	}
	
	
	@PostMapping(value = "/create")
	public String create(@RequestParam(value = "prof_surname", required=false) String surname, 
						@RequestParam(value = "prof_name", required=false) String name,
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "aula", required=false) String aula,
						@RequestParam(value = "data", required=false) String data,
						@RequestParam(value = "ora_inizio", required=false) String oraInizio,
						@RequestParam(value = "ora_fine", required=false) String oraFine,
						@RequestParam(value = "error", required = false) String error, 
						Model uiModel) {
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		uiModel.addAttribute("variabile_view", auth.getName());
		User user = this.profileService.findByUsername(auth.getName());
		System.out.println(user);
		return "prenotations/list";
	}
	
	
	
	
	@GetMapping("/delete/{prenotationId}")
	public String jobofferdelete (@PathVariable("prenotationId") Long prenotationId, Model model) {
		this.prenotationService.delete(prenotationId);
		return "redirect:/prenotations/list";
	}
	
	
	@GetMapping("/{prenotationId}/edit")
	public String edit(@PathVariable("prenotationId") Long prenotationId, Model uiModel) {
		SimpleDateFormat formatter_ora = new SimpleDateFormat("HH:mm");
		SimpleDateFormat formatter_giorno = new SimpleDateFormat("yyyy-MM-dd");
		Prenotation p = this.prenotationService.findById(prenotationId);
		uiModel.addAttribute("prenot", p);
		uiModel.addAttribute("formatter_ora", formatter_ora);
		uiModel.addAttribute("formatter_giorno", formatter_giorno);
		return "prenotations/form";
	}
	
	
	@PostMapping(value = "/save/{prenotationId}")
	public String save(	@PathVariable("prenotationId") Long prenotationId,
						@RequestParam(value = "nome_evento", required=false) String nome_evento, 
						@RequestParam(value = "note", required=false) String note,
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "aula", required=false) String aula_nome,
						@RequestParam(value = "data", required=false) String data,
						@RequestParam(value = "ora_inizio", required=false) String oraInizio,
						@RequestParam(value = "ora_fine", required=false) String oraFine,
						@RequestParam(value = "error", required = false) String error, 
						Model uiModel) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.profileService.findByUsername(auth.getName());
		System.out.println(user);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		String data_orainizio = data + ' ' + oraInizio;
		DateTime dt_inizio = formatter.parseDateTime(data_orainizio);
		String data_orafine = data + ' ' + oraFine;
		DateTime dt_fine = formatter.parseDateTime(data_orafine);
		
		int quota_int = Integer.parseInt(quota);
		Aula aula = this.aulaService.findByNameQuota(aula_nome, quota_int);
		
		Prenotation p = this.prenotationService.findById(prenotationId);
		p.setUser(user);
		p.setNomeEvento(nome_evento);
		p.setNote(note);
		p.setOraInizio(dt_inizio);
		p.setOraFine(dt_fine);
		p.setAula(aula);
		
		this.prenotationService.update(p);
		
		return "redirect:/prenotations/list";
	}
	
	
	
}
