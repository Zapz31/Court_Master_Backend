package com.swp391.Court_Master.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.Court_Master.Entities.BadmintonClubImage;
import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.BookingSchedule;
import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.BookingRepository;
import com.swp391.Court_Master.Repository.ClubRepository;
import com.swp391.Court_Master.Repository.CourtManagerRepository;
import com.swp391.Court_Master.Repository.DetailClubRepository;
import com.swp391.Court_Master.Repository.StaffRepository;
import com.swp391.Court_Master.Service.BookingService;
import com.swp391.Court_Master.Service.ClubRegisterService;
import com.swp391.Court_Master.Service.DetailPageService;
import com.swp391.Court_Master.Service.StaffService;
import com.swp391.Court_Master.dto.request.Request.BookingSlotRequest;
import com.swp391.Court_Master.dto.request.Request.ClubRegisterDTO;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/test")
public class testAPI {

    @Autowired
    private StaffService staffService;

    @Autowired
    private DetailClubRepository detailClubRepository;

    @Autowired
    private DetailPageService detailPageService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private CourtManagerRepository courtManagerRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubRegisterService clubRegisterService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/all")
    public String allAccess() {
        return "Hahahahahahahahaa";
    }

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('USER_CUSTOMER')") /* or hasAuthority('USER_COURT_MANAGER') */
    public String getMethodName() {
        return " chi co ta moi la dang chi ton";
    }

    @GetMapping("/courtmanager")
    @PreAuthorize("hasAuthority('USER_COURT_MANAGER')")
    public String getCourtManagerAccess() {
        return "tai sao phai lam vua";
    }

