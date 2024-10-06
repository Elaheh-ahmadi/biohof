package com.ahmadi;

import com.ahmadi.exception.InvalidName;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.time.Year;
import java.util.ArrayList;

public class Biohof {
    private String hofName;
    private ArrayList<Tier> tiere;
    private int maxAnzahl;

    public Biohof(int maxAnzahl, String hofName) throws InvalidName, IllegalArgumentException {
        if (hofName == null || hofName.isEmpty()) {
            throw new InvalidName("Fehler: Name darf nicht null sein!");
        }

        if (maxAnzahl <= 0) {
            throw new IllegalArgumentException("Fehler:maxAnzahl muss großer als null sein!");
        }

        this.hofName = hofName;
        this.maxAnzahl = maxAnzahl;
        this.tiere = new ArrayList<>();
    }

    public boolean add(Tier t) {
        if (this.tiere.size() == this.maxAnzahl) {
            return false;
        }

        if (this.tiere.contains(t)) {
            return false;
        }

        Biohof b = t.getBiohof();
        if (b == this) {
            return false;
        }

        if (!b.remove(t)) {
            return false;
        }

        t.setBiohof(this);
        return true;
    }

    public boolean remove(Tier t) {
        if (!this.tiere.contains(t)) {
            return false;
        }

        t.setBiohof(null);
        return true;
    }

    public double avgAge() {
        int sum = 0;
        for (Tier t : this.tiere) {
            int alter = Year.now().getValue() - t.getGeburstjahr().getValue();
            sum += alter;
        }
        return (double) sum / this.tiere.size();
    }

    public double medianAge() {
        int n = this.tiere.size();
        int now = Year.now().getValue();
        Tier[] tiere = this.getSorted();

        if (n % 2 == 0) {
            Tier t1 = tiere[n / 2 - 1];
            Tier t2 = tiere[n / 2];

            int alter1 = now - t1.getGeburstjahr().getValue();
            int alter2 = now - t2.getGeburstjahr().getValue();

            return (alter1 + alter2) / 2.0;
        } else {
            Tier t = tiere[n / 2];

            return now - t.getGeburstjahr().getValue();
        }
    }

    public int totalWeight() {
        int sum = 0;
        for (Tier t : this.tiere) {
            sum += t.getGewicht();
        }
        return sum;
    }

    public Tier oldestTier() throws Exception {
        if (this.tiere.isEmpty()) {
            throw new Exception("Invalid tiere size");
        }

        Tier oldest = this.tiere.get(0);
        int now = Year.now().getValue();
        int max = now - oldest.getGeburstjahr().getValue();

        for (Tier t : this.tiere) {
            int alter = now - t.getGeburstjahr().getValue();

            if (alter > max) {
                oldest = t;
                max = alter;
            }
        }

        return oldest;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.format("%s's Biohof\n", this.hofName));
        s.append("=".repeat(Math.max(0, s.length())));
        s.append("\n");
        for(Tier t: this.tiere) {
            s.append(t.toString());
            s.append("\n");
        }

        return s.toString();
    }

    private Tier[] getSorted() {
        int n = this.tiere.size();
        int now = Year.now().getValue();
        Tier[] tiere = new Tier[n];

        for (int i = 0; i < n; i++) {
            tiere[i] = this.tiere.get(i);
        }

        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                int alter1 = now - tiere[j].getGeburstjahr().getValue();
                int alter2 = now - tiere[j + 1].getGeburstjahr().getValue();

                if (alter1 > alter2) {
                    Tier temp = tiere[j];
                    tiere[j] = tiere[j + 1];
                    tiere[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        return tiere;
    }
}
