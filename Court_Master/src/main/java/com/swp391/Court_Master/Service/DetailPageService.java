package com.swp391.Court_Master.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Entities.PricingService;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.DetailClubRepository;
import com.swp391.Court_Master.dto.request.Respone.CustomerFeedback;
import com.swp391.Court_Master.dto.request.Respone.DetailPageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

@Service
public class DetailPageService {
    @Autowired
    private DetailClubRepository detailClubRepository;

   // private static final String IMAGE_CLUB_DIRECTORY = "D:/FPTU/Semester V/SWP391/courtmasterimage/club-image/";
   // private static final String IMAGE_USER_DIRECTORY = "D:/FPTU/Semester V/SWP391/courtmasterimage/user-image/";

    public List<TimeFramePricingServiceDTO> getTimeFramePricingService(String clubId) {
        List<TimeFramePricingServiceDTO> list = detailClubRepository.getClubTimeFramePricing(clubId);
        return list;
    }

    public List<TimeFrame> getTimeFrames(String clubId) {
        List<TimeFrame> timeFrames = new ArrayList<>();

        String tfMark = "";
        List<TimeFramePricingServiceDTO> timeFramePricingServiceList = detailClubRepository
                .getClubTimeFramePricing(clubId);
        int index = 0;
        Collections.sort(timeFramePricingServiceList, new Comparator<TimeFramePricingServiceDTO>() {

            @Override
            public int compare(TimeFramePricingServiceDTO o1, TimeFramePricingServiceDTO o2) {
                return o1.getTimeFrameId().compareTo(o2.getTimeFrameId());
            }

        });

        for (TimeFramePricingServiceDTO tfps : timeFramePricingServiceList) {

            if (tfMark.isEmpty() || (!tfMark.equals(tfps.getTimeFrameId()))) {
                TimeFrame tf = new TimeFrame();
                tf.setTimeFrameId(tfps.getTimeFrameId());
                tf.setStarTime(tfps.getStarTime());
                tf.setEndTime(tfps.getEndTime());
                tfMark = tfps.getTimeFrameId();
                timeFrames.add(tf);
            }
        }

        for (TimeFrame timeFrame : timeFrames) {
            List<PricingService> pricingServices = new ArrayList<>();
            for (TimeFramePricingServiceDTO tfps : timeFramePricingServiceList) {
                if (timeFrame.getTimeFrameId().equals(tfps.getTimeFrameId())) {
                    PricingService ps = new PricingService(tfps.getDayOfWeek(), tfps.getFlexible(), tfps.getFixed(),
                            tfps.getOneTimePlay());
                    pricingServices.add(ps);
                }

            }
            timeFrame.setPricingServiceList(pricingServices);

        }

        return timeFrames;
    }

    public DetailPageResponseDTO detailPageBuild(String clubId) throws IOException {
        File clubImages = new File("club-image");
        String clubImageAbsolutePath = clubImages.getAbsolutePath() + "/";  
        File userImages = new File("user-image");
        String userImageAbsolutePath = userImages.getAbsolutePath() + "/";        
        List<DetailPageResponseDTO> list = detailClubRepository.getClubInfo(clubId);
        DetailPageResponseDTO detailPageResponseDTO = list.get(0);
        List<ImageResponseDTO> listImages = detailClubRepository.getAllImageName(clubId);
        List<CustomerFeedback> feedbacks = detailClubRepository.getFeedBacks(clubId);


        //Convert image to base64
        for (ImageResponseDTO imageResponseDTO : listImages) {
            String imageFileName = imageResponseDTO.getImageName();
            File file = new File(clubImageAbsolutePath + imageFileName);
            Path path = Paths.get(file.getAbsolutePath());
            byte[] bytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            imageResponseDTO.setImageName(base64Image);

        }

        for (CustomerFeedback customerFeedback : feedbacks) {
            String imageFileName = customerFeedback.getCustomerImageBase64();
            File file = new File(userImageAbsolutePath + imageFileName);
            Path path = Paths.get(file.getAbsolutePath());
            byte[] bytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            customerFeedback.setCustomerImageBase64(base64Image);

        }

        List<TimeFrame> listTimeFrames = getTimeFrames(clubId);
        detailPageResponseDTO.setImageNames(listImages);
        detailPageResponseDTO.setTimeFrame(listTimeFrames);
        detailPageResponseDTO.setFeedbacks(feedbacks);
        return detailPageResponseDTO;
    }

}
