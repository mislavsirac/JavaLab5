package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diplomska obrazovna ustanova za računarstvo.
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public FakultetRacunarstva(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladjihStudenataException{

        List<BigDecimal> najvecaOcjena = new ArrayList<>();

        for (int i=0 ; i< getIspiti().size(); i++){
            najvecaOcjena.add(BigDecimal.valueOf(0));
        }

        BigDecimal najvecaOcjena1 = new BigDecimal(0);
        List<Student> najboljiStudenti = new ArrayList<>();
        Student najboljiStudent = new Student();
        Integer i = 0;



        for (Student student: getStudenti()) {
            List<Ispit> ispitiOdStudenta = filtrirajIspitePoStudentu(getIspiti(), student);
            BigDecimal konacnaOcjena = izracunajKonacnuOcjenuStudijaZaStudenta(ispitiOdStudenta, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada());
            if(najvecaOcjena.get(i).compareTo(konacnaOcjena) == -1){
                najvecaOcjena1 = konacnaOcjena;
            }
            i++;
        }
        i=0;
        for (Student student: getStudenti()) {
            List<Ispit> ispitiOdStudenta = filtrirajIspitePoStudentu(getIspiti(), student);
            BigDecimal konacnaOcjena = izracunajKonacnuOcjenuStudijaZaStudenta(ispitiOdStudenta, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada());
            if(konacnaOcjena.compareTo(najvecaOcjena1) == 0){
                najvecaOcjena.add(najvecaOcjena1);
                najboljiStudenti.add(student);
            }
            i++;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        LocalDate trenutniDatum = LocalDate.parse(datum, dtf);

        for (Student student: najboljiStudenti) {
            if(student.getDatumRodjenja().isBefore(trenutniDatum)){
                najboljiStudent = student;
                trenutniDatum = student.getDatumRodjenja();
            }
        }

        List<Student> istiStudenti = new ArrayList<>();
        Integer counter = 0;
        for (Student student: najboljiStudenti) {
            if(student.getDatumRodjenja().equals(trenutniDatum)){
                istiStudenti.add(student);
                counter++;
            }
        }

        String outputMessage ="";

        for (Student student: istiStudenti) {
            outputMessage = outputMessage + " " + student.getIme();
        }


        if(counter>1){
            logger.error("Ima vise najmladih studenata koji su : " + outputMessage);
            throw new PostojiViseNajmladjihStudenataException("Ima vise najmladih studenata koji su : " + outputMessage);
        }


        return najboljiStudent;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {
        Student najboljiStudent = new Student();
        List<Ispit> ispitiOdOveGodine = new ArrayList<>();
        Integer i = 0;
        Integer brojOcjena = 0;
        for (Ispit ispit: getIspiti()) {
            if(ispit.getDatumIVrijeme().getYear() == godina){
                ispitiOdOveGodine.add(ispit);
                i++;
            }
        }

        for (Student student: getStudenti()) {
            List<Ispit> ispitiOdStudenta = filtrirajIspitePoStudentu(ispitiOdOveGodine, student);
            Integer brojPetica = 0;
            for (Ispit ispit: ispitiOdStudenta) {
                if (ispit.getOcjena().getNumerickaVrijednost() == 5){
                    brojPetica++;
                }
            }
            if(brojPetica > brojOcjena){
                brojOcjena = brojPetica;
                najboljiStudent = student;
            }
        }

        return najboljiStudent;
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Integer ocjenaPismenogDijela, Integer ocjenaObraneDiplomskogRada) {
        BigDecimal prosjekOcjena = null;
        try {
            prosjekOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            logger.warn(e.getMessage());
            System.out.println(e.getMessage());;
            BigDecimal konacnaOcjena1 = BigDecimal.valueOf(1);
            return konacnaOcjena1;
        }
        BigDecimal konacnaOcjena = (prosjekOcjena.multiply(BigDecimal.valueOf(3)).add(BigDecimal.valueOf(ocjenaPismenogDijela)).add(BigDecimal.valueOf(ocjenaObraneDiplomskogRada))).divide(BigDecimal.valueOf(5));
        return konacnaOcjena;
    }
}
