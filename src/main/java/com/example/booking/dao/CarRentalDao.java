package com.example.booking.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface CarRentalDao {

    @Update("update car_in_stock t set t.in_stock=t.in_stock-1 where t.car_model=#{type} and t.in_stock>0 ")
    int deduct(String type);

    @Update("update car_in_stock t set t.in_stock=t.in_stock+1 where t.car_model=#{type}")
    int recycle(String type);

    @Select("select count(1) from user_info t where t.mobile=#{mobile}")
    boolean checkMobile(String mobile);

    @Update("update user_info t set t.account_balance=t.account_balance-#{shouldCost} where t.account_balance>=#{shouldCost}")
    int deductBalance(String mobile, BigDecimal shouldCost);

    @Select("select account_balance from user_info where mobile = #{mobile}")
    BigDecimal getBalance(String mobile);

    @Select("select one_day_cost*#{day} from car_in_stock where car_model = #{type} ")
    BigDecimal getCarCost(String type, int day);

    @Insert("insert rental_log (mobile, car_model, duration, create_date) values (#{mobile}, #{type}, #{day}, now())")
    void rentalLog(String mobile, String type, int day);

    @Update("update rental_log set return_date = now() where mobile=#{mobile} and id=#{id} and return_date is null")
    int returnBack(String mobile, String id);

    @Select("select in_stock from car_in_stock where car_model = #{type}")
    int getInStock(String type);

    @Select("select r.id as \"id\", c.car_model_name \"car\" from rental_log r, car_in_stock c where r.mobile=#{mobile} and r.car_model=c.car_model and r.return_date is null")
    List<Map<String, String>> queryRental(String mobile);
}
