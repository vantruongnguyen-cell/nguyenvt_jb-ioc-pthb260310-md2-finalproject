create table Admin(
    admin_id int primary key auto_increment,
    username varchar(50) not null unique,
    password varchar(255) not null
);

create table Product(
    product_id int primary key auto_increment ,
    product_name varchar(100) not null ,
    brand varchar(50) not null ,
    price decimal(12,2) not null ,
    stock int not null
);

create table Customer(
    customer_id int primary key auto_increment ,
    customer_name varchar(100) not null ,
    phone varchar(20) ,
    email varchar(100) unique,
    address varchar(255)
);

create table Invoice(
    invoice_id int primary key auto_increment ,
    customer_id int references Customer(customer_id),
    created_at datetime default current_timestamp,
    total_amount decimal(12,2) not null
);

create table Invoice_Details(
    id int primary key auto_increment ,
    invoice_id int references Invoice(invoice_id),
    product_id int references Product(product_id),
    quantity int not null ,
    unit_price decimal(12,2) not null
);

-- vi du them tai khoan
INSERT INTO Admin (username, password)
VALUES ('admin', '123456');
select * from Admin;
select * from Product;
select * from Customer;
select * from Invoice;
select * from Invoice_Details;
