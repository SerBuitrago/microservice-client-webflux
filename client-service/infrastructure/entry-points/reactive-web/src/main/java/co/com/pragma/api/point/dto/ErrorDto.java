package co.com.pragma.api.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {
    private String message;
    private Integer code;
    private Integer lineNumber;
    private String fileName;
    private String methodName;
    private String className;
}