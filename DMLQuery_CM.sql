--====================================== INSERT ===================================================
insert into authenticated_user(first_name, last_name, role) 
values('Doan Minh', 'Tien', 1)

insert into badminton_club(badminton_club_name, description) values('Cau long cua Giap', N'Sân cầu lông đẹp vãi ò')
insert into badminton_club(badminton_club_name, description, address_id, court_manager_id) values('aaaaaa', N'aaaaaaa', 'A0000001', 'MNG000001')
insert into address(unit_number, ward, district, province) values('', N'Nguyễn Xiển', N'Phường Long Thạnh Mỹ', N'TP Hồ Chí Minh')

insert into authenticated_user(first_name)
values('aaaaaaaaa')

insert into password_reset_token(user_id, token, expiration_time, is_expired)
values('ADM000001', 'ABCCC', '2024-06-05 20:53:12', 0)

--	Nguyễn Xiển – Phường Long Thạnh Mỹ – Quận 9 – TP Hồ Chí Minh
--====================================== SELECT ===================================================
select badminton_club_name, description, a.unit_number +', '+ a.ward +', '+ a.district +', '+ a.province as A from badminton_club bc
inner join address a on bc.address_id = a.address_id
where a.address_id = 'A0000001'

select email, phone_number as phoneNumber,user_id as userId, password, birth_day as birthDay, 
register_date as registerDate, role, avatar_image_url as avatarImageUrl, 
first_name as firstName, last_name as lastName from authenticated_user
where email = 'giaphdse182890@fpt.edu.vn' or phone_number = '0867901057'

select user_id as userId, token, expiration_time as expirationTime from password_reset_token where user_id = 'ADM000004'

select * from address

delete from address
where address_id in ('A0000001', 'A0000002')

select * from authenticated_user
where user_id = 'STF000007 '

select * from authenticated_user au
where au.email = '867901057' or au.phone_number = '867901057'

select * from password_reset_token
--====================================== DELETE ===================================================
delete from badminton_club 
where address_id in ('A0000001')

delete from authenticated_user
where user_id in ('STF000007 ')

delete from password_reset_token
where token = '6bd8d75d-e7cd-43b7-b17c-db5a78bcc857'

use Court_Master
go

--========================================ALTER TABLE=================================================
alter table authenticated_user
add constraint phone_number_unique UNIQUE (phone_number)


Create table password_reset_token(
	user_id varchar(100),
	token varchar(150),
	expiration_time datetime,
	CONSTRAINT PK_prs_pk primary key (user_id),
	CONSTRAINT FK_prs_fk foreign key (user_id) references authenticated_user(user_id)
);

alter table password_reset_token 
add is_expired int

--=======================================UPDATE==================================
UPDATE authenticated_user
SET email = 'hotiencchk9@gmail.com'
where user_id = 'ADM000004 '

UPDATE authenticated_user
SET password = 'aaa'
where email = 'nhancd909@gmail.com'

update password_reset_token
set expiration_time = '2024-06-10 21:30:02.397', token = '534222', user_id = 'STF000007 '

select * from authenticated_user
select * from password_reset_token

delete from password_reset_token where token = '630556'

delete from password_reset_token where user_id = 'STF000007 '


--========================================= ADDRESS FUNCTION ==========================================

--SELECT code, name, full_name
--INTO province
--FROM VietNamAddress.dbo.provinces;

--SELECT code, name, full_name, province_code
--INTO districts
--FROM VietNamAddress.dbo.districts;

--SELECT code, name, full_name, district_code
--INTO wards
--FROM VietNamAddress.dbo.wards;

--alter table wards 
--add constraint FK_Wards_Districts foreign key (district_code) references districts(code)

--alter table province
--add constraint PK_Provinces primary key(code)

--alter table districts
--add constraint FK_Districts_Provinces foreign key (province_code) references province(code)

select code, name, full_name as fullName from provinces order by full_name asc

-- Lay full tinh/thanh pho
select full_name from provinces
-- Lay cac quan/huyen thuoc mot tinh/thanh pho 

