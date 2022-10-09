package domain.modelo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Newspaper {
    private int id;
    private String name;
    private LocalDate releaseDate;

    public Newspaper(String fileLine) {
        String[] parts = fileLine.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.releaseDate = LocalDate.parse(parts[2]);
    }

    public String toFileLine() {
        return this.id + ";" + this.name + ";" + this.releaseDate + "\n";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
