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
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.HashMap;
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

    private String NOME_FILE_CSV = "Lista_studenti.csv";
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
    public Studente cercaStudenteperMatricola(String matricola) {
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
 public void salvaCSV() {
    
    final String SEPARATORE = ";";
    final String NOME_FILE_CSV = "Lista_studenti.csv"; 

    try (PrintWriter pw = new PrintWriter(new FileWriter(NOME_FILE_CSV))) {
        
        pw.println("Elenco Studenti"); 
        
        // Intestazione con 14 campi
        pw.println("Cognome; Nome; Matricola; Email; Sanzione; Ritardo; PrestitoAttivo; ISBN; Titolo; Autore; AnnoPubblicazione; DataInizio; DataScadenza; DataRestituzione");

        for (Studente s : elencoStudenti) {
            
            // Dati base dello Studente
            String cognome = s.getCognome();
            String nome = s.getNome();
            String matricola = s.getMatricola();
            String email = s.getEmail();
            String sanzione = s.getSanzione();
            String ritardo = String.valueOf(s.isRitardo()); // boolean -> "true"/"false"
            
            List<Prestito> prestitiAttivi = s.getPrestitiAttivi(); 

            // Costruiamo la parte iniziale della riga
            String rigaBase = cognome + SEPARATORE + nome + SEPARATORE + matricola + SEPARATORE + 
                              email + SEPARATORE + sanzione + SEPARATORE + ritardo + SEPARATORE;

            if (prestitiAttivi.isEmpty()) {
                // Studente senza prestiti (solo riga base + 8 campi vuoti)
                pw.println(rigaBase + 
                           "0" + SEPARATORE + // PrestitoAttivo = 0
                           SEPARATORE + SEPARATORE + SEPARATORE + // ISBN, Titolo, Autore
                           SEPARATORE + SEPARATORE + SEPARATORE + SEPARATORE); // DataI, DataS, DataR, AnnoPb
            } else {
                // Studente con N prestiti: Scrivi N righe
                for (Prestito p : prestitiAttivi) {
                    
                    // Dati del Libro
                    String isbn = p.getLibro().getIsbn(); 
                    String titolo = p.getLibro().getTitolo();
                    String autore = p.getLibro().getAutore();
                    String annoPb = p.getLibro().getAnnoPb().toString(); // Data Pubblicazione
                    
                    // Dati del Prestito
                    String dataInizio = p.getDataInizio().toString();
                    String dataScadenza = p.getDataScadenza().toString();
                    String dataRestituzione = (p.getDataRestituzione() != null) ? p.getDataRestituzione().toString() : "";
                    
                    String riga = rigaBase + 
                                  prestitiAttivi.size() + SEPARATORE + 
                                  isbn + SEPARATORE + titolo + SEPARATORE + autore + SEPARATORE + 
                                  annoPb+ SEPARATORE + dataInizio + SEPARATORE + dataScadenza + SEPARATORE + dataRestituzione ;
                                  
                    pw.println(riga);
                }
            }
        }
        
    } catch (IOException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Errore durante il salvataggio CSV.", ex);
    }
}
 
/**
 * Carica gli studenti dal file CSV nel TreeSet, gestendo righe multiple (prestiti).
 */
public void caricaDati() {
    
    HashMap<String, Studente> studentiMappa = new HashMap<>(); 
    final String SEPARATORE = ";"; 
    final String NOME_FILE_CSV = "Lista_studenti.csv"; 

    try (BufferedReader br = new BufferedReader(new FileReader(NOME_FILE_CSV))) {
        
        br.readLine(); // Salta "Elenco Studenti"
        br.readLine(); // Salta l'intestazione dei campi
        
        String riga;
        
        while ((riga = br.readLine()) != null) {
            
            try { 
                String[] campi = riga.split(SEPARATORE, -1); // Usa -1 per includere campi vuoti
                
                // Richiede almeno 6 campi per i dati base dello Studente
                if (campi.length >= 6) { 
                    
                    String matricola = campi[2].trim();
                    Studente studenteCorrente = studentiMappa.get(matricola);
                    
                    if (studenteCorrente == null) {
                        // Creazione Studente (solo se non esiste ancora nella mappa)
                        String cognome = campi[0].trim();
                        String nome = campi[1].trim();
                        String email = campi[3].trim(); 
                        String sanzione = campi[4].trim(); 
                        boolean ritardo = Boolean.parseBoolean(campi[5].trim()); 
                        
                        // Costruttore Studente: Studente(nome, cognome, matricola, email, sanzione, ritardo)
                        studenteCorrente = new Studente(nome, cognome, matricola, email, sanzione, ritardo); 
                        studentiMappa.put(matricola, studenteCorrente);
                    }
                    
                    // 2. GESTIONE PRESTITI (Richiede 14 campi in totale)
                    // Campo [6] è PrestitoAttivo ("1")
                    if (campi.length >= 14 && campi[6].trim().equals("1")) {
                        
                        // Dati del libro e prestito (campi 7-13)
                        String isbn = campi[7].trim();
                        String titolo = campi[8].trim();
                        String autore = campi[9].trim();
                        String annoPbStr = campi[10].trim(); // Data Pubblicazione
                        String dataInizioStr = campi[11].trim();
                        String dataScadenzaStr = campi[12].trim();
                        String dataRestituzioneStr = campi[13].trim();
                       
                        
                        // Conversione Date
                        LocalDate dataInizio = LocalDate.parse(dataInizioStr); 
                        LocalDate dataScadenza = LocalDate.parse(dataScadenzaStr); 
                        LocalDate annoPb = LocalDate.parse(annoPbStr);
                        
                        LocalDate dataRestituzione = null;
                        if (!dataRestituzioneStr.isEmpty()) {
                            dataRestituzione = LocalDate.parse(dataRestituzioneStr);
                        }
                        
                        // Creazione Oggetti Libro e Prestito
                        // Assumo numCopie=0 per il libro in prestito (il conteggio è gestito nel Catalogo generale)
                        Libro libro = new Libro(isbn, titolo, autore, annoPb, 0); 
                        
                        // Prestito costruttore: Prestito(libro, studente, dataInizio, dataScadenza, dataRestituzione)
                        Prestito prestito = new Prestito(libro, studenteCorrente, dataInizio, dataScadenza, dataRestituzione); 
                        
                        studenteCorrente.aggiungiPrestito(prestito); 
                    }
                    
                } else {
                    System.err.println("AVVISO: Riga CSV ignorata (campi insufficienti o malformati): " + riga);
                }
            } catch (DateTimeParseException e) {
                 System.err.println("ERRORE: Formato data non valido nella riga: " + riga);
            } catch (Exception parsingEx) {
                System.err.println("ERRORE CRITICO DI PARSING NELLA RIGA: " + riga);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, parsingEx);
            }
        }
        
    } catch (IOException e) {
        System.err.println("File dati non trovato o errore di lettura I/O. La collezione sarà vuota.");
    }
    
    // Aggiungi tutti gli studenti unici dalla Mappa al TreeSet finale
    this.elencoStudenti.clear(); 
    this.elencoStudenti.addAll(studentiMappa.values()); 
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