package com.example.booking.web;

import com.example.booking.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    /**
     * 租车
     * @param mobile
     * @param type
     * @param day
     * @return
     */
    @RequestMapping("/car/booking")
    public String booking(@RequestParam String mobile, @RequestParam String type, @RequestParam int day) {
        boolean check = carRentalService.checkMobile(mobile);
        if(!check) {
            return "无效用户";
        }

        int inStock = carRentalService.getInStock(type);
        if(inStock == 0) {
            return "此车型已租完了，请选择其他车型";
        }
        //查余额
        BigDecimal balance = carRentalService.getBalance(mobile);
        //租车需要花费的价格
        BigDecimal shouldCost = carRentalService.getCarCost(type, day);
        if(balance.compareTo(shouldCost) < 0) {
            return "余额不足";
        }

        int res = carRentalService.rentalCar(mobile, shouldCost, type, day);
        String msg;
        if(res > 0) {
            msg = "租车成功";
        } else {
            msg = "租车失败";
        }
        return msg;
    }

    /**
     * 还车
     * @param mobile
     * @param id
     * @return
     */
    @RequestMapping("/car/recycle")
    public String booking(@RequestParam String mobile, @RequestParam String id, @RequestParam String type) {
        int res = carRentalService.returnBack(mobile, id, type);
        if(res > 0) {
            return "还车成功";
        } else {
            return "没有可还的车";
        }
    }
}
