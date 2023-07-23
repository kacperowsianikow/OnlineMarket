package org.example.dto;

public record UpdateUserRequest(String phone,
                                String address,
                                String city,
                                String country,
                                String zipCode) {

}
