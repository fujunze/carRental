package com.example.booking.service;

import java.math.BigDecimal;

public interface CarRentalService {

    int rentalCar(String mobile, BigDecimal shouldCost, String type, int day);

    boolean checkMobile(String mobile);

    BigDecimal getBalance(String mobile);

    BigDecimal getCarCost(String type, int day);

    int returnBack(String mobile, String id, String type);

    int getInStock(String type);
}