-- TP HCM
select d.full_name from districts d
inner join provinces p on d.province_code = p.code
where p.full_name = N'Thành phố Hồ Chí Minh'
-- TP HN 
select d.full_name from districts d
inner join provinces p on d.province_code = p.code
where p.full_name like N'%Tỉnh Bắc Giang%'	

-- Lay cac quan/huyen thuoc mot tinh/thanh pho 
select p.full_name as province_name, d.full_name as district_name, w.full_name as ward_name from districts d
inner join provinces p on d.province_code = p.code
inner join wards w on w.district_code = d.code
where p.full_name like N'%Bắng Giang%'

-- Lay phuong/xa dua tren quan/huyen
select w.full_name from wards w
inner join districts d on w.district_code = d.code
inner join provinces p on d.province_code = p.code
where p.full_name = N'Thành phố Hồ Chí Minh' and d.full_name = N'Huyện Bình Chánh'
order by w.full_name asc

select * from districts


select * from wards

select * from address

select * from badminton_club
select * from authenticated_user
--========================================= CLUB FUNCTION ==========================================
-- Cau lenh truy van cac thong tin san de hien thi tren man hinh hompage
   select au.phone_number, bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, 
   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, 
   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc
   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1
   inner join address ad on bc.address_id = ad.address_id
   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id
   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
   inner join authenticated_user au on au.user_id = bc.court_manager_id
   group by au.phone_number, bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url


select * from badminton_club_image

select * from pricing_rule

--alter table pricing_rule 
--add flexible int,
--    fixed int,
--	one_time_play int

--([schedule_type]='Flexible' OR [schedule_type]='Fixed' OR [schedule_type]='One-time play')
 
alter table badminton_club
ADD CONSTRAINT default_badminton_club_status DEFAULT 1 FOR badminton_club_status;


select * from time_frame
select * from pricing_rule
Order by time_frame_id asc
select * from authenticated_user
select * from badminton_club

--ADD A CLUB 
insert into badminton_club(badminton_club_name, address_id, description, court_manager_id)
values--(N'Best court','A0000006','jksjdlfkj','STF000004 '),
      --(N'A court','A0000007','jksjdlfkj','STF000005 '),
	  --(N'B court','A0000008','jksjdlfkj','STF000007 '),
	  (N'C court','A0000009','jksjdlfkj','STF000008 ')

--ADD CLUB ADDRESS
select * from address
--delete from address
--where address_id in ('A0000004', 'A0000005', 'A0000006', 'A0000007', 'A0000008')
insert into address(unit_number, ward, district, province)
values--(N'Hẻm 133 Gò Dầu',N'Tân Quý',N'Tân Phú',N'Thành phố Hồ Chí Minh'),
		--(N'60 Hẻm 75 Đ. Gò Dầu',N'Tân Quý',N'Tân Phú',N'Thành phố Hồ Chí Minh'), --60 Hẻm 75 Đ. Gò Dầu, Tân Quý, Tân Phú, Thành phố Hồ Chí Minh, Vietnam
		--(N'Hẻm 456 TCH 10',N'Tân Chánh Hiệp',N'Quận 12',N'Thành phố Hồ Chí Minh'), -- Hẻm 456 TCH 10, Tân Chánh Hiệp, Quận 12, Thành phố Hồ Chí Minh, Vietnam
		--(N'1 67',N'Tổ 4',N'Hóc Môn',N'Thành phố Hồ Chí Minh'), -- 1 67, Tổ 4, Hóc Môn, Thành phố Hồ Chí Minh, Vietnam
		--(N'Hẻm 258 Dương Bá Trạc',N'Phường 2',N'Quận 8',N'Thành phố Hồ Chí Minh'), 
		(N'684-710 ĐT743',N' Phú Hoà',N'Thủ Dầu Một',N'Bình Dương') --684-710 ĐT743, Phú Hoà, Thủ Dầu Một, Bình Dương, Vietnam

-- ADD Time frame 
select * from time_frame
insert into time_frame(start_time, end_time, badminton_club_id)
values('05:30:00', '22:30:00', 'C0000007')

select * from badminton_club
where 1 = 1 

select * from badminton_club_image

insert into badminton_club_image(image_id, image_url, is_main_avatar, badminton_club_id)
values('6','ad.png',1,'C0000006')

-- ADD pricing rule

