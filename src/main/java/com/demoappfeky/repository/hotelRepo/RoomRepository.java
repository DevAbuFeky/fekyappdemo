package com.demoappfeky.repository.hotelRepo;

import com.demoappfeky.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
