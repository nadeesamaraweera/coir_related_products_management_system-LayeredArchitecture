
DROP DATABASE IF EXISTS coir_related_products_management_system;

CREATE DATABASE IF NOT EXISTS coir_related_products_management_system;

USE coir_related_products_management_system;



CREATE TABLE employee(
                         employee_id VARCHAR(20) PRIMARY KEY,
                         employee_name VARCHAR(25) NOT NULL,
                         email VARCHAR(30),
                         tel INT (20),
                         job_title VARCHAR(15) NOT NULL,
                         salary  Double NOT NULL,
                         date   DATE,
                         INDEX (email)
);

DESC employee;



CREATE TABLE user(
                     user_id VARCHAR(20) PRIMARY KEY,
                     user_name VARCHAR(25) NOT NULL,
                     email VARCHAR(30),
                     password VARCHAR(20) NOT NULL,
                     employee_id VARCHAR(20) NOT NULL,
                     CONSTRAINT FOREIGN KEY(employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE customer(
                         customer_id VARCHAR(20) PRIMARY KEY,
                         customer_name VARCHAR(25) NOT NULL,
                         address VARCHAR(20) NOT NULL,
                         tel VARCHAR(15)
);


DESC customer;


CREATE TABLE orders(
                       Order_id VARCHAR(20) PRIMARY KEY,
                       date date,
                       customer_id VARCHAR(20) NOT NULL,
                       CONSTRAINT  FOREIGN KEY(customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DESC orders;

create table raw_material(
                             rawMaterial_id VARCHAR(20) PRIMARY KEY,
                             material_name VARCHAR(25) NOT NULL,
                             qty_on_stock DOUBLE NOT NULL,
                             unit_price DOUBLE NOT NULL



);


DESC raw_material;



CREATE TABLE item(
                     item_id VARCHAR(20) PRIMARY KEY ,
                     item_name VARCHAR(25) NOT NULL,
                     unit_price DOUBLE NOT NULL,
                     qty_on_hand INT NOT NULL,
                     rawMaterial_id VARCHAR(20) NOT NULL ,

                     CONSTRAINT FOREIGN KEY(rawMaterial_id) REFERENCES raw_material(rawMaterial_id) ON DELETE CASCADE ON UPDATE CASCADE


);

DESC item;


CREATE TABLE Order_detail(
                             Order_id VARCHAR(20) NOT NULL,
                             item_id VARCHAR(20) NOT NULL,
                             qty INT NOT NULL ,
                             unit_price DOUBLE NOT NULL,
                             CONSTRAINT FOREIGN KEY(Order_id) REFERENCES orders(Order_id) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT FOREIGN KEY(item_id) REFERENCES item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);


DESC Order_detail;


CREATE TABLE delivery(
                         delivery_id VARCHAR(20) PRIMARY KEY,
                         Order_id VARCHAR(20)NOT NULL,
                         employee_id VARCHAR(20) NOT NULL,
                         location VARCHAR(25) ,
                         delivery_status VARCHAR(20),
                         email varchar(30),

                         CONSTRAINT FOREIGN KEY(employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT FOREIGN KEY(Order_id) REFERENCES orders(Order_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT FOREIGN KEY(email) REFERENCES employee(email) ON DELETE CASCADE ON UPDATE CASCADE

);


DESC delivery;


CREATE TABLE supplier(
                         supplier_id VARCHAR(20) PRIMARY KEY,
                         supplier_name VARCHAR(25)NOT NULL,
                         address VARCHAR(15) NOT NULL,
                         tel INT (20)

);


DESC supplier;





CREATE TABLE supplier_detail(
                                supplier_id VARCHAR(20) NOT NULL,
                                rawMaterial_id VARCHAR(20) NOT NULL,
                                date DATE,
                                unit_price DOUBLE NOT NULL,
                                qty_on_stock INT(25),
                                FOREIGN KEY(supplier_id) REFERENCES supplier(supplier_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY(rawMaterial_id) REFERENCES raw_material (rawMaterial_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DESC supplier_detail;

INSERT INTO employee VALUES('E001', 'abc','kathrina@gmail.com', 071-2223334, 'Manager',50000.00,'2023-12-09');

INSERT INTO user VALUES ('U001','abc','nadeesamaraweera2000@gmail.com','1111','E001');


select i.item_id,
       i.item_name,
       i.unit_price,
       od.qty,
       od.qty * od.unit_price AS  payments,
       o.date
FROM  item i
          join Order_detail od on i.item_id=od.item_id
          join orders o on od.order_id=o.order_id
where od.order_id='O001';
