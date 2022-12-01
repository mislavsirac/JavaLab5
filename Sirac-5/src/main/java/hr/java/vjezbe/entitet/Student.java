package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Student na tercijarnoj obrazovnoj ustanovi.
 */

public class Student extends Osoba{
    private String ime;
    private String prezime;
    private String jmbag;
    private LocalDate datumRodjenja;
    private Integer ocjenaZavrsnogRada;
    private Integer ocjenaObraneZavrsnogRada;
    private Boolean mogucnostIzlaskaNaZavrsniRad;

    public void setMogucnostIzlaskaNaZavrsniRad(Boolean mogucnostIzlaskaNaZavrsniRad) {
        this.mogucnostIzlaskaNaZavrsniRad = mogucnostIzlaskaNaZavrsniRad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(getIme(), student.getIme()) && Objects.equals(getPrezime(), student.getPrezime()) && Objects.equals(getJmbag(), student.getJmbag()) && Objects.equals(getDatumRodjenja(), student.getDatumRodjenja()) && Objects.equals(getOcjenaZavrsnogRada(), student.getOcjenaZavrsnogRada()) && Objects.equals(getOcjenaObraneZavrsnogRada(), student.getOcjenaObraneZavrsnogRada()) && Objects.equals(getMogucnostIzlaskaNaZavrsniRad(), student.getMogucnostIzlaskaNaZavrsniRad());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIme(), getPrezime(), getJmbag(), getDatumRodjenja(), getOcjenaZavrsnogRada(), getOcjenaObraneZavrsnogRada(), getMogucnostIzlaskaNaZavrsniRad());
    }

    public Boolean getMogucnostIzlaskaNaZavrsniRad() {
        return mogucnostIzlaskaNaZavrsniRad;
    }

    public Student() {
        super();
    }

    public Integer getOcjenaZavrsnogRada() {
        return ocjenaZavrsnogRada;
    }

    public void setOcjenaZavrsnogRada(Integer ocjenaZavrsnogRada) {
        this.ocjenaZavrsnogRada = ocjenaZavrsnogRada;
    }

    public Integer getOcjenaObraneZavrsnogRada() {
        return ocjenaObraneZavrsnogRada;
    }

    public void setOcjenaObraneZavrsnogRada(Integer ocjenaObraneZavrsnogRada) {
        this.ocjenaObraneZavrsnogRada = ocjenaObraneZavrsnogRada;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }
}
