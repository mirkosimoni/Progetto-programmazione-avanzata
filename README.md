# AuleProgram
Progetto di Programmazione Avanzata
Il programma progettato ha lo scopo di fornire un sistema per la gestione delle prenotazioni delle aule all'interno del polo di ingegneria ddell'Università Politecnica delle Marche.
L'applicazione non gestisce la registrazione degli utenti, l'idea è quella di utilizzare il database degli utenti universitari.
Il programma permette la gestione di aule e prenotazione in base a tre ruoli:
-Student: Ha la possibilità di navigare tra le prenotazioni (lezioni o eventi) e filtrarle. Inoltre può navigare le aule e ricercare quelle libere (non prenotate) in un determinato momento.
-Teacher: Oltre alle funzionalità offerte allo Student, può effettuare prenotazioni e modificarle in seguito.
-Admin: Oltre alle funzionalità precedenti, può creare/modificare Aule e gestire qualsiasi prenotazione effettuata. Viene inoltre fornita la possibilità di visionare gli utenti presenti nel sistema.


# Istruzioni d'uso
1. Una volta clonato il progetto andare su src/main/resources e modificare il file path.properties con i dati del proprio path dove è presente la cartella "img_user" del progetto nel vostro pc.
2. Inserire i propri dati al posto di [...] nella classe univpm.advprog.aule.app.DataServiceConfig:
            ds.setUrl("jdbc:mysql://localhost:.../");
			ds.setUsername("...");
			ds.setPassword("...");
3. Aprire la classe univpm.advprog.aule.test.LoadDataTest e lanciarla con Run as--> Java Application
4. Lanciare il progetto per intero come Run as--->Run on Server utilizzando un server (per esempio TomCat dopo averlo configurato)
5. All'atto dell'eventuale aggiunta di immagini al proprio profilo di utente o azienda fare refresh (F5) del progetto su eclipse o altro Ide per vedere aggiornare l'immagine del proprio profilo

Utenti presenti nel sistema: (matricola, password)
(admin, 12345)
(teacher1, 12345)
(teacher2, 12345)
(teacher3, 12345)
(teacher4, 12345)
(teacher5, 12345)
(teacher6, 12345)
(student1, 12345)
(student2, 12345)
(student3, 12345)

### Autori
Daniele Delli Rocili
Lorenzo Medici
Fabio Morganti
Alberto Pierini
Mirko Simoni