select * from pricing_rule
where time_frame_id = 'TF000004'

select * from pricing_rule
where time_frame_id = 'TF000005'

select * from pricing_rule
where time_frame_id = 'TF000006'

select * from pricing_rule
where time_frame_id = 'TF000007'

select * from pricing_rule
where time_frame_id = 'TF000008'

select * from pricing_rule
where time_frame_id = 'TF000009'

select * from badminton_club_image

insert into pricing_rule(day_of_week, price_per_hour, time_frame_id, flex)



select * from address ad
inner join badminton_club bc on ad.address_id = bc.address_id

select * from badminton_club bcl 
inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
where bcl.badminton_club_id = 'C0000001'

select * from time_frame tf
inner join badminton_club bc on tf.badminton_club_id = bc.badminton_club_id 

select * from badminton_court bc
left join booking_slot bs on bs.badminton_court_id = bc.badminton_court_id
where booking_slot_id is null



where start_time > '05:30'


--C0000003 co san 


insert into booking_slot(start_time, end_time, booking_date, price, badminton_court_id, booking_schedule_id)
values('15:30', '19:00', '2024-06-15', 73000, 'CO000004', 'SD0000001')


select *from badminton_club_image
delete from booking_schedule
delete from booking_slot
insert into booking_schedule(customer_fullname, customer_phone_number, booking_schedule_status, start_date, end_date, schedule_type, customer_id, total_price)
values('Giap', '0867901057', 'Deposited 25%', '2024-06-18', '2024-06-18', 'One-time play', 'STF000002', 35000)
--===================================== FILTER SEARCH =============================================
-- <1> Khong dinh thoi gian choi, chi dinh ten va dia chi 

 select t1.clubId, t1.clubName, t1.clubAddress, t1.clubImageName, t1.averagePrice from (
   select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, 
   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, 
   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc
   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1
   inner join address ad on bc.address_id = ad.address_id
   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id
   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
   group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url
) as t1 where t1.clubId in (

     select bcl.badminton_club_id from badminton_club bcl
     inner join address ad on bcl.address_id = ad.address_id
     where 1=1 

	 --and ((bcl.badminton_club_name like N'%Nguyễn%') or (ad.unit_number like N'%Nguyễn%')) 
     and ad.province like N'%Hồ Chí Minh%' 
     --and ad.district like N'%Thành phố Thủ Đức%' 
     and ad.ward like N'%Long Thạnh Mỹ%'

)

select * from time_frame tf
inner join badminton_club bcl on tf.badminton_club_id = bcl.badminton_club_id

select * from authenticated_user au
inner join badminton_club bcl on au.user_id = bcl.court_manager_id

--  <2> FILTER CO THEM THOI GIAN CHOI (FILTER HOAN CHINH)
 select t1.clubId, t1.clubName, t1.clubAddress, t1.clubImageName, t1.averagePrice from (
   select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, 
   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, 
   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc
   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1
   inner join address ad on bc.address_id = ad.address_id
   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id
   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
   group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url
) as t1 where t1.clubId in (
	 select bcl.badminton_club_id from badminton_club bcl
	 inner join address ad on bcl.address_id = ad.address_id
	 inner join badminton_court bc on bc.badminton_club_id = bcl.badminton_club_id 
	 inner join time_frame tf on tf.badminton_club_id = bcl.badminton_club_id
	 left join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id
	 	 where 1=1 
	 	 and ((bcl.badminton_club_name like N'%%') or (ad.unit_number like N'%%')) 
	      and ad.province like N'%%' 
	      and ad.district like N'%%' 
	      and ad.ward like N'%%'
	 	 --and tf.start_time = '06:30'
	 	 and bc.badminton_court_id not in (
	 	    Select bc.badminton_court_id from badminton_club bcl 
	 		inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
	 		left join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id
	 	 	where (start_time <='19:00' and end_time > '19:00')
			group by bc.badminton_court_id
		 )

	 group by bcl.badminton_club_id
)

--===============================================================================
select * from authenticated_user
select * from badminton_court
select * from badminton_club
select * from booking_slot
select * from booking_schedule
select * from badminton_club
select * from time_frame
select * from address
select * from badminton_club bcl 

select * from badminton_club_image


