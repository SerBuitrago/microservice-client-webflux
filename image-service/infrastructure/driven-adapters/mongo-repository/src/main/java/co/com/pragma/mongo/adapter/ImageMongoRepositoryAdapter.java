package co.com.pragma.mongo.adapter;

import co.com.pragma.model.image.Image;
import co.com.pragma.mongo.document.ImageDocument;
import co.com.pragma.mongo.helper.AdapterOperations;
import co.com.pragma.mongo.repository.ImageMongoDBRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ImageMongoRepositoryAdapter
        extends AdapterOperations<Image, ImageDocument, String, ImageMongoDBRepository>{

    public ImageMongoRepositoryAdapter(ImageMongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Image.class));
    }
}
