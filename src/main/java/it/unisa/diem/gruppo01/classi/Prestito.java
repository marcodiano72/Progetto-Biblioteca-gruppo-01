/**
*@file Elenco.java
*@brief Questo file gestisce i dettagli di un singolo prestito di un libro.
*
*@version 1.0
*/

/**
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */
package it.unisa.diem.gruppo01.classi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Classe Prestito
 * La classe gestisce i dettagli di un singolo prestito di un libro
 * a uno studente. Contiene informazioni sul libro, lo studente e le date temporali
 * necessarie per calcolare lo stato e le eventuali sanzioni.
 *
 */
public class Prestito {
    
   
    public static final int DURATA_PRESTITO = 50; /// Costante per la durata base del prestito (50 giorni come da specifica)
    
    public static final int LIMITE_PRESTITI = 3; /// Limite massimo di prestiti attivi prima che scattino le sanzioni in caso di ritardo (3).

    public static final String NESSUN_RITARDO = "Nessun ritardo riscontrato.";
    
    private Libro libro; ///< Il libro oggetto del prestito.
    private Studente studente; ///< Lo studente che ha richiesto il prestito.
    private LocalDate dataInizio;///< Data di inizio del prestito.
    private LocalDate dataScadenza;///< Data di scadenza prevista per la restituzione.
    private LocalDate dataRestituzione;///< Data effettiva di restituzione del libro (null se il prestito è attivo).
    
    
    /**
     * Costruttore per creare un nuovo oggetto.
     * @param libro Il libro preso in prestito.
     * @param studente Lo studente che prende in prestito il libro.
     * @param dataInizio La data in cui il prestito è iniziato.
     * @param dataScadenza La data in cui il prestito scade.
     * @param dataRestituzione La data in cui è stato restituito.
     */
    public Prestito(Libro libro, Studente studente, LocalDate dataInizio, LocalDate dataScadenza, LocalDate dataRestituzione){
        
        this.libro = libro;
        this.studente = studente;
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.dataRestituzione = dataRestituzione;
    }
    
    /**
     * Restituisce l'oggetto associato a questo prestito.
     * @return Il libro.
     */
    public Libro getLibro(){
        return libro;
    }
    
    /**
     * Restituisce l'oggetto associato a questo prestito.
     * @return Il libro.
     */
    public void setLibro(Libro libro){
        this.libro = libro;
    }
    
    /**
     * Restituisce l'oggetto associato a questo prestito.
     * @return Lo studente.
     */
    public Studente getStudente(){
        return studente;
    }
    
    /**
     * Imposta l'oggetto associato a questo prestito.
     * @param studente Il nuovo studente.
     */
    public void setStudente(Studente studente){
        this.studente = studente;
    }
    
    /**
     * Restituisce la data di inizio del prestito.
     * @return La data di inizio.
     */
    public LocalDate getDataInizio(){
        return dataInizio;
    }
    
    /**
     * Imposta la data di inizio del prestito.
     * @param dataInizio La nuova data di inizio.
     */
    public void setDataInizio(LocalDate dataInizio){
        this.dataInizio = dataInizio;
    }
    
    /**
     * Restituisce la data di scadenza del prestito.
     * @return La data di scadenza.
     */
    public LocalDate getDataScadenza(){
        return dataScadenza;
    }
    
    /**
     * Imposta la data di scadenza del prestito.
     * @param dataScadenza La nuova data di scadenza.
     */
    public void setDataScadenza(LocalDate dataScadenza){
        this.dataScadenza = dataScadenza;
    }
    
    /**
     * Restituisce la data effettiva di restituzione.
     * @return La data di restituzione, o se il libro non è stato ancora restituito.
     */
    public LocalDate getDataRestituzione(){
        return dataRestituzione;
    }
    
    /**
     * Imposta la data effettiva di restituzione.
     * @param dataRestituzione La nuova data di restituzione.
     */
    public void setDatarestituzione(LocalDate dataRestituzione){
        this.dataRestituzione = dataRestituzione;
    }
    
    /**
     * Verifica se il prestito è ancora attivo.
     * Un prestito è attivo se dataRestituzione è null.
     * @return true se il prestito è attivo, false altrimenti.
     */
public boolean isPrestitoAttivo(){
        return this.dataRestituzione == null;
    }
    
/**
     * Calcola i giorni di ritardo.
     * CORREZIONE FONDAMENTALE: Se il libro non è stato restituito, confronta con OGGI.
     */
    public int calcolaGiorniRitardo(){
        // Se dataRestituzione è null (libro ancora in mano allo studente), usiamo OGGI
        LocalDate dataFine = (this.dataRestituzione != null) ? this.dataRestituzione : LocalDate.now();
        
        long giorni = ChronoUnit.DAYS.between(this.getDataScadenza(), dataFine);
        
        // Ritorna i giorni solo se positivi (ritardo), altrimenti 0
        return giorni > 0 ? (int) giorni : 0;
    }
        
    public String gestioneSanzioni() {
        int giorniRitardo = this.calcolaGiorniRitardo();

        if (giorniRitardo <= 0) {
            return NESSUN_RITARDO;
        }
        
        if (giorniRitardo >= 1 && giorniRitardo <= 10) {
            return "Categoria 1 (" + giorniRitardo + " gg ritardo): Blocco lieve.";
        } else if (giorniRitardo > 10 && giorniRitardo <= 20) {
            return "Categoria 2 (" + giorniRitardo + " gg ritardo): Blocco 30 giorni.";
        } else { 
            return "Categoria 3 (" + giorniRitardo + " gg ritardo): Blocco PERMANENTE.";
        }
    }
      
    
    
    /**
     * Restituisce una appresentazione in formato stringa dell'oggetto.
     * Include lo stato del prestito, le date temporali, l'eventuale ritardo e l'esito della sanzione applicabile.
     * @return Una stringa contenente i dettagli completi del prestito.
     */
    @Override
    public String toString(){
        
        
        StringBuffer sb = new StringBuffer();
         sb.append("--- Dettagli Prestito ---\n");
       
        // Dati di Riferimento
        sb.append("Libro: ").append(this.getLibro().toString()).append("\n"); 
        sb.append("STATO: ").append(this.isPrestitoAttivo() ? "ATTIVO" : "CONCLUSO").append("\n");
        sb.append("Studente: ").append(this.getStudente().toString()).append("\n"); 
        
        // Dati Temporali
        sb.append("\nData inizio prestito: " + this.getDataInizio());
        sb.append("\nData scadenza prestito: "+ this.getDataScadenza());
        sb.append("\nData restituzione: "+ this.getDataRestituzione());
        
        
       // Se restituito, mostra il ritardo
        if (!this.isPrestitoAttivo()) {
            int ritardo = this.calcolaGiorniRitardo();
            sb.append("Giorni di Ritardo: ").append(ritardo > 0 ? ritardo + " giorni" : "Nessun ritardo").append("\n");
        }
        
        sb.append("\n\n--- Esito Sanzione ---\n");
        // Utilizza il metodo di supporto per descrivere la sanzione senza applicarla
        sb.append("Prestiti Attivi dello Studente (incluso questo): ").append(this.getStudente().contaPrestitiAttivi()).append(" (Limite: ").append(LIMITE_PRESTITI).append(")\n");
        sb.append("Regola Sanzione Applicabile: ").append(this.gestioneSanzioni()).append("\n");
        
        sb.append("-------------------------\n");
        
        return sb.toString();
    }
    
}