insert into badminton_club_image(image_id, image_url, is_main_avatar, badminton_club_id)
values('8', 'window1.jpg', 0, 'C0000001'),
	  ('9', 'window2.jpg', 0, 'C0000001'),
	  ('10', 'window3.jpg', 0, 'C0000001')

select * from pricing_rule pr 
inner join time_frame tf on pr.time_frame_id = tf.time_frame_id

--============================= GET ALL IMAGE OF A CLUB ===============================
select bci.image_url from badminton_club bcl 
inner join badminton_club_image bci on bcl.badminton_club_id = bci.badminton_club_id
where bcl.badminton_club_id = 'C0000001'

--============ lay chi tiet club
select au.phone_number, bcl.badminton_club_name, bcl.badminton_club_id, bcl.description, 
CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress from badminton_club bcl
inner join address ad on bcl.address_id = ad.address_id
inner join authenticated_user au on au.user_id = bcl.court_manager_id
where bcl.badminton_club_id = 'C0000002'

--============ Lay chi tiet san dua vao club
select bc.badminton_court_id, bc.badminton_court_name, badminton_court_status from badminton_court bc 
inner join badminton_club bcl on bc.badminton_club_id = bcl.badminton_club_id
where bcl.badminton_club_id = 'C0000002'

group by au.phone_number, bcl.badminton_club_name, bcl.badminton_club_id, bcl.description, 
CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province)

select * from badminton_court


-- Sua laij

select * from badminton_club bcl
inner join address ad on bcl.address_id = ad.address_id
inner join authenticated_user au on au.user_id = bcl.court_manager_id
inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
where bcl.badminton_club_id = 'C0000002'


--========================== GET TIME FRAME + PRICING RULE HANDLE=======================
select bcl.badminton_club_id, tf.time_frame_id, tf.start_time, tf.end_time, pr.day_of_week, pr.one_time_play, pr.fixed, pr.flexible from badminton_club bcl 
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
inner join pricing_rule pr on pr.time_frame_id = tf.time_frame_id
where bcl.badminton_club_id = 'C0000003' --and tf.time_frame_id = 'TF000003'

--============================

select * from badminton_club bcl 
inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
where bcl.badminton_club_id = 'C0000003'

SELECT * from badminton_club bcl 
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id

select * from authenticated_user

insert into customer_rate(rating_point, comment, rating_date_time, badminton_club_id, customer_id)     
values(5, N'RẤT TÔT', '2024-06-17 22:43:00', 'C0000002', 'STF000001 ')

--=========================== COMMENT HANDLE==========================
select CONCAT(au.first_name,' ',au.last_name) as customerName, 
au.avatar_image_url, cr.comment, cr.rating_point, cr.rating_date_time from badminton_club bcl  
inner join customer_rate cr on bcl.badminton_club_id = cr.badminton_club_id
inner join authenticated_user au on cr.customer_id = au.user_id
where bcl.badminton_club_id = 'C0000002'




--============================XAC DINH BOOKING SLOT THUOC TIME FRAME NAO =========================
-- Truyen court_id vao de xac dinh thuoc cau lac bo nao tu do => time frame cua cau lac bo do

	--select * from (
	--	select tf.time_frame_id, tf.start_time, tf.end_time from badminton_court bc 
	--	inner join badminton_club bcl on bc.badminton_club_id = bcl.badminton_club_id
	--	inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
	--	inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
	--	where  bc.badminton_court_id = 'CO000004'-- bcl.badminton_club_id = 'C0000001'/ bc.badminton_court_id = 'CO000004'
	--) as t1 
	--where ('17:31' >= t1.start_time and '21:00' <= t1.end_time) or ('17:31' >= t1.start_time and '17:31' <= t1.end_time or '21:00' > t1.start_time )

--select * from badminton_club bcl 
--inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id

select * from badminton_club bcl 
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id

DECLARE @start_time_booking TIME, @end_time_booking TIME;
SET @start_time_booking = '12:30'; -- Set the start time booking value
SET @end_time_booking = '17:30'; -- Set the end time booking value

SELECT
	bc.badminton_club_id,
    tf.time_frame_id,
    tf.start_time,
    tf.end_time,
	pr.day_of_week,
	pr.flexible,
	pr.fixed,
	pr.one_time_play
