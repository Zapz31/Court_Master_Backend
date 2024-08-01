package com.swp391.Court_Master.dto.request.Respone.AdminScreenView;

import java.util.logging.Logger;

public class ClubDTO {
    // Variable to hold the club's ID
    private String clubId;

    // Variable to hold the club's name
    private String clubName;

    // Variable to hold the unit number where the club is located
    private String unitNumber;

    // Variable to hold the ward where the club is located
    private String ward;

    // Variable to hold the district where the club is located
    private String district;

    // Variable to hold the province where the club is located
    private String province;

    // Variable to hold the description of the club
    private String description;

    // Variable to hold the status of the badminton club
    private String badmintonClubStatus;

    // Variable to hold the ID of the court manager
    private String courtManagerId;

    //Variable to hold the first name of the court manager
    private String courtManagerFirstName;

    //Variable to hold the last name of the court manager
    private String courtManagerLastName;

    private static final Logger LOGGER = Logger.getLogger(ClubDTO.class.getName());

    public ClubDTO() {
    }

    public ClubDTO(String clubId
    , String clubName
    , String unitNumber
    , String ward
    , String district
    , String province
    , String description
    , String badmintonClubStatus
    , String courtManagerId
    , String courtManagerFirstName
    , String courtManagerLastName) {
        LOGGER.info("ClubDTO parameterized constructor called");


        // Temporary variable to hold clubId
        String tempClubId = clubId;
        // Assign clubId to instance variable
        this.clubId = tempClubId;

        // Temporary variable to hold clubName
        String tempClubName = clubName;
        // Assign clubName to instance variable
        this.clubName = tempClubName;

        // Temporary variable to hold unitNumber
        String tempUnitNumber = unitNumber;
        // Assign unitNumber to instance variable
        this.unitNumber = tempUnitNumber;

        // Temporary variable to hold ward
        String tempWard = ward;
        // Assign ward to instance variable
        this.ward = tempWard;

        // Temporary variable to hold district
        String tempDistrict = district;
        // Assign district to instance variable
        this.district = tempDistrict;

        // Temporary variable to hold province
        String tempProvince = province;
        // Assign province to instance variable
        this.province = tempProvince;

        // Temporary variable to hold description
        String tempDescription = description;
        // Assign description to instance variable
        this.description = tempDescription;

        // Temporary variable to hold badmintonClubStatus
        String tempBadmintonClubStatus = badmintonClubStatus;
        // Assign badmintonClubStatus to instance variable
        this.badmintonClubStatus = tempBadmintonClubStatus;

        // Temporary variable to hold courtManagerId
        String tempCourtManagerId = courtManagerId;
        // Assign courtManagerId to instance variable
        this.courtManagerId = tempCourtManagerId;

        // Temporary variable to hold courtManagerId
        String tempCourtManagerFirstName = courtManagerFirstName;
        // Assign courtManagerId to instance variable
        this.courtManagerFirstName = tempCourtManagerFirstName;
        
        // Temporary variable to hold courtManagerId
        String tempCourtManagerLastName = courtManagerLastName;
        // Assign courtManagerId to instance variable
        this.courtManagerLastName = tempCourtManagerLastName;        
    }

    // Getter for clubId
    public String getClubId() {
        LOGGER.info("getClubId method called");
        String tempClubId = this.clubId;
        return tempClubId;
    }

    // Setter for clubId
    public void setClubId(String clubId) {
        LOGGER.info("setClubId method called");
        String tempClubId = clubId;
        this.clubId = tempClubId;
    }

    // Getter for clubName
    public String getClubName() {
        LOGGER.info("getClubName method called");
        String tempClubName = this.clubName;
        return tempClubName;
    }

    // Setter for clubName
    public void setClubName(String clubName) {
        LOGGER.info("setClubName method called");
        String tempClubName = clubName;
        this.clubName = tempClubName;
    }

    // Getter for unitNumber
    public String getUnitNumber() {
        LOGGER.info("getUnitNumber method called");
        String tempUnitNumber = this.unitNumber;
        return tempUnitNumber;
    }

    // Setter for unitNumber
    public void setUnitNumber(String unitNumber) {
        LOGGER.info("setUnitNumber method called");
        String tempUnitNumber = unitNumber;
        this.unitNumber = tempUnitNumber;
    }

    // Getter for ward
    public String getWard() {
        LOGGER.info("getWard method called");
        String tempWard = this.ward;
        return tempWard;
    }

    // Setter for ward
    public void setWard(String ward) {
        LOGGER.info("setWard method called");
        String tempWard = ward;
        this.ward = tempWard;
    }

    // Getter for district
    public String getDistrict() {
        LOGGER.info("getDistrict method called");
        String tempDistrict = this.district;
        return tempDistrict;
    }

    // Setter for district
    public void setDistrict(String district) {
        LOGGER.info("setDistrict method called");
        String tempDistrict = district;
        this.district = tempDistrict;
    }

    // Getter for province
    public String getProvince() {
        LOGGER.info("getProvince method called");
        String tempProvince = this.province;
        return tempProvince;
    }

