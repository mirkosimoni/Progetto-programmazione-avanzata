package univpm.advprog.aule.controller;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String search(@RequestParam(value = "giorno", required=false) DateTime giorno, Model uiModel) {
		
/*		List<Aula> aule = new ArrayList<>();
		
		aule = aulaService.findLibere(oraInizio);
		
		uiModel.addAttribute("aule", aule);
*/
		System.out.println("Cosa stampa quando immettiamo il giono");
		System.out.println(giorno.toString());
		
		return "aula/list";
	}
	
	

	
	

	
}
