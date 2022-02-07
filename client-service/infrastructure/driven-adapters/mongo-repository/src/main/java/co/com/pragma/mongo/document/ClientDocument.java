package co.com.pragma.mongo.document;

import co.com.pragma.model.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client")
public class ClientDocument {

    @Id
    private String id;

    private String name;
    private String lastName;
    private Long document;
    private TypeDocument typeDocument;
    private Integer age;
    private String birthCity;
}
