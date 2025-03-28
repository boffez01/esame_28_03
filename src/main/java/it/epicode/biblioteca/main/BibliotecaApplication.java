package it.epicode.biblioteca.main;

import it.epicode.biblioteca.dao.BibliotecaDAO;
import it.epicode.biblioteca.entities.*;
import it.epicode.biblioteca.utils.Periodicita;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BibliotecaApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        EntityManager em = emf.createEntityManager();
        BibliotecaDAO dao = new BibliotecaDAO();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("Scegli un'operazione:");
            System.out.println("1. Aggiungi un libro");
            System.out.println("2. Aggiungi una rivista");
            System.out.println("3. Aggiungi un utente");
            System.out.println("4. Aggiungi un prestito");
            System.out.println("5. Visualizza tutti i libri");
            System.out.println("6. Visualizza tutte le riviste");
            System.out.println("7. Visualizza tutti gli utenti");
            System.out.println("8. Visualizza prestiti attivi per un numero di tessera");
            System.out.println("9. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    System.out.print("Inserisci ISBN: ");
                    String isbnLibro = scanner.nextLine();
                    System.out.print("Inserisci titolo: ");
                    String titoloLibro = scanner.nextLine();
                    System.out.print("Inserisci anno pubblicazione: ");
                    int annoLibro = scanner.nextInt();
                    System.out.print("Inserisci numero di pagine: ");
                    int pagineLibro = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Inserisci autore: ");
                    String autoreLibro = scanner.nextLine();
                    System.out.print("Inserisci genere: ");
                    String genereLibro = scanner.nextLine();

                    Libro libro = new Libro();
                    libro.setIsbn(isbnLibro);
                    libro.setTitolo(titoloLibro);
                    libro.setAnnoPubblicazione(annoLibro);
                    libro.setNumeroPagine(pagineLibro);
                    libro.setAutore(autoreLibro);
                    libro.setGenere(genereLibro);

                    em.getTransaction().begin();
                    em.persist(libro);
                    em.getTransaction().commit();
                    System.out.println("Libro aggiunto con successo!");
                    break;

                case 2:
                    System.out.print("Inserisci ISBN: ");
                    String isbnRivista = scanner.nextLine();
                    System.out.print("Inserisci titolo: ");
                    String titoloRivista = scanner.nextLine();
                    System.out.print("Inserisci anno pubblicazione: ");
                    int annoRivista = scanner.nextInt();
                    System.out.print("Inserisci numero di pagine: ");
                    int pagineRivista = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Inserisci periodicità: ");
                    String periodicitaRivista = scanner.nextLine();

                    Rivista rivista = new Rivista();
                    rivista.setIsbn(isbnRivista);
                    rivista.setTitolo(titoloRivista);
                    rivista.setAnnoPubblicazione(annoRivista);
                    rivista.setNumeroPagine(pagineRivista);
                    rivista.setPeriodicita(Periodicita.valueOf(periodicitaRivista));

                    em.getTransaction().begin();
                    em.persist(rivista);
                    em.getTransaction().commit();
                    System.out.println("Rivista aggiunta con successo!");
                    break;

                case 3:
                    System.out.print("Inserisci nome utente: ");
                    String nomeUtente = scanner.nextLine();
                    System.out.print("Inserisci cognome utente: ");
                    String cognomeUtente = scanner.nextLine();
                    System.out.print("Inserisci data di nascita (YYYY-MM-DD): ");
                    String dataNascitaUtente = scanner.nextLine();
                    System.out.print("Inserisci numero tessera: ");
                    String numeroTessera = scanner.nextLine();

                    Utente utente = new Utente();
                    utente.setNome(nomeUtente);
                    utente.setCognome(cognomeUtente);
                    utente.setDataNascita(LocalDate.parse(dataNascitaUtente));
                    utente.setNumeroTessera(numeroTessera);

                    em.getTransaction().begin();
                    em.persist(utente);
                    em.getTransaction().commit();
                    System.out.println("Utente aggiunto con successo!");
                    break;

                case 4:
                    System.out.print("Inserisci numero tessera utente: ");
                    String tesseraUtentePrestito = scanner.nextLine();
                    System.out.print("Inserisci ISBN dell'elemento (libro/rivista): ");
                    String isbnPrestito = scanner.nextLine();

                    Utente utentePrestito = em.find(Utente.class, tesseraUtentePrestito);
                    Object elementoPrestato = em.createQuery("SELECT e FROM Libro e WHERE e.isbn = :isbn", Libro.class)
                            .setParameter("isbn", isbnPrestito)
                            .getSingleResult();

                    if (utentePrestito != null && elementoPrestato != null) {
                        Prestito prestito = new Prestito();
                        prestito.setUtente(utentePrestito);
                        prestito.setElementoPrestato((ElementoCatalogo) elementoPrestato);
                        prestito.setDataInizioPrestito(LocalDate.now());
                        prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));

                        em.getTransaction().begin();
                        em.persist(prestito);
                        em.getTransaction().commit();
                        System.out.println("Prestito aggiunto con successo!");
                    } else {
                        System.out.println("Errore: Utente o Elemento non trovati.");
                    }
                    break;

                case 5:
                    System.out.println("\n📚 Tutti i libri:");
                    List<Libro> libri = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
                    libri.forEach(System.out::println);
                    break;

                case 6:
                    System.out.println("\n📖 Tutte le riviste:");
                    List<Rivista> riviste = em.createQuery("SELECT r FROM Rivista r", Rivista.class).getResultList();
                    riviste.forEach(System.out::println);
                    break;

                case 7:
                    System.out.println("\n👤 Tutti gli utenti:");
                    List<Utente> utenti = em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
                    utenti.forEach(System.out::println);
                    break;

                case 8:
                    System.out.print("Inserisci numero tessera per prestiti attivi: ");
                    String tesseraPrestito = scanner.nextLine();
                    List<Object> prestitiAttivi = dao.findPrestitiAttivi(tesseraPrestito);
                    prestitiAttivi.forEach(System.out::println);
                    break;

                case 9:
                    running = false;
                    System.out.println("Arrivederci!");
                    break;

                case 10: {
                    List<Prestito> prestitiScaduti = dao.findPrestitiScaduti();
                    if (prestitiScaduti.isEmpty()) {
                        System.out.println("Non ci sono prestiti scaduti.");
                    } else {
                        prestitiScaduti.forEach(System.out::println);
                    }
                    break;
                }

                default:
                    System.out.println("Opzione non valida!");
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }
}

