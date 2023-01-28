package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {

        ParkingLot parkingLot = new ParkingLot(name,address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

//       List<Spot> spots = new ArrayList<>();

        Spot newSpot = new Spot();
        newSpot.setPricePerHour(pricePerHour);
        if(numberOfWheels==2)
            newSpot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels==4)
            newSpot.setSpotType(SpotType.FOUR_WHEELER);
        else if(numberOfWheels>4)
            newSpot.setSpotType(SpotType.OTHERS);

//        spots.add(newSpot);
        parkingLot.getSpotList().add(newSpot);

        spotRepository1.save(newSpot);
        parkingLotRepository1.save(parkingLot);


        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).get();
        spotRepository1.deleteById(spotId);

        ParkingLot parkingLot = spot.getParkingLot();
        List<Spot> spotList = parkingLot.getSpotList();

        spotList.remove(spot);

        parkingLot.setSpotList(spotList);

        parkingLotRepository1.save(parkingLot);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        Spot existingSpot = spotRepository1.findById(spotId).get();
        List<Spot> spotList = parkingLot.getSpotList();

        for(Spot spot:spotList)
        {
            if(spot==existingSpot)
                spot.setPricePerHour(pricePerHour);
        }
        existingSpot.setPricePerHour(pricePerHour);

        parkingLot.setSpotList(spotList);

        spotRepository1.save(existingSpot);
        parkingLotRepository1.save(parkingLot);
        return existingSpot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        parkingLotRepository1.delete(parkingLot);

    }
}
