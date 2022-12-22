package com.demoappfeky.services.hotelServices;

import com.demoappfeky.model.Room;
import com.demoappfeky.repository.hotelRepo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void removeOne(long id) {
        roomRepository.deleteById(id);
    }
    public Room findRoomById(long id){
        return roomRepository.findById(id).get();
    }
}
