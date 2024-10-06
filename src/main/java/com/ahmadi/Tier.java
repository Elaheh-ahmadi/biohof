package com.ahmadi;

import com.ahmadi.exception.InvalidName;

import java.time.Year;
import java.util.Locale;

public class Tier {
    private String name;
    private Year geburstjahr;
    private int gewicht;
    private Biohof biohof;

    public Tier(String name, Year geburstjahr, int gewicht) throws InvalidName, IllegalArgumentException {
        setName(name);
        setGeburstjahr(geburstjahr);
        setGewicht(gewicht);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidName {
        if (name == null || name.length() < 3) {
            throw new InvalidName("Invalid Name !");
        }

        /*
        if(!name.equals(name.toUpperCase())) {
            throw new InvalidName("lasjkdlkajslkdjasd");
        }
         */

        this.name = name;
    }

    public Year getGeburstjahr() {
        return geburstjahr;
    }

    public void setGeburstjahr(Year geburstjahr) throws IllegalArgumentException {
        if (geburstjahr.isAfter(Year.now())) {
            throw new IllegalArgumentException("Invalid Year");
        }

        Year tenYearsAgo = Year.now().minusYears(10);
        if (geburstjahr.isBefore(tenYearsAgo)) {
            throw new IllegalArgumentException("Too old");
        }

        this.geburstjahr = geburstjahr;
    }

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) throws IllegalArgumentException {
        if (gewicht < 500) {
            throw new IllegalArgumentException("So leicht");
        }

        if (gewicht > 1000) {
            throw new IllegalArgumentException("Übergewicht");
        }

        this.gewicht = gewicht;

    }

    public Biohof getBiohof() {
        return biohof;
    }

    public void setBiohof(Biohof biohof) {
        this.biohof = biohof;
    }

    public String toString(){
        int alter = Year.now().getValue() - getGeburstjahr().getValue();
        // return "Name:" +name+",Gewicht="+ gewicht+",Alter=" + alter;
        return String.format("Name:%s,Gewicht=%d,Alter=%d", name, gewicht, alter);
    }

    public void print(){
        System.out.println(toString());

    }

}

