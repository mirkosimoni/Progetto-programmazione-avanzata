package univpm.advprog.aule.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;

import univpm.advprog.aule.model.dao.*;
import univpm.advprog.aule.model.entities.*;
import univpm.advprog.aule.services.AulaService;
import univpm.advprog.aule.services.MyProfileService;
import univpm.advprog.aule.services.PrenotationService;
import univpm.advprog.aule.services.PrenotationServiceDefault;
import univpm.advprog.aule.utils.PrenotationsOverlapFinder;

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
	public String list(@RequestParam(value = "message", required=false) String message, 
					   @RequestParam(value="errorMessageData", required=false) String errorMessageData,
					   Model uiModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.profileService.findByUsername(auth.getName());
		uiModel.addAttribute("user", user);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		List<Prenotation> allPrenotations = this.prenotationService.findAllFromToday();
		System.out.println(allPrenotations.size());
		uiModel.addAttribute("errorMessageData",errorMessageData);
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
		
		System.out.println(data);
		System.out.println(oraInizio);
		System.out.println(oraFine);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.profileService.findByUsername(auth.getName());
		uiModel.addAttribute("user", user);
		
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
		
		uiModel.addAttribute("formatter",formatter_view);
		uiModel.addAttribute("errorMessageData",error);
		
		return "prenotations/list";
	}
	
	
	@PostMapping(value = "/create")
	public String create(@RequestParam(value = "nome_evento", required=false) String nome_evento,
						@RequestParam(value = "note", required=false) String note,
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "aula", required=false) String aula,
						@RequestParam(value = "data", required=false) String data,
						@RequestParam(value = "ora_inizio", required=false) String oraInizio,
						@RequestParam(value = "ora_fine", required=false) String oraFine,
						@RequestParam(value = "errorMessageData", required=false) String errorMessageData,
						Model uiModel) {
		
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			uiModel.addAttribute("variabile_view", auth.getName());
			User user = this.profileService.findByUsername(auth.getName());
			
			
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
			String data_orainizio = data + ' ' + oraInizio;
			DateTime dt_inizio = formatter.parseDateTime(data_orainizio);
			String data_orafine = data + ' ' + oraFine;
			DateTime dt_fine = formatter.parseDateTime(data_orafine);
			
			Aula aulanuova = this.aulaService.findByNameQuota(aula, Integer.parseInt(quota));
			
			if(dt_inizio.isBeforeNow()) {
				errorMessageData = "Creazione prenotazione non riuscita scegli una data successiva ad oggi";
				uiModel.addAttribute("errorMessageData",errorMessageData);
				return "redirect:/prenotations/list";
			}
			
			Prenotation p = this.prenotationService.create(dt_inizio, dt_fine, user, aulanuova, nome_evento, note);
			if(p == null) {
				errorMessageData = "Creazione prenotazione non riuscita";
				uiModel.addAttribute("errorMessageData",errorMessageData);
			}
		} catch (Exception e) {
			errorMessageData = "Creazione prenotazione non riuscita";
			uiModel.addAttribute("errorMessageData",errorMessageData);
		}
		return "redirect:/prenotations/list";
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
						@RequestParam(value = "errorMessageData", required = false) String errorMessageData, 
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
		
		if(dt_inizio.isBeforeNow()) {
			errorMessageData = "Creazione prenotazione non riuscita scegli una data successiva ad oggi";
			uiModel.addAttribute("errorMessageData",errorMessageData);
			return "redirect:/prenotations/list";
		}
		
		Prenotation prenotazione_controllo = this.prenotationService.update(p);
		if(prenotazione_controllo == null) {
			errorMessageData = "Modifica non riuscita";
			uiModel.addAttribute("errorMessageData",errorMessageData);
		}
		return "redirect:/prenotations/list";
	}
	
	
	
	
	public class AjaxObject {
		  String id;
		  String nome_evento;
		  String note;
		  String quota;
		  String nome_aula;
		  String giorno;
		  String oraInizio;
		  String oraFine;
		}
	
	
	@PostMapping(value= "/ajaxtest", headers = "Accept=*/*",produces = "application/text", consumes="application/json")
    public @ResponseBody Integer validate(@RequestBody String oggetto) {
		Gson gson = new Gson();  
		AjaxObject obj = gson.fromJson(oggetto, AjaxObject.class); 
		System.out.println(obj.nome_evento);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		String data_orainizio = obj.giorno + ' ' + obj.oraInizio;
		DateTime dt_inizio = formatter.parseDateTime(data_orainizio);
		String data_orafine = obj.giorno + ' ' + obj.oraFine;
		DateTime dt_fine = formatter.parseDateTime(data_orafine);
		
		Aula aula = this.aulaService.findByNameQuota(obj.nome_aula, Integer.parseInt(obj.quota));
		
		if(aula == null) {
			return 1;
		}
		
		if(dt_fine.isBefore(dt_inizio.toInstant()) || dt_fine.equals(dt_inizio)) {
			return 2;
		}
		
		if(dt_inizio.isBeforeNow()) {
			return 6;
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = this.profileService.findByUsername(auth.getName());
		
		Prenotation p = new Prenotation();
		p.setAula(aula);
		p.setNomeEvento(obj.nome_evento);
		p.setNote(obj.note);
		p.setOraInizio(dt_inizio);
		p.setOraFine(dt_fine);
		p.setUser(user);
		p.setId(Long.parseLong(obj.id));
		
		Prenotation p_vecchia = this.prenotationService.findById(Long.parseLong(obj.id));

		PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();

		List<Prenotation> prenotazioniData = this.prenotationService.findPrenotationsData(null, null, String.valueOf(p.getAula().getQuota()), p.getAula().getNome(), p.getOraInizio());
		boolean overlapped = false;
		for(Prenotation pr : prenotazioniData) {
			if(overlapFinder.areOverlapped(pr, p) && pr.getId() != p.getId())
				overlapped = true;
		}
		 if(overlapped) {
			 return 4;
		} else {
			return 3;
		}	
    }
	
	
	

	
	//controllo in tempo reale per creazione prenotazione
	@PostMapping(value= "/ajaxtestcreate", headers = "Accept=*/*",produces = "application/text", consumes="application/json")
    public @ResponseBody Integer validatecreate(@RequestBody String oggetto) {
		Gson gson = new Gson();  //trasforma stringa json in un json in background
		AjaxObject obj = gson.fromJson(oggetto, AjaxObject.class); 
		System.out.println(obj.nome_evento);
		System.out.println(obj.note);
		System.out.println(obj.quota);
		System.out.println(obj.nome_aula);
		System.out.println(obj.giorno);
		System.out.println(obj.oraInizio);
		System.out.println(obj.oraFine);
		
		if (!"".equals(obj.nome_evento) && !"".equals(obj.note) && !"".equals(obj.quota) && !"".equals(obj.nome_aula) && !"".equals(obj.giorno) && !"Scegli".equals(obj.oraInizio) && !"Scegli".equals(obj.oraFine)) {
			
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
			String data_orainizio = obj.giorno + ' ' + obj.oraInizio;
			DateTime dt_inizio = formatter.parseDateTime(data_orainizio);
			String data_orafine = obj.giorno + ' ' + obj.oraFine;
			DateTime dt_fine = formatter.parseDateTime(data_orafine);
			
			Aula aula = this.aulaService.findByNameQuota(obj.nome_aula, Integer.parseInt(obj.quota));
			
			if(aula == null) {
				return 1;
			}
			
			if(dt_fine.isBefore(dt_inizio.toInstant()) || dt_fine.equals(dt_inizio)) {
				return 2;
			}
			
			
			if(dt_inizio.isBeforeNow()) {
				return 6;
			}

			Prenotation p = new Prenotation();
			p.setAula(aula);
			p.setNomeEvento(obj.nome_evento);
			p.setNote(obj.note);
			p.setOraInizio(dt_inizio);
			p.setOraFine(dt_fine);
			
			PrenotationsOverlapFinder overlapFinder = new PrenotationsOverlapFinder();
	
			List<Prenotation> prenotazioniData = this.prenotationService.findPrenotationsData(null, null, obj.quota, obj.nome_aula, dt_inizio);
			boolean overlapped = false;
			for(Prenotation pr : prenotazioniData) {
				if(overlapFinder.areOverlapped(pr, p) )
					overlapped = true;
			}
			 if(overlapped) {
				 return 4;
			} else {
				return 3;
			}
			 
		} 
		return 5;
    }
	
	
	
}
