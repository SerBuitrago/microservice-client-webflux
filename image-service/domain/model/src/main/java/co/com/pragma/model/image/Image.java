package co.com.pragma.model.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Image {
    private String id;
    private String filename;
    private String contentType;
    private String content;
}