FROM
    badminton_court bc
	INNER JOIN badminton_club bcl ON bc.badminton_club_id = bcl.badminton_club_id
    INNER JOIN time_frame tf ON bcl.badminton_club_id = tf.badminton_club_id
    INNER JOIN pricing_rule pr ON tf.time_frame_id = pr.time_frame_id
    CROSS APPLY (
        SELECT
            time_frame_id,
            start_time,
            end_time,
            CASE
                WHEN @start_time_booking >= start_time AND @end_time_booking <= end_time THEN 1
                WHEN @start_time_booking < start_time AND @end_time_booking > end_time THEN 1
                WHEN @start_time_booking >= start_time AND @start_time_booking < end_time AND @end_time_booking > end_time THEN 1
                WHEN @start_time_booking < start_time AND @end_time_booking >= start_time AND @end_time_booking <= end_time THEN 1
                ELSE 0
            END AS is_valid
        FROM
            time_frame
        WHERE
            time_frame_id = tf.time_frame_id
    ) cte
WHERE
    bc.badminton_court_id = 'CO000004'
    AND cte.is_valid = 1;

	select * from authenticated_user
	

--=========================== CALCULATE PRICE FOR A BOOKING_SLOT==========================
-- da thuc hien tinh trong spring boot

select * from badminton_club bcl
inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
where bcl.badminton_club_id = 'C0000003'

select * from badminton_club bcl
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
where bcl.badminton_club_id = 'C0000003'

select * from time_frame tf
inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
where tf.time_frame_id in ('TF000005') --'TF000010', 'TF000011', 'TF000012', 'TF000013', 'TF000014')

select * from pricing_rule
--TF000010
insert into pricing_rule(day_of_week, time_frame_id, flexible, fixed, one_time_play)
values--('Mon', 'TF000014', 34000, 30000, 35000),
	  --('Tue', 'TF000014', 32000, 30000, 35000),
	  --('Wed', 'TF000014', 32000, 30000, 35000),
	  --('Thu', 'TF000014', 32000, 31000, 35000),
	  --('Fri', 'TF000014', 32000, 47000, 35000),
	  --('Sat', 'TF000014', 35000, 31000, 35000),
	  --('Sun', 'TF000014', 32000, 30000, 25000),

update time_frame 
set end_time = '23:00:00.0000000'
where time_frame_id = 'TF000014'

--========================= UPLOAD IMAGE===================
values(N'aaaa', N'ab', N'ac', N'Tỉnh Bình Dương')
select * from badminton_club
select * from badminton_club_image

insert into badminton_club_image(image_url,is_main_avatar, badminton_club_id)
values('url', 0, 'bclid')

--delete badminton_club_image
--where badminton_club_id = 'C0000006'

select * from booking_slot bs 
inner join badminton_court bc on bs.badminton_court_id = bc.badminton_court_id
where bc.badminton_court_id = 'CO000001'
order by start_time asc

-- ========================= KIEM TRA XEM BOOKING_SLOT CO BI TRUNG HAY KO ==========================



-- Lay tat ca cac booking_slot tren cac san ma khach hang thuc hien dat 
select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bc.badminton_court_id, bc.badminton_court_name from booking_slot bs
inner join badminton_court bc on bc.badminton_court_id = bs.badminton_court_id
where bc.badminton_court_id = 'CO000001' --or bc.badminton_court_id = 'CO000003' --or bc.badminton_court_id = 'CO000004'
order by bc.badminton_court_id, bs.start_time

--select * from booking_slot
--insert into booking_slot(start_time, end_time, booking_date, price, badminton_court_id, booking_schedule_id)
--values('08:30:00.0000000', '09:30:00.0000000', '2024-06-15', 320000, 'CO000003' , 'SD0000001')

--==========================================
select tf.start_time, tf.end_time from badminton_club bcl 
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
where bcl.badminton_club_id = 'C0000001' order by tf.start_time asc

