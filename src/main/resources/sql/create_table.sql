create table car_in_stock(
    id bigint primary key not null auto_increment,
    car_model varchar(1) not null,
    car_model_name  varchar(20) not null,
    in_stock int not null comment '库存',
    one_day_cost decimal not null comment '租一天的价格',
    update_date timestamp not null
);

create table user_info(
    id bigint primary key not null auto_increment,
    user_name varchar(100) not null,
    mobile varchar(20) not null,
    account_balance decimal not null comment '账户余额'
);

create table rental_log(
    id bigint primary key not null auto_increment,
    mobile varchar(20) not null,
    car_model varchar(1) not null,
    create_date timestamp not null,
    return_date timestamp comment '还车时间',
    duration int not null comment '租车天数'
);

insert into car_in_stock(car_model,car_model_name,in_stock,one_day_cost,update_date) values ('1', 'Toyota Camry', 2, 400, now());
insert into car_in_stock(car_model,car_model_name,in_stock,one_day_cost,update_date) values ('2', 'BMW 650', 2, 700, now());
insert into user_info(user_name, mobile, account_balance) values ('jeff', '13800138000', 2000);
insert into user_info(user_name, mobile, account_balance) values ('tom', '13800138111', 300);
insert into user_info(user_name, mobile, account_balance) values ('kate', '13800138222', 0);