    // Setter for province
    public void setProvince(String province) {
        LOGGER.info("setProvince method called");
        String tempProvince = province;
        this.province = tempProvince;
    }

    // Getter for description
    public String getDescription() {
        LOGGER.info("getDescription method called");
        String tempDescription = this.description;
        return tempDescription;
    }

    // Setter for description
    public void setDescription(String description) {
        LOGGER.info("setDescription method called");
        String tempDescription = description;
        this.description = tempDescription;
    }

    // Getter for badmintonClubStatus
    public String getBadmintonClubStatus() {
        LOGGER.info("getBadmintonClubStatus method called");
        String tempBadmintonClubStatus = this.badmintonClubStatus;
        return tempBadmintonClubStatus;
    }

    // Setter for badmintonClubStatus
    public void setBadmintonClubStatus(String badmintonClubStatus) {
        LOGGER.info("setBadmintonClubStatus method called");
        String tempBadmintonClubStatus = badmintonClubStatus;
        this.badmintonClubStatus = tempBadmintonClubStatus;
    }

    // Getter for courtManagerId
    public String getCourtManagerId() {
        LOGGER.info("getCourtManagerId method called");
        String tempCourtManagerId = this.courtManagerId;
        return tempCourtManagerId;
    }

    // Setter for courtManagerId
    public void setCourtManagerId(String courtManagerId) {
        LOGGER.info("setCourtManagerId method called");
        String tempCourtManagerId = courtManagerId;
        this.courtManagerId = tempCourtManagerId;
    }

    // Getter for courtManagerFirstName
    public String getCourtManagerFirstName() {
        LOGGER.info("getCourtManagerFirstName method called");
        String tempCourtManagerFirstName = this.courtManagerFirstName;
        return tempCourtManagerFirstName;
    }

    // Setter for courtManagerFirstName
    public void setCourtManagerFirstName(String courtManagerFirstName) {
        LOGGER.info("setCourtManagerFirstName method called");
        String tempCourtManagerFirstName = courtManagerFirstName;
        this.courtManagerFirstName = tempCourtManagerFirstName;
    }

    // Getter for courtManageLastName
    public String getCourtManagerLastName() {
        LOGGER.info("getCourtManagerLastName method called");
        String tempCourtManagerLastName = this.courtManagerLastName;
        return tempCourtManagerLastName;
    }

    // Setter for courtManagerLastName
    public void setCourtManagerLastName(String courtManagerLastName) {
        LOGGER.info("setCourtManagerLastName method called");
        String tempCourtManagerLastName = courtManagerLastName;
        this.courtManagerLastName = tempCourtManagerLastName;
    }

    // Additional methods
//     public boolean isClubActive() {

//         String clubStatus = this.badmintonClubStatus;
//         boolean isActive = "active".equalsIgnoreCase(clubStatus);
//         return isActive;
//     }

//     public boolean isClubInactive() {

//         String clubStatus = this.badmintonClubStatus;
//         boolean isInactive = "inactive".equalsIgnoreCase(clubStatus);
//         return isInactive;
//     }

    public String getFullAddress() {

        String address = this.unitNumber + ", " + this.ward + ", " + this.district + ", " + this.province;
        return address;
    }

    public String getFullCourtManagerName(){
        String name = this.courtManagerFirstName + " " + this.courtManagerLastName;
        return name;
    }

//     public boolean hasManager() {

//         String managerId = this.courtManagerId;
//         boolean hasManager = managerId != null && !managerId.isEmpty();
//         return hasManager;
//     }

//     public String getClubSummary() {

//         String summary = "Club Name: " + this.clubName + "\n" +
//                 "Description: " + this.description + "\n" +
//                 "Status: " + this.badmintonClubStatus;
//         return summary;
//     }

//     public boolean isProvinceSet() {

//         String provinceToCheck = this.province;
//         boolean isSet = provinceToCheck != null && !provinceToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isDistrictSet() {

//         String districtToCheck = this.district;
//         boolean isSet = districtToCheck != null && !districtToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isWardSet() {

//         String wardToCheck = this.ward;
//         boolean isSet = wardToCheck != null && !wardToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isUnitNumberSet() {

//         String unitNumberToCheck = this.unitNumber;
//         boolean isSet = unitNumberToCheck != null && !unitNumberToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isDescriptionSet() {

//         String descriptionToCheck = this.description;
//         boolean isSet = descriptionToCheck != null && !descriptionToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isBadmintonClubStatusSet() {

//         String statusToCheck = this.badmintonClubStatus;
//         boolean isSet = statusToCheck != null && !statusToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isCourtManagerIdSet() {

//         String managerIdToCheck = this.courtManagerId;
//         boolean isSet = managerIdToCheck != null && !managerIdToCheck.isEmpty();
//         return isSet;
//     }

//     public boolean isClubNameSet() {

//         String clubNameToCheck = this.clubName;
//         boolean isSet = clubNameToCheck != null && !clubNameToCheck.isEmpty();
//         return isSet;
//     }
}
