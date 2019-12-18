package univpm.advprog.aule.model.entities;

import java.io.Serializable;

import javax.persistence.Entity;


@Entity
public class Prenotation implements Serializable{
	
	private long id;
	//data, ora inizio, ora fine
	private User user;
	private Aula aula;
	private String nomeEvento;
	private String note;
	
	
	
	

}
