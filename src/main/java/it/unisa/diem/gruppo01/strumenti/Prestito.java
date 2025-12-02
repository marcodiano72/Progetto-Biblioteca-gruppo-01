/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.strumenti;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Marco Diano'
 */
public class Prestito {
    
    // Costante per la durata base del prestito (50 giorni come da specifica)
    public static final int DURATA_PRESTITO_BASE_GIORNI = 50;
    
    private Libro libro;
    private Studente studente;
    private LocalDate dataInizio;
    private LocalDate dataScadenza;
    private LocalDate dataRestituzione;
    
    public Prestito(Libro libro, Studente studente, LocalDate dataInizio, LocalDate dataScadenza, LocalDate dataRestituzione){
        
        this.libro = libro;
        this.studente = studente;
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.dataRestituzione = dataRestituzione;
    }
    
    public Libro getLibro(){
        return libro;
    }
    
    public void setLibro(Libro libro){
        this.libro = libro;
    }
    
    public Studente getStudente(){
        return studente;
    }
    
    public void setStudente(Studente studente){
        this.studente = studente;
    }
    
    public LocalDate getDataInizio(){
        return dataInizio;
    }
    
    public void setDataInizio(LocalDate dataInizio){
        this.dataInizio = dataInizio;
    }
    
    public LocalDate getDataScadenza(){
        return dataScadenza;
    }
    
    public void setDataScadenza(LocalDate dataScadenza){
        this.dataScadenza = dataScadenza;
    }
    
    public LocalDate getDataRestituzione(){
        return dataRestituzione;
    }
    
    public void setDatarestituzione(LocalDate dataRestituzione){
        this.dataRestituzione = dataRestituzione;
    }
    
    public boolean isPrestitoAttivo(){
        if(this.getDataRestituzione() == null){
            return true;
        }else{
            return false;
        }
    }
    
    public int calcolaGiorniRitardo(){
        return (int) ChronoUnit.DAYS.between(this.getDataScadenza(), this.getDataRestituzione());  //il metodo ChronoUnit restituisce un tipo long
    }
    
    public void applicaSanzione(){
       // Non applicare sanzioni se il prestito è ancora attivo.
        if (this.dataRestituzione == null) {
            return;
        }
        
        int giorniRitardo = this.calcolaGiorniRitardo();
        
        if (giorniRitardo > 0) {
            
            System.out.println("\n*** Tentativo di applicare sanzione con " + giorniRitardo + " giorni di ritardo ***");
            
            if (giorniRitardo >= 1 && giorniRitardo <= 10) {
                // (1-10) gg: negazione prestito di altri libri fino a restituzione (blocco lieve)
                // Assumo 7 giorni di blocco dopo la restituzione come sanzione post-ritorno.
                LocalDate dataFineSanzione = this.dataRestituzione.plusDays(7);
                // this.studente.setDenialEndDate(dataFineSanzione); 
                System.out.println("  -> SANZIONE: Blocco prestito per 7 giorni, fino al: " + dataFineSanzione);
                
            } else if (giorniRitardo > 10 && giorniRitardo <= 20) {
                // (10-20) gg: negazione prestito per i successivi 30gg dalla data di consegna
                LocalDate dataFineSanzione = this.dataRestituzione.plusDays(30);
                // this.studente.setDenialEndDate(dataFineSanzione); 
                System.out.println("  -> SANZIONE: Blocco prestito per 30 giorni, fino al: " + dataFineSanzione);
                
            } else if (giorniRitardo > 20) {
                // (20-oltre) gg: negazione prestito permanente
                // this.studente.setPermanentDenial(true); 
                System.out.println("  -> SANZIONE: Blocco prestito PERMANENTE.");
            }
        }
        // Se giorniRitardo è <= 0, nessuna sanzione viene applicata.
    }
 
    
    
    public String gestioneSanzioni()
    {
        if (this.dataRestituzione == null) {
            return "NON APPLICABILE (Prestito Attivo)";
        }

        int giorniRitardo = this.calcolaGiorniRitardo();

        if (giorniRitardo <= 0) {
            return "Nessun ritardo riscontrato.";
        } else if (giorniRitardo >= 1 && giorniRitardo <= 10) {
            return "Categoria 1 (" + giorniRitardo + " gg di ritardo). Sanzione lieve: negazione prestito di altri libri fino a restituzione.";
        } else if (giorniRitardo > 10 && giorniRitardo <= 20) {
            return "Categoria 2 (" + giorniRitardo + " gg di ritardo). Sanzione: blocco 30 giorni post-restituzione.";
        } else { // giorniRitardo > 20
            return "Categoria 3 (" + giorniRitardo + " gg di ritardo). Sanzione: blocco PERMANENTE.";
        }
        
    }
    
    
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
        
        sb.append("\n--- Esito Sanzione ---\n");
        // Utilizza il metodo di supporto per descrivere la sanzione senza applicarla
        sb.append("Regola Sanzione Applicabile: ").append(this.gestioneSanzioni()).append("\n");
        
        sb.append("-------------------------\n");
        
        return sb.toString();
    }
    
}
