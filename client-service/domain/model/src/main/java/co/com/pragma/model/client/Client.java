package co.com.pragma.model.client;

import co.com.pragma.model.TypeDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String id;
    private String name;
    private String lastName;
    private Long document;
    private TypeDocument typeDocument;
    private Integer age;
    private String birthCity;
    private String idImage;
}
