/**
*@file Studente.java
*@brief Questo file Studente rappresenta un'entità studente all'interno di un sistema di gestione  di una biblioteca.
*
*@version 1.0
*/




/**
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */
package it.unisa.diem.gruppo01.classi;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Studente
 * La classe Studente rappresenta un'entità studente
 * all'interno di un sistema di gestione  di una biblioteca.
 * Contiene informazioni anagrafiche, di contatto e relative allo stato dei prestiti.
 *
 */
public class Studente {
    
    private String nome; ///< Nome dello studente.
    private String cognome; ///< Cognome dello studente.
    private String matricola; ///< Numero di matricola, utilizzato per l'identificazione unica.
    private String email; ///< Indirizzo email dello studente.
    private String sanzione; ///< Descrizione della sanzione eventualmente applicata allo studente.
    private boolean ritardo; ///< Indica se lo studente è in ritardo con la restituzione di un prestito.
    private List<Prestito> prestitiAttivi; ///< Lista dei prestiti attualmente attivi per lo studente.
    private Prestito prestito;
    
    /**
     * Costruttore della classe
     * Inizializza i dati dello studente e la lista dei prestiti attivi.
     *
     * @param nome Il nome dello studente.
     * @param cognome Il cognome dello studente.
     * @param matricola Il numero di matricola dello studente.
     * @param email L'indirizzo email dello studente.
     * @param sanzione (Non usato, il valore viene sovrascritto a "Nessuna") La descrizione iniziale della sanzione.
     * @param ritardo (Non usato, il valore viene sovrascritto a false) Lo stato iniziale di ritardo.
     */
    public Studente(String cognome, String nome, String matricola, String email, String sanzione, boolean ritardo){
        
        this.cognome = cognome;
        this.nome = nome;
        this.matricola = matricola;
        this.email = email;
        this.sanzione = "Nessuna";
        this.ritardo = false;
        prestitiAttivi = new ArrayList<>();
     
    }
    
    
    /**
     * Restituisce il nome dello studente.
     * @return Il nome dello studente.
     */
    public String getNome(){
        return nome;
    }
    
    /**
     * Imposta il nome dello studente.
     * @param nome Il nuovo nome da impostare.
     */
    public void setNome(String nome){
        this.nome = nome;
    }
    
    /**
     * Restituisce il cognome dello studente.
     * @return Il cognome dello studente.
     */
    public String getCognome(){
        return cognome;
    }
    
    /**
     * Imposta il cognome dello studente.
     * @param cognome Il nuovo cognome da impostare.
     */
    public void setCognome(String cognome){
        this.cognome = cognome;
    }
    
    /**
     * Restituisce il numero di matricola dello studente.
     * @return La matricola dello studente.
     */
    public String getMatricola(){
        return matricola;
    }
    
    /**
     * Imposta il numero di matricola dello studente.
     * @param matricola La nuova matricola da impostare.
     */
    public void setMatricola(String matricola){
        this.matricola = matricola;
    }
    
    /**
     * Restituisce l'indirizzo email dello studente.
     * @return L'indirizzo email dello studente.
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * Imposta l'indirizzo email dello studente.
     * @param email Il nuovo indirizzo email da impostare.
     */
    public void setEmail(String email){
        this.email = email;
    }
    
    /**
     * Restituisce la lista dei prestiti attivi dello studente.
     * @return La lista di oggetti attivi.
     */
      public List<Prestito> getPrestitiAttivi(){
        return prestitiAttivi;
    }
      
     /**
      * Aggiunge un prestito alla lista dei prestiti attivi.
     * @param p L'oggetto da aggiungere.
     */
      public void aggiungiPrestito(Prestito p){
        prestitiAttivi.add(p);
    }
    
     /**
     * Restituisce il numero di prestiti attualmente attivi.
     * @return Il numero di prestiti attivi.
     */
    public int contaPrestitiAttivi(){
      
        return prestitiAttivi.size();
    }
    
    /**
     * Restituisce la descrizione della sanzione dello studente.
     * @return La stringa che descrive lo stato di sanzione.
     */
    public String getSanzione(){
        for(Prestito p: prestitiAttivi)
        {
            if(!p.gestioneSanzioni().equals("Nessun ritardo riscontrato."))
            {
                return p.gestioneSanzioni();
            }    
        }
      return sanzione;
    }
    
    /**
     * Imposta lo stato di sanzione dello studente.
     * @param sanzione La descrizione della sanzione da impostare.
     */
    public void setSanzione(String sanzione){
        this.sanzione = sanzione;
    }
    
    /**
     * Controlla se lo studente è attualmente in ritardo con un prestito.
     * @return true se lo studente è in ritardo,false altrimenti.
     */
    public boolean isRitardo(){
        return ritardo;
    }
    
    /**
     * Imposta lo stato di ritardo dello studente.
     * @param ritardo Lo stato di ritardo da impostare.
     */
    public void setRitardo(boolean ritardo){
        this.ritardo = ritardo;
    }
    
    /**
     * Controlla se lo studente è abilitato a effettuare nuovi prestiti.
     * L'abilitazione è concessa se:
     * 1. Il numero di prestiti attivi è inferiore al limite massimo ).
     * 2. Non è in stato di ritardo).
     *
     * @return true se lo studente è abilitato, false altrimenti.
     */
    public boolean isAbilitato(){
        if( this.contaPrestitiAttivi() < Prestito.LIMITE_PRESTITI && !(this.isRitardo())) {
            
            return true;
            
        }else{
            return false;
        }
    }
    
   
    /**
     * Rimuove un prestito dalla lista dei prestiti attivi.
     * @param p L'oggetto da rimuovere.
     */
    public void rimuoviPrestito(Prestito p){
        
        prestitiAttivi.remove(p);
    }
    
    /**
     * Genera un codice hash basato unicamente sulla matricola.
     * Questo supporta la gestione dell'unicità basata sulla matricola.
     * @return Il codice hash.
     */
    @Override
    public int hashCode()
    {
        int code=matricola == null ? 0 : matricola.hashCode();
        return code;
    }
    
    /**
     * Confronta questo oggetto {@code Studente} con un altro oggetto per verificarne l'uguaglianza.
     * Due studenti sono considerati uguali se hanno la stessa matricola.
     * @param obj L'oggetto da confrontare.
     * @return true se gli oggetti sono uguali (stessa matricola), false altrimenti.
     */
    
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
    
    /**
    * Restituisce una rappresentazione in formato stringa dell'oggetto {@code Studente}.
    * Include nome, cognome, matricola, email, numero di prestiti attivi,
    * stato della sanzione e stato di abilitazione al prestito.
    * @return Una stringa contenente i dettagli dello studente.
    */
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

