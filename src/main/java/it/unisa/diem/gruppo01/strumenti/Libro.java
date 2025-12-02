/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.strumenti;
import java.time.LocalDate;

/**
 *
 * @author Marco Diano'
 */
public class Libro {
    private String isbn; //codice libro
    private String titolo;
    private String autore;
    private LocalDate annoPb; //anno di pubblicazione
    private int numCopie; //copie disponibili
    
    
    
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
    
    public String getIsbn()
    {
        return isbn;
    }
   
    public String getTitolo()
    {
        return titolo;
    }
    
    public String getAutore()
    {
        return autore;
    }
    
    public LocalDate getAnnoPb()
    {
        return annoPb;
    }
    
    public int getNumCopie()
    {
        return numCopie;
    }

    public void setNumCopia(int numCopie) {
        this.numCopie = numCopie;
    }
    
    public boolean isDisponibile()
    {
       return this.numCopie > 0;
    }
    
   
    
 
    public void incrementaCopie(int quantita)
{
     //controllo se la quantità da aggiungere è positiva
    if (quantita > 0) {
       
        this.numCopie = this.numCopie + quantita;
    } else {
        
        System.out.println("Attenzione: La quantità da incrementare deve essere un numero positivo.");
    }
}
    
    public boolean decrementaCopie()
    {
        if (this.numCopie > 0) {
            this.numCopie--;
            return true; // Operazione riuscita
        }
        return false; // Operazione fallita (libro non disponibile)
    }
    
    
    @Override
    public String toString()
    {
        return "Libro [ISBN=" + this.getIsbn() + ", Titolo=" + this.getTitolo() + ", Autore=" + this.getAutore()
                + ", Pubblicazione=" + this.getAnnoPb() + ", Copie=" + this.getNumCopie() + "]";
    }
    
}
