package com.swp391.Court_Master.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryBookingSlotMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalRevenueMapper;
import com.swp391.Court_Master.Repository.CourtManagerRepository;
import com.swp391.Court_Master.Service.ClubRegisterService;
import com.swp391.Court_Master.Service.CourtManagerService;
import com.swp391.Court_Master.dto.request.Request.ClubRegisterDTO;
import com.swp391.Court_Master.dto.request.Request.DashBoardRequest;
import com.swp391.Court_Master.dto.request.Request.SearchStaffByPhoneNameRequest;
import com.swp391.Court_Master.dto.request.Request.UpdateStaffRequest;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.CourManagerScreenView.StaffAccountDTO;
import com.swp391.Court_Master.dto.request.Respone.DashBoardResponse.TotalBookingSlotInformation;
import com.swp391.Court_Master.dto.request.Respone.DashBoardResponse.TotalCustomerInformation;
import com.swp391.Court_Master.dto.request.Respone.DashBoardResponse.TotalRevenueInformation;
import com.swp391.Court_Master.dto.request.Respone.StaffScreenView.StaffViewBookingSlotDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/courtmanager")
public class CourtManagerController {

    @Autowired
    private ClubRegisterService clubRegisterService;

    @Autowired
    private CourtManagerService courtManagerService;

    @Autowired
    private CourtManagerRepository courtManagerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/register-club")
    public ResponseEntity<MessageResponse> registerClub(@RequestPart("clubData") String clubDataJson,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("avatar") MultipartFile avatar) throws IOException {

        String decodedBody = URLDecoder.decode(clubDataJson, "UTF-8");

        ClubRegisterDTO clubRegisterDTO = objectMapper.readValue(decodedBody, ClubRegisterDTO.class);

        MessageResponse mess = null;
        // Kiem tra xem court manager do co club nao truoc do chua. Neu co thi ko cho
        // dang ky
        List<BadmintonClubImage> imageList = new ArrayList<>();
        BadmintonClubImage avatarImage = new BadmintonClubImage(avatar.getOriginalFilename());
        if (!images.isEmpty() || images != null) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);
                String imageName = image.getOriginalFilename();
                imageList.add(new BadmintonClubImage(imageName));
                uploadClubImages(image);
            }
        }
        if (avatar != null || !avatar.isEmpty()) {
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

    // View dash board here
    @PostMapping("/gettotalrevenueinfor")
    public ResponseEntity<TotalRevenueInformation> postMethodName(@RequestBody DashBoardRequest dashBoardRequest) {
        TotalRevenueInformation totalRevenueInformation = courtManagerService.getTotalRevenueInfo(dashBoardRequest);
        return ResponseEntity.ok().body(totalRevenueInformation);
    }

    @PostMapping("/gettotalbookinginfor")
    public ResponseEntity<TotalBookingSlotInformation> getTotalBS(@RequestBody DashBoardRequest dashBoardRequest) {
        TotalBookingSlotInformation totalBookingSlotInformation = courtManagerService
                .getTotalBookingSlot(dashBoardRequest);
        return ResponseEntity.ok().body(totalBookingSlotInformation);
    }

    @PostMapping("/gettotalcustomernumber")
    public ResponseEntity<TotalCustomerInformation> getTotalCusNumber(@RequestBody DashBoardRequest dashBoardRequest) {
        TotalCustomerInformation totalCustomerInformation = courtManagerService.getTotalCustomerInfo(dashBoardRequest);
        return ResponseEntity.ok().body(totalCustomerInformation);
    }

    @PostMapping("/get-daily-revenue")
    public ResponseEntity<List<QueryTotalRevenueMapper>> getDailyRevenue(
            @RequestBody DashBoardRequest dashBoardRequest) {
        List<QueryTotalRevenueMapper> list = courtManagerService.getDailyRevenue(dashBoardRequest);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/get-daily-booking")
    public ResponseEntity<List<QueryBookingSlotMapper>> getDailyBooking(
            @RequestBody DashBoardRequest dashBoardRequest) {
        List<QueryBookingSlotMapper> list = courtManagerService.getDailyBooking(dashBoardRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-clubId-by-cId")
    public ResponseEntity<HashMap<String, String>> getCLubIdByUserId(@RequestParam("userId") String courtManagerId) {
        HashMap<String, String> map = courtManagerService.getClubIdByUserId(courtManagerId);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/get-all-staff")
    public ResponseEntity<List<StaffAccountDTO>> getAllStaff(
            @RequestParam("court_manager_id") String court_manager_id) {
        List<StaffAccountDTO> list = courtManagerService.getAllStaff(court_manager_id);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/search-staff")
    public ResponseEntity<List<StaffAccountDTO>> getStaffByPhoneName(
            @RequestBody SearchStaffByPhoneNameRequest SearchStaffByPhoneNameRequest) {
        List<StaffAccountDTO> list = courtManagerService.getStaffByPhoneName(SearchStaffByPhoneNameRequest);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/update-staff")
    public String updateStaffInfo(
            @RequestBody UpdateStaffRequest UpdateStaffRequest) {
        String result = courtManagerService.updateStaffInfo(UpdateStaffRequest);
        return result;
    }

    @GetMapping("/delete-staff")
    public String deleteStaff(@RequestParam("staff_id") String staff_id,
            @RequestParam("court_manager_id") String court_manager_id) {
        return courtManagerService.deleteStaff(staff_id, court_manager_id);
    }

    @GetMapping("/get-clubId-by-mngid")
    public ResponseEntity<MessageResponse> getMethodName(@RequestParam("id") String courtManagerId) {
        return ResponseEntity.ok().body( new MessageResponse(courtManagerService.getClubIdByCourtManagerId(courtManagerId)));
    }
    
}
