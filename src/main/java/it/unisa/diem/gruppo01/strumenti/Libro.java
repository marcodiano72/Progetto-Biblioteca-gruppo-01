/**
*@file Libro.java
*@brief Questo file rappresenta un'entità libro all'interno di un sistema di gestione bibliotecaria.
*
*@version 1.0
*/


/**
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */

package it.unisa.diem.gruppo01.strumenti;
import java.time.LocalDate;

/**
 * Classe Libro
 * La classe rappresenta un'entità libro all'interno
 * di un sistema di gestione bibliotecaria. Contiene i dettagli anagrafici del
 * libro e le informazioni sulla sua disponibilità (numero di copie).
 *
 */
public class Libro {
    private String isbn; ///< Codice identificativo univoco del libro.
    private String titolo; ///< Titolo del libro.
    private String autore; ///< Autore del libro.
    private LocalDate annoPb; ///< Anno di pubblicazione del libro.
    private int numCopie; ///< Numero di copie del libro.
    
    
    
    /**
     * Costruttore della classe
     * Inizializza i dati del libro.
     * 
     * @param isbn Il codice identificativo univoco del libro.
     * @param titolo Il titolo del libro.
     * @param autore L'autore del libro.
     * @param annoPb L'anno di pubblicazione del libro.
     * @param numCopie Il numero di copie del libro. 
     * @throws IllegalArgumentException Se il numero di copie è negativo.
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
     * Restituisce il codice Isbn del libro.
     * @return Il codice Isbn del libro.
     */
    public String getIsbn()
    {
        return isbn;
    }
    
    /**
     * Restituisce il titolo del libro.
     * @return Il titolo del libro.
     */
    public String getTitolo()
    {
        return titolo;
    }
    
    /**
     * Restituisce l'autore del libro.
     * @return L'autore del libro.
     */
    public String getAutore()
    {
        return autore;
    }
    
    /**
     * Restituisce l'anno di pubblicazione del libro.
     * @return L'anno di pubblicazione del libro.
     */
    public LocalDate getAnnoPb()
    {
        return annoPb;
    }
    
    /**
     * Restituisce il numero di copie del libro.
     * @return Il numero di copie del libro.
     */
    public int getNumCopie()
    {
        return numCopie;
    }
    
    /**
     * Imposta il numero di copie del libro.
     * @param numCopie Il nuovo numero di copie da impostare.
     */
    public void setNumCopia(int numCopie) {
        this.numCopie = numCopie;
    }
    
    /**
     * Controlla se il libro è disponibile.
     * @return true se il numero di copie è maggiore di zero, false altimenti.
     */
    public boolean isDisponibile()
    {
       return this.numCopie > 0;
    }
    
   
    /**
     * Incrementa il numero di copie del libro.
     * Se il valore (quantita) da aggiungere è maggiore di zero:
     * Incrementa il numero di copie, aggiungendo quantita,
     * altrimenti stampa una stringa.
     * @param quantita Il numero di copie da aggiungere (deve essere positivo).
     */
    public void incrementaCopie(int quantita)
{
     //controllo se la quantità da aggiungere è positiva
    if (quantita > 0) {
       
        this.numCopie = this.numCopie + quantita;
    } else {
        
        System.out.println("Attenzione: La quantità da incrementare deve essere un numero positivo.");
    }
}
    
    /**
     * Restituisce true o false a seconda dell'esito dell'operazione
     * (Verifica la disponibilità del libro).
     * Se il numero di copie del libro è superiore a zero:
     * Decrementa il numero di copie e restituisce true.
     * Altrimenti non modifica il numero di copie e restituisce false.
     
     * @return true se il decremento è riuscito, false se non c'erano copie disponibili.
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
     * Genera un codice hash basato univocamente sul codice isbn.
     * Questo supporta la gestione dell'unicità basata sul codice Isbn.
     * @return Il codice hash.
     */
    
    //gestione unicità basata sull'ISBN
    @Override
    public int hashCode()
    {
        int code=isbn == null ? 0 : isbn.hashCode();
        return code;
    }
    
    /**
     * Confronta questo oggetto {@code Libro} con un altro oggetto per verificarne l'uguaglianza.
     * Due libri sono considerati uguali se hanno lo stesso codice Isbn.
     * @param obj L'oggetto da confrontare con l'istanza corrente.
     * @return true se l'oggetto è un libro con lo stesso Isbn, false altrimenti.
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
     * Restituisce una rappresentazione in formato stringa dell'oggetto {@code Libro}.
     * Include Isbn,titolo,autore,anno di pubblicazione e numero di copie.
     * @return Una stringa contenente i dettagli del libro.
     */
    
    
    @Override
    public String toString()
    {
        return "Libro [ISBN=" + this.getIsbn() + ", Titolo=" + this.getTitolo() + ", Autore=" + this.getAutore()
                + ", Pubblicazione=" + this.getAnnoPb() + ", Copie=" + this.getNumCopie() + "]";
    }
    
}
