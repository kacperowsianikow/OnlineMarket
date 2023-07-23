package org.example.dto;

public record GetUserResponse(String firstName,
                              String lastName,
                              String email,
                              String phone,
                              String address,
                              String city,
                              String country,
                              String zipCode) {

}
