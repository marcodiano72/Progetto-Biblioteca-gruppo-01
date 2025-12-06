package it.unisa.diem.gruppo01.strumenti;

import java.util.TreeSet;
import java.util.Comparator;
 

/**
 * Gestisce l'inventario dei libri, ordinandoli per titolo.
 */
public class Elenco {

   
    private TreeSet<Studente> elencoStudenti; // TreeSet per mantenere i libri ordinati alfabeticamente per titolo.

   

    public Elenco() 
    {
        this.elencoStudenti = new TreeSet<>(new StudentComparator());
    }
    
    public TreeSet<Studente> getElencoStudenti()
    {
        return elencoStudenti;
    }
    
    // Metodo  per trovare uno studente per matricola 
    private Studente cercaStudenteperMatricola(String matricola) {
        for (Studente studente : elencoStudenti) {
            if (studente.getMatricola().equals(matricola)) {
                return studente;
            }
        }
        return null;
    }
    
     // Comparator per definire l'ordinamento per cognome dello Studente (alfabetico e insensibile al caso)
    public class StudentComparator implements Comparator<Studente> {
        @Override
        public int compare(Studente s1, Studente s2) {
            // Confronta i cognome
            int risultato = s1.getCognome().compareToIgnoreCase(s2.getCognome());

            // Se i cognomi sono identici, ordina per matricola per garantire l'unicità
            if (risultato == 0) {
                risultato = s1.getMatricola().compareTo(s2.getMatricola());
            }
            return risultato;
        }
    }

    /*
      Aggiunge un nuovo libro al catalogo. Se il libro (stesso ISBN) esiste già,
      aggiorna il numero di copie e restituisce false.
     */
    public boolean aggiungiStudente(Studente nuovoStudente) {
        
        Studente studenteEsistente = cercaStudenteperMatricola(nuovoStudente.getMatricola());
        
        if (studenteEsistente != null) {
            
            System.out.println("Studente con Matricola " + nuovoStudente.getMatricola() + " già presente");
            return false; 
        } else {
            //  aggiungiamo il nuovo libro, se non esiste già.
            return elencoStudenti.add(nuovoStudente);
        }
    }
    
    


    // Funzione per modificare i dati dello studente
    public boolean modificaStudente(String matricola, String nuovoNome, String nuovoCognome, String nuovaEmail) {
        // Cerca lo studente con la matricola fornita
        for (Studente studente : elencoStudenti) {
            if (studente.getMatricola().equals(matricola)) {
                // Se trovato, aggiorna i dati
                studente.setNome(nuovoNome);
                studente.setCognome(nuovoCognome);
                studente.setEmail(nuovaEmail);
                // Restituisce true se la modifica è stata effettuata
                return true;
            }
        }
        // Restituisce false se lo studente non è stato trovato
        return false;
    }

   // Funzione per cancellare uno studente
    public boolean eliminaStudente(String matricola) {
        // Cerca lo studente con la matricola fornita
        for (Studente studente : elencoStudenti) {
            if (studente.getMatricola().equals(matricola)) {
                // Se trovato, rimuove lo studente dalla lista
                elencoStudenti.remove(studente);
                // Restituisce true se la cancellazione è riuscita
                return true;
            }
        }
        // Restituisce false se lo studente non è stato trovato
        return false;
    }
    

  
    @Override
    public String toString() {
        if (elencoStudenti.isEmpty()) {
            return "L'elenco è vuoto.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== ELENCO STUDENTI (Ordinato per Cognome) =====\n");
        
        for (Studente studente : elencoStudenti) {
            sb.append(studente.toString()).append("\n");
        }
        sb.append("================================================");
        return sb.toString();
    }
    
   
    
}