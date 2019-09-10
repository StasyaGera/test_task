package ru.od;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.od.model.MainEntity;
import ru.od.model.SubEntity;
import ru.od.repository.MainEntityRepository;

@SpringBootApplication
public class Application {
    @Bean
    public CommandLineRunner loadData(MainEntityRepository mainEntityRepository) {
        return (args) -> {
            for (int a = 0; a < 1000; a++) {
                MainEntity mainEntity = new MainEntity(
                        String.format("Entity name of %d", Math.round(Long.MAX_VALUE * Math.random())));
                for (int i = 0; i < 10; i++) {
                    mainEntity.getSubEntities().add(new SubEntity(" i " + i));
                }
                mainEntityRepository.save(mainEntity);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
