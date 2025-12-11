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

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 

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
    
        public void salvaDOS(String nomeFile) throws FileNotFoundException, IOException {
        
        // apre file
        FileOutputStream fos = new FileOutputStream(nomeFile); 
        
        // aggiunge buffer
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        // stream per i dati primitivi
        DataOutputStream dos = new DataOutputStream(bos);
        
        //descrizione anagrafica
        dos.writeUTF("Elenco Studenti\n");
        
        //per ogni studente memorizzo attributi sul file
        for(Studente s : elencoStudenti) {
            
            dos.writeUTF(s.getCognome());
            dos.writeUTF(s.getNome());
            dos.writeUTF(s.getMatricola());
            dos.writeUTF(s.getEmail());
            List<Prestito> prestitiAttivi = s.getPrestitiAttivi();
            // 1. Scriviamo la dimensione della lista
            dos.writeInt(prestitiAttivi.size());
            // 2. Iteriamo sulla lista e scriviamo i dettagli di ogni Prestito
            for (Prestito p : prestitiAttivi) {    
            // Scriviamo i dati del Libro in prestito
            dos.writeUTF(p.getLibro().getTitolo());
            dos.writeUTF(p.getLibro().getAutore());
            // Scriviamo la Data di Scadenza (Stringa)
            dos.writeUTF(p.getDataScadenza().toString());
    
    }
            
    }
        
        dos.close(); //chiudo stream
        
        
    }
    
        
           // scrive il contenuto della struttura dati in un file CSV (file con valori separati da virgola)
 public void salvaCSV(String nomeFile) throws IOException {
    
    
    // Usiamo try-with-resources per garantire la chiusura automatica del PrintWriter
    try (PrintWriter pw = new PrintWriter(nomeFile)) { 
        pw.append("Elenco Studneti");
        // 1. Scrivi l'intestazione (header) del CSV
        pw.println("Cognome; Nome; Matricola ; Email; PrestitiAttivi ;TitoloLibro ; AutoreLibro ; DataScadenza");

        // 2. Itera su tutti gli studenti
        for (Studente s : elencoStudenti) {
            
            // Estrai i dati dello studente e la lista dei prestiti
            String cognome = s.getCognome();
            String nome = s.getNome();
            String matricola = s.getMatricola();
            String email = s.getEmail();
            List<Prestito> prestitiAttivi = s.getPrestitiAttivi();
            
            // 3. Itera sui prestiti dello studente
            if (prestitiAttivi.isEmpty()) {
                // Caso: Studente senza prestiti attivi. Scrivi una riga comunque (opzionale)
                pw.println(cognome + ";" + 
                           nome + ";" + 
                           matricola + ";" + 
                           email + ";" + 
                           "0" + ";" + // 0 Prestiti in questa riga
                           "" + ";" + 
                           "" + ";" + 
                           "");
            } else {
                // Caso: Studente con N prestiti. Scrivi N righe.
                for (Prestito p : prestitiAttivi) {
                    
                    String titolo = p.getLibro().getTitolo();
                    String autore = p.getLibro().getAutore();
                    String dataScadenza = p.getDataScadenza().toString();
                    
                    // Costruisci la riga del CSV
                    String riga = cognome + ";" + 
                                  nome + ";" + 
                                  matricola + ";" + 
                                  email + ";" + 
                                  "1" + ";" + // C'è 1 prestito in questa riga
                                  titolo + ";" + 
                                  autore + ";" + 
                                  dataScadenza;
                                  
                    pw.println(riga); // Scrivi la riga nel file
                }
            }
        }
    } 
    // Il pw.close() è chiamato automaticamente alla fine del blocco try
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