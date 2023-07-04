package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelMangementService {

    @Autowired
    HotelManagementRepo hotelManagementRepo;
    public boolean addHotel(Hotel hotel) {
        HashMap<String, Hotel> hotelList = hotelManagementRepo.getHotelMap();
        if(hotelList.keySet().contains(hotel.getHotelName())) return false;
        hotelManagementRepo.addHotel(hotel);
        return true;
    }

    public Integer addUser(User user) {
        hotelManagementRepo.addUser(user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFac() {
        HashMap<String, Hotel> hotelList = hotelManagementRepo.getHotelMap();
        if(hotelList.size() == 0) return "";
        List<String> names = new ArrayList<>(hotelList.keySet());
        Hotel res = hotelList.get(names.get(0));
        for(String hName: hotelList.keySet()){
            Hotel hotel = hotelList.get(hName);
            if(hotel.getFacilities().size() > res.getFacilities().size())
                res = hotel;
            else if(res.getFacilities().size() == hotel.getFacilities().size() && hotel.getHotelName().compareTo(res.getHotelName()) < 0)
                res = hotel;
        }
        if( res.getFacilities().size() == 0) return "";
        return res.getHotelName();
    }

    public int addBooking(Booking booking) {
        int aadhar = booking.getBookingAadharCard();
        String personName = booking.getBookingPersonName();
        String hotelName = booking.getHotelName();

        HashMap<String, Hotel> hotelList = hotelManagementRepo.getHotelMap();
        HashMap<Integer, User> userList = hotelManagementRepo.getUserMap();

        if(!hotelList.keySet().contains(hotelName)) return -1;
        if(!userList.keySet().contains(aadhar) || !userList.get(aadhar).getName().equals(personName)) return -1;
        if(hotelList.get(hotelName).getAvailableRooms() < booking.getNoOfRooms()) return -1;

        String id = UUID.randomUUID().toString();
        int bill = booking.getNoOfRooms() * hotelList.get(hotelName).getPricePerNight();
        booking.setBookingId(id);
        booking.setAmountToBePaid(bill);
        hotelManagementRepo.addBooking(booking);

        Hotel hotel = hotelList.get(hotelName);
        hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());

        return bill;

    }

    public int getBookingsCount(Integer aadharCard) {
        HashMap<String, Booking> bookinMap = hotelManagementRepo.getBookingMap();
        int count = 0;
        for(Map.Entry<String, Booking> entry: bookinMap.entrySet()){
            Booking booking = entry.getValue();
            if(booking.getBookingAadharCard() == aadharCard)
                count++;
        }
        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        HashMap<String, Hotel> hotelMap = hotelManagementRepo.getHotelMap();
        Hotel hotel = hotelMap.get(hotelName);
        List<Facility> fList = hotel.getFacilities();
        for(Facility facility: newFacilities){
            if(!fList.contains(facility))
                fList.add(facility);
        }
        hotel.setFacilities(fList);
        return hotel;
    }
}
