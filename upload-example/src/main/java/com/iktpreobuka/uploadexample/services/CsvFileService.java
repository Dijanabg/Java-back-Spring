package com.iktpreobuka.uploadexample.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.uploadexample.entity.UserEntity;
import com.iktpreobuka.uploadexample.repositories.UserRepository;

@Service
public class CsvFileService {
	@Autowired
    private UserRepository userRepository;

    public String createCsvFile() throws IOException {
    	List<UserEntity> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        String fileName = "users.csv";
        FileWriter fileWriter = new FileWriter(fileName);

        // Dodavanje zaglavlja kolona u CSV
        fileWriter.append("ID,Name,Email,City,Costs\n");

        // Dodavanje podataka o svakom korisniku
        for (UserEntity user : users) {
            fileWriter.append(String.join(",", 
                String.valueOf(user.getId()), 
                user.getName(), 
                user.getEmail(), 
                user.getCity(), 
                String.valueOf(user.getCosts())
            ));
            fileWriter.append("\n");
        }

        fileWriter.flush();
        fileWriter.close();

        return fileName;
    }
}
