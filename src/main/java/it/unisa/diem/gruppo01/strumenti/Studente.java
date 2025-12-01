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
        this.sanzione = sanzione;
        this.ritardo = ritardo;
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
    
    public boolean isRitardo(){
        return ritardo;
    }
    
  
    public boolean isAbilitato(){
        if( this.contaPrestitiAttivi() < 3 && !(this.isRitardo())) {
            
            return true;
            
        }else{
            return false;
        }
    }
    
   
    
    public void rimuoviPrestito(Prestito p){
        
        prestitiAttivi.remove(p);
    }
    
    
   @Override
   public String toString(){
       StringBuffer sb = new StringBuffer();
       sb.append("\nNome: "+ this.getNome());
       sb.append("\nCognome: "+this.getCognome());
       sb.append("\nMatricola: "+this.getMatricola());
       
       return sb.toString();
   }


    
    
}

