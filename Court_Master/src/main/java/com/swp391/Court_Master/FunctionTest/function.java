package com.swp391.Court_Master.FunctionTest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class function {

    DBContex dbContex = new DBContex();

    public void addToPricingRule(){
        PricingRuleDTO pricingRuleDTO = new PricingRuleDTO();
        List<PricingRuleDTO> fixedList = new ArrayList<>();
        List<PricingRuleDTO> flexibleList = new ArrayList<>();
        List<PricingRuleDTO> oneTimePlayList = new ArrayList<>();

        int defaut_price1 = 32000;
        int defaut_price2 = 40000;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter time frame id: ");
        String timeFrimeId = sc.nextLine();
        pricingRuleDTO.setTimeFrameId(timeFrimeId);
        System.out.println("Pricing for fixed: ");
        for(int i = 0; i < 7; i++){
            String userInput = "";
            if(i==0){
                System.out.print("Mon: ");
                pricingRuleDTO.setDayOfWeek("Mon");
                pricingRuleDTO.setScheduleTpye("fixed");
                userInput = sc.nextLine();
                if(userInput.isEmpty() || !userInput.matches("[0-9]+")){               
                    pricingRuleDTO.setPricePerHours(defaut_price1);               
                } else {
                    pricingRuleDTO.setPricePerHours(Integer.parseInt(userInput));
                }
                fixedList.add(pricingRuleDTO);
            } else if(i==1){
                System.out.print("Tue: ");
                pricingRuleDTO.setDayOfWeek("Tue");
                pricingRuleDTO.setScheduleTpye("fixed");
                userInput = sc.nextLine();
                if(userInput.isEmpty() || !userInput.matches("[0-9]+")){               
                    pricingRuleDTO.setPricePerHours(defaut_price1);               
                } else {
                    pricingRuleDTO.setPricePerHours(Integer.parseInt(userInput));
                }
                fixedList.add(pricingRuleDTO);
            } else if(i==2){
                System.out.print("Wed: ");
                pricingRuleDTO.setDayOfWeek("Wed");
                pricingRuleDTO.setScheduleTpye("fixed");
                userInput = sc.nextLine();
                if(userInput.isEmpty() || !userInput.matches("[0-9]+")){               
                    pricingRuleDTO.setPricePerHours(defaut_price1);               
                } else {
                    pricingRuleDTO.setPricePerHours(Integer.parseInt(userInput));
                }
                fixedList.add(pricingRuleDTO);
            }
        }

        String sql = "insert into pricing_rule(day_of_week, price_per_hour, schedule_type, time_frame_id)\r\n" + //
                        "values(?, ?, ?, ?)";
        dbContex.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter(){

            @Override
            public int getBatchSize() {
                return fixedList.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PricingRuleDTO pricingRuleDTO = fixedList.get(i);
                ps.setString(1, pricingRuleDTO.getDayOfWeek());
                ps.setInt(2, pricingRuleDTO.getPricePerHours());
                ps.setString(3, pricingRuleDTO.getScheduleTpye());
                ps.setString(4, pricingRuleDTO.getTimeFrameId());
                
            }

        });
    }
}
