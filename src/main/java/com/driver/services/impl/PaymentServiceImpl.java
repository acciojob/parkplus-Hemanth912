package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    SpotRepository spotRepository;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        Spot spot = reservation.getSpot();

        int bill = reservation.getNumberOfHours()*spot.getPricePerHour();

        ParkingLot parkingLot = spot.getParkingLot();
        List<Spot> spotList = parkingLot.getSpotList();

        String paymentMode = mode.toUpperCase();
        PaymentMode[] paymentModes = PaymentMode.values();
        Payment pay= null;
        for(PaymentMode p : paymentModes)
        {
            if(paymentMode.equals(p))
            {
                if(bill<=amountSent)
                {
                    pay.setPaymentMode(p);
                    pay.setPaymentCompleted(true);
                    reservation.setPayment(pay);
                    spot.setOccupied(true);
                    for(Spot sp:spotList)
                    {
                        if(sp==spot)
                            sp.setOccupied(true);
                    }
                    parkingLot.setSpotList(spotList);

                    parkingLotRepository.save(parkingLot);
                    paymentRepository2.save(pay);
                    spotRepository.save(spot);
                    reservationRepository2.save(reservation);
                    return pay;
                }
//                else
//                    throw new Exception("Insufficient Amount");

            }
//            throw new Exception("Payment mode not detected");
        }
        return null;
    }
}
