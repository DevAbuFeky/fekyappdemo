package com.demoappfeky.repository.hotelRepo;

import com.demoappfeky.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
