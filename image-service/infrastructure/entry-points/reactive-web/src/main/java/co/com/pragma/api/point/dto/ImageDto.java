package co.com.pragma.api.point.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private String id;
    private String filename;
    private String contentType;
    private String content;
}
