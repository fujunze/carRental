package com.example.booking.service.impl;

import com.example.booking.dao.CarRentalDao;
import com.example.booking.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CarRentalServiceImpl implements CarRentalService {
    @Autowired
    private CarRentalDao carRentalDao;

    @Override
    @Transactional
    public int rentalCar(String mobile, BigDecimal cost, String type, int day) {
        int res = carRentalDao.deductBalance(mobile, cost);
        if(res == 0) {
            return res;
        }

        res = carRentalDao.deduct(type);
        if(res > 0) {
            carRentalDao.rentalLog(mobile, type, day);
        } else {
            //租车失败补偿
            carRentalDao.deductBalance(mobile, cost.multiply(new BigDecimal(-1)));
        }
        return res;
    }

    @Override
    public boolean checkMobile(String mobile) {
        return carRentalDao.checkMobile(mobile);
    }

    @Override
    public BigDecimal getBalance(String mobile) {
        return carRentalDao.getBalance(mobile);
    }

    @Override
    public BigDecimal getCarCost(String type, int day) {
        return carRentalDao.getCarCost(type, day);
    }

    @Override
    @Transactional
    public int returnBack(String mobile, String id, String type) {
        int res = carRentalDao.returnBack(mobile, id);
        if(res > 0) {
            res = carRentalDao.recycle(type);
        }
        return res;
    }

    @Override
    public int getInStock(String type) {
        return carRentalDao.getInStock(type);
    }


}
