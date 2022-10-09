package domain.modelo;

import domain.type_adapters.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "reader")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reader {

    private int id;

    private String name;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate dateOfBirth;

    @XmlElementWrapper(name = "subscriptions")
    @XmlElement(name = "subscription")
    private List<Subscription> subscriptions;

    @XmlElementWrapper(name = "readArticles")
    @XmlElement(name = "readArticle")
    private List<ArticleRating> articleRatings;

}
