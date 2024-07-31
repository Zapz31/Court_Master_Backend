package com.swp391.Court_Master.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryBookingSlotMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalCustomerMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalRevenueMapper;
import com.swp391.Court_Master.Repository.CourtManagerRepository;
import com.swp391.Court_Master.dto.request.Request.SearchStaffByPhoneNameRequest;
import com.swp391.Court_Master.dto.request.Request.UpdateStaffRequest;
import com.swp391.Court_Master.dto.request.Request.DashBoardRequest;
import com.swp391.Court_Master.dto.request.Respone.CourManagerScreenView.StaffAccountDTO;
import com.swp391.Court_Master.dto.request.Respone.DashBoardResponse.TotalBookingSlotInformation;
import com.swp391.Court_Master.dto.request.Respone.DashBoardResponse.TotalCustomerInformation;
import com.swp391.Court_Master.dto.request.Respone.DashBoardResponse.TotalRevenueInformation;

@Service
public class CourtManagerService {
    @Autowired
    CourtManagerRepository courtManagerRepository;

    // Tinh toan va tra ve doi tuong TotalRevenueInformation
    public TotalRevenueInformation getTotalRevenueInfo(DashBoardRequest dashBoardRequest) {
        List<QueryTotalRevenueMapper> paymentList = new ArrayList<>();
        int totalAmountCurrentMonth = 0;
        int totalAmountPreviousMonth = 0;
        int percentVariation = 0;
        if (dashBoardRequest.getMonth() == 1) {
            dashBoardRequest.setPreviousMonth(12);
            dashBoardRequest.setPreviousYear(dashBoardRequest.getYear() - 1);
        } else {
            dashBoardRequest.setPreviousMonth(dashBoardRequest.getMonth() - 1);
            dashBoardRequest.setPreviousYear(dashBoardRequest.getYear());
        }
        paymentList = courtManagerRepository.getPaymentDetailList(dashBoardRequest);
        if (!paymentList.isEmpty()) { // xu ly truong hop ca 2 thang khong co doanh thu
            totalAmountCurrentMonth = getTotalAmountPerMonth(dashBoardRequest.getMonth(), dashBoardRequest.getYear(),
                    paymentList);
            totalAmountPreviousMonth = getTotalAmountPerMonth(dashBoardRequest.getPreviousMonth(),
                    dashBoardRequest.getPreviousYear(), paymentList);
            if (totalAmountPreviousMonth == 0) {
                percentVariation = 100;
                return new TotalRevenueInformation(totalAmountCurrentMonth, percentVariation);
            } else {
                percentVariation = getPercentVariation(totalAmountCurrentMonth, totalAmountPreviousMonth);
            }
        }
        // Tinh % chenh lech (Tang hoac giam bao nhieu %)

        return new TotalRevenueInformation(totalAmountCurrentMonth, percentVariation);
    }

    /**
     * Ham tinh phan tram tang giam doanh thu giua 2 thang
     * 
     * @param totalAmountCurrentMonth  : tong doanh thu thang truoc
     * @param totalAmountPreviousMonth : tong doanh thu thang sau
     * @return
     */
    public int getPercentVariation(int totalAmountCurrentMonth, int totalAmountPreviousMonth) {
        double rateOfChange = (totalAmountCurrentMonth - totalAmountPreviousMonth) / (double) totalAmountPreviousMonth
                * 100;
        return (int) Math.round(rateOfChange);
    }

    /**
     * Ham tinh tong tien theo mot thang nhat dinh
     * 
     * @param month       thang can tinh tong tien
     * @param year        nam can tinh, ket hop voi thang ra mot don vi thoi gian
     *                    can tinh tong doanh thu
     * @param paymentList lay ra cac chi tiet hoa don dua tren month va year de tinh
     *                    tong doanh thu
     * @return tong doanh thu dua tren don vi thoi gian do
     *         /
     *         /*
     *         Ham tinh tong tien theo thang va nam can tinh
     */
    public int getTotalAmountPerMonth(int month, int year, List<QueryTotalRevenueMapper> paymentList) {
        int amount = 0;
        for (QueryTotalRevenueMapper queryTotalRevenueMapper : paymentList) {
            if ((queryTotalRevenueMapper.getPaymentTime().getMonthValue() == month)
                    && (queryTotalRevenueMapper.getPaymentTime().getYear() == year)) {
                amount += queryTotalRevenueMapper.getAmount();
            }
        }
        return amount;
    }

    // Tinh toan va tra ve doi tuong getTotalBookingSlot
    public TotalBookingSlotInformation getTotalBookingSlot(DashBoardRequest dashBoardRequest) {
        List<LocalDate> bookingTimeList = new ArrayList<>();
        int currentMonthBSQuantity = 0;
        int previousMontBSQuantity = 0;
        int percentVariation = 0;
        if (dashBoardRequest.getMonth() == 1) {
            dashBoardRequest.setPreviousMonth(12);
            dashBoardRequest.setPreviousYear(dashBoardRequest.getYear() - 1);
        } else {
            dashBoardRequest.setPreviousMonth(dashBoardRequest.getMonth() - 1);
            dashBoardRequest.setPreviousYear(dashBoardRequest.getYear());
        }

        bookingTimeList = courtManagerRepository.getBookingTimeList(dashBoardRequest);
        if (!bookingTimeList.isEmpty()) {
            currentMonthBSQuantity = getBookingSlotQuantity(dashBoardRequest.getMonth(), dashBoardRequest.getYear(),
                    bookingTimeList);
            previousMontBSQuantity = getBookingSlotQuantity(dashBoardRequest.getPreviousMonth(),
                    dashBoardRequest.getPreviousYear(), bookingTimeList);
            if (previousMontBSQuantity == 0) {
                return new TotalBookingSlotInformation(currentMonthBSQuantity, 100);
            } else {
                percentVariation = getPercentVariation(currentMonthBSQuantity, previousMontBSQuantity);
            }
        }
        return new TotalBookingSlotInformation(currentMonthBSQuantity, percentVariation);
    }

