package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class HotelManagementRepo {

    private HashMap<String, Hotel> hotelMap = new HashMap<>();
    private HashMap<Integer, User> userMap = new HashMap<>();
    private HashMap<String, Booking> bookingMap = new HashMap<>();

    public HashMap<String, Hotel> getHotelMap() {
        return hotelMap;
    }
    public HashMap<Integer, User> getUserMap(){
        return userMap;
    }
    public HashMap<String, Booking> getBookingMap(){
        return bookingMap;
    }

    public void addHotel(Hotel hotel) {
        hotelMap.put(hotel.getHotelName(), hotel);
    }

    public void addUser(User user) {
        userMap.put(user.getaadharCardNo(), user);
    }

    public void addBooking(Booking booking) {
        bookingMap.put(booking.getBookingId(), booking);
    }
}