--==================== Hien thi booking slot trong ham onMount
--C0000003
select bc.badminton_court_id, bc.badminton_court_name, bs.start_time, bs.end_time, bs.booking_date, bs.booking_slot_id, bs.is_check_in, bs.price, CONCAT(au.first_name,' ',au.last_name ) as cusFullName, au.user_id from badminton_club bcl 
inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id
inner join booking_schedule bsd on bs.booking_schedule_id = bsd.booking_schedule_id
inner join authenticated_user au on bsd.customer_id = au.user_id
where bcl.badminton_club_id = 'C0000003'
order by start_time
--==================== INSERT BOOKING SCHEDULE VAO DATABASE=======================
select * from authenticated_user
insert into booking_schedule(customer_fullname, customer_phone_number, booking_schedule_status, start_date, end_date, schedule_type, customer_id, total_price)
values('AA', '0101010101', 'Deposited 50%', '2024-06-26', '2024-06-26', 'Fixed', 'STF000001 ', 320000)

select TOP 1 booking_schedule_id from booking_schedule
ORDER BY booking_schedule_id DESC

insert into booking_slot(start_time, end_time, booking_date, price, badminton_court_id, booking_schedule_id)
values(?, ?, ?, ?, ?, ?)

select * from badminton_club bcl
INNER JOIN authenticated_user au on bcl.court_manager_id = au.user_id
where bcl.badminton_club_id='C0000002'

select * from authenticated_user
--=========================== XEM KET QUA THANH TOAN NGAY HOAC FIXED ===============	
select * from booking_schedule
select * from booking_slot
where booking_schedule_id = 'SD0000009'
select * from invoice
select * from payment_detail
select * from customer_playable_time
select * from booking_slot

select * from badminton_club bcl 
inner join authenticated_user au on bcl.court_manager_id = au.user_id


select * from badminton_court bc
where bc.badminton_club_id = 'C0000002'
--============================== DELETE THANH TOAN =================================
delete payment_detail

delete invoice
--where badminton_club_id = 'C0000002'

delete booking_slot
where badminton_court_id in ('CO000005', 'CO000011', 'CO000012', 'CO000004')

delete booking_schedule
--where customer_fullname = N'AB'  
where booking_schedule_id in ('SD0000003', 'SD0000004', 'SD0000005', 'SD0000006', 'SD0000002')

delete customer_playable_time
where customer_id = 'STF000002' and badminton_club_id = 'C0000002'
--============================== DELETE THANH TOAN =================================

select * from authenticated_user

select cpt.playable_time from badminton_club bcl
inner join customer_playable_time cpt on bcl.badminton_club_id = cpt.badminton_club_id
where cpt.customer_id = 'STF000001'


select bcl.badminton_club_id, bcl.badminton_club_name, tf.start_time, tf.end_time from badminton_club bcl 
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
order by bcl.badminton_club_id, tf.start_time

select * from badminton_club bcl 
inner join address ad on bcl.address_id = ad.address_id
--=============================== THANH TOAN CHO KIEU FLEXIBLE ==========================
select * from customer_playable_time
alter table booking_schedule
add total_playing_time int 
select * from badminton_club
--STF000002
--C0000002
insert into customer_playable_time(customer_id, badminton_club_id, playable_time)
values('STF000002', 'C0000002', 500)

select * from customer_playable_time
where customer_id = 'STF000002' and badminton_club_id = 'C0000002'

update customer_playable_time
set playable_time = 500
where customer_id = 'STF000002' and badminton_club_id = 'C0000002'

--=============================== Xem lich su thanh toan (BOOKING HISTORY) ==========================
select * from booking_schedule
select * from booking_slot
--where badminton_court_id in ('CO000005', 'CO000011', 'CO000012', 'CO000004')
select * from invoice
select * from payment_detail
select * from customer_playable_time

-- lay lich su cua booking schedule 
select iv.badminton_club_name, iv.court_manager_phone, bs.booking_schedule_id, bs.booking_schedule_status, bs.start_date, bs.end_date, bs.schedule_type, bs.total_price, bs.total_playing_time, iv.booking_phone_number from booking_schedule bs
inner join invoice iv on bs.booking_schedule_id = iv.booking_schedule_id
where bs.customer_id = 'STF000002 '

    --private String clubName;
    --private String courtManagerPhone;
    --private String bookingScheduleId;
    --private String bookingScheduleStatus;
    --private LocalDate startDate;
    --private LocalDate endDate;
    --private String scheduleType;
    --private int totalPrice;
    --private int totalPlayingTime;
