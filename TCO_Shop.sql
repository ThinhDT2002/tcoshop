use master
go

if exists(select * from sys.databases where name = 'TCO_Shop')
	drop database TCO_Shop

create database TCO_Shop

go
use TCO_Shop

go

create table Roles(
	Id varchar(10) not null,
	Name nvarchar(20) not null,
	primary key (Id)
)

go

create table Users(
	Username varchar(30) not null,
	Password varchar(30),
	Email varchar(40) not null,
	Fullname nvarchar(40),
	Address nvarchar(200),
	Phone nvarchar(10),
	Introduce nvarchar(300),
	Status bit,
	Activate_Code varchar(40),
	Forgot_Password_Code varchar(40),
	Avatar varchar(40),
	primary key (Username),
	Role_Id varchar(10) not null,
	constraint FK_Users_Roles
	foreign key (Role_Id) references Roles(Id),
	Create_Date date
)

go

create table Variations(
	Id varchar(20) not null,
	Name nvarchar(40) not null,
	primary key (Id)
)





create table Categories(
	Id varchar(10) not null,
	Name nvarchar(40) not null,
	Icon varchar(40),
	primary key (Id)
)

create table Subcategories(
	Id varchar(20) not null,
	Name nvarchar(40) not null,
	Category_Id varchar(10) not null,
	constraint FK_Subcategories_Categories
	foreign key (Category_Id) references Categories (Id),
	Icon varchar(40),
	primary key (Id)
)

create table Products(
	Id int identity(1,1),
	Name nvarchar(100) not null,
	Image1 varchar(50),
	Image2 varchar(50),
	Image3 varchar(50),
	Image4 varchar(50),
	Price decimal(12,2) not null,
	Description nvarchar(500),
	Stock int not null,
	Discount int,
	Category_Id varchar(10) not null,
	constraint FK_Products_Categories
	foreign key (Category_Id) references Categories(Id),
	Subcategory_Id varchar(20) not null,
	constraint FK_Products_Subcategories
	foreign key (Subcategory_Id) references Subcategories(Id),
	primary key (Id)
)

create table Products_Variations(
	Id int identity(1,1),
	Product_Id int,
	constraint FK_ProductsVariations_Products
	foreign key (Product_Id) references Products(Id) on delete cascade,
	Name nvarchar(40) not null,
	Value nvarchar(50) not null,
	primary key (Id)
)



create table Orders(
	Id int identity(1,1),
	Username varchar(30) not null,
	constraint FK_Orders_Users
	foreign key (Username) references Users (Username),
	Create_Date date,
	Status varchar(30),
	Address nvarchar(100),
	Phone_Number varchar(10),
	description nvarchar(300),
	expected_date date,
	order_Time_Detail varchar(50),
	is_Paid int,
	Shipping_cost decimal(10,2),
	primary key (Id)
)

create table Order_Status(
	Id varchar(30),
	Status_Name nvarchar(40),
	primary key(Id)
)


create table Reviews(
	Id int identity(1,1),
	Username varchar(30),
	constraint FK_Reviews_Users
	foreign key (Username) references Users(Username),
	Product_Id int,
	constraint FK_Reviews_Products
	foreign key (Product_Id) references Products(Id),
	Content nvarchar(300),
	Review_Time date,
	Review_Time_Detail varchar(30),
	edited bit,
	primary key (Id)
)

insert into Order_Status
values
('ChoXacNhan',N'Chờ xác nhận'),
('ChuanBi',N'Đang chuẩn bị hàng'),
('XuatKho',N'Đã xuất kho'),
('VanChuyen',N'Đang vận chuyển'),
('DaGiaoHang',N'Đã giao hàng'),
('HuyBo',N'Huỷ đơn')

create table Orders_Detail(
	Id int identity(1,1),
	Order_Id int not null,
	constraint FK_OrdersDetail_Orders
	foreign key (Order_Id) references Orders (Id) on delete cascade,
	Product_Id int not null,
	constraint FK_OrdersDetail_Products
	foreign key (Product_Id) references Products (Id),
	Quantity int,
	Price decimal(12,2),
	primary key (Id)
)

create table Favorites(
	Id int identity(1,1),
	Username varchar(30),
	constraint FK_Favorties_Users
	foreign key (Username) references Users(Username) on delete cascade,
	Product_Id int,
	constraint FK_Favorites_Products
	foreign key (Product_id) references Products(Id) on delete cascade,
	primary key(Id)
)

create table Transactions(
	Id int identity(1,1),
	Amount decimal(12,2),
	Bank_Code varchar(30),
	Card_Type varchar(30),
	Transaction_Info varchar(50),
	Pay_Date date,
	Pay_Time varchar(20),
	Pay_Status nvarchar(100),
	Transaction_Status nvarchar(250),
	Transaction_No varchar(30),
	Order_Id int,
	constraint FK_Transactions_Orders
	foreign key (Order_Id) references Orders(Id) on delete set null,
	primary key (Id),
	Bank_Tran_No varchar(30)
)



