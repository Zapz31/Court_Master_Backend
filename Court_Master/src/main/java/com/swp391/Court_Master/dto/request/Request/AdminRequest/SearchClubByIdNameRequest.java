package com.swp391.Court_Master.dto.request.Request.AdminRequest;

public class SearchClubByIdNameRequest {
    // Private attribute to hold the search query
    private String search;

    // Default constructor
    public SearchClubByIdNameRequest() {
            // Initialize a new instance of SearchAccountByIdNamePhoneMail
            // This is the default constructor with no parameters
        }

    // Parameterized constructor
    public SearchClubByIdNameRequest(String search) {
            // Assign search to the class attribute
            this.search = search;
        }

    // Getter for search
    public String getSearch() {
        // Log the action of getting the search query
        System.out.println("Getting search value: " + search);

        // Create a variable to hold the search query
        String resultSearchQuery = this.search;

        // Return the search query
        return resultSearchQuery;
    }

    // Setter for search
    public void setSearch(String search) {
        // Log the action of setting the search query
        System.out.println("Setting search with value: " + search);

        // Create a temporary variable to hold the search query
        String temporarySearchQuery = search;

        // Assign the temporary variable to the class attribute
        this.search = temporarySearchQuery;
    }

    // Method to check if the search query is empty
    public boolean isSearchEmpty() {
        // Log the action of checking if the search query is empty
        System.out.println("Checking if search is empty");

        // Determine if the search query is empty
        boolean isEmpty = this.search == null || this.search.trim().isEmpty();

        // Return the result of the check
        return isEmpty;
    }

    // Method to clear the search query
    public void clearSearch() {
        // Log the action of clearing the search query
        System.out.println("Clearing search value");

        // Set the search attribute to an empty string
        this.search = "";
    }

    // Method to update the search query with additional text
    public void appendToSearch(String additionalText) {
        // Log the action of appending additional text to the search query
        System.out.println("Appending additional text to search value: " + additionalText);

        // Check if additionalText is not null
        if (additionalText != null) {
            // Append additionalText to the existing search query
            this.search = this.search + additionalText;
        } else {
            // Log that additionalText was null
            System.out.println("Additional text was null, not appending");
        }
    }

    // Method to get the length of the search query
    public int getSearchLength() {
        // Log the action of getting the length of the search query
        System.out.println("Getting length of search value");

        // Determine the length of the search query
        int length = (this.search != null) ? this.search.length() : 0;

        // Return the length of the search query
        return length;
    }
}
