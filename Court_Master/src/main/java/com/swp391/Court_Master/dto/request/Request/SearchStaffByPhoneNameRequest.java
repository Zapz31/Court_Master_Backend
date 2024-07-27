package com.swp391.Court_Master.dto.request.Request;

import org.springframework.core.annotation.MergedAnnotations.Search;

public class SearchStaffByPhoneNameRequest {
    String court_manager_id;
    String search;

    public SearchStaffByPhoneNameRequest() {

    }

    public SearchStaffByPhoneNameRequest(String court_manager_id, String search) {
        this.court_manager_id = court_manager_id;
        this.search = search;
    }

    // Getter and Setter for court_manager_id
    public String getCourtManagerId() {
        return court_manager_id;
    }

    public void setCourtManagerId(String court_manager_id) {
        this.court_manager_id = court_manager_id;
    }

    // Getter and Setter for search
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
