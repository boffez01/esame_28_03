package it.epicode.biblioteca.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "libri")
public class Libro extends ElementoCatalogo {

    @Column(nullable = false, length = 255)
    private String autore;

    @Column(nullable = false, length = 100)
    private String genere;

    public Libro() {}

    public Libro(String isbn, String titolo, Integer annoPubblicazione, Integer numeroPagine, String autore, String genere) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