    // Ham lay so luong bookingSlot theo ngay va thang.
    public int getBookingSlotQuantity(int month, int year, List<LocalDate> bookingSlotTimes) {
        int bookingSlotQuantity = 0;
        for (LocalDate bsDate : bookingSlotTimes) {
            if (bsDate.getMonthValue() == month && bsDate.getYear() == year) {
                bookingSlotQuantity++;
            }
        }
        return bookingSlotQuantity;
    }

    // Lay so luong khach hang thanh toan dat san trong thang
    public TotalCustomerInformation getTotalCustomerInfo(DashBoardRequest dashBoardRequest) {
        List<QueryTotalCustomerMapper> totalCusNumList = new ArrayList<>();
        int currentMonthNumberOfCus = 0;
        int previousMonthNumberOfCus = 0;
        int percentVariation = 0;
        if (dashBoardRequest.getMonth() == 1) {
            dashBoardRequest.setPreviousMonth(12);
            dashBoardRequest.setPreviousYear(dashBoardRequest.getYear() - 1);
        } else {
            dashBoardRequest.setPreviousMonth(dashBoardRequest.getMonth() - 1);
            dashBoardRequest.setPreviousYear(dashBoardRequest.getYear());
        }
        totalCusNumList = courtManagerRepository.getRegisterCusList(dashBoardRequest);
        if (!totalCusNumList.isEmpty()) {
            currentMonthNumberOfCus = getNumberOfCusByMonth(dashBoardRequest.getMonth(), totalCusNumList);
            previousMonthNumberOfCus = getNumberOfCusByMonth(dashBoardRequest.getPreviousMonth(), totalCusNumList);
            if (previousMonthNumberOfCus == 0) {
                return new TotalCustomerInformation(currentMonthNumberOfCus, 100);
            } else {
                percentVariation = getPercentVariation(currentMonthNumberOfCus, previousMonthNumberOfCus);
            }
        }
        return new TotalCustomerInformation(currentMonthNumberOfCus, percentVariation);
    }

    public int getNumberOfCusByMonth(int month, List<QueryTotalCustomerMapper> totalCusNumList) {
        int numberOfCus = 0;
        for (QueryTotalCustomerMapper record : totalCusNumList) {
            if (record.getMonth() == month) {
                numberOfCus = record.getNumberOfCustomer();
                break;
            }
        }
        return numberOfCus;
    }

    // Lay danh sach doanh thu hang ngay hien thi len bieu do
    public List<QueryTotalRevenueMapper> getDailyRevenue(DashBoardRequest dashBoardRequest) {
        dashBoardRequest.setPreviousMonth(dashBoardRequest.getMonth());
        dashBoardRequest.setPreviousYear(dashBoardRequest.getYear());
        return courtManagerRepository.getPaymentDetailList(dashBoardRequest);
    }

    public List<QueryBookingSlotMapper> getDailyBooking(DashBoardRequest dashBoardRequest) {
        dashBoardRequest.setPreviousMonth(dashBoardRequest.getMonth());
        dashBoardRequest.setPreviousYear(dashBoardRequest.getYear());
        return courtManagerRepository.getBookingDateNumber(dashBoardRequest);
    }

    public HashMap<String, String> getClubIdByUserId(String userId) {
        String clubId = courtManagerRepository.getClubId(userId);
        HashMap<String, String> map = new HashMap<>();
        map.put("clubId", clubId);
        return map;
    }

    // Lay danh sach nhan vien
    public List<StaffAccountDTO> getAllStaff(String court_manager_id) {
        List<StaffAccountDTO> StaffList = courtManagerRepository.getAllStaff(court_manager_id);
        return StaffList;
    }

    // Search nhan vien by name/phone
    public List<StaffAccountDTO> getStaffByPhoneName(SearchStaffByPhoneNameRequest SearchStaffByPhoneNameRequest) {
        List<StaffAccountDTO> StaffList = courtManagerRepository.getStaffByNamePhone(SearchStaffByPhoneNameRequest);
        return StaffList;
    }

    // Delete nhan vien by id
    public String deleteStaff(String staff_id, String court_manager_id) {
        boolean isDeleteStaff = courtManagerRepository.isDeleteStaff(staff_id, court_manager_id);
        if (isDeleteStaff) {
            return "success";
        } else {
            return "false";
        }
    }

    // Update info staff
    public String updateStaffInfo(UpdateStaffRequest UpdateStaffRequest) {
        boolean isUpdateStaff = courtManagerRepository.updateStaffInfo(UpdateStaffRequest);
        if (isUpdateStaff) {
            return "success";
        } else {
            return "false";
        }
    }

    public String getClubIdByCourtManagerId(String courtManagerId){
        return courtManagerRepository.getClubIdByMngId(courtManagerId);
    }

    public void updateCourtInfo(Court court){
        boolean isUpdateCourt = courtManagerRepository.updateCourtInfo(court.getCourtId(), court.getCourtName(), court.getStatus());
        if(!isUpdateCourt){
            throw new RuntimeException("failed");
        }
    }
}
