package com.swp391.Court_Master.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp391.Court_Master.Entities.BadmintonClubImage;
import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.PricingService;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.FunctionTest.PricingRuleDTO;
import com.swp391.Court_Master.Repository.ClubRegisterRepository;
import com.swp391.Court_Master.dto.request.Request.ClubRegisterDTO;

@Service
public class ClubRegisterService {
    @Autowired
    private ClubRegisterRepository clubRegisterRepository;

    @Transactional
    public boolean isRegisterClub(ClubRegisterDTO clubRegisterDTO, List<BadmintonClubImage> images, BadmintonClubImage mainAvatar){
        boolean isRegiter = true;
        List<TimeFrame> setTimeFrames = new ArrayList<>();
        //============================== SETUP FOREIGN KEY==========================
        // 1> Lay tat ca danh sach cac id can thiet
        List<String> addressIdList = clubRegisterRepository.getAllAddressIds();
        List<String> clubIdList = clubRegisterRepository.getAllClubIds();
        List<String> timeFrameIdList = clubRegisterRepository.getAllTimeFrameIds();

        // Tim ra id lon nhat cua moi danh sach id 
        // Viet mot ham tra ve 1 String la chuoi lon nhat
        String latestAddressId = getLatestIdString(addressIdList, "A", 8);
        String latestClubId = getLatestIdString(clubIdList, "C", 8);
        String latestTimeFrameId = getLatestIdString(timeFrameIdList, "TF", 8);

        // tao foreign key cho tung address
        String fkAddressId = getForeignKeyId(latestAddressId, "A", 1, 8).get(0);
        String fkClubId = getForeignKeyId(latestClubId, "C", 1, 8).get(0);
        List<String> fkTimeFrameIdList = getForeignKeyId(latestTimeFrameId, "TF", clubRegisterDTO.getTimeFramesList().size(), 8);

        //============================== TAO VA INSERT CAC DOI TUONG==========================
        // try {
            // Ham them dia chi o day --
            clubRegisterRepository.insertAddress(clubRegisterDTO.getAddress());
            // Ham them club o day. --
            clubRegisterRepository.insertBadmintonClub(clubRegisterDTO.getBadmintonClub(), fkAddressId);
            // Ham them hinh anh o day
            if(!images.isEmpty()){
                for(BadmintonClubImage image: images){
                    image.setBadmintonClubId(fkClubId);
                    image.setIsMainAvatar(0);
                }
            }
            if(mainAvatar != null){
                mainAvatar.setBadmintonClubId(fkClubId);
                mainAvatar.setIsMainAvatar(1);
                images.add(mainAvatar);
            } 
            if(images!=null || !images.isEmpty()){
                // insert hinh anh.
                clubRegisterRepository.insertImages(images);
            }
        
            // Ham them court o day
            for(Court court: clubRegisterDTO.getCourtList()){
                court.setClubId(fkClubId);
            }
            clubRegisterRepository.insertCourt(clubRegisterDTO.getCourtList());
            // Ham them time fram va pricing rule o day
            // Set id cho tung pricing rule tuong ung voi no.
            for(int i = 0; i < fkTimeFrameIdList.size(); i++){
                TimeFrame tf = clubRegisterDTO.getTimeFramesList().get(i);
                tf.setClubId(fkClubId);
                for(PricingService pr : tf.getClubPricingRegister()){
                    pr.setTimeFrameId(fkTimeFrameIdList.get(i));
                }
                setTimeFrames.add(tf);
            }
            clubRegisterRepository.insertTimeFrame(setTimeFrames);
        // } catch (Exception e) {
        //     System.out.println("Error at isRegisterClub in clubRegisterService: " + e.getMessage());
        //     return false;
        // }
        return isRegiter;
    }

    // Ham lay ra id lon nhat (danh sach id, tien to truoc id, do dai id)
    private String getLatestIdString(List<String> idList, String prefix, int length){
        if(idList.isEmpty() || idList == null){
            return generateId(prefix, length, 1);
        } else {
            String latestIdString = idList.get(0);
            for(String id : idList){
                if(Integer.parseInt(id.substring(prefix.length())) > Integer.parseInt(latestIdString.substring(prefix.length()))){
                    latestIdString = id;
                }
            }
            return latestIdString;
        }

    }

    private List<String> getForeignKeyId(String latestIdString, String prefix, int numberOfId, int idLength){
        List<String> foreignKeyIdList = new ArrayList<>();
        int latestIdNumber = Integer.parseInt(latestIdString.substring(prefix.length()));
        for(int i = 0; i < numberOfId; i++){
            latestIdNumber += 1;
            foreignKeyIdList.add(generateId(prefix, idLength, latestIdNumber));
        }
        return foreignKeyIdList;

    }

    // Ham tao ra id tu prefix va length
    private String generateId(String prefix, int length, int numberId) {
        int numDigits = length - prefix.length();
        String format = "%s%0" + numDigits + "d";       
        return String.format(format, prefix, numberId);
    }

    
}
