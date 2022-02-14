package co.com.pragma.api.point.dto;

import co.com.pragma.model.TypeDocument;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ClientTypeDocumentAndDocumentDto {
    @NotNull(message = "El campo documento no puede estar vacio.")
    private Long document;

    @NotNull(message = "El campo tipo documento no puede estar vacio.")
    private TypeDocument typeDocument;
}
