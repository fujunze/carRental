package com.example.booking.web;

import com.example.booking.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    public ModelMap booking(@RequestParam String mobile, @RequestParam String type, @RequestParam int day) {
        ModelMap model = new ModelMap();
        boolean check = carRentalService.checkMobile(mobile);
        if(!check) {
            model.put("CODE", "01");
            model.put("MSG", "无效用户");
            return model;
        }

        int inStock = carRentalService.getInStock(type);
        if(inStock == 0) {
            model.put("CODE", "01");
            model.put("MSG", "此车型已租完了，请选择其他车型");
            return model;
        }
        //查余额
        BigDecimal balance = carRentalService.getBalance(mobile);
        //租车需要花费的价格
        BigDecimal shouldCost = carRentalService.getCarCost(type, day);
        if(balance.compareTo(shouldCost) < 0) {
            model.put("CODE", "01");
            model.put("MSG", "余额不足");
            return model;
        }

        int res = carRentalService.rentalCar(mobile, shouldCost, type, day);
        if(res > 0) {
            model.put("CODE", "00");
            model.put("MSG", "租车成功");
        } else {
            model.put("CODE", "01");
            model.put("MSG", "租车失败");
        }
        return model;
    }

    /**
     * 还车
     * @param mobile
     * @param id
     * @return
     */
    @RequestMapping("/car/recycle")
    public ModelMap booking(@RequestParam String mobile, @RequestParam String id, @RequestParam String type) {
        ModelMap model = new ModelMap();
        int res = carRentalService.returnBack(mobile, id, type);
        if(res > 0) {
            model.put("CODE", "00");
            model.put("MSG", "还车成功");
        } else {
            model.put("CODE", "01");
            model.put("MSG", "没有可还的车");
        }
        return model;
    }

    /**
     * 查询租车
     * @param mobile
     * @return
     */
    @RequestMapping("/car/query")
    public ModelMap query(@RequestParam String mobile) {
        ModelMap model = new ModelMap();
        List<Map<String,String>> list = carRentalService.queryRental(mobile);
        model.put("DATA", list);
        model.put("CODE", "00");
        return model;
    }
}
