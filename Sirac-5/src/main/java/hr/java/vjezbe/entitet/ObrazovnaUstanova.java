package hr.java.vjezbe.entitet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bazna klasa za obrazovne ustanove.
 */

public abstract class ObrazovnaUstanova {
    private String naziv;
    private List<Predmet> Predmeti;
    private List<Profesor> Profesori;
    private List<Student> Studenti;
    private List<Ispit> Ispiti;

    public ObrazovnaUstanova(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        this.naziv = naziv;
        Predmeti = predmeti;
        Profesori = profesori;
        Studenti = studenti;
        Ispiti = ispiti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Predmet> getPredmeti() {
        return Predmeti;
    }

    public void setPredmeti(List<Predmet> predmeti) {
        Predmeti = predmeti;
    }

    public List<Profesor> getProfesori() {
        return Profesori;
    }

    public void setProfesori(List<Profesor> profesori) {
        Profesori = profesori;
    }

    public List<Student> getStudenti() {
        return Studenti;
    }

    public void setStudenti(List<Student> studenti) {
        Studenti = studenti;
    }

    public List<Ispit> getIspiti() {
        return Ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
        Ispiti = ispiti;
    }


    /**
     * Pronalazi najuspješnijeg studenta na godini.
     * @param godina Godina za pretraživanje.
     * @return Najuspješniji student.
     */
    abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

    public List<Ispit> ispitiSOcjenomIzvrstan() {
        return Ispiti.stream().filter(i -> i.getOcjena() == Ocjena.IZVRSTAN).collect(Collectors.toList());
    }

}