insert into Roles(id, name)
values('ADMIN','Administrators'),
	  ('USER','Users'),
	  ('SADMIN','Super Administrators')
	
insert into Users(username, password, email, fullname, address, phone,introduce, status, Activate_Code, Forgot_Password_Code, Avatar, Role_Id, Create_Date)
values('thinhdt15048','123456','thinhdtps15048@fpt.edu.vn',N'Đỗ Tiến Thịnh',N'Đường Đông Bắc, Quận 12','0337429180',N'Thịnh tha thiết',1,'0123456789','0123456789','avatar1.png', 'SADMIN', null),
	  ('vannd15047','123456','vanndtps15048@fpt.edu.vn',N'Nguyễn Đạt Văn',N'Đường Đông Bắc, Quận 12','0337429182',N'Văn vụng về',1,'0123456789','0123456789','avatar2.png', 'SADMIN', null),
	  ('anndd14885','123456','annddps14885@fpt.edu.vn',N'Nguyễn Đỗ Duy An',N'Đường Đông Bắc, Quận 12','0337429183',N'An x3',1,'0123456789','0123456789','avatar3.png', 'SADMIN', null),
	  ('khangtg15054','123456','khangtgps15054@fpt.edu.vn',N'Trần Gia Khang',N'Đường Đông Bắc, Quận 12','0337429181',N'Nick name của tôi là Cris Khang.
	   Tôi đến từ Bình Thuận. Hiện tại đang học lập trình viên Java tại trường cao đẳng FPT Polytechnic. Sở thích của tôi là nghe nhạc, chơi game, đá bóng. Tôi đang độc thân.' ,1,'0123456789','0123456789','avatar4.png', 'SADMIN', null),
	  ('antht15011','123456','anthtps15011@fpt.edu.vn',N'Trịnh Hữu Thiện Ân',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','avatar5.png', 'SADMIN', null),
	  ('john123','123456','jonh@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-07-05'),
	  ('john576','123456','haha@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-02-05'),
	  ('thinhdeptrai','123456','thienan@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-05-23'),
	  ('vandeptrai','123456','johnyoutube@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-11-20'),
	  ('duyan','123456','youtube123@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-11-11'),
	  ('quang','123456','abcsa@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-11-23'),

	  ('autate','123456','autate@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2021-07-05'),
	  ('frank','123456','frank@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2021-06-05'),
	  ('franky123','123456','franky123@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2021-05-23'),
	  ('khang1231321','123456','khange42131@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2021-11-20'),
	  ('anhuka','123456','huhukakak@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2021-01-11'),
	  ('hoangnhi','123456','hoangnhi@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2021-11-23'),
	  ('bao','123456','dsadsa@gmail.com',N'',N'Đường Đông Bắc, Quận 12','0337429184',N'',1,'0123456789','0123456789','', 'USER', '2022-11-05'),

	  ('Guest','123456','Guest@tcoshom.com',N'Khách','None', 0123456789, 'hello' , 1, 0123456789, 0123456789, 'avatar1.png', 'USER', null)
	  

insert into Categories(id, name, icon)
values
		('MEDIA',N'Tivi, Âm thanh, Loa kéo, Loa xách tay','tivi.png'),
		('FRIDGE',N'Tủ lạnh, Tủ đông, Tủ mát','fridge.png'),
		('LAPTOP',N'Laptop, linh kiện Laptop','laptop.png'),
		('WASHER',N'Máy giặt, Máy sấy','washer.png'),
		('AIRFRESHER',N'Máy lạnh, Quạt, Lọc khí','airFresher.png'),
		('ELECTRONIC',N'Gia dụng điện','iconElectric.png'),
		('PHONE',N'Điện thoại, Table, Phụ kiện','phone.png'),
		('PURIFIER',N'Lọc nước, Máy nước nóng','purifier.png'),
		('KITCHEN',N'Đồ dùng nhà bếp, Gia đình','iconBep.png'),
		('ROOM',N'Phòng khách, Phòng ăn','room.png'),
		('BEDROOM',N'Phòng ngủ, Trang trí','bedroom.png'),
		('HEALTH',N'Sức khỏe và sắc đẹp','health.png')

insert into Subcategories(id,name,Category_Id,icon)
values
		-- tivi
		('SSMEDIA','Samsung','MEDIA','samsung.png'),
		('LGMEDIA','LG','MEDIA','LG.png'),
		('SONYMEDIA','Sony','MEDIA','sony.png'),
		('TCLMEDIA','TCL','MEDIA','tcl.png'),
		('MiniMEDIA','Mini led','MEDIA','led.png'),

		-- sound
		('SOUNDH',N'Loa cao cấp','MEDIA','sounDh.png'),
		('SOUND',N'Dàn âm thanh','MEDIA','sounds.png'),
		('KARAOKE',N'Dàn karaoke','MEDIA','danKaraoke.png'),
		('BLUETOOTH',N'Loa bluetooth','MEDIA','bluetooth.png'),
		('SOUNDPC',N'Loa vi tính','MEDIA','sound.png'),
		('SOUNDKARAOKE',N'Loa karaoke','MEDIA','soundKaraoke.png'),
		('MIXER',N'Mixer','MEDIA','mixer.png'),
		('MICRO',N'Micro','MEDIA','micro.png'),


		-- laptop
		('AC','Acer','LAPTOP','acer.png'),
		('AS','Asus','LAPTOP','asus.png'),
		('MS','MSI','LAPTOP','MSI.jpg'),
		('LE','Lenovo','LAPTOP','lenovo.png'),

		-- air
		('REFRIGE',N'Máy lạnh','AIRFRESHER','refrige.png'),
		('FAN',N'Quạt','AIRFRESHER','fan.png'),
		('AIR',N'Lọc khí','AIRFRESHER','air.png'),

		-- ELECTRONIC
		('POT',N'Nồi điện','ELECTRONIC','pot.png'),
		('KITCHENELC',N'Bếp tiện nghi','ELECTRONIC','kitchenElec.png'),

		-- phone
		('PHONE',N'Điện thoại','PHONE','phone.png'),

		-- PURIFIER
		('PURIFIER',N'Lọc nước','PURIFIER','purifier.png'),

		-- kitchen
		('KITCHEN',N'Nhà bếp','KITCHEN','kitchen.png'),

		-- room
		('SOFA',N'Sofa','ROOM','sofa.png'),

		-- BEDROOM
		('BED',N'Giường','BEDROOM','bed.png'),

		-- health
		('MASK',N'Mặt nạ','HEALTH','mask.png'),

		-- FRIDGE
		('FRIDGE',N'Tủ lạnh','FRIDGE','fridge.png'),

		-- WASHER
		('WASHER',N'Máy giặt','WASHER','washer.png')

insert into Products(name,Image1,Image2,Image3,Image4,price,description,stock,discount,category_id,subcategory_id)
values
		(N'Smart Tivi Samsung UHD 4K  55 Inch UA55AU7700','UA55AU7700_1.jpg','UA55AU7700_2.jpg','UA55AU7700_3.jpg','UA55AU7700_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 4K UHD 50 Inch UA50AU7002','UA50AU7002_1.jpg','UA50AU7002_2.jpg','UA50AU7002_3.jpg','UA50AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,5,'MEDIA','SSMEDIA'),
		/*
		(N'Smart Tivi Samsung QLED 4K 65 Inch QA65Q60B','QA65Q60B_1.jpg','QA65Q60B_2.jpg','QA65Q60B_3.jpg','QA65Q60B_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 4K UHD 50 Inch UA50AU8000','UA50AU8000_1.jpg','UA50AU8000_2.jpg','UA50AU8000_3.jpg','UA50AU8000_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),		
		(N'Smart Tivi Samsung 4K UHD 55 Inch UA55AU7002','UA55AU7002_1.jpg','UA55AU7002_2.jpg','UA55AU7002_3.jpg','UA55AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		*/
		--LG
		(N'Smart Tivi LG 4K 55 Inch OLED 55A1PTA','55A1PTA_1.jpg','55A1PTA_2.jpg','55A1PTA_3.jpg','55A1PTA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,20,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Nanocell 4K LG 43 Inch 43NANO77TPA','43NANO77TPA_1.jpg','43NANO77TPA_2.jpg','43NANO77TPA_3.jpg','43NANO77TPA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,7,'MEDIA','SSMEDIA'),

		--sony
		(N'Google Tivi Sony 4K 50 Inch KD-50X80K','KD-50X80K_1.jpg','KD-50X80K_2.jpg','KD-50X80K_3.jpg','KD-50X80K_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,33,'MEDIA','SSMEDIA'),
		(N'Google Tivi Sony 4K 43 Inch KD-43X80K','KD-43X80K_1.jpg','KD-43X80K_2.jpg','KD-43X80K_3.jpg','KD-43X80K_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,7,'MEDIA','SSMEDIA'),

		--TCL		
		(N'TCL Android Tivi 32 Inch L32S5200','L32S5200_1.jpg','L32S5200_2.jpg','L32S5200_3.jpg','L32S5200_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,25,'MEDIA','SSMEDIA'),

		--Mini led
		(N'Smart Tivi QNED LG 4K 55 Inch 55QNED80SQA','55QNED80SQA_1.jpg','55QNED80SQA_2.jpg','55QNED80SQA_3.jpg','55QNED80SQA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,15,'MEDIA','SSMEDIA'),
		(N'Smart Tivi QNED LG 4K 65 Inch 65QNED80SQA','65QNED80SQA_1.jpg','65QNED80SQA_2.jpg','65QNED80SQA_3.jpg','65QNED80SQA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,15,'MEDIA','SSMEDIA'),	
		

		-- sound
		--Loa cao cấp
		(N'Loa Bluetooth B&O Beoplay A9 Black','a9black_1.jpg','a9black_2.jpg','a9black_3.jpg','a9black_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,15,'MEDIA','SSMEDIA'),
		(N'Loa Bluetooth B&O Beoplay A9 White','a9white_1.jpg','a9white_2.jpg','a9white_3.jpg','a9white_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,15,'MEDIA','SSMEDIA'),
		
		-- amply cao cấp
		(N'Loa Bluetooth B&O Beoplay A9 Black','a9black_1.jpg','a9black_2.jpg','a9black_3.jpg','a9black_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,15,'MEDIA','SSMEDIA'),
		(N'Amply Denon AVR-X2500HBKE2','X2500HBKE2_1.jpg','X2500HBKE2_2.jpg','X2500HBKE2_3.jpg','X2500HBKE2_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,50,'MEDIA','SSMEDIA'),

		-- dan karaoke
		(N'Dàn Âm Thanh Hifi Sony MHC-V43D/M1 SP6','V43D_1.jpg','V43D_2.jpg','V43D_3.jpg','V43D_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,60,'MEDIA','SSMEDIA'),

		--Loa bluetooth
		(N'Loa Bluetooth Marshall Acton II','acton_1.jpg','acton_2.jpg','acton_3.jpg','acton_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,5,'MEDIA','SSMEDIA'),
		(N'Loa Bluetooth OLIKE S1 Đen','OLIKE_1.jpg','OLIKE_2.jpg','OLIKE_3.jpg','OLIKE_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,5,'MEDIA','SSMEDIA'),

		-- loa vi tinh
		(N'Loa Bluetooth OLIKE S1 Đen','OLIKE_1.jpg','OLIKE_2.jpg','OLIKE_3.jpg','OLIKE_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,5,'MEDIA','SSMEDIA'),

		-- air
		--REFRIGE
		(N'Máy lạnh Panasonic 1 HP CU/CS-N9WKH-8M','8m_1.jpg','8m_2.jpg','8m_3.jpg','8m_4.jpg',9490000,N'Công nghệ lọc nanoe-G loại bỏ bụi nhỏ như PM2.5 hiệu quả',100,15,'AIRFRESHER','REFRIGE'),
		(N'Máy lạnh Sharp Inverter 1 Hp AH/AU-X9XEW','x9xew_1.jpg','x9xew_2.jpg','x9xew_3.jpg','x9xew_4.jpg',8990000,N'Laptop gaming tốt nhất phân khúc',100,15,'AIRFRESHER','REFRIGE'), 
		(N'Máy Lạnh Karofi Inverter 1.0 HP KDC-WI309','10CRN8_1.jpg','10CRN8_2.jpg','10CRN8_3.jpg','10CRN8_4.jpg',3290000,N'Laptop gaming tốt nhất phân khúc',100,15,'AIRFRESHER','REFRIGE'),

		-- FAN
		(N'Quạt Đứng Panasonic F409K','F409K_1.jpg','F409K_2.jpg','F409K_3.jpg','F409K_4.jpg',2800000,N'Điều khiển từ xa không dây thoải mái điều chỉnh tốc độ',100,10,'AIRFRESHER','FAN'),
		
		-- AIR
		(N'Máy lọc không khí Samsung AX34R3020WW/SV','3020WW_1.jpg','3020WW_2.jpg','3020WW_3.jpg','3020WW_4.jpg',3900000,N'Điều khiển từ xa không dây thoải mái điều chỉnh tốc độ',100,10,'AIRFRESHER','AIR'),

		-- ELECTRONIC
		-- POT
		(N'Nồi cơm điện Toshiba 1.8 lít RC-18NTFV(W)','18NTFV_1.jpg','18NTFV_2.jpg','18NTFV_3.jpg','18NTFV_4.jpg',2800000,N'Dung tích 1.8 lít thích hợp cho gia đình có khoảng 4 - 6 người',100,10,'ELECTRONIC','POT'),

		-- KITCHENELC
		(N'Bếp điện từ hồng ngoại Sunhouse Mama MMB9200MIX','MMB9200MIX_1.jpg','MMB9200MIX_2.jpg','MMB9200MIX_3.jpg','MMB9200MIX_4.jpg',1000000,N'Dung tích 1.8 lít thích hợp cho gia đình có khoảng 4 - 6 người',100,10,'ELECTRONIC','KITCHENELC'),

		--phone
		(N'Điện thoại Samsung Galaxy A13 4GB/128GB Xanh','GalaxyA13_1.jpg','GalaxyA13_2.jpg','GalaxyA13_3.jpg','GalaxyA13_4.jpg',3500000,N'Điện thoại chụp ảnh chất lượng với 4 camera sau 50MP + 5MP + 2MP + 2MP',100,10,'PHONE','PHONE'),
		
		--PURIFIER
		(N'Máy lọc nước RO nóng lạnh Sunhouse SHA76215CK','SHA76215CK_1.jpg','SHA76215CK_2.jpg','SHA76215CK_3.jpg','SHA76215CK_4.jpg',1100000,N'3 chế độ nước NÓNG – LẠNH – R.O tiện dụng',100,10,'PURIFIER','PURIFIER'),



		--kitchen
		(N'Bộ Nồi Inox 7 Món JUNGER CWJ-070','CWJ-070_1.jpg','CWJ-070_2.jpg','CWJ-070_3.jpg','CWJ-070_4.jpg',5800000,N'Bộ nồi 5 lớp 7 chi tiết bằng Inox 304 cao cấp',100,10,'KITCHEN','KITCHEN'),
		--room
		(N'Sofa DUBAI','DUBAI_1.jpg','DUBAI_2.jpg','DUBAI_3.jpg','DUBAI_4.jpg',6750000,N'Phù hợp trong không gian phòng khách',100,10,'ROOM','SOFA'),
		--BEDROOM
		(N'Giường Sunday 5317 1M6 Đen','Sunday5317_1.jpg','Sunday5317_2.jpg','Sunday5317_3.jpg','Sunday5317_4.jpg',8890000,N'Chân giường chịu tải tốt, đảm bảo sự cân đối',100,10,'BEDROOM','BED'),
		--health
		(N'Mặt nạ Derma LED LG BWL1','DUBAI_1.jpg','DUBAI_2.jpg','DUBAI_3.jpg','DUBAI_4.jpg',24000000,N'160 đèn LED Trẻ hóa làn da từ bên trong',100,10,'HEALTH','MASK'),
		--FRIDGE
		(N'Tủ Lạnh Samsung Inverter 234 Lít RT22FARBDSA/SV','RT22FARBDSA_1.jpg','RT22FARBDSA_2.jpg','RT22FARBDSA_3.jpg','RT22FARBDSA_4.jpg',5650000,N'Dung tích thực: 234 Lít',100,10,'FRIDGE','FRIDGE'),
		--WASHER
		(N'Máy giặt sấy Panasonic Inverter 10.5 kg NA-V105FR1BV','NA-V105FR1BV_1.jpg','NA-V105FR1BV_2.jpg','NA-V105FR1BV_3.jpg','NA-V105FR1BV_4.jpg',16750000,N'Hybrid Dry Lite tính năng sấy tiện ích',100,10,'WASHER','WASHER'),


		-- acer 
		/* 
		(N'Acer Aspire 7 A715 42G R4XX','r4xx_1.jpg','r4xx_2.jpg','r4xx_3.jpg','r4xx_4.jpg',14990000,N'Laptop gaming tốt nhất phân khúc',5,0,'LAPTOP','AC'),
		(N'Acer Aspire 7 A715 42G R05G','R05G_1.jpg','R05G_2.jpg','R05G_3.jpg','R05G_4.jpg',15990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Aspire 7 A715 43G R8GA','R8GA_1.jpg','R8GA_2.jpg','R8GA_3.jpg','R8GA_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Aspire 7 A715 75G 58U4','58U4_1.jpg','58U4_2.jpg','58U4_3.jpg','58U4_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 AN515 45 R6EV','R6EV_1.jpg','R6EV_2.jpg','R6EV_3.jpg','R6EV_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 Eagle AN515 57 5669','5669_1.jpg','5669_2.jpg','5669_3.jpg','5669_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 Eagle AN515 57 54MV','54MV_1.jpg','54MV_2.jpg','54MV_3.jpg','54MV_4.jpg',20990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 AN515 57 71VV','71VV_1.jpg','71VV_2.jpg','71VV_3.jpg','71VV_4.jpg',23490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 AN515 58 52SP','52SP_1.jpg','52SP_2.jpg','52SP_3.jpg','52SP_4.jpg',24390000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 Eagle AN515 57 57MX','57MX_1.jpg','57MX_2.jpg','57MX_3.jpg','57MX_4.jpg',24990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 Tiger AN515 58 773Y','773Y_1.jpg','773Y_2.jpg','773Y_3.jpg','773Y_4.jpg',28490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 AN515 45 R86D','R86D_1.jpg','R86D_2.jpg','R86D_3.jpg','R86D_4.jpg',28490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Nitro 5 Tiger AN515 58 79UJ','79UJ_1.jpg','79UJ_2.jpg','79UJ_3.jpg','79UJ_4.jpg',36490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Predator Helios 300 PH315 54 99S6','99S6_1.jpg','99S6_2.jpg','99S6_3.jpg','99S6_4.jpg',37490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Predator Helios 300 PH315 55 76KG','76KG_1.jpg','76KG_2.jpg','76KG_3.jpg','76KG_4.jpg',41490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Predator Helios 300 PH315 55 751D','751D_1.jpg','751D_2.jpg','751D_3.jpg','751D_4.jpg',52490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		*/
		(N'Acer Predator Triton 500 SE PT516 52S 75E3','75E3_1.jpg','75E3_2.jpg','75E3_3.jpg','75E3_4.jpg',64990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),
		(N'Acer Predator Triton 500 SE PT516 52S 91XH','91XH_1.jpg','91XH_2.jpg','91XH_3.jpg','91XH_4.jpg',105990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AC'),

		-- asus
		/*
		(N'Asus TUF FA506IHRB HN019W','HN019W_1.jpg','HN019W_2.jpg','HN019W_3.jpg','HN019W_4.jpg',14990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus TUF Gaming F15 FX506LH HN188W','HN188W_1.jpg','HN188W_2.jpg','HN188W_3.jpg','HN188W_4.jpg',15990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus TUF Gaming F15 FX506LHB HN188W','HN188W2_1.jpg','HN188W_2.jpg','HN188W_3.jpg','HN188W_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus ROG Strix G15 G513IH HN015W','HN015W_1.jpg','HN015W_2.jpg','HN015W_3.jpg','HN015W_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),		
		(N'Asus TUF Dash F15 FX516PC HN558W','HN558W_1.jpg','HN558W_2.jpg','HN558W_3.jpg','HN558W_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus TUF Gaming F15 FX506HC HN144W','HN144W_1.jpg','HN144W_2.jpg','HN144W_3.jpg','HN144W_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus TUF Gaming FX706HC HX105W','HX105W_1.jpg','HX105W_2.jpg','HX105W_3.jpg','HX105W_4.jpg',20990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus TUF Gaming FX706HCB HX105W','HX105WB_1.jpg','HX105WB_2.jpg','HX105WB_3.jpg','HX105WB_4.jpg',23490000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		 */
		(N'Asus ROG Strix G15 G513IE HN246W','HN246W_1.jpg','HN246W_2.jpg','HN246W_3.jpg','HN246W_4.jpg',24390000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),
		(N'Asus ROG Strix G15 G513IE HN192W','HN192W_1.jpg','HN192W_2.jpg','HN192W_3.jpg','HN192W_4.jpg',24990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','AS'),

		-- msi
		/* 
		(N'MSI Bravo 15 B5DD 276VN','276VN_1.jpg','276VN_2.jpg','276VN_3.jpg','276VN_4.jpg',14990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','MS'),
		(N'MSI GF63 Thin 11SC 664VN','664VN_1.jpg','664VN_2.jpg','664VN_3.jpg','664VN_4.jpg',15990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','MS'),
		(N'MSI Bravo 15 B5DD 275VN','275VN_1.jpg','275VN_2.jpg','275VN_3.jpg','275VN_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','MS'),
		(N'MSI GF63 Thin 11SC 662VN','662VN_1.jpg','662VN_2.jpg','662VN_3.jpg','662VN_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','MS'),
		*/
		(N'MSI Katana GF66 11UC 676VN','676VN_1.jpg','676VN_2.jpg','676VN_3.jpg','676VN_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','MS'),
		(N'MSI GF63 Thin 11UD 628VN','628VN_1.jpg','628VN_2.jpg','628VN_3.jpg','628VN_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'LAPTOP','MS'),


		-- tivi
		-- samsung
		(N'Smart Tivi Samsung 4K UHD 55 Inch UA55AU8000','AU8000_1.jpg','AU8000_2.jpg','AU8000_3.jpg','AU8000_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 4K UHD 43 Inch UA43AU7002','AU7002_1.jpg','AU7002_2.jpg','AU7002_3.jpg','AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA')
		/*
		(N'Smart Tivi Samsung QLED 4K 55 Inch QA55Q60B','QA55Q60B_1.jpg','QA55Q60B_2.jpg','QA55Q60B_3.jpg','QA55Q60B_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung QLED 4K 43 Inch QA43Q60B','QA43Q60B_1.jpg','QA43Q60B_2.jpg','QA43Q60B_3.jpg','QA43Q60B_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 4K UHD 65 Inch UA65AU8000','UA65AU8000_1.jpg','UA65AU8000_2.jpg','UA65AU8000_3.jpg','UA65AU8000_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 4K UHD 43 Inch UA43AU7002','UA43AU7002_1.jpg','UA43AU7002_2.jpg','UA43AU7002_3.jpg','UA43AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 4K UHD 43 Inch UA43AU8000','UA43AU8000_1.jpg','UA43AU8000_2.jpg','UA43AU8000_3.jpg','UA43AU8000_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung QLED 4K 50 Inch QA50Q60B','QA50Q60B_1.jpg','QA50Q60B_2.jpg','QA50Q60B_3.jpg','QA50Q60B_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung UHD 4K 43 Inch UA43AU7700','UA43AU7700_1.jpg','UA43AU7700_2.jpg','UA43AU7700_3.jpg','UA43AU7700_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA'),
		(N'Smart Tivi Samsung 32 Inch UA32T4500A','UA32T4500A_1.jpg','UA32T4500A_2.jpg','UA32T4500A_3.jpg','UA32T4500A_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',100,10,'MEDIA','SSMEDIA')
		*/

insert into Orders(Username, Create_Date, Status, Address, Phone_Number, description, expected_date, order_Time_Detail, is_Paid, Shipping_cost)
		values(N'antht15011', CAST(N'2020-12-20' AS Date), N'DaGiaoHang', N'Quan 3', N'0908987777', NULL, CAST(N'2020-12-27' AS Date), N'19:23:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2020-12-20' AS Date), N'DaGiaoHang', N'Quan 3', N'0909834323', NULL, CAST(N'2020-12-27' AS Date), N'19:25:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2020-12-20' AS Date), N'HuyBo', N'Quan 3', N'0908987777', N'', CAST(N'2020-12-27' AS Date), N'19:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2021-12-20' AS Date), N'ChoXacNhan', N'Quan 3', N'0908987777', N'', CAST(N'2021-12-27' AS Date), N'19:27:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2021-12-20' AS Date), N'ChoXacNhan', N'Quan 3', N'0908987777', N'', CAST(N'2021-12-27' AS Date), N'19:27:44', 1, CAST(0.00 AS Decimal(10, 2))),			
			(N'antht15011', CAST(N'2022-03-20' AS Date), N'DaGiaoHang', N'Quan 3', N'0908987777', NULL, CAST(N'2022-03-27' AS Date), N'19:40:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2022-06-20' AS Date), N'DaGiaoHang', N'Quan 3', N'0909834323', NULL, CAST(N'2022-06-27' AS Date), N'21:00:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2022-07-20' AS Date), N'ChoXacNhan', N'Quan 3', N'0908987777', N'', CAST(N'2022-07-27' AS Date), N'22:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'antht15011', CAST(N'2022-11-22' AS Date), N'ChoXacNhan', N'Quan 3', N'0908987777', N'', CAST(N'2022-11-29' AS Date), N'23:50:44', 1, CAST(0.00 AS Decimal(10, 2))),

			(N'anndd14885', CAST(N'2020-12-18' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2020-11-25' AS Date), N'19:23:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2020-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2020-12-27' AS Date), N'19:25:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2021-12-20' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2021-12-27' AS Date), N'19:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2022-03-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-03-27' AS Date), N'19:27:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2022-05-20' AS Date), N'ChuanBi', N'Quan 12', N'0908987777', N'', CAST(N'2022-05-27' AS Date), N'19:27:44', 1, CAST(0.00 AS Decimal(10, 2))),			
			(N'anndd14885', CAST(N'2022-07-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2022-07-27' AS Date), N'19:40:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2022-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2022-12-27' AS Date), N'21:00:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2022-12-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-27' AS Date), N'22:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2022-12-21' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-28' AS Date), N'22:30:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'anndd14885', CAST(N'2022-12-22' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-29' AS Date), N'23:50:44', 1, CAST(0.00 AS Decimal(10, 2))),

			(N'khangtg15054', CAST(N'2020-12-18' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2020-11-25' AS Date), N'19:23:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2020-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2020-12-27' AS Date), N'19:25:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2021-12-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2021-12-27' AS Date), N'19:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2022-05-20' AS Date), N'ChuanBi', N'Quan 12', N'0908987777', N'', CAST(N'2022-05-27' AS Date), N'19:27:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2022-08-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-08-27' AS Date), N'19:27:44', 1, CAST(0.00 AS Decimal(10, 2))),			
			(N'khangtg15054', CAST(N'2022-11-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2022-11-27' AS Date), N'21:00:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2022-11-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-11-27' AS Date), N'22:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2022-12-21' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-28' AS Date), N'22:30:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'khangtg15054', CAST(N'2022-12-22' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-29' AS Date), N'23:50:44', 1, CAST(0.00 AS Decimal(10, 2))),

			(N'thinhdt15048', CAST(N'2020-12-18' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2020-11-25' AS Date), N'19:23:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2020-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2020-12-27' AS Date), N'19:25:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2021-12-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2021-12-27' AS Date), N'19:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2022-01-20' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2022-01-27' AS Date), N'19:27:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2022-03-20' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2022-03-27' AS Date), N'19:27:44', 1, CAST(0.00 AS Decimal(10, 2))),			
			(N'thinhdt15048', CAST(N'2022-05-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2022-05-27' AS Date), N'19:40:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2022-07-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2022-07-27' AS Date), N'21:00:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2022-09-20' AS Date), N'ChuanBi', N'Quan 12', N'0908987777', N'', CAST(N'2022-09-27' AS Date), N'22:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2022-12-21' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-28' AS Date), N'22:30:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'thinhdt15048', CAST(N'2022-12-22' AS Date), N'ChuanBi', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-29' AS Date), N'23:50:44', 1, CAST(0.00 AS Decimal(10, 2))),

			(N'vannd15047', CAST(N'2020-12-18' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2020-11-25' AS Date), N'19:23:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2020-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2020-12-27' AS Date), N'19:25:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2021-12-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2021-12-27' AS Date), N'19:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-27' AS Date), N'19:27:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-27' AS Date), N'19:27:44', 1, CAST(0.00 AS Decimal(10, 2))),			
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0908987777', NULL, CAST(N'2022-12-27' AS Date), N'19:40:16', 2, CAST(0.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'DaGiaoHang', N'Quan 12', N'0909834323', NULL, CAST(N'2022-12-27' AS Date), N'21:00:45', 2, CAST(12000.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'ChoXacNhan', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-27' AS Date), N'22:27:08', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'ChuanBi', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-28' AS Date), N'22:30:27', 1, CAST(0.00 AS Decimal(10, 2))),
			(N'vannd15047', CAST(N'2022-12-20' AS Date), N'HuyBo', N'Quan 12', N'0908987777', N'', CAST(N'2022-12-29' AS Date), N'23:50:44', 1, CAST(0.00 AS Decimal(10, 2)))

insert into Orders_Detail(Order_Id, Product_Id, Quantity, Price)
		values(1, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (1, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (2, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (2, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (3, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (3, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (3, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (4, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (4, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (4, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (5, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (5, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (5, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (6, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (6, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (7, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (7, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (8, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (8, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (8, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (9, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (9, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (9, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),

			 (10, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (10, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (11, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (11, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (12, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (12, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (12, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (13, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (13, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (13, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (14, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (14, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (14, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (15, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (15, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (16, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (16, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (17, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (17, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (17, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (18, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (18, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (18, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (19, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (19, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (19, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),

			 (20, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (20, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (21, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (21, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (22, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (22, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (22, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (23, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (23, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (23, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (24, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (24, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (24, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),

			 (25, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (25, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (26, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (26, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (26, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (27, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (27, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (27, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (28, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (28, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (28, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),

			 (29, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (29, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (30, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (30, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (31, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (31, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (31, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (32, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (32, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (32, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (33, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (33, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (33, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (34, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (34, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (35, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (35, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (36, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (36, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (36, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (37, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (37, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (37, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (38, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (38, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (38, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),

			 (39, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (39, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (40, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (40, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (41, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (41, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (41, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (42, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (42, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (42, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (43, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (43, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (43, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (44, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (44, 3, 2, CAST(15192000.00 AS Decimal(12, 2))),
			 (45, 6, 1, CAST(17660700.00 AS Decimal(12, 2))),
			 (45, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (46, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (46, 7, 2, CAST(14242500.00 AS Decimal(12, 2))),
			 (46, 8, 2, CAST(16141500.00 AS Decimal(12, 2))),
			 (47, 2, 2, CAST(18040500.00 AS Decimal(12, 2))),
			 (47, 1, 2, CAST(17091000.00 AS Decimal(12, 2))),
			 (47, 8, 1, CAST(16141500.00 AS Decimal(12, 2))),
			 (48, 6, 2, CAST(17660700.00 AS Decimal(12, 2))),
			 (48, 5, 2, CAST(12723300.00 AS Decimal(12, 2))),
			 (48, 2, 2, CAST(18040500.00 AS Decimal(12, 2)))

insert into Transactions(Amount, Bank_Code, Card_Type, Transaction_Info, Pay_Date, Pay_Time, Pay_Status, Transaction_Status, Transaction_No, Bank_Tran_No)
values('120000000','VietcomBank', 'ATM', 'thanh toan hoa don', '2022-12-19', '19:00', N'Giao dịch thành công', N'Giao dịch thành công' , '12345678','VCB12345678'),
	('130000000','VietinBank', 'ATM', 'thanh toan hoa don','2022-12-19', '20:00', N'Giao dịch thành công', N'Giao dịch thành công', '12345678','VTB12345678'),
	('140000000','AgriBank', 'ATM', 'thanh toan hoa don', '2022-12-19', '21:00', N'Giao dịch thành công', N'Giao dịch thành công', '12345678','AGB12345678'),
	('144000000','MbBank', 'ATM', 'thanh toan hoa don', '2022-12-19', '22:00', N'Giao dịch thành công', N'Giao dịch thành công', '12345678','MB12345678'),
	('146000000','SacomBank', 'ATM', 'thanh toan hoa don', '2022-12-19', '23:00', N'Giao dịch thành công', N'Giao dịch thành công', '12345678','SA12345678')

insert into Variations(id, name) values ('DT', N'Dung tích'),
										('CS', N'Công xuất'),
										('CL', N'Chất liệu'),
										('CNHA', N'Công nghệ hình ảnh'),
										('DPG', N'Độ phân giải'),
										('HDH', N'Hệ điều hành'),
										('TNK', N'Tính năng khác'),
										('HSX', N'Hãng sản xuất'),
										('BH', N'Bảo hành'),
										('NRM', N'Năm ra mắt'),
										('KT', N'Kích thước'),
										('MS', N'Màu sắc'),
										('LSP', N'Loại sản phẩm'),
										('CTG', N'Chương trình giặt'),
										('HG', N'Hẹn giờ'),
										('CMN', N'Chọn mức nước'),
										('CNG', N'Công nghệ giặt'),
										('TDV', N'Tốc độ vắt')






