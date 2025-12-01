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
        //da terminare
        if(studente.isRitardo()){
            
        }
    }
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        
        sb.append("\nData inizio prestito: " + this.getDataInizio());
        sb.append("\nData scadenza prestito: "+ this.getDataScadenza());
        sb.append("\nData restituzione: "+ this.getDataRestituzione());
        
        
        
        //da completare
        
        
        return sb.toString();
    }
    
}
