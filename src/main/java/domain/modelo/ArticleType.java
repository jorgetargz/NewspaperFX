package domain.modelo;

import lombok.Data;

@Data
public class ArticleType {
    private int id;
    private String description;

    public ArticleType(String fileLine) {
        String[] parts = fileLine.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.description = parts[1];
    }

    public String toFileLine() {
        return this.id + ";" + this.description + "\n";
    }

    @Override
    public String toString() {
        return this.description;
    }
}
