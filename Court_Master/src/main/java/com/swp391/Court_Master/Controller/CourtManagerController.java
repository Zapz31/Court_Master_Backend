package com.swp391.Court_Master.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.Court_Master.Entities.BadmintonClubImage;
import com.swp391.Court_Master.Service.ClubRegisterService;
import com.swp391.Court_Master.dto.request.Request.ClubRegisterDTO;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;

@RestController
@RequestMapping("/courtmanager")
public class CourtManagerController {

    @Autowired
    private ClubRegisterService clubRegisterService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/register-club")
    public ResponseEntity<MessageResponse> registerClub(@RequestPart("clubData") String clubDataJson,
                                 @RequestParam("images") List<MultipartFile> images,
                                 @RequestParam("avatar") MultipartFile avatar) throws IOException{
        
        String decodedBody = URLDecoder.decode(clubDataJson, "UTF-8");
        
        ClubRegisterDTO clubRegisterDTO = objectMapper.readValue(decodedBody, ClubRegisterDTO.class);

        MessageResponse mess = null;
        // Kiem tra xem court manager do co club nao truoc do chua. Neu co thi ko cho dang ky
        List<BadmintonClubImage> imageList = new ArrayList<>();
        BadmintonClubImage avatarImage = new BadmintonClubImage(avatar.getOriginalFilename());
        if(!images.isEmpty() || images != null){
            for(int i = 0; i < images.size(); i++){
                MultipartFile image = images.get(i);
                String imageName = image.getOriginalFilename();
                imageList.add( new BadmintonClubImage(imageName));
                uploadClubImages(image);
            }
        }
        if(avatar != null || !avatar.isEmpty()){
            uploadClubImages(avatar);
        }
        mess = clubRegisterService.isRegisterClub(clubRegisterDTO, imageList, avatarImage);
        
        return ResponseEntity.ok().body(mess);
    }


    public void uploadClubImages(MultipartFile file) {
        File clubImages = new File("club-image");
        String clubImagesAbsolutePath = clubImages.getAbsolutePath() + "/";
        try {
            // Lưu tệp ảnh vào thư mục đã định nghĩa
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(clubImagesAbsolutePath, fileName);
            Files.write(path, file.getBytes());


        } catch (IOException e) {
            System.out.println("Error at uploadClubImages in testAPI: " + e.getMessage());
        }
    }
}
