package co.com.pragma.model.image;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Class Image")
class ImageTest {

    private Image image;

    @BeforeEach
    void before(){
        image = Image.builder()
                        .id("123456789")
                        .filename("image.jpg")
                        .contentType("image/jpg")
                        .content("9387DSDKDS88SDJDSJDYDS732")
                        .build();
    }

    @Tag("id")
    @ParameterizedTest(name = "Test {index} image id: {0}.")
    @ValueSource(strings = { "987654321", "567894321", "000000002", "123459876" })
    @DisplayName("Test Id.")
    void id(String id){
        image.setId(id);

        String expected = id;
        String actual = image.getId();

        assertEquals(expected, actual);
    }

    @Tag("image")
    @ParameterizedTest(name = "Test {index} image filename: {0}.")
    @ValueSource(strings = { "image.png", "image.jpg", "image.jpeg", "image.csv" })
    @DisplayName("Test Filename.")
    void filename(String filename){
        image.setFilename(filename);

        String expected = filename;
        String actual = image.getFilename();

        assertEquals(expected, actual);
    }

    @Tag("contentType")
    @ParameterizedTest(name = "Test {index} image content type: {0}.")
    @ValueSource(strings = { "image", "png", "jpeg", "csv" })
    @DisplayName("Test Content Type.")
    void contentType(String contentType){
        image.setContentType(contentType);

        String expected = contentType;
        String actual = image.getContentType();

        assertEquals(expected, actual);
    }

    @Tag("content")
    @ParameterizedTest(name = "Test {index} image content: {0}.")
    @ValueSource(strings = { "123456789", "ASDFGHJKK", "098765432", "A1A2A3A4A5" })
    @DisplayName("Test Content.")
    void content(String content){
        image.setContent(content);

        String expected = content;
        String actual = image.getContent();

        assertEquals(expected, actual);
    }
}