package univpm.advprog.aule.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import univpm.advprog.aule.model.dao.AulaDao;
import univpm.advprog.aule.model.entities.Aula;
import univpm.advprog.aule.model.entities.Prenotation;
import univpm.advprog.aule.services.AulaService;

@RequestMapping("/aula")
@Controller
public class AulaController {
	
	private AulaService aulaService;
	
	@Autowired
	public void setAulaService(AulaService aulaService) {
		this.aulaService = aulaService;
	}
	
	
	@GetMapping(value = "/list")
	public String list(@RequestParam(value = "message", required=false) String message, Model uiModel) {

		List<Aula> allAule = this.aulaService.findAll();
		
		uiModel.addAttribute("aula", allAule);
		
		return "aula/list";
	}
	
	
	@PostMapping(value = "/search")
	public String search(@RequestParam(value = "giorno", required=false) String giorno, 
						@RequestParam(value = "oraInizio", required=false) String oraInizio,
						@RequestParam(value = "oraFine", required=false) String oraFine,
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "nome", required=false) String nome,
						@RequestParam(value = "numPosti", required=false) String numPosti,
						@RequestParam(value = "prese", required=false) Boolean prese,
						@RequestParam(value = "error", required = false) String error, 
						Model uiModel) {
		
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		
		SimpleDateFormat formatter_view = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
		error = null;
		
		if(quota == "") quota = "-1";
		
		if(nome == "") nome = null;
		if(numPosti == "") numPosti = "-1";	
		
		DateTime dt_inizio = new DateTime();
		DateTime dt_fine = new DateTime();

		
		if(!giorno.equals("") && ((!oraInizio.equals("Scegli") || !oraFine.equals("Scegli")))) {
			
			if(!oraInizio.equals("Scegli")) {
				String data_orainizio = giorno + ' ' + oraInizio;
				dt_inizio = formatter.parseDateTime(data_orainizio);
			}else {
				String data_orainizio = giorno + ' ' + "01:00";
				dt_inizio = formatter.parseDateTime(data_orainizio);
			}
		
			if(!oraFine.equals("Scegli")) {
				String data_orafine = giorno + ' ' + oraFine;
				dt_fine = formatter.parseDateTime(data_orafine);
			}else {
				String data_orafine = giorno + ' ' + "23:00";
				dt_fine = formatter.parseDateTime(data_orafine);
			}
			

			
	}	
	
		int quotaInt = Integer.parseInt(quota);
		int numPostiInt = Integer.parseInt(numPosti);
		
		if(giorno=="") {dt_inizio=null; dt_fine=null;}
		

		List<Aula> auleLibere = this.aulaService.findAuleLibere(dt_inizio, dt_fine, quotaInt, nome, numPostiInt, prese);
		
	
		uiModel.addAttribute("aula", auleLibere);
		
		uiModel.addAttribute("formatter",formatter_view);
		uiModel.addAttribute("errorMessageData",error);
		return "aula/list";
	}
	
	@GetMapping("/delete/{aulaId}")
	public String jobofferdelete (@PathVariable("aulaId") Long aulaId, Model model) {
		this.aulaService.delete(aulaId);
		return "redirect:/aula/list";
	}
	
	
	
	
	
	
	
	
	
}


