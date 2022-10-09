package domain.modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "readArticles")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleRating {

    private int id;

    @XmlElement(name = "id_reader")
    private int idReader;

    @XmlElement(name = "id_article")
    private int idArticle;

    private int rating;

}