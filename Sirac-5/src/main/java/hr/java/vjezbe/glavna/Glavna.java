package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/***
 * Glavna klasa s metodom main
 */

public class Glavna {

    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_PREDMETA = 3;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_ISPITA = 2;

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    /***
     * Početak programa
     * @param args argumenti iz komandne linije
     */

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        Integer brojUstanova = null;
        Boolean unos;
        do{
            unos = false;
            try{
                System.out.println("Unesite broj obrazovnih ustanova: ");
                brojUstanova = Integer.parseInt(sc.nextLine());
                logger.info("Unjeli ste broj ustanova: " + brojUstanova);

            }catch (NumberFormatException ex){
                logger.error("Unesena je ne numericka vrijednost");
                System.out.println("Morate unesti numericku vrijednust");
                unos = true;
            }
        }while (unos);
        Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>();

        for(int k=0;k<brojUstanova;k++) {

            List<Profesor> profesori = new ArrayList<>();
            List<Predmet> predmeti = new ArrayList<>();
            List<Student> studenti = new ArrayList<>();
            List<Ispit> ispiti = new ArrayList<>();

            System.out.println("Unesite podatke za" + (k + 1) + ". obrazovnu ustanovu: ");

            for (int i = 0; i < BROJ_PROFESORA; i++) {
                System.out.println("Unesite " + (i + 1) + ". profesora: ");
                System.out.println("Unesite šifru profesora: ");
                String sifra = sc.nextLine();
                logger.info("Unesena sifra: " + sifra);
                System.out.println("Unesite ime profesora: ");
                String ime = sc.nextLine();
                System.out.println("Unesite prezime profesora: ");
                String prezime = sc.nextLine();
                System.out.println("Unesite titulu profesora: ");
                String titula = sc.nextLine();
                profesori.add(new Profesor.Builder(sifra)
                        .saImenom(ime)
                        .saPrezimenom(prezime)
                        .saTitulom(titula)
                        .build()); 
            }

            for (int i = 0; i < BROJ_STUDENATA; i++) {
                System.out.println("Unesite " + (i + 1) + ". studenta: ");
                System.out.println("Unesite ime studenta: ");
                String imeStudenta = sc.nextLine();
                System.out.println("Unesite prezime studenta: ");
                String prezime = sc.nextLine();
                System.out.println("Unesite datum rođenja studenta " + imeStudenta + " " + prezime + " u formatu (dd.MM.yyyy.): ");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                LocalDate datum = LocalDate.parse(sc.nextLine(), dtf);
                System.out.println("Unesite JMBAG studenta: ");
                String jmbag = sc.nextLine();
                studenti.add(new Student(imeStudenta, prezime, jmbag, datum));
            }

            Map<Profesor, List<Predmet>> nositelji = new HashMap<>();
            boolean provjera = true;
            while(provjera){
                for (int i = 0; i < BROJ_PREDMETA; i++) {
                    System.out.println("Unesite " + (i + 1) + ". predmet: ");
                    System.out.println("Unesite šifru predmeta: ");
                    String sifra = sc.nextLine();
                    System.out.println("Unesite naziv predmeta: ");
                    String naziv = sc.nextLine();
                    Integer brojBodova = null;

                    do{
                        unos = false;
                        try{
                            System.out.println("Unesite broj ECTS bodova za predmet '" + naziv + "': ");
                            brojBodova = Integer.parseInt(sc.nextLine());
                            logger.info("Unjeli ste broj ECTS bodova: " + brojBodova);

                        }catch (NumberFormatException ex){
                            logger.error("Unesena je ne numericka vrijednost");
                            System.out.println("Morate unesti numeričku vrijednust");
                            unos = true;
                        }
                    }while (unos);

                    Integer brojProfesora = null;

                    do{
                        unos = false;
                        try{
                            System.out.println("Odaberite profesora:");
                            Integer counter = 1;
                            for (Profesor profesor: profesori) {
                                System.out.println(counter + "." + profesor.getIme() + " " + profesor.getPrezime());
                                counter++;
                            }
                            brojProfesora = Integer.parseInt(sc.nextLine());
                            logger.info("Unjeli ste broj profesora : " + brojProfesora);

                        }catch (NumberFormatException ex){
                            logger.error("Unesena je ne numericka vrijednost");
                            System.out.println("Morate unesti numeričku vrijednust");
                            unos = true;
                        }
                    }while (unos);

                    Profesor profesor = profesori.get(brojProfesora-1);
                    Integer brojStudenata = null;

                    do{
                        unos = false;
                        try{
                            System.out.println("Unesite broj studenata za predmet '" + naziv + "': ");
                            brojStudenata = Integer.parseInt(sc.nextLine());
                            logger.info("Unjeli ste broj studenata : " + brojStudenata);

                        }catch (NumberFormatException ex){
                            logger.error("Unesena je ne numericka vrijednost");
                            System.out.println("Morate unesti numeričku vrijednust");
                            unos = true;
                        }
                    }while (unos);

                    Set<Student> studentSet = new HashSet<>();
                    Integer odabir = 0;
                    for(int j= 0; j<brojStudenata;j++){
                        Integer c = 1;
                        System.out.println("Odaberite studenta");
                        for (Student student: studenti) {
                            System.out.println(c + ". " + student.getIme() + " " + student.getPrezime());
                            c++;
                        }
                        odabir = Integer.parseInt(sc.nextLine());
                        studentSet.add(studenti.get(odabir-1));
                    }
                    var predmet = new Predmet(sifra, naziv, brojBodova, profesor, studentSet);
                    var nositelj = predmet.getNositelj();
                    predmeti.add(predmet);

                    if (nositelji.containsKey(profesor)){
                        nositelji.get(profesor).add(predmet);
                    }else{
                        nositelji.put(profesor, new ArrayList<>() {{add(predmet);}});
                    }
                }

                if (profesori.stream().allMatch(nositelji::containsKey) && profesori.stream().anyMatch(p -> {
                    var l = nositelji.get(p);
                    return l != null && l.size() > 1;
                })) {
                    nositelji.forEach((nositeljNovi, predmetList) -> {
                        System.out.printf("%s %s je nositelj predmeta:%n", nositeljNovi.getIme(), nositeljNovi.getPrezime());
                        for (var p : predmetList) {
                            System.out.printf("  %s %s%n", p.getSifra(), p.getNaziv());
                        }
                    });
                    provjera = false;
                } else {
                    System.out.println("Greška pri unosu! Svaki profesor mora biti nositelj barem jednog predmeta i barem jedan mora biti nositelj više od jednog predmeta!");
                }
            }



            for (int i = 0; i < BROJ_ISPITA; i++) {
                System.out.println("Unesite " + (i + 1) + ". ispitni rok: ");

                Integer brojPredmeta = null;

                do{
                    unos = false;
                    try{
                        Integer counter = 1;
                        System.out.println("Odaberite predmet: ");
                        for (Predmet predmet: predmeti) {
                            System.out.println(counter + ". " + predmet.getNaziv());
                            counter++;
                        }
                        brojPredmeta = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj predmeta : " + brojPredmeta);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                Predmet predmet = predmeti.get(brojPredmeta - 1);
                System.out.println("Unesite naziv dvorane: ");
                String dvorana = sc.nextLine();
                System.out.println("Unesite zgradu dvorane: ");
                String zgrada = sc.nextLine();
                Dvorana dvorana1 = new Dvorana(dvorana,zgrada);

                Integer brojStudenta = null;

                do{
                    unos = false;
                    try{
                        Integer counter = 1;
                        System.out.println("Odaberite studenta: ");
                        for (Student student: studenti) {
                            System.out.println(counter + ". " + student.getIme() + " " + student.getPrezime());
                            counter++;
                        }
                        brojStudenta = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj studenta : " + brojStudenta);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                Student student = studenti.get((brojStudenta - 1));

                Ocjena Ocjena = null;
                System.out.println("Unesite ocjenu na ispitu (1-5)");
                Integer ocjena = Integer.parseInt(sc.nextLine());
                switch (ocjena) {
                    case 1 -> Ocjena = Ocjena.NEDOVOLJAN;
                    case 2 -> Ocjena = Ocjena.DOVOLJAN;
                    case 3 -> Ocjena = Ocjena.DOBAR;
                    case 4 -> Ocjena = Ocjena.VRLO_DOBAR;
                    case 5 -> Ocjena = Ocjena.IZVRSTAN;
                    default -> {
                        String message = "Kritična greška: dosegnut nedostižan dio koda pri upisu ocjene!";
                        logger.error(message);
                        throw new RuntimeException(message);
                    }
                }
                if(Ocjena.getNumerickaVrijednost() == 1){
                    student.setMogucnostIzlaskaNaZavrsniRad(false);
                }else{
                    student.setMogucnostIzlaskaNaZavrsniRad(true);
                }

                System.out.println("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
                LocalDateTime datumIVrijeme = LocalDateTime.parse(sc.nextLine(), dtf);

                ispiti.add(new Ispit(predmet, student, Ocjena, datumIVrijeme, dvorana1));


                Integer input = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Odrzava li se ispit online? ");
                        System.out.println("1. DA");
                        System.out.println("2. NE");

                        input = Integer.parseInt(sc.nextLine());
                        logger.info("Odabrali ste da se ispit odrzava : " + input);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                if(input == 1){

                    Integer input1 = null;

                    do{
                        unos = false;
                        try{
                            System.out.println("Molimo odaberite software u kojemu se odrzava ispit");
                            System.out.println("1. IntelliJ");
                            System.out.println("2. Rider");

                            input1 = Integer.parseInt(sc.nextLine());
                            logger.info("Odabrali ste software : " + input1);

                        }catch (NumberFormatException ex){
                            logger.error("Unesena je ne numericka vrijednost");
                            System.out.println("Morate unesti numeričku vrijednust");
                            unos = true;
                        }
                    }while (unos);

                    String program = "nista";
                    if(input1 == 1){
                        program = ispiti.get(i).imePrograma("intellij");
                    } else if (input1 == 2) {
                        program = ispiti.get(i).imePrograma("rider");
                    }
                    System.out.println("Programski jezik koristen na ispitu je: " + program);
                }



            }

            for (Predmet predmet: predmeti) {
                if (!predmet.getStudenti().isEmpty()){
                    System.out.println("Studenti upisani na predmet '" + predmet.getNaziv() + "' su:");
                    predmet.getStudenti().stream()
                            .sorted(new StudentSorter())
                            .toList()
                            .forEach(s -> System.out.printf("%s %s %s%n", s.getJmbag(), s.getPrezime(), s.getIme()));
                }else{
                    System.out.println("Nema studenata upisanih na predmet '" + predmet.getNaziv() + "'.");
                }
            }

            Integer ustanova = null;

            do{
                unos = false;
                try{
                    System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");

                    ustanova = Integer.parseInt(sc.nextLine());
                    logger.info("Unjeli ste broj ustanove : " + ustanova);

                }catch (NumberFormatException ex){
                    logger.error("Unesena je ne numericka vrijednost");
                    System.out.println("Morate unesti numeričku vrijednust");
                    unos = true;
                }
            }while (unos);

            String nazivObrazovneUstanove;
            switch (ustanova) {
                case 1 -> {
                    System.out.println("Unesite naziv obrazovne ustanove: ");
                    nazivObrazovneUstanove = sc.nextLine();
                    sveuciliste.dodajObrazovnuUstanovu(new VeleucilisteJave(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti));

                    System.out.println("Ispiti s ocjenom 5:");
                    var ispitiIzvrstan = sveuciliste.dohvatiObrazovnuUstanovu(k).ispitiSOcjenomIzvrstan();
                    if (ispitiIzvrstan.size() == 0) {
                        System.out.println("Nitko nije dobio ocjenu 5!");
                    } else {
                        ispitiIzvrstan.forEach(i -> System.out.printf("%s %s, %s%n", i.getStudent().getIme(), i.getStudent().getPrezime(), i.getPredmet().getNaziv()));
                    }

                    for (Student student : sveuciliste.dohvatiObrazovnuUstanovu(k).getStudenti()) {
                        if(student.getMogucnostIzlaskaNaZavrsniRad()){

                        student.setOcjenaZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste OcjenaZavrsnogRada : " + student.getOcjenaZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        student.setOcjenaObraneZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaObraneZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste Ocjena Obrane Zavrsnog Rada : " + student.getOcjenaObraneZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        List<Ispit> filtriraniIspiti = new ArrayList<>();
                        filtriraniIspiti = ((VeleucilisteJave) sveuciliste.dohvatiObrazovnuUstanovu(k)).filtrirajIspitePoStudentu(sveuciliste.dohvatiObrazovnuUstanovu(k).getIspiti(), student);
                        System.out.println("konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + ": "
                                + ((VeleucilisteJave) sveuciliste.dohvatiObrazovnuUstanovu(k))
                                .izracunajKonacnuOcjenuStudijaZaStudenta(filtriraniIspiti, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada()));

                        }else{
                            student.setOcjenaObraneZavrsnogRada(1);
                            student.setOcjenaZavrsnogRada(1);
                        }
                    }
                    Student najboljiStudent = ((VeleucilisteJave) sveuciliste.dohvatiObrazovnuUstanovu(k)).odrediNajuspjesnijegStudentaNaGodini(2022);
                    System.out.println("Najbolji student 2022. godine je " + najboljiStudent.getIme() + " " + najboljiStudent.getPrezime() + " JMBAG: " + najboljiStudent.getJmbag());
                }
                case 2 -> {
                    System.out.println("Unesite naziv obrazovne ustanove: ");
                    nazivObrazovneUstanove = sc.nextLine();
                    sveuciliste.dodajObrazovnuUstanovu(new FakultetRacunarstva(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti));

                    System.out.println("Ispiti s ocjenom 5:");
                    var ispitiIzvrstan = sveuciliste.dohvatiObrazovnuUstanovu(k).ispitiSOcjenomIzvrstan();
                    if (ispitiIzvrstan.size() == 0) {
                        System.out.println("Nitko nije dobio ocjenu 5!");
                    } else {
                        ispitiIzvrstan.forEach(i -> System.out.printf("%s %s, %s%n", i.getStudent().getIme(), i.getStudent().getPrezime(), i.getPredmet().getNaziv()));
                    }

                    for (Student student : sveuciliste.dohvatiObrazovnuUstanovu(k).getStudenti()) {

                        if(student.getMogucnostIzlaskaNaZavrsniRad()){
                        student.setOcjenaZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste Ocjena Zavrsnog Rada : " + student.getOcjenaZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        student.setOcjenaObraneZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaObraneZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste Ocjena Obrane Zavrsnog Rada : " + student.getOcjenaObraneZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        System.out.println("konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + ": "
                                + ((FakultetRacunarstva) sveuciliste.dohvatiObrazovnuUstanovu(k))
                                .izracunajKonacnuOcjenuStudijaZaStudenta(((FakultetRacunarstva) sveuciliste.dohvatiObrazovnuUstanovu(k))
                                        .filtrirajIspitePoStudentu(sveuciliste.dohvatiObrazovnuUstanovu(k).getIspiti(), student), student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada()));
                        }else{
                            student.setOcjenaObraneZavrsnogRada(1);
                            student.setOcjenaZavrsnogRada(1);
                        }
                    }
                    Student najboljiStudentFakultet = ((FakultetRacunarstva) sveuciliste.dohvatiObrazovnuUstanovu(k)).odrediNajuspjesnijegStudentaNaGodini(2022);
                    System.out.println("Najbolji student 2022. godine je " + najboljiStudentFakultet.getIme() + " " + najboljiStudentFakultet.getPrezime() + " JMBAG: " + najboljiStudentFakultet.getJmbag());
                    Student studentSaRektorovomNagradom = new Student();

                    try {
                        studentSaRektorovomNagradom = ((FakultetRacunarstva) sveuciliste.dohvatiObrazovnuUstanovu(k)).odrediStudentaZaRektorovuNagradu();
                    } catch (PostojiViseNajmladjihStudenataException e) {
                        logger.warn(e.getMessage());
                        System.out.println(e.getMessage());
                        return;
                    }
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + studentSaRektorovomNagradom.getIme() + " " + studentSaRektorovomNagradom.getPrezime() + " JMBAG: " + studentSaRektorovomNagradom.getJmbag());
                }
                default -> System.out.println("Unesen je krivi broj obrazovne ustanove");
            }

        }
        var sortiraneUstanove = sveuciliste
                .getObrazovneUstanove()
                .stream()
                .sorted((a, b) -> {
                    int nStudentComp = b.getStudenti().size() - a.getStudenti().size();
                    return nStudentComp != 0 ? nStudentComp : a.getNaziv().compareTo(b.getNaziv());
                })
                .toList();

        System.out.printf("%nBroj studenata:%n");
        for (var o : sortiraneUstanove) {
            System.out.printf("%s: %d%n", o.getNaziv(), o.getStudenti().size());
        }
    }
}
