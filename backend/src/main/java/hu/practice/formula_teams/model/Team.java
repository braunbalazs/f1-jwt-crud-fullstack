package hu.practice.formula_teams.model;

import jakarta.persistence.*;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_gen")
    @SequenceGenerator(name = "team_gen", sequenceName = "team_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer yearEstablished;
    private Integer championshipsWon;
    private Boolean regFeePaid;

    public Team() {
    }

    public Team(String name, Integer yearEstablished, Integer championshipsWon, Boolean regFeePaid) {
        this.name = name;
        this.yearEstablished = yearEstablished;
        this.championshipsWon = championshipsWon;
        this.regFeePaid = regFeePaid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(Integer yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public Integer getChampionshipsWon() {
        return championshipsWon;
    }

    public void setChampionshipsWon(Integer championshipsWon) {
        this.championshipsWon = championshipsWon;
    }

    public Boolean getRegFeePaid() {
        return regFeePaid;
    }

    public void setRegFeePaid(Boolean regFeePaid) {
        this.regFeePaid = regFeePaid;
    }
}
