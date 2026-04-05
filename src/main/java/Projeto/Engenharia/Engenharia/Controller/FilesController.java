package Projeto.Engenharia.Engenharia.Controller;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import Projeto.Engenharia.Engenharia.Entity.FileInfo;
import Projeto.Engenharia.Engenharia.Service.FilesStorageService;
import Projeto.Engenharia.Engenharia.Service.FileStorageServiceImpl;

@Controller
@CrossOrigin("*")
public class FilesController {
	
	 @Autowired
	  FilesStorageService storageService;
	 
	 
	 @Value("${aws.bucketName}")
	    private String bucketName;

	    @Value("${aws.region}")
	    private String region;

	  @PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	      storageService.save(file);

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	  }
	  @DeleteMapping("/files/{filename:.+}")
	  public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String filename) {
	    String message = "";
	    
	    try {
	      boolean existed = storageService.delete(filename);
	      
	      if (existed) {
	        message = "Delete the file successfully: " + filename;
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      }
	      
	      message = "The file does not exist!";
	      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
	    }
	  }

	  
	  

	  @GetMapping("/files")
	  public ResponseEntity<List<FileInfo>> getListFiles() {
	    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
	      String filename = path.getFileName().toString();
          String url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + filename;


	      return new FileInfo(filename, url);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	  }

	  @GetMapping("/files/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
	    Resource file = storageService.load(filename);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }

}
