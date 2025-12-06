/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.strumenti;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco Diano'
 */
public class Studente {
    
    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private String sanzione;
    private boolean ritardo;
    private List<Prestito> prestitiAttivi;
    
    public Studente(String nome, String cognome, String matricola, String email, String sanzione, boolean ritardo){
        
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.sanzione = "Nessuna"; //valore iniziale
        this.ritardo = false;
        prestitiAttivi = new ArrayList<>();
        
        
        
        
        
    }
    
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getCognome(){
        return cognome;
    }
    
    public void setCognome(String cognome){
        this.cognome = cognome;
    }
    
    public String getMatricola(){
        return matricola;
    }
    
    public void setMatricola(String matricola){
        this.matricola = matricola;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
      public List<Prestito> getPrestitiAttivi(){
        return prestitiAttivi;
    }
      
      public void aggiungiPrestito(Prestito p){
        prestitiAttivi.add(p);
    }
    
     
    public int contaPrestitiAttivi(){
      
        return prestitiAttivi.size();
    }
            
    public String getSanzione(){
      return sanzione;
    }
    
    // Metodo per impostare lo stato di sanzione
    public void setSanzione(String sanzione){
        this.sanzione = sanzione;
    }
    
    
    public boolean isRitardo(){
        return ritardo;
    }
    
    // Metodo per impostare lo stato di ritardo (se necessario esternamente)
    public void setRitardo(boolean ritardo){
        this.ritardo = ritardo;
    }
    
  /*
      Controlla se lo studente è abilitato:
      1. Non deve superare il limite di prestiti (3).
      2. Non deve essere in uno stato di sanzione per ritardo (ritardo == true).
   */
    public boolean isAbilitato(){
        if( this.contaPrestitiAttivi() < Prestito.LIMITE_PRESTITI && !(this.isRitardo())) {
            
            return true;
            
        }else{
            return false;
        }
    }
    
   
    
    public void rimuoviPrestito(Prestito p){
        
        prestitiAttivi.remove(p);
    }
    
        //gestione unicità basata sulla matricola
    @Override
    public int hashCode()
    {
        int code=matricola == null ? 0 : matricola.hashCode();
        return code;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        
        if(obj == null)
        {
            return false;
        }
        
        if(this.getClass() != obj.getClass())
        {
            return false;
        } 
        Studente other=(Studente)obj;
        if(this.matricola.equals((other.matricola))) return true;
        return false;
        
    }
    
   @Override
   public String toString(){
       StringBuffer sb = new StringBuffer();
       sb.append("\nNome: "+ this.getNome());
       sb.append("\nCognome: "+this.getCognome());
       sb.append("\nMatricola: "+this.getMatricola());
       sb.append("\nEmail: "+this.getEmail());
       sb.append("\nPrestiti Attivi: "+ this.contaPrestitiAttivi());
       sb.append("\nStato Sanzione: "+ this.getSanzione());
       sb.append("\nAbilitato al Prestito: "+ this.isAbilitato());
       
       return sb.toString();
   }


    
    
}

