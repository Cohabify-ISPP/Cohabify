package org.ispp4.cohabify.loader;

import java.util.Arrays;
import java.util.List;

import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.tag.TagRepository;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private TagRepository tagRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Carga el archivo JSON como un recurso
        Resource resource = new ClassPathResource("data/initial_data.json");

        // Lee los datos del archivo JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(resource.getInputStream());

        userRepository.deleteAll();
        tagRepository.deleteAll();
        
        // Procesa las etiquetas
        JsonNode tagsNode = rootNode.get("tags");
        if (tagsNode != null) {
            List<Tag> tagsToInsert = Arrays.asList(objectMapper.readValue(tagsNode.toString(), Tag[].class));
            tagRepository.saveAll(tagsToInsert);
            System.out.println(tagsToInsert.size() + " etiquetas insertadas correctamente.");
        }
        
        // Procesa los usuarios
        JsonNode usersNode = rootNode.get("users");
        if (usersNode != null) {
            List<User> usersToInsert = Arrays.asList(objectMapper.readValue(usersNode.toString(), User[].class));
            userRepository.saveAll(usersToInsert);
            System.out.println(usersToInsert.size() + " usuarios insertados correctamente.");
        }
        
    }
}


