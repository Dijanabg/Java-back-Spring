package com.iktpreobuka.uploadexample.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iktpreobuka.uploadexample.entity.UserEntity;
import com.iktpreobuka.uploadexample.repositories.UserRepository;

@Service
public class FileHandlerImpl implements FileHandler{
	@Autowired
	public UserRepository userRepository;
	
	private static String UPLOADED_FOLDER = "C:\\temp\\";

//	@Override
//	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
//		
//			if (file.isEmpty()) {
//			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//			return "redirect:uploadStatus";
//			}
//			try {
//			// Get the file and save it somewhere
//			byte[] bytes = file.getBytes();
//			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//			Files.write(path, bytes);
//			redirectAttributes.addFlashAttribute("message",
//			"You successfully uploaded '" + file.getOriginalFilename() + "'");
//			} catch (IOException e) {
//			throw e;
//			}
//			return "redirect:/uploadStatus";
//	}
	
	 public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes)
	            throws IOException {
	        if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            return "redirect:/uploadStatus";
	        }

	        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] tokens = line.split(",");
	                if (tokens.length == 2) {
	                    String name = tokens[0];
	                    String email = tokens[1];

	                    // Provera da li email već postoji u bazi
	                    boolean emailExists = userRepository.findByEmail(email).isPresent(); // Pretpostavka da postoji metoda findByEmail

	                    // Ako email ne postoji, kreiraj novog korisnika i sačuvaj u bazi
	                    if (!emailExists) {
	                        UserEntity user = new UserEntity(name, email);
	                        userRepository.save(user);
	                    }
	                }
	            }
	            redirectAttributes.addFlashAttribute("message", "File uploaded successfully.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("message", "Error during file upload.");
	        }
	            return "redirect:/uploadStatus";
	  }
}
