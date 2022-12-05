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
	Phone int,
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
	Name nvarchar(50) not null,
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
	foreign key (Order_Id) references Orders (Id),
	Product_Id int not null,
	constraint FK_OrdersDetail_Products
	foreign key (Product_Id) references Products (Id),
	Quantity int,
	Price decimal(12,2),
	primary key (Id)
)



insert into Roles(id, name)
values('ADMIN','Administrators'),
	  ('USER','Users'),
	  ('SADMIN','Super Administrators'),
	  ('SHIPPER','Shipper')
	
insert into Users(username, password, email, fullname, address, phone,introduce, status, Activate_Code, Forgot_Password_Code, Avatar, Role_Id)
values('thinhdt15048','123456','thinhdtps15048@fpt.edu.vn',N'Đỗ Tiến Thịnh',N'Đường Đông Bắc, Quận 12',0337429180,N'Thịnh tha thiết',1,'0123456789','0123456789','avatar1.png', 'SADMIN'),
	  ('vannd15047','123456','vanndtps15048@fpt.edu.vn',N'Nguyễn Đạt Văn',N'Đường Đông Bắc, Quận 12',0337429182,N'Văn vụng về',1,'0123456789','0123456789','avatar2.png', 'SADMIN'),
	  ('anndd14885','123456','annddps14885@fpt.edu.vn',N'Nguyễn Đỗ Duy An',N'Đường Đông Bắc, Quận 12',0337429183,N'An x3',1,'0123456789','0123456789','avatar3.png', 'SADMIN'),
	  ('khangtg15054','123456','khangtgps15054@fpt.edu.vn',N'Trần Gia Khang',N'Đường Đông Bắc, Quận 12',0337429181,N'Nick name của tôi là Cris Khang.
	   Tôi đến từ Bình Thuận. Hiện tại đang học lập trình viên Java tại trường cao đẳng FPT Polytechnic. Sở thích của tôi là nghe nhạc, chơi game, đá bóng. Tôi đang độc thân.'
	  ,1,'0123456789','0123456789','avatar4.png', 'SADMIN'),
	  ('antht15011','123456','anthtps15011@fpt.edu.vn',N'Trịnh Hữu Thiện Ân',N'Đường Đông Bắc, Quận 12',0337429184,N'Ân lo?',1,'0123456789','0123456789','avatar5.png', 'SADMIN'),
	  ('Guest','123456','Guest@tcoshom.com','Khách','None', 0123456789, 'hello' , 1, 0123456789, 0123456789, 'avatar1.png', 'USER')

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
		('KITCHEN',N'Bếp tiện nghi','ELECTRONIC','kitchen.png'),

		-- phone
		('PHONE',N'Điện thoại','PHONE','phone.png'),

		-- PURIFIER
		('PURIFIER',N'Lọc nước','PURIFIER','purifier.png'),

		-- kitchen


insert into Products(name,Image1,Image2,Image3,Image4,price,description,stock,discount,category_id,subcategory_id)
values
	-- laptop gaming 
		-- acer
		('Acer Aspire 7 A715 42G R4XX','r4xx_1.jpg','r4xx_2.jpg','r4xx_3.jpg','r4xx_4.jpg',14990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Aspire 7 A715 42G R05G','R05G_1.jpg','R05G_2.jpg','R05G_3.jpg','R05G_4.jpg',15990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Aspire 7 A715 43G R8GA','R8GA_1.jpg','R8GA_2.jpg','R8GA_3.jpg','R8GA_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Aspire 7 A715 75G 58U4','58U4_1.jpg','58U4_2.jpg','58U4_3.jpg','58U4_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 AN515 45 R6EV','R6EV_1.jpg','R6EV_2.jpg','R6EV_3.jpg','R6EV_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 Eagle AN515 57 5669','5669_1.jpg','5669_2.jpg','5669_3.jpg','5669_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 Eagle AN515 57 54MV','54MV_1.jpg','54MV_2.jpg','54MV_3.jpg','54MV_4.jpg',20990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 AN515 57 71VV','71VV_1.jpg','71VV_2.jpg','71VV_3.jpg','71VV_4.jpg',23490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 AN515 58 52SP','52SP_1.jpg','52SP_2.jpg','52SP_3.jpg','52SP_4.jpg',24390000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 Eagle AN515 57 57MX','57MX_1.jpg','57MX_2.jpg','57MX_3.jpg','57MX_4.jpg',24990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 Tiger AN515 58 773Y','773Y_1.jpg','773Y_2.jpg','773Y_3.jpg','773Y_4.jpg',28490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 AN515 45 R86D','R86D_1.jpg','R86D_2.jpg','R86D_3.jpg','R86D_4.jpg',28490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Nitro 5 Tiger AN515 58 79UJ','79UJ_1.jpg','79UJ_2.jpg','79UJ_3.jpg','79UJ_4.jpg',36490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Predator Helios 300 PH315 54 99S6','99S6_1.jpg','99S6_2.jpg','99S6_3.jpg','99S6_4.jpg',37490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Predator Helios 300 PH315 55 76KG','76KG_1.jpg','76KG_2.jpg','76KG_3.jpg','76KG_4.jpg',41490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Predator Helios 300 PH315 55 751D','751D_1.jpg','751D_2.jpg','751D_3.jpg','751D_4.jpg',52490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Predator Triton 500 SE PT516 52S 75E3','75E3_1.jpg','75E3_2.jpg','75E3_3.jpg','75E3_4.jpg',64990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),
		('Acer Predator Triton 500 SE PT516 52S 91XH','91XH_1.jpg','91XH_2.jpg','91XH_3.jpg','91XH_4.jpg',105990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AC'),

		-- asus
		('Asus TUF FA506IHRB HN019W','HN019W_1.jpg','HN019W_2.jpg','HN019W_3.jpg','HN019W_4.jpg',14990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus TUF Gaming F15 FX506LH HN188W','HN188W_1.jpg','HN188W_2.jpg','HN188W_3.jpg','HN188W_4.jpg',15990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus TUF Gaming F15 FX506LHB HN188W','HN188W2_1.jpg','HN188W_2.jpg','HN188W_3.jpg','HN188W_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus ROG Strix G15 G513IH HN015W','HN015W_1.jpg','HN015W_2.jpg','HN015W_3.jpg','HN015W_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),		
		('Asus TUF Dash F15 FX516PC HN558W','HN558W_1.jpg','HN558W_2.jpg','HN558W_3.jpg','HN558W_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus TUF Gaming F15 FX506HC HN144W','HN144W_1.jpg','HN144W_2.jpg','HN144W_3.jpg','HN144W_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus TUF Gaming FX706HC HX105W','HX105W_1.jpg','HX105W_2.jpg','HX105W_3.jpg','HX105W_4.jpg',20990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus TUF Gaming FX706HCB HX105W','HX105WB_1.jpg','HX105WB_2.jpg','HX105WB_3.jpg','HX105WB_4.jpg',23490000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus ROG Strix G15 G513IE HN246W','HN246W_1.jpg','HN246W_2.jpg','HN246W_3.jpg','HN246W_4.jpg',24390000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),
		('Asus ROG Strix G15 G513IE HN192W','HN192W_1.jpg','HN192W_2.jpg','HN192W_3.jpg','HN192W_4.jpg',24990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','AS'),

		-- msi
		('MSI Bravo 15 B5DD 276VN','276VN_1.jpg','276VN_2.jpg','276VN_3.jpg','276VN_4.jpg',14990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','MS'),
		('MSI GF63 Thin 11SC 664VN','664VN_1.jpg','664VN_2.jpg','664VN_3.jpg','664VN_4.jpg',15990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','MS'),
		('MSI Bravo 15 B5DD 275VN','275VN_1.jpg','275VN_2.jpg','275VN_3.jpg','275VN_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','MS'),
		('MSI GF63 Thin 11SC 662VN','662VN_1.jpg','662VN_2.jpg','662VN_3.jpg','662VN_4.jpg',17990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','MS'),
		('MSI Katana GF66 11UC 676VN','676VN_1.jpg','676VN_2.jpg','676VN_3.jpg','676VN_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','MS'),
		('MSI GF63 Thin 11UD 628VN','628VN_1.jpg','628VN_2.jpg','628VN_3.jpg','628VN_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'LAPTOP','MS'),


		-- tivi
		-- samsung
		('Smart Tivi Samsung 4K UHD 55 Inch UA55AU8000','AU8000_1.jpg','AU8000_2.jpg','AU8000_3.jpg','AU8000_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 4K UHD 43 Inch UA43AU7002','AU7002_1.jpg','AU7002_2.jpg','AU7002_3.jpg','AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung QLED 4K 55 Inch QA55Q60B','QA55Q60B_1.jpg','QA55Q60B_2.jpg','QA55Q60B_3.jpg','QA55Q60B_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung QLED 4K 43 Inch QA43Q60B','QA43Q60B_1.jpg','QA43Q60B_2.jpg','QA43Q60B_3.jpg','QA43Q60B_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 4K UHD 65 Inch UA65AU8000','UA65AU8000_1.jpg','UA65AU8000_2.jpg','UA65AU8000_3.jpg','UA65AU8000_4.jpg',18590000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 4K UHD 43 Inch UA43AU7002','UA43AU7002_1.jpg','UA43AU7002_2.jpg','UA43AU7002_3.jpg','UA43AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 4K UHD 43 Inch UA43AU8000','UA43AU8000_1.jpg','UA43AU8000_2.jpg','UA43AU8000_3.jpg','UA43AU8000_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung QLED 4K 50 Inch QA50Q60B','QA50Q60B_1.jpg','QA50Q60B_2.jpg','QA50Q60B_3.jpg','QA50Q60B_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung UHD 4K 43 Inch UA43AU7700','UA43AU7700_1.jpg','UA43AU7700_2.jpg','UA43AU7700_3.jpg','UA43AU7700_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 32 Inch UA32T4500A','UA32T4500A_1.jpg','UA32T4500A_2.jpg','UA32T4500A_3.jpg','UA32T4500A_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung UHD 4K  55 Inch UA55AU7700','UA55AU7700_1.jpg','UA55AU7700_2.jpg','UA55AU7700_3.jpg','UA55AU7700_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 4K UHD 50 Inch UA50AU7002','UA50AU7002_1.jpg','UA50AU7002_2.jpg','UA50AU7002_3.jpg','UA50AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung QLED 4K 65 Inch QA65Q60B','QA65Q60B_1.jpg','QA65Q60B_2.jpg','QA65Q60B_3.jpg','QA65Q60B_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Samsung 4K UHD 50 Inch UA50AU8000','UA50AU8000_1.jpg','UA50AU8000_2.jpg','UA50AU8000_3.jpg','UA50AU8000_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),		
		('Smart Tivi Samsung 4K UHD 55 Inch UA55AU7002','UA55AU7002_1.jpg','UA55AU7002_2.jpg','UA55AU7002_3.jpg','UA55AU7002_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		
		--LG
		('Smart Tivi LG 4K 55 Inch OLED 55A1PTA','55A1PTA_1.jpg','55A1PTA_2.jpg','55A1PTA_3.jpg','55A1PTA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi Nanocell 4K LG 43 Inch 43NANO77TPA','43NANO77TPA_1.jpg','43NANO77TPA_2.jpg','43NANO77TPA_3.jpg','43NANO77TPA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),

		--sony
		('Google Tivi Sony 4K 50 Inch KD-50X80K','KD-50X80K_1.jpg','KD-50X80K_2.jpg','KD-50X80K_3.jpg','KD-50X80K_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Google Tivi Sony 4K 43 Inch KD-43X80K','KD-43X80K_1.jpg','KD-43X80K_2.jpg','KD-43X80K_3.jpg','KD-43X80K_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),

		--TCL
		('TCL AI Tivi 40 Inch L40S6500','L40S6500_1.jpg','L40S6500_2.jpg','L40S6500_3.jpg','L40S6500_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('TCL Android Tivi 32 Inch L32S5200','L32S5200_1.jpg','L32S5200_2.jpg','L32S5200_3.jpg','L32S5200_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),

		--Mini led
		('Smart Tivi QNED LG 4K 55 Inch 55QNED80SQA','55QNED80SQA_1.jpg','55QNED80SQA_2.jpg','55QNED80SQA_3.jpg','55QNED80SQA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Smart Tivi QNED LG 4K 65 Inch 65QNED80SQA','65QNED80SQA_1.jpg','65QNED80SQA_2.jpg','65QNED80SQA_3.jpg','65QNED80SQA_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),	
		

		-- sound
		--Loa cao cấp
		('Loa Bluetooth B&O Beoplay A9 Black','a9black_1.jpg','a9black_2.jpg','a9black_3.jpg','a9black_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Loa Bluetooth B&O Beoplay A9 White','a9white_1.jpg','a9white_2.jpg','a9white_3.jpg','a9white_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		
		-- amply cao cấp
		('Loa Bluetooth B&O Beoplay A9 Black','a9black_1.jpg','a9black_2.jpg','a9black_3.jpg','a9black_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Amply Denon AVR-X2500HBKE2','X2500HBKE2_1.jpg','X2500HBKE2_2.jpg','X2500HBKE2_3.jpg','X2500HBKE2_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),

		-- dan karaoke
		('Dàn Âm Thanh Hifi Sony MHC-V13 M1 SP6','v13_1.jpg','v13_2.jpg','v13_3.jpg','v13_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Dàn Âm Thanh Hifi Sony MHC-V43D/M1 SP6','V43D_1.jpg','V43D_2.jpg','V43D_3.jpg','V43D_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),

		--Loa bluetooth
		('Loa Bluetooth Marshall Acton II','acton_1.jpg','acton_2.jpg','acton_3.jpg','acton_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Loa Bluetooth OLIKE S1 Đen','OLIKE_1.jpg','OLIKE_2.jpg','OLIKE_3.jpg','OLIKE_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),

		-- loa vi tinh
		('Loa Vi Tính A600','a600_1.jpg','a600_2.jpg','a600_3.jpg','a600_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA'),
		('Loa Bluetooth OLIKE S1 Đen','OLIKE_1.jpg','OLIKE_2.jpg','OLIKE_3.jpg','OLIKE_4.jpg',18990000,N'Laptop gaming tốt nhất phân khúc',5,10,'MEDIA','SSMEDIA')


insert into Reviews (Username, Product_Id, Content, Review_Time)
	values('khangtg15054', 1,N'Tuyệt Vời','2022-01-01')
