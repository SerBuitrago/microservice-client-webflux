package co.com.pragma.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private String id;
    private String filename;
    private String contentType;
    private String content;
}