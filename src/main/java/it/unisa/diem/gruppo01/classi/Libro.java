/**
*@file Libro.java
*@brief Definizione della classe Libro per un sistema di gestione bibliotecaria.
* Questo file contiene l'implementazione dell'entità Libro, includendo la gestione 
* dei dati anagrafici e la logica per la gestione delle copie disponibili.
* @author Gruppo01
*@version 1.0
*/


/**
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */

package it.unisa.diem.gruppo01.classi;
import java.time.LocalDate;

/**
 * @brief Rappresenta un'entità libro in un sistema bibliotecario.
 * La classe gestisce i dettagli anagrafici del libro e il numero di copie fisiche disponibili.
 * @invariant numCopie >= 0 Il numero di copie non deve mai essere negativo.
 * 
 *
 */
public class Libro {
    private String isbn; ///< Codice identificativo univoco del libro.
    private String titolo; ///< Titolo del libro.
    private String autore; ///< Autore del libro.
    private LocalDate annoPb; ///< Anno di pubblicazione del libro.
    private int numCopie; ///< Numero di copie disponibili del libro.
    
    
    
    /**
     * @brief Costruttore della classe
     * Inizializza un nuovo oggetto Libro con i dati forniti.
     * @pre numCopie >= 0
     * @post L'oggetto è creato con i valori passati e numCopie inizializzato.
     * @param[in] isbn Il codice identificativo univoco del libro.
     * @param[in] titolo Il titolo del libro.
     * @param[in] autore L'autore del libro.
     * @param[in] annoPb L'anno di pubblicazione.
     * @param[in] numCopie Il numero di copie del libro.
     * @throws IllegalArgumentException Se numCopie è negativo.
     */
    
    public Libro(String isbn, String titolo, String autore, LocalDate annoPb, int numCopie)
    {
        
        if(numCopie<0)
        {
            throw new IllegalArgumentException("Il numero di copie non può essere negativo.");
        }
        this.isbn=isbn;
        this.titolo=titolo;
        this.autore=autore;
        this.annoPb=annoPb;
        this.numCopie=numCopie;
        
    }
    
    /**
     * @brief Restituisce il codice Isbn del libro.
     * @return Il codice Isbn del libro come stringa.
     */
    public String getIsbn()
    {
        return isbn;
    }
    
    /**
     * @brief Restituisce il titolo del libro.
     * @return Il titolo del libro.
     */
    public String getTitolo()
    {
        return titolo;
    }
    
    /**
     * @brief Imposta un nuovo titolo per il libro.
     * @param[in] titolo Il nuovo titolo da impostare.
     */

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

   

    
    
    /**
     * @brief Restituisce l'autore del libro.
     * @return L'autore del libro.
     */
    public String getAutore()
    {
        return autore;
    }
    
    /**
     * @brief Imposta un nuovo autore per il libro.
     * @param[in] autore Il nuovo autore da impostare.
     */
    
     public void setAutore(String autore) {
        this.autore = autore;
    }
    
    /**
     * @brief Restituisce la data di pubblicazione del libro.
     * @return La data di pubblicazione del libro come oggetto LocalDate.
     */
    public LocalDate getAnnoPb()
    {
        return annoPb;
    }
    
    /**
     * @brief Imposta la data di pubblicazione.
     * @param[in] annoPb La nuova data di pubblicazione.
     */
    
    public void setAnnoPb(LocalDate annoPb)
    {
        this.annoPb = annoPb;
    }
    
    /**
     * @brief Restituisce il numero di copie del libro.
     * @return Il numero di copie del libro come intero.
     */
    public int getNumCopie()
    {
        return numCopie;
    }
    
    /**
     * @brief Imposta manualmente il numero di copie.
     * @pre numCopie >= 0
     * @param[in] numCopie Il nuovo numero di copie.
     */
    public void setNumCopia(int numCopie) {
        this.numCopie = numCopie;
    }
    
    /**
     * @brief Verifica la disponibilità del libro.
     * @return true se c'è almeno una copia disponibile (numCopie > 0), false altrimenti.
     */
    public boolean isDisponibile()
    {
       return this.numCopie > 0;
    }
    
   
    /**
     * @brief Incrementa il numero di copie del libro.
     * Aggiunge una quantità specificata al numero corrente di copie.
     * @pre quantita > 0.
     * @post numCopie(nuovo) = numCopie(vecchio valore) + quantita.
     * @param[in] quantita Il numero di copie da aggiungere.
     * @throws IllegalArgumentException Se la quantità non è positiva.
     */
    public void incrementaCopie(int quantita)
{
     //controllo se la quantità da aggiungere è positiva
    if (quantita > 0) {
       
        this.numCopie = this.numCopie + quantita;
    } else {
        //se la quantità non è positiva lancio un'eccezione
      throw new IllegalArgumentException(("La quantità da incrementare deve essere positiva."));
    }
}
    
   /**
     * @brief Tenta di decrementare di uno il numero di copie (prestito).
     * Se ci sono copie disponibili, ne sottrae una e restituisce true.
     * Altrimenti lascia lo stato invariato e restituisce false.
     * @post (return == true) AND (numCopie_new == numCopie_old - 1)
     * @post (return == false) AND (numCopie_new == numCopie_old)
     * @return true se il decremento è riuscito, false se non c'erano copie.
     */
     
    
    public boolean decrementaCopie()
    {
        if (this.numCopie > 0) {
            this.numCopie--;
            return true; // Operazione riuscita
        }
        return false; // Operazione fallita (libro non disponibile)
    }
    
   /**
     * @brief Calcola l'hash code dell'oggetto.
     * L'hash è calcolato esclusivamente basandosi sull'ISBN per garantire coerenza con equals.
     * @return Un intero che rappresenta l'hash code.
     * @see equals(Object obj)
     */
   
    @Override
    public int hashCode()
    {
        int code=isbn == null ? 0 : isbn.hashCode();
        return code;
    }
    
   /**
     * @brief Confronta questo libro con un altro oggetto.
     * Due libri sono considerati uguali se e solo se hanno lo stesso codice ISBN.
     * @param[in] obj L'oggetto con cui confrontare questa istanza.
     * @return true se gli oggetti sono uguali (stesso ISBN), false altrimenti.
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
        Libro other=(Libro)obj;
        if(this.isbn.equals((other.isbn))) return true;
        return false;
        
    }
    
    /**
     * @brief Restituisce una rappresentazione in formato stringa dell'oggetto {@code Libro}.
     * Include Isbn,titolo,autore,anno di pubblicazione e numero di copie.
     * @return Una stringa contenente tutti i dettagli del libro.
     */
    
    
    @Override
    public String toString()
    {
        return "Libro [ISBN=" + this.getIsbn() + ", Titolo=" + this.getTitolo() + ", Autore=" + this.getAutore()
                + ", Pubblicazione=" + this.getAnnoPb() + ", Copie=" + this.getNumCopie() + "]";
    }
    
}
