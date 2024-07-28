package com.swp391.Court_Master.dto.request.Request;

import org.springframework.core.annotation.MergedAnnotations.Search;

public class SearchStaffByPhoneNameRequest {

    // Declaring a variable to hold the court manager ID
    String court_manager_id;

    // Declaring a variable to hold the search string
    String search;

    // Default constructor
    public SearchStaffByPhoneNameRequest() {
        // No initialization in default constructor
    }

    // Parameterized constructor
    public SearchStaffByPhoneNameRequest(String court_manager_id, String search) {
        // Assigning court manager ID
        String temporaryCourtManagerId = court_manager_id;
        this.court_manager_id = temporaryCourtManagerId;

        // Assigning search query
        String temporarySearchQuery = search;
        this.search = temporarySearchQuery;
    }

    // Getter for court_manager_id
    public String getCourtManagerId() {
        String resultCourtManagerId = this.court_manager_id;
        return resultCourtManagerId;
    }

    // Setter for court_manager_id
    public void setCourtManagerId(String court_manager_id) {
        String temporaryCourtManagerId = court_manager_id;
        this.court_manager_id = temporaryCourtManagerId;
    }

    // Getter for search
    public String getSearch() {
        String resultSearchQuery = this.search;
        return resultSearchQuery;
    }

    // Setter for search
    public void setSearch(String search) {
        String temporarySearchQuery = search;
        this.search = temporarySearchQuery;
    }

    // Method to print current state of object
    public void printCurrentState() {
        // Printing the current state of the object
        System.out.println("Court Manager ID: " + this.court_manager_id);
        System.out.println("Search Query: " + this.search);
    }

    // Method to log detailed state of object
    public void logDetailedState() {
        String detailedCourtManagerId = getCourtManagerId();
        System.out.println("Detailed Court Manager ID: " + detailedCourtManagerId);

        String detailedSearchQuery = getSearch();
        System.out.println("Detailed Search Query: " + detailedSearchQuery);
    }

    // Method to compute and print lengths of fields
    public void computeAndPrintLengths() {
        int lengthOfCourtManagerId = this.court_manager_id.length();
        int lengthOfSearchQuery = this.search.length();

        System.out.println("Length of Court Manager ID: " + lengthOfCourtManagerId);
        System.out.println("Length of Search Query: " + lengthOfSearchQuery);
    }
}
