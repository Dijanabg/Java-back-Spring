package com.iktpreobuka.uploadexample.contollers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.iktpreobuka.uploadexample.services.FileHandler;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(path = "/")
public class UploadController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileHandler fileHandler;
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		logger.debug("This is a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is an error message");
		String result = null;
		try {
			result = fileHandler.singleFileUpload(file, redirectAttributes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	
}