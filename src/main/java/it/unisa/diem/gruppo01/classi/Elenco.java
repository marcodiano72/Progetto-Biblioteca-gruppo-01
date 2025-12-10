/**
*@file Elenco.java
*@brief Questo file gestisce l'elenco di tutti gli studenti registrati nel sistema.
*
*@version 1.0
*/

/*
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */
package it.unisa.diem.gruppo01.classi;

import java.util.TreeSet;
import java.util.Comparator;
 

/**
 * Classe Elenco
 * La classe gestisce l'elenco di tutti gli studenti registrati nel sistema.
 * Utilizza una collezione ordinata per mantenere gli studenti
 * organizzati in base al cognome in ordine alfabetico. 
 */
public class Elenco {

   
    private TreeSet<Studente> elencoStudenti; ///< Insieme ordinato (TreeSet) degli studenti.

   
    
    /**
     * Costruttore della classe.
     * Inizializza l'elenco degli studenti utilizzando un comparatore personalizzato.
     */
    public Elenco() 
    {
        this.elencoStudenti = new TreeSet<>(new StudentComparator());
    }
    
    /**
     * Restituisce l'intera collezione degli studenti.
     * @return Un TreeSet contenente gli oggetti Studente.
     */
    public TreeSet<Studente> getElencoStudenti()
    {
        return elencoStudenti;
    }
    
    /**
     * Cerca uno studente all'interno dell'elenco tramite la matricola.
     * Il metodo scorre la collezione.
     * @param matricola La matricola univoca dello studente da cercare.
     * @return L'oggetto Studente se trovato, altrimenti null.
     */
    // Metodo  per trovare uno studente per matricola 
    private Studente cercaStudenteperMatricola(String matricola) {
        for (Studente studente : elencoStudenti) {
            if (studente.getMatricola().equals(matricola)) {
                return studente;
            }
        }
        return null;
    }
    
    /**
     * Classe comparatore interna.
     * Stabilisce la logica di ordinamento degli studenti all'interno del TreeSet.
     * L'ordinamento naturale è per cognome (case-insensitive),
     * quello secondario (in caso di anomalia) è per matricola.
     */
    
     // Comparator per definire l'ordinamento per cognome dello Studente (alfabetico e insensibile al caso)
    public class StudentComparator implements Comparator<Studente> {
        
        /**
         * Effettua il confronto tra due studenti.
         * @param s1 Il primo studente.
         * @param s2 Is secondo studente.
         * @return Un intero negativo, zero o positivo se s1 è rispettivamente minore, uguale o maggiore di s2.
         */
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

    /**
     * Aggiunge un nuovo studente all'elenco.
     * Verifica se esiste un altro studente avente la stessa matricola.
     * @param nuovoStudente L'oggetto da aggiungere.
     * @return true se l'inserimento è riuscito, false altrimenti.
     */
    public boolean aggiungiStudente(Studente nuovoStudente) {
        
        Studente studenteEsistente = cercaStudenteperMatricola(nuovoStudente.getMatricola());
        
        if (studenteEsistente != null) {
            
            System.out.println("Studente con Matricola " + nuovoStudente.getMatricola() + " già presente");
            return false; 
        } else {
            //  aggiungiamo il nuovo Studente, se non esiste già.
            return elencoStudenti.add(nuovoStudente);
        }
    }
    
    
/**
 * Modifica i dati anagrafici di uno studente già presente nell'elenco.
 * @param matricola La matricola dello studente.
 * @param nuovoNome Il nuovo nome da assegnare.
 * @param nuovoCognome Il nuovo cognome da assegnare.
 * @param nuovaEmail La nuova email da assegnare.
 * @return true se la modifica è andata a buon fine, false se lo studente non è stato trovato.
 */

    // Funzione per modificare i dati dello studente
    public boolean modificaStudente(String matricola, String nuovoNome, String nuovoCognome, String nuovaEmail) {
        // Cerca lo studente con la matricola fornita
        Studente studente = cercaStudenteperMatricola(matricola);
        
            if (studente != null) {
                //Rimuoviamo lo studente prima di modificarlo.
                elencoStudenti.remove(studente);
             
                 //Modifichiamo i dati
                studente.setNome(nuovoNome);
                studente.setCognome(nuovoCognome);
                studente.setEmail(nuovaEmail);
                
               //Reinseriamo lo studente (il TreeSet ricalcola la posizione corretta).
               elencoStudenti.add(studente);
                // Restituisce true se la modifica è stata effettuata
                return true;
            }
        
        // Restituisce false se lo studente non è stato trovato
        return false;
    }
    
    /**
     * Rimuove uno studente dall'elenco.
     * @param matricola La matricola dello studente da eliminare.
     * @return true se la rimozione è andata a buon fine, false se lo studente non è stato trovato.
     */ 
    

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
    
    /**
     * Restituisce una rappresentazione in formato stringa dell'intero elenco.
     * @return Una stringa con l'elenco degli studenti o un messaggio, se l'elenco è vuoto.
     * 
     */
  
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