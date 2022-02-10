package co.com.pragma.model.image;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Image {
    private String id;
    private String filename;
    private String contentType;
    private String content;
}
