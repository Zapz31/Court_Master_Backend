package com.swp391.Court_Master.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.BookingSlotFilterRequest;
import com.swp391.Court_Master.Entities.FilterHistorySchedule;
import com.swp391.Court_Master.Entities.FilterHistorySchedule;
import com.swp391.Court_Master.RowMapper.BookedDTOHistoryRowMapper;
import com.swp391.Court_Master.RowMapper.BookingScheduleHistoryRowMapper;
import com.swp391.Court_Master.RowMapper.ClubHomePageResponseRowMapper;
import com.swp391.Court_Master.dto.request.Respone.BookingScheduleHistory;
import com.swp391.Court_Master.dto.request.Respone.ClubHomePageResponse;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FilterService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ClubHomePageResponse> filterNoTime(String nameOrUnitNumber, String province, String district,
            String ward) {
        StringBuilder queryBuilder = new StringBuilder(
                " select t1.clubId, t1.clubName, t1.clubAddress, t1.clubImageName, t1.averagePrice from (\r\n" + //
                        "   select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, \r\n" + //
                        "   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, \r\n"
                        + //
                        "   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc\r\n"
                        + //
                        "   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1\r\n"
                        + //
                        "   inner join address ad on bc.address_id = ad.address_id\r\n" + //
                        "   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id\r\n" + //
                        "   group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url\r\n"
                        + //
                        ") as t1 where t1.clubId in (\r\n" + //
                        "\r\n" + //
                        "     select bcl.badminton_club_id from badminton_club bcl\r\n" + //
                        "     inner join address ad on bcl.address_id = ad.address_id\r\n" + //
                        "     where 1=1 \r\n" + //
                        "");

        List<Object> params = new ArrayList<>();

        if (nameOrUnitNumber != null) {
            queryBuilder.append(
                    "and ((bcl.badminton_club_name like N'%' + ? + '%') or (ad.unit_number like N'%' + ? + '%')) \r\n" + //
                            "");
            params.add(nameOrUnitNumber);
            params.add(nameOrUnitNumber);
        }

        if (province != null) {
            queryBuilder.append("and ad.province like N'%' + ? + '%'");
            params.add(province);
        }

        if (district != null) {
            queryBuilder.append("and ad.district like N'%' + ? + '%'"); //and ad.district like N'%' + ? + '%'
            params.add(district);
        }

        if (ward != null) {
            queryBuilder.append("     and ad.ward like N'%' + ? + '%'\r\n" + //
                    ")");
            params.add(ward);
        }

        String query = queryBuilder.toString();

        return jdbcTemplate.query(query, params.toArray(), new ClubHomePageResponseRowMapper());
    }

    public List<ClubHomePageResponse> filterFullField(String nameOrUnitNumber, String province, String district,
            String ward, LocalTime openedTime, LocalTime hoursOfExpect) {
        StringBuilder queryBuilder = new StringBuilder(
                "select t1.clubId, t1.clubName, t1.clubAddress, t1.clubImageName, t1.averagePrice from (\r\n" + //
                        "   select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, \r\n" + //
                        "   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, \r\n"
                        + //
                        "   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc\r\n"
                        + //
                        "   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1\r\n"
                        + //
                        "   inner join address ad on bc.address_id = ad.address_id\r\n" + //
                        "   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id\r\n" + //
                        "   group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url\r\n"
                        + //
                        ") as t1 where t1.clubId in (\r\n" + //
                        "\t select bcl.badminton_club_id from badminton_club bcl\r\n" + //
                        "\t inner join address ad on bcl.address_id = ad.address_id\r\n" + //
                        "\t inner join badminton_court bc on bc.badminton_club_id = bcl.badminton_club_id \r\n" + //
                        "\t inner join time_frame tf on tf.badminton_club_id = bcl.badminton_club_id\r\n" + //
                        "\t left join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                        "\t \t where 1=1 ");

        List<Object> params = new ArrayList<>();

        if (nameOrUnitNumber != null) {
            queryBuilder.append(
                    "and ((bcl.badminton_club_name like N'%' + ? + '%') or (ad.unit_number like N'%' + ? + '%')) \r\n" + //
                            "");
            params.add(nameOrUnitNumber);
            params.add(nameOrUnitNumber);
        }

        if (province != null) {
            queryBuilder.append("and ad.province like N'%' + ? + '%'");
            params.add(province);
        }

        if (district != null) {
            queryBuilder.append("and ad.district like N'%' + ? + '%'");
            params.add(district);
        }

        if (ward != null) {
            queryBuilder.append("     and ad.ward like N'%' + ? + '%'\r\n" + //
                    "");
            params.add(ward);
        }
        if (openedTime != null) {
            queryBuilder.append("and tf.start_time = ?");
            Time openedTimeTime = Time.valueOf(openedTime);
            System.out.println(openedTimeTime);
            params.add(openedTimeTime);
        }
        if (hoursOfExpect != null) {
            Time hoursOfExpectTime = Time.valueOf((hoursOfExpect));
            params.add(hoursOfExpectTime);
            params.add(hoursOfExpectTime);
            queryBuilder.append("and bc.badminton_court_id not in (\r\n" + //
                    "\t \t    Select bc.badminton_court_id from badminton_club bcl \r\n" + //
                    "\t \t\tinner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                    "\t \t\tleft join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                    "\t \t \twhere (start_time <= ? and end_time > ?)\r\n" + //
                    "\t\t\tgroup by bc.badminton_court_id\r\n" + //
                    "\t\t )\r\n" + //
                    "");

        }

        queryBuilder.append("\t group by bcl.badminton_club_id\r\n" + //
                ")");

        String query = queryBuilder.toString();

        return jdbcTemplate.query(query, params.toArray(), new ClubHomePageResponseRowMapper());

    }

    /*
     * Filter bookingSchedule trong booking history
     */
    public List<BookingScheduleHistory> getFilterBookingScheduleHis(FilterHistorySchedule filterHistorySchedule) {
        StringBuilder sqlFilter = new StringBuilder(
                "select iv.badminton_club_name, iv.court_manager_phone, bs.booking_schedule_id, bs.booking_schedule_status, bs.start_date, bs.end_date, bs.schedule_type, bs.total_price, bs.total_playing_time, iv.booking_phone_number from booking_schedule bs\r\n"
                        + //
                        "inner join invoice iv on bs.booking_schedule_id = iv.booking_schedule_id\r\n" + //
                        "where bs.customer_id = ? and bs.is_view = 1");
        List<Object> params = new ArrayList<>();
        params.add(filterHistorySchedule.getCustomerId());

        if (filterHistorySchedule.getClubNameOrCMPhone() != null) {
            sqlFilter.append(" and (iv.badminton_club_name like ? or iv.court_manager_phone like ?)");
            params.add("%" + filterHistorySchedule.getClubNameOrCMPhone() + "%");
            params.add("%" + filterHistorySchedule.getClubNameOrCMPhone() + "%");
        }

        if (filterHistorySchedule.getStartDate() != null) {
            sqlFilter.append(" and bs.start_date = ?");
            params.add(filterHistorySchedule.getStartDate());
        }

        if (filterHistorySchedule.getEndDate() != null) {
            sqlFilter.append(" and bs.end_date = ?");
            params.add(filterHistorySchedule.getEndDate());
        }
        if (filterHistorySchedule.getScheduleType() != null) {
            sqlFilter.append(" and bs.schedule_type like ?");
            params.add("%" + filterHistorySchedule.getScheduleType() + "%");
        }

        sqlFilter.append(
                "group by iv.badminton_club_name, iv.court_manager_phone, bs.booking_schedule_id, bs.booking_schedule_status, bs.start_date, bs.end_date, bs.schedule_type, bs.total_price, bs.total_playing_time, iv.booking_phone_number");

        String query = sqlFilter.toString();
        return jdbcTemplate.query(query, params.toArray(), new BookingScheduleHistoryRowMapper());

    }

    // XOA MOT BOOKING SCHEDULE TRONG LICH SU
    @Transactional
    public boolean removeBookingScheduleHistories(List<String> bookingScheduleIDS) {
        try {
            StringBuilder sqlUpdateIsView = new StringBuilder("update booking_schedule\r\n" + //
                    "set is_view = 0\r\n" + //
                    "where booking_schedule_id in (");

            List<Object> params = new ArrayList<>();
            for (int i = 0; i < bookingScheduleIDS.size(); i++) {
                if (i == bookingScheduleIDS.size() - 1) {
                    sqlUpdateIsView.append("?)");
                } else {
                    sqlUpdateIsView.append("?, ");
                }
                params.add(bookingScheduleIDS.get(i));
            }
            int rowUpdate = jdbcTemplate.update(sqlUpdateIsView.toString(), params.toArray());
            if(rowUpdate > 0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error at filter service at function removeBookingScheduleHistories: " + e);
            return false;
        }

    }

    // Filter cho booking slot
    public List<BookedDTO> getFilterBookingSlots(BookingSlotFilterRequest bookingSlotFilterRequest){
        StringBuilder sqlFilter = new StringBuilder("select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bs.is_check_in, price, bs.badminton_court_id, bc.badminton_court_name from  booking_slot bs\r\n" + //
                        "inner join badminton_court bc on bs.badminton_court_id = bc.badminton_court_id\r\n" + //
                        "where booking_schedule_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(bookingSlotFilterRequest.getBookingScheduleId());

        if(bookingSlotFilterRequest.getStartTime()!=null){
            sqlFilter.append(" and bs.start_time = ?");
            params.add(bookingSlotFilterRequest.getStartTime());
        }

        if(bookingSlotFilterRequest.getEndTime()!=null){
            sqlFilter.append(" and bs.end_time = ?");
            params.add(bookingSlotFilterRequest.getEndTime());
        }
        
        if(bookingSlotFilterRequest.getBookingDate() != null){
            sqlFilter.append(" and bs.booking_date = ?");
            params.add(bookingSlotFilterRequest.getBookingDate());
        }

        if(bookingSlotFilterRequest.getIsCheckIn() != 2){
            sqlFilter.append(" and bs.is_check_in = ?");
            params.add(bookingSlotFilterRequest.getIsCheckIn());
        }

        List<BookedDTO> list = jdbcTemplate.query(sqlFilter.toString(), params.toArray(), new BookedDTOHistoryRowMapper());

        Collections.sort(list, new Comparator<BookedDTO>() {

            @Override
            public int compare(BookedDTO o1, BookedDTO o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
            
        });
        return list;
    }

}
