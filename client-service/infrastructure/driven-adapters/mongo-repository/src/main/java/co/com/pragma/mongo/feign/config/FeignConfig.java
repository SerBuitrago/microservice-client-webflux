package co.com.pragma.mongo.feign.config;

public class FeignConfig {

    public static final String IMAGE_SERVICE = "image-service";

    public static final String IMAGE_ENDPOINT = "/api/image";
    public static final String IMAGE_ENDPOINT_FIND_BY_ID = "/api/image/{id}";
    public static final String IMAGE_ENDPOINT_SAVE = "/api/image";
    public static final String IMAGE_ENDPOINT_DELETE_BY_ID = "/api/image/{id}";
}
