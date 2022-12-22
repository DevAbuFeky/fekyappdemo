package com.demoappfeky.services.hotelServices;

import com.demoappfeky.model.Hotel;
import com.demoappfeky.repository.hotelRepo.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository categoryRepository) {
        this.hotelRepository = categoryRepository;
    }

    public void removeOne(long id) {
        hotelRepository.deleteById(id);
    }
//    public List<Hotel> findAll() {
//        return hotelRepository.findAll();
//    }
    public Hotel findHotelById(long id) {
        return hotelRepository.findById(id).get();
    }
}
