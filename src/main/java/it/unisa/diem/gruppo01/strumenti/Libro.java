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
    private String ISBN; //codice libro
    private String titolo;
    private String autore;
    private LocalDate annoPb; //anno di pubblicazione
    int numCopie;
    
    
    
    public Libro(String ISBN, String titolo, String autore, LocalDate annoPb, int numCopie)
    {
        this.ISBN=ISBN;
        this.titolo=titolo;
        this.autore=autore;
        this.annoPb=annoPb;
        this.numCopie=numCopie;
        
    }
    
    public String getISBN()
    {
        return ISBN;
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

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setNumCopia(int numCopia) {
        this.numCopie = numCopie;
    }
    
    public boolean isDisponibile()
    {
       return this.numCopie > 0;
    }
    
    
    @Override
    public String ToString()
    {
       return "Codice libro:" + ISBN + ""+ "titolo:" +  titolo + "" + "autore" + autore + "" + "anno di pubblicazione" + annoPb + "" + "numero copie disponibili" + numCopie + "\n";
    }
    
}
