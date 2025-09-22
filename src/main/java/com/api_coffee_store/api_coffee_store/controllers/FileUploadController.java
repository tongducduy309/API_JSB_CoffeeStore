package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/v1/files")
public class FileUploadController {

    @Autowired
    private IStorageService storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(SuccessCode.REQUEST.getStatus(), "Upload File Successfully",generatedFileName)
            );
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(400,exception.getMessage(),"")
            );
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) throws APIException {
        try{

            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            throw new APIException(ErrorCode.CANNOT_READ_IMAGE);

        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploadedFiles(){
        try{
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile",path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject(200,"List Files Successfully",urls));
        } catch (Exception e) {
            return  ResponseEntity.ok(
                    new ResponseObject(400,"List files failed",new String[] {})
            );
        }
    }




}
