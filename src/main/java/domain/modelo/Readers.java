package domain.modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "readers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Readers {

    @XmlElement(name = "reader")
    List<Reader> readers;

}
