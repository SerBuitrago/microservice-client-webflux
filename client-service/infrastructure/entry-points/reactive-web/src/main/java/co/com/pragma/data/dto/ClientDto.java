package co.com.pragma.data.dto;

import co.com.pragma.model.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private String id;

    @NotEmpty(message = "El campo nombre es obligatorio.")
    private String name;

    @NotEmpty(message = "El campo apellido es obligatorio.")
    private String lastName;

    @NotNull(message = "El campo documento no puede estar vacio.")
    private Long document;

    @NotNull(message = "El campo tipo documento no puede estar vacio.")
    private TypeDocument typeDocument;

    @NotNull(message = "El campo edad no puede estar vacio.")
    private Integer age;

    @NotEmpty(message = "El campo ciudad de nacimiento es obligatorio.")
    private String birthCity;
}
