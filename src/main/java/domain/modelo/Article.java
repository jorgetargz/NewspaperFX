package domain.modelo;

import lombok.Data;

@Data
public class Article {
    private int id;
    private String name;
    private int articleType;
    private int newspaperId;

    public Article(String fileLine) {
        String[] parts = fileLine.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.articleType = Integer.parseInt(parts[2]);
        this.newspaperId = Integer.parseInt(parts[3]);
    }

    public Article(int id, String name, int articleType, int newspaperId) {
        this.id = id;
        this.name = name;
        this.articleType = articleType;
        this.newspaperId = newspaperId;
    }

    public String toFileLine() {
        return this.id + ";" + this.name + ";" + this.articleType + ";" + this.newspaperId + "\n";
    }

    public boolean equals(Article article) {
        if (article == null || article.getClass() != this.getClass()) {
            return false;
        }
        return this.id == article.id;
    }
}
