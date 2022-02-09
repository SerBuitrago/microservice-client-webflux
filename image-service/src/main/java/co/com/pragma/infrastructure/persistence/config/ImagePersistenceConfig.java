package co.com.pragma.infrastructure.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@EnableMongoRepositories(
	    basePackages = "co.com.pragma.infrastructure.persistence.repository")
@NoArgsConstructor
@Getter
@Setter
@EnableMongoAuditing
@EntityScan(basePackages = "co.com.pragma.infrastructure.persistence.document")
public class ImagePersistenceConfig {

}