-- Xem lich su thanh toan booking slot

select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bs.is_check_in, price, bs.badminton_court_id, bc.badminton_court_name from  booking_slot bs
inner join badminton_court bc on bs.badminton_court_id = bc.badminton_court_id
where booking_schedule_id = 'SD0000003'

    --private String courtName;
  

select pd.payment_id, pd.amount, pd.payment_time, pd.payment_method from booking_schedule bsd
inner join invoice iv on bsd.booking_schedule_id = iv.booking_schedule_id
inner join payment_detail pd on iv.invoice_id = pd.invoice_id
where bsd.booking_schedule_id = ''

--===========================THANH TOAN DANG KY GIO CHOI =============================
select * from playable_time_payment
insert into playable_time_payment(payment_id, amount, minute_amount, payment_method, payment_time, customer_id, badminton_club_id, badminton_club_name)
values('aaaa', 123000, 800, 'ATM', '2024-07-01 09:18:20', 'STF000002', 'C0000002', 'Cho Nhannnnn')

select playable_time from customer_playable_time
where customer_id = 'STF000002' and badminton_club_id = 'C0000002'

insert into customer_playable_time(customer_id, badminton_club_id, playable_time)
values('STF000002', 'C0000002', 500)
select * from badminton_club

update customer_playable_time
set playable_time = 3000
where customer_id = 'STF000002' and badminton_club_id = 'C0000002' 

select * from playable_time_payment 
select * from customer_playable_time
--====================================== TINH TIEN DANG KY GIO CHOI CHO FLEXIBLE
select flexible from badminton_club bcl 
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
where bcl.badminton_club_id = 'C0000002'
group by flexible

--================================== UPDATE STATUS CHO BOOKING SCHEDULE
select * from booking_schedule
update booking_schedule
set booking_schedule_status = 'Paid'
where booking_schedule_id = 'SD0000003'

select * from badminton_club bcl inner join authenticated_user au on bcl.court_manager_id = au.user_id

--====================================== Filter history ===============================
select iv.badminton_club_name, iv.court_manager_phone, bs.booking_schedule_id, bs.booking_schedule_status, bs.start_date, bs.end_date, bs.schedule_type, bs.total_price, bs.total_playing_time, iv.booking_phone_number from booking_schedule bs
inner join invoice iv on bs.booking_schedule_id = iv.booking_schedule_id
where bs.customer_id = 'STF000001 ' and bs.is_view = 1
--and (iv.badminton_club_name like N'%%' or iv.court_manager_phone like N'%%') 
--and bs.start_date = '2024-06-25' 
--and bs.end_date = '2024-06-25' 
--and bs.schedule_type like '%%'
group by iv.badminton_club_name, 
iv.court_manager_phone, 
bs.booking_schedule_id, 
bs.booking_schedule_status, 
bs.start_date, bs.end_date, bs.schedule_type, bs.total_price, bs.total_playing_time, iv.booking_phone_number

select * from badminton_club
--====================================== XOA MOT BOOKING SCHEDULE DE KO HIEN THI CHO NGUOI DUNG
update booking_schedule
set is_view = 1
where booking_schedule_id in ('SD0000001')

update booking_schedule
set is_view = 0
where booking_schedule_id in ('SD0000001')

select * from booking_schedule

select * from authenticated_user

delete authenticated_user
where user_id = 'STF000009 '

--===================== filter booking slot
select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bs.is_check_in, price, bs.badminton_court_id, bc.badminton_court_name from  booking_slot bs
inner join badminton_court bc on bs.badminton_court_id = bc.badminton_court_id
where booking_schedule_id ='SD0000001' 
--and bs.start_time = '23:59'
--and bs.end_time = '23:59:59'
--and bs.booking_date = '2024-06-17'
and bs.is_check_in = 0
order by bs.start_time 

--=============== XEM BOOKING SCHEDULE CUA AI 
select * from authenticated_user au
inner join booking_schedule bsd on au.user_id = bsd.customer_id
where bsd.booking_schedule_id = 'SD0000001'

select * from time_frame

