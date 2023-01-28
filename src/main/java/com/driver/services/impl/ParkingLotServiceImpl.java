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

//       List<Spot> spots = new ArrayList<>();

        Spot newSpot = new Spot();
        newSpot.setPricePerHour(pricePerHour);
        if(numberOfWheels>2)
            newSpot.setSpotType(SpotType.FOUR_WHEELER);
        else if(numberOfWheels>4)
            newSpot.setSpotType(SpotType.OTHERS);
        else
            newSpot.setSpotType(SpotType.TWO_WHEELER);

//        spots.add(newSpot);
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        newSpot.setParkingLot(parkingLot);
        parkingLot.getSpotList().add(newSpot);

        //spotRepository1.save(newSpot);
        parkingLotRepository1.save(parkingLot);


        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).get();
        spotRepository1.deleteById(spotId);

//        ParkingLot parkingLot = spot.getParkingLot();
//        List<Spot> spotList = parkingLot.getSpotList();
//
//        if(spotList.contains(spot))
//            spotList.remove(spot);
//
//        parkingLot.setSpotList(spotList);
//
//        parkingLotRepository1.save(parkingLot);

    }


    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();



        Spot spot=null;
        List<Spot> spotList=parkingLot.getSpotList();
        for(Spot spot1:spotList){
            if(spot1.getId()==spotId)
                spot=spot1;
        }







        spot.setParkingLot(parkingLot);
        spot.setPricePerHour(pricePerHour);



        spotRepository1.save(spot);

        return spot;



    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        //ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        parkingLotRepository1.deleteById(parkingLotId);

    }
}
