package co.com.pragma.models.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "image")
public class ImageDocument {

    @Id
    private String id;
    private String filename;
    private String contentType;
    private String content;
}