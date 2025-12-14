/**
*@file Studente.java
*@brief Implementazione dell'entità Studente per il sistema di gestione bibliotecaria.
* Questo file contiene la classe Studente che gestisce i dati anagrafici,
* lo stato dei prestiti e le sanzioni.
* @author Gruppo01
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
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Rappresenta un'entità studente nel sistema bibliotecario.
 * La classe mantiene le informazioni personali dello studente, la lista 
 * dei suoi prestiti attivi e il suo stato (sanzioni, ritardi, abilitazione).
 * @invariant matricola != null La matricola identifica univocamente lo studente.
 * 
 *
 */
public class Studente {
    
    private String nome; ///< Nome dello studente.
    private String cognome; ///< Cognome dello studente.
    private String matricola; ///< Numero di matricola dello studente (identficativo univoco).
    private String email; ///< Indirizzo email dello studente.
    private String sanzione; ///< Descrizione della sanzione eventualmente applicata allo studente.
    private boolean ritardo; ///< Indica se lo studente è in ritardo con la restituzione di un prestito.
    private List<Prestito> prestitiAttivi; ///< Lista contenente i prestiti attivi dello studente.
    private Prestito prestito;  ///< Variabile di supporto per verifiche sui prestiti.
    
    /**
     * @brief Costruttore della classe
     * Inizializza un nuovo studente con i dati anagrafici forniti e una lista vuota di prestiti.
     * La sanzione viene inizializzata di default a "Nessuna"
     * 
     * @param[in] cognome Il cognome dello studente.
     * @param[in] nome Il nome dello studente.
     * @param[in] matricola La matricola univoca.
     * @param[in] email L'indirizzo email dello studente.
     * @param[in] sanzione Parametro per la sanzione (Attualmente sovrascritto dal costruttore).
     * @param[in] ritardo Lo stato iniziale del ritardo.
     */
    public Studente(String cognome, String nome, String matricola, String email, String sanzione, boolean ritardo){
        
        this.cognome = cognome;
        this.nome = nome;
        this.matricola = matricola;
        this.email = email;
        this.sanzione = sanzione;
        this.ritardo = ritardo;
        prestitiAttivi = new ArrayList<>();
     
    }
    
    
    /**
     * @brief Restituisce il nome dello studente.
     * @return Il nome dello studente come stringa.
     */
    public String getNome(){
        return nome;
    }
    
    /**
     * @brief Imposta il nome dello studente.
     * @param[in] nome Il nuovo nome da impostare.
     */
    public void setNome(String nome){
        this.nome = nome;
    }
    
    /**
     * @brief Restituisce il cognome dello studente.
     * @return Il cognome dello studente come stringa.
     */
    public String getCognome(){
        return cognome;
    }
    
    /**
     * @brief Imposta il cognome dello studente.
     * @param[in] cognome Il nuovo cognome da impostare.
     */
    public void setCognome(String cognome){
        this.cognome = cognome;
    }
    
    /**
     * @brief Restituisce la matricola dello studente.
     * @return La matricola dello studente come stringa.
     */
    public String getMatricola(){
        return matricola;
    }
    
    /**
     * @brief Imposta la matricola dello studente.
     * @param[in] matricola La nuova matricola da impostare.
     */
    public void setMatricola(String matricola){
        this.matricola = matricola;
    }
    
    /**
     * @brief Restituisce l'indirizzo email dello studente.
     * @return L'indirizzo email dello studente.
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * @brief Imposta l'indirizzo email dello studente.
     * @param[in] email Il nuovo indirizzo email da impostare.
     */
    public void setEmail(String email){
        this.email = email;
    }
    
    /**
     * @brief Restituisce la lista dei prestiti attivi dello studente.
     * @return La lista di oggetti Prestito.
     */
      public List<Prestito> getPrestitiAttivi(){
        return prestitiAttivi;
    }
      
     /**
      * @brief Aggiunge un nuovo prestito alla lista dei prestiti attivi dello studente.
     * @param[in] p L'oggetto Prestito da aggiungere.
     */
      public void aggiungiPrestito(Prestito p){
        prestitiAttivi.add(p);
    }
    
     /**
     * @brief Conta il numero di prestiti attualmente attivi.
     * @return Il numero intero di prestiti attivi nella lista.
     */
    public int contaPrestitiAttivi(){
      
        return prestitiAttivi.size();
    }
    
    /**
     * @brief Restituisce lo stato della sanzione.
     * @return La stringa che descrive lo stato di sanzione.
     */
public String getSanzione(){ return sanzione; }
    
    /**
     * @brief Imposta lo stato della sanzione.
     * @param[in] sanzione La descrizione della sanzione da applicare.
     */
    public void setSanzione(String sanzione){
        this.sanzione = sanzione;
    }
    
    /**
     * @brief Verifica se lo studente è in stato di ritardo.
     * Il ritardo è determinato se lo studente non è abilitato e ha meno di 3 prestiti.
     * @return true se lo studente è in ritardo, false altrimenti.
     */
    
    public boolean isRitardo(){
        if(!this.isAbilitato() && this.prestitiAttivi.size()<3)
            return true;
        else return false;
    }
    
    /**
     * @brief Imposta manualmente il flag di ritardo.
     * @param[in] ritardo Il valore booleano da impostare.
     */
    public void setRitardo(boolean ritardo){
        this.ritardo = ritardo; // Reset
  
    }
    
    /**
     * @brief Verifica se lo studente è abilitato a richiedere nuovi prestiti.
     * Uno studente NON è abilitato se:
     * - Ha 3 o più prestiti attivi.
     * - Ha una sanzione di "Categoria 3".
     * - Ha una sanzione di "Categoria 2" legata a un prestito scaduto da meno di 30 giorni.
     * @return true se lo studente può effettuare prestiti, false altrimenti.
     */
    
    public boolean isAbilitato(){
        if(this.prestitiAttivi.size()>=3)
            return false;
        if(this.sanzione.startsWith("Categoria 3")){
            return false;
        }
        else if(this.sanzione.startsWith("Categoria 2")){
            if(ChronoUnit.DAYS.between(prestito.getDataRestituzione(), LocalDate.now())<30)
                return false;
        }
            return true;
    }
    
    /**
     * @brief Rimuove un prestito dalla lista dei prestiti attivi.
     * @post prestitiAttivi.size() == old(prestitiAttivi.size()) - 1 (se p era presente)
     * @param[in] p Il prestito da rimuovere.
     */
    
    public void rimuoviPrestito(Prestito p){
        prestitiAttivi.remove(p);
    }
    
    /**
     * @brief Genera l'hash code basato sulla matricola.
     * @return Il codice hash.
     * @see equals(Object obj)
     */
    
    @Override
    public int hashCode()
    {
        int code=matricola == null ? 0 : matricola.hashCode();
        return code;
    }
    
    /**
     * @brief Confronta questo oggetto {@code Studente} con un altro oggetto per verificarne l'uguaglianza.
     * Due studenti sono considerati uguali se hanno la stessa matricola.
     * @param[in] obj L'oggetto da confrontare.
     * @return true se le matricole coincidono, false altrimenti.
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
    * @brief Restituisce una rappresentazione in formato stringa dell'oggetto {@code Studente}.
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

