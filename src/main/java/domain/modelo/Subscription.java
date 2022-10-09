package domain.modelo;

import domain.type_adapters.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@XmlRootElement(name = "subscriptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subscription {

    private int id;

    @XmlElement(name = "id_reader")
    private int idReader;

    @XmlElement(name = "id_newspaper")
    private int idNewspaper;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate signingDate;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate cancellationDate;

}
