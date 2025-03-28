package it.epicode.biblioteca.entities;

import it.epicode.biblioteca.utils.Periodicita;
import jakarta.persistence.*;

@Entity
@Table(name = "riviste")
public class Rivista extends ElementoCatalogo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Periodicita periodicita;

    public Rivista() {}

    public Rivista(String isbn, String titolo, Integer annoPubblicazione, Integer numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}