    @GetMapping("/images/{clubId}")
    public ResponseEntity<List<ImageResponseDTO>> getMethodName(@PathVariable("clubId") String clubId) {
        List<ImageResponseDTO> list = detailClubRepository.getAllImageName(clubId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/timeprices/{clubId}")
    public ResponseEntity<List<TimeFramePricingServiceDTO>> getTimePrices(@PathVariable("clubId") String clubId) {
        List<TimeFramePricingServiceDTO> list = detailPageService.getTimeFramePricingService(clubId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/test3/{clubId}")
    public ResponseEntity<List<TimeFrame>> getAll(@PathVariable("clubId") String clubId) {
        List<TimeFrame> list = detailPageService.getTimeFrames(clubId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/test/gettfbycourt")
    public ResponseEntity<List<TimeFramePricingServiceDTO>> getAll(@RequestBody BookingSlotRequest bookingSlotRequest) {
        List<TimeFramePricingServiceDTO> list = bookingRepository.getClubTimeFramePricingByCourtId(
                bookingSlotRequest.getCourtId(), bookingSlotRequest.getStartBooking(),
                bookingSlotRequest.getEndBooking());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/getpriceperslot")
    public ResponseEntity<Integer> getAll(@RequestBody PricePerSlotRequestDTO pricePerSlotRequestDTO) {
        Integer price = bookingService.getPricePerSlot(pricePerSlotRequestDTO.getCourtId(),
                pricePerSlotRequestDTO.getStartBooking(), pricePerSlotRequestDTO.getEndBooking(),
                pricePerSlotRequestDTO.getBookingDate(), pricePerSlotRequestDTO.getBookingType());
        return ResponseEntity.ok().body(price);
    }

    // Test tinhs tong tien tat ca cac slot
    @GetMapping("/getpriceallslot")
    public ResponseEntity<Integer> getPriceAllSlot(
            @RequestBody List<PricePerSlotRequestDTO> pricePerSlotRequestDTOList) {
        Integer price = bookingService.getAllBookingSLotTotalPrice(pricePerSlotRequestDTOList);
        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/getTotalPlayTime")
    public ResponseEntity<String> getTotalHoursAllSlot(
            @RequestBody List<PricePerSlotRequestDTO> pricePerSlotRequestDTOList) {
        String playTimeTotal = bookingService.getTotalHoursAllSlot(pricePerSlotRequestDTOList);
        return ResponseEntity.ok().body(playTimeTotal);
    }

    /*
     * Ham goc dung de luu hinh anh vao file hinh anh
     */
    // @PostMapping("/uploadclubimage")
    // public ResponseEntity<String> uploadClubImages(@RequestParam("image") MultipartFile file,
    //         @RequestParam("isMainAvatar") int isMainAvatar, @RequestParam("clubId") String clubId) {
    //     File clubImages = new File("club-image");
    //     String clubImagesAbsolutePath = clubImages.getAbsolutePath() + "/";
    //     try {
    //         // Lưu tệp ảnh vào thư mục đã định nghĩa
    //         String fileName = file.getOriginalFilename();
    //         Path path = Paths.get(clubImagesAbsolutePath, fileName);
    //         Files.write(path, file.getBytes());

    //         // Lưu tên ảnh và định dạng vào cơ sở dữ liệu
    //         String result = clubRepository.saveImageNameToDB(fileName, isMainAvatar, clubId);

    //         return ResponseEntity.ok(result);
    //     } catch (IOException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Lỗi khi tải lên ảnh: " + e.getMessage());
    //     }
    // }

    @PostMapping("/getBookedListByCourtId")
    public ResponseEntity<List<BookedDTO>> getBookedListByCourtId(
            @RequestBody List<BookingSlotResponseDTO> pricePerSlotRequestDTOList) {
                List<BookedDTO> list = bookingRepository.getBookedList(pricePerSlotRequestDTOList);
        return ResponseEntity.ok().body(list);
    }


    @GetMapping("/get-timeframe-by-{clubId}")
    public ResponseEntity<List<TimeFrame>> getAllTimeFrameByCourtId(@PathVariable("clubId") String clubId) {
        List<TimeFrame> list = bookingRepository.getTimeFrameByClubId(clubId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-bookedlist-{clubId}")
    public ResponseEntity<List<BookedDTO>> getAllBookedSlotByClubId(@PathVariable("clubId") String clubId) {
        List<BookedDTO> list = bookingRepository.getAllBookingSlotByClubId(clubId);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/insert-booking-schedule")
    public ResponseEntity<String> postMethodName(@RequestBody BookingSchedule entity) {
        
        String bookingScheduleId = bookingRepository.insertBookingSchedule(entity);
        return ResponseEntity.ok().body(bookingScheduleId);
    }
    
    @PostMapping("/insert-booking-slots")
    public ResponseEntity<String> insertBookingSlots(@RequestBody BookingSchedule entity) {
        
        bookingRepository.insertBookingSlots(entity.getBookingSlotResponseDTOs(), "SD0000003");
        return ResponseEntity.ok().body("insert successfully");
    }


    @PostMapping("/staffAvailability")
    //Ham nay gui 4 tham so va yeu cau tra ve danh sach cac slot nam trong khoang thoi gian cua court
    public ResponseEntity<String> checkAvailability(@RequestBody BookedDTO Entity){
        //Con phai sua
        staffRepository.bookedCourts("C0000001", LocalDate.of(2024, 7, 9),LocalTime.of(18, 0,0),LocalTime.of(23, 0, 0));
        return ResponseEntity.ok().body("check availability successfully");

    }


    // Lay nhung booking duoc yeu cau dat nhung chua thanh toan ma bi danh dau la trung voi cac booking da co
    @PostMapping("/get-duplicate-bookingslot-request")
    public ResponseEntity<List<BookingSlotResponseDTO>> getDuplicateBookingSlotRequest(@RequestBody List<BookingSlotResponseDTO> pricePerSlotRequestDTOs) {       
        return ResponseEntity.ok().body(bookingService.getDupBookingSlotRequest(pricePerSlotRequestDTOs, 0));
    }

    @PostMapping("/getDayAllowChange")
    public ResponseEntity<HashMap<String, List<LocalDate>>> getAllowDays(@RequestBody BookingSlotResponseDTO entity) {
        //TODO: process POST request
        List<LocalDate> allowDays = bookingService.generateDatesWithinMonth(entity.getBookingDate());
        HashMap<String, List<LocalDate>> result = new HashMap<>();
        result.put("allowdays", allowDays);
        return ResponseEntity.ok().body(result);
    }

    
    


    
  
}
    