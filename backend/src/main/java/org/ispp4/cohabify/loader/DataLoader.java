package org.ispp4.cohabify.loader;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Carga el archivo JSON como un recurso
        Resource resource = new ClassPathResource("data/initial_data.json");

        // Lee los datos del archivo JSON
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> usersToInsert = Arrays.asList(objectMapper.readValue(resource.getInputStream(), User[].class));

        // Elimina todos los datos existentes
        userRepository.deleteAll();

        // Inserta los nuevos usuarios en la base de datos
        userRepository.saveAll(usersToInsert);
        
        System.out.println(usersToInsert.size() + " usuarios insertados correctamente: "+usersToInsert.get(0).getUsername()+" y "+usersToInsert.get(1).getUsername());
    }
}


