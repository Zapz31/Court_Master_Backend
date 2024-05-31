--====================================== INSERT ===================================================
insert into authenticated_user(first_name, last_name, role) 
values('Doan Minh', 'Tien', 1)

insert into badminton_club(badminton_club_name, description) values('Cau long cua Giap', N'Sân cầu lông đẹp vãi ò')
insert into badminton_club(badminton_club_name, description, address_id, court_manager_id) values('aaaaaa', N'aaaaaaa', 'A0000001', 'MNG000001')
insert into address(unit_number, ward, district, province) values('', N'Nguyễn Xiển', N'Phường Long Thạnh Mỹ', N'TP Hồ Chí Minh')

insert into authenticated_user(first_name)
values('aaaaaaaaa')

--	Nguyễn Xiển – Phường Long Thạnh Mỹ – Quận 9 – TP Hồ Chí Minh
--====================================== SELECT ===================================================
select badminton_club_name, description, a.unit_number +', '+ a.ward +', '+ a.district +', '+ a.province as A from badminton_club bc
inner join address a on bc.address_id = a.address_id
where a.address_id = 'A0000001'

select * from authenticated_user

select * from address

delete from address
where address_id in ('A0000001', 'A0000002')

--====================================== DELETE ===================================================
delete from badminton_club 
where address_id in ('A0000001')
