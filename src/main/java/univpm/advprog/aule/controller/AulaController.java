package univpm.advprog.aule.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import univpm.advprog.aule.model.entities.User;
import univpm.advprog.aule.services.AulaService;
import univpm.advprog.aule.services.MyProfileService;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;


@RequestMapping("/aula")
@Controller
public class AulaController {
	
	private AulaService aulaService;
	
	@Autowired
	public void setAulaService(AulaService aulaService) {
		this.aulaService = aulaService;
	}
	
	
	@GetMapping(value = "/list")
	public String list(@RequestParam(value = "message", required=false) String message,
			@RequestParam(value = "errorMessageData", required = false) String errorMessageData,
			Model uiModel) {

		List<Aula> allAule = this.aulaService.findAll();
		List<String> quote = this.aulaService.findQuota();
		List<String> nomi = this.aulaService.findName();
		
		System.out.println(quote.size());
		
		uiModel.addAttribute("aula", allAule);
		uiModel.addAttribute("quote", quote);
		uiModel.addAttribute("nomi", nomi);
		
		uiModel.addAttribute("errorMessageData", errorMessageData);
		
		return "aula/list";
	}
	
	
	@PostMapping(value = "/search")
	public String search(@RequestParam(value = "giorno", required=false) String giorno, 
						@RequestParam(value = "oraInizio", required=false) String oraInizio,
						@RequestParam(value = "oraFine", required=false) String oraFine,
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "nomi", required=false) String nome,
						@RequestParam(value = "numPosti", required=false) String numPosti,
						@RequestParam(value = "prese", required=false) Boolean prese, 
						Model uiModel) {
		
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		
		SimpleDateFormat formatter_view = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		//error = null;
		
		if(quota.equals("Scegli")) quota = "-1";
		
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
		List<String> q = this.aulaService.findQuota();
		List<String> n = this.aulaService.findName();
	
		uiModel.addAttribute("aula", auleLibere);
		uiModel.addAttribute("formatter",formatter_view);
		uiModel.addAttribute("quote", q);
		uiModel.addAttribute("nomi", n);
		
		
		return "aula/list";
	}
	
	
	
	
	
	
	
	@GetMapping("/delete/{aulaId}")
	public String delete (@PathVariable("aulaId") Long aulaId, Model model) {
		this.aulaService.delete(aulaId);
		return "redirect:/aula/list";
	}
	
	@GetMapping("/{aulaId}/edit")
	public String edit(@PathVariable("aulaId") Long aulaId, Model uiModel) {
		
		Aula a = this.aulaService.findById(aulaId);
		List<String> quote = this.aulaService.findQuota();
		uiModel.addAttribute("aula", a);
		uiModel.addAttribute("quote", quote);
		
		return "aula/form";
	}
	
	@PostMapping(value = "/save/{aulaId}")
	public String save(	@PathVariable("aulaId") Long aulaId,
						@RequestParam(value = "nome", required=false) String nome, 
						@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "numero_posti", required=false) String numero_posti,
						@RequestParam(value = "prese", required=false) boolean prese,
						@RequestParam(value = "errorMessageData", required = false) String errorMessageData, 
						Model uiModel) {
		
		int quota_int = 0;
		int posti_int = 0;
		
		try {
			quota_int = Integer.parseInt(quota);
			posti_int = Integer.parseInt(numero_posti);
		} catch(Exception e) {
			uiModel.addAttribute("errorMessageData","Modifica non riuscita: Numero posti");
			return "redirect:/aula/list";
		}
		
		if(nome.equals("") || nome == null) {
			uiModel.addAttribute("errorMessageData","Modifica non riuscita: errore inserimento nome");
			return "redirect:/aula/list";
		}
		
		Aula a = this.aulaService.findById(aulaId);
		a.setNome(nome);
		a.setNumeroPosti(posti_int);
		a.setQuota(quota_int);
		a.setPresentiPrese(prese);
		
		this.aulaService.update(a);
		
		return "redirect:/aula/list";
	}
	
	
		

	@PostMapping(value = "/create")
	public String create(@RequestParam(value = "quota", required=false) String quota,
						@RequestParam(value = "nome", required=false) String nome,
						@RequestParam(value = "numPosti", required=false) String numPosti,
						@RequestParam(value = "prese", required=false) Boolean prese,
						@RequestParam(value = "errorMessageData", required=false) String errorMessageData,
						Model uiModel) {
		
			if(prese==null) prese=false;
		
			try {
			Aula a = this.aulaService.create(nome, Integer.parseInt(quota), Integer.parseInt(numPosti), prese);
			
			if(a==null) {
				errorMessageData = "Creazione aula non riuscita";
				uiModel.addAttribute("errorMessageData",errorMessageData);
				return "redirect:/aula/list";
			}

			} catch (Exception e) {
				errorMessageData = "Creazione aula non riuscita";
				uiModel.addAttribute("errorMessageData",errorMessageData);
			}

			
		
		return "redirect:/aula/list";
		
	}
	
	
}