select * from badminton_club bcl  
inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id
where bcl.badminton_club_id = 'C0000001'

insert into badminton_court(badminton_court_name, badminton_club_id)
values(N'C4', 'C0000001')

select * from badminton_court

--============================= CUM LENH XU LY CHUC NANG DANG KY CAU LAC BO =======================================
select * from authenticated_user
select * from address ad
left join badminton_club bcl on ad.address_id = bcl.address_id
select badminton_club_id from badminton_club
select time_frame_id from time_frame
select * from badminton_club
select * from address

delete from address
where address_id = 'A0000011'

insert into address(unit_number, ward, district, province)
values('test01', 'test01', 'test01', 'test01')


insert into badminton_club(badminton_club_name, address_id, description, court_manager_id)
values(N'test01 nè', N'A0000011', 'test01', 'STF000009 ')

select * from badminton_club_image

SELECT * FROM badminton_club_image

update badminton_club
set description = N'Đường chân trời của thành phố lấp lánh bên dưới khi bạn đứng trên sân cầu lông trên sân thượng. Một làn gió mát mẻ buổi tối mang theo những âm thanh giao thông xa xôi, một bản nhạc nền độc đáo cho trò chơi của bạn. Mặt sân bằng vật liệu tổng hợp mang lại độ nảy thỏa đáng, và những cú đập mạnh dường như được khuếch đại trước phông nền của khu rừng đô thị.'
where badminton_club_id = 'C0000002'	

select * from authenticated_user

select * from badminton_club bcl 
inner join address ad on bcl.address_id = ad.address_id
inner join badminton_club_image bci on bcl.badminton_club_id = bci.badminton_club_id
inner join bad
-- XEM CAC THONG TIN SAU KHI DANG KY CLUB REGISTER 
-- Xem hinh anh cua badminton club
select bcl.badminton_club_id, bcl.badminton_club_name, bci.image_url, bci.is_main_avatar from badminton_club bcl 
inner join badminton_club_image bci on bcl.badminton_club_id = bci.badminton_club_id
where bcl.badminton_club_id = 'C0000001'
--===================================

-- xem dia chi
select badminton_club_name, ad.unit_number, ad.ward, ad.district, ad.province from badminton_club bcl 
inner join address ad on bcl.address_id = ad.address_id
where bcl.badminton_club_id = ''
--===================================

-- Xem time frame va pricing rule: 
select bcl.badminton_club_id, bcl.badminton_club_name, tf.time_frame_id, tf.start_time, tf.end_time, pr.day_of_week, pr.fixed, pr.flexible, pr.one_time_play from badminton_club bcl
inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id
inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id
where bcl.badminton_club_id = ''
--===================================

-- Xem ai la chu clb nao
select * from authenticated_user au
left join badminton_club bcl on au.user_id = bcl.court_manager_id
--===================================

--========= XEM DATA ================

select * from badminton_club
select * from address
select * from time_frame
select * from badminton_club_image
select * from badminton_court
select * from pricing_rule
select * from authenticated_user

--===============================  XOA DATA REGISTER CLUB
delete badminton_court
where badminton_court_id in ('CO000016', 'CO000017', 'CO000018', 'CO000019', 'CO000020', 'CO000021', 'CO000022', 'CO000023')

DELETE pricing_rule
where time_frame_id in ('TF000016', 'TF000017', 'TF000018')

delete time_frame
where badminton_club_id = 'C0000010'

delete badminton_club_image
where badminton_club_id = 'C0000010'

delete from badminton_club
where badminton_club_id = 'C0000010'
delete address
where address_id = 'A0000012'


--================================
delete address
where address_id = 'A0000012'

insert into badminton_club_image(image_url, is_main_avatar, badminton_club_id)
values('Cinema13.JPG', 0, 'C0000006')

select * from  badminton_court
insert into badminton_court(badminton_court_name, badminton_club_id)
values('C5', 'C0000001')

select * from time_frame
insert into time_frame(start_time, end_time, badminton_club_id)
values('13:30','22:30','C0000007')

-- Xem 
select * from pricing_rule
insert into pricing_rule(day_of_week, time_frame_id, flexible, fixed, one_time_play)
values('Mon', 'TF000015', 25000, 25000, 25000)


