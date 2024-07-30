--=====================================================================================================================================
USE [Court_Master]    --(1)
GO

CREATE TRIGGER [dbo].[trg_InsertUserID]
ON [dbo].[authenticated_user]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO [authenticated_user] (user_id, first_name, last_name, email, password, phone_number, birthday, role, user_status, register_date, court_manager_id)
    SELECT dbo.GenerateUserID(role), first_name, last_name, email, password, phone_number, birthday, role, user_status, register_date, court_manager_id
    FROM inserted;
END

--=====================================================================================================================================
USE [Court_Master]       --(2)
GO

CREATE TRIGGER [dbo].[trg_InserAddressID]
ON [dbo].[address]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO [address] (address_id, unit_number, ward, district, province)
    SELECT dbo.GenerateAddressID(), unit_number, ward, district, province
    FROM inserted;
END

--=====================================================================================================================================
USE [Court_Master]        --(3)
GO
CREATE TRIGGER [dbo].[trg_InsertClubID]
ON [dbo].[badminton_club]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO [badminton_club] (badminton_club_id, badminton_club_name, address_id, description, badminton_club_status, court_manager_id)
    SELECT dbo.GenerateBadmintonClubID(), badminton_club_name, address_id, description, badminton_club_status, court_manager_id
    FROM inserted;
END

--=====================================================================================================================================
USE [Court_Master]        --(4)
GO
CREATE TRIGGER [dbo].[trg_InsertCourtID]
ON [dbo].[badminton_court]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO badminton_court (badminton_court_id, badminton_court_name, badminton_club_id)
    SELECT dbo.GenerateCourtID(), badminton_court_name, badminton_club_id
    FROM inserted;
END

--=====================================================================================================================================
USE [Court_Master]        --(5)
GO
CREATE TRIGGER [dbo].[trg_InserScheduleID]
ON [dbo].[booking_schedule]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO [booking_schedule] (booking_schedule_id, customer_fullname, customer_phone_number, booking_schedule_status, total_price, start_date, end_date, schedule_type, customer_id, total_playing_time, remaining_amount)
    SELECT dbo.GenerateScheduleID(), customer_fullname, customer_phone_number, booking_schedule_status, total_price, start_date, end_date, schedule_type, customer_id, total_playing_time, remaining_amount
    FROM inserted;
END
--=====================================================================================================================================
USE [Court_Master]        --(6)
GO

CREATE TRIGGER [dbo].[trg_InsertBookingSlotID]
ON [dbo].[booking_slot]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO [booking_slot] (booking_slot_id, start_time, end_time, booking_date, is_check_in, price, badminton_court_id, booking_schedule_id, is_temp)
    SELECT dbo.GenerateBookingSlotID(), start_time, end_time, booking_date, is_check_in, price, badminton_court_id, booking_schedule_id, is_temp
    FROM inserted;
END
--=====================================================================================================================================
USE [Court_Master]        --(7)
GO
CREATE TRIGGER [dbo].[trg_InserRateID]
ON [dbo].[customer_rate]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO customer_rate (rate_id, rating_point, comment, rating_date_time, badminton_club_id, customer_id)
    SELECT dbo.GenerateRateID(), rating_point, comment, rating_date_time, badminton_club_id, customer_id
    FROM inserted;
END

--=====================================================================================================================================
USE [Court_Master]        --(8)
GO

CREATE TRIGGER [dbo].[trg_InsertPricingRuleID]
ON [dbo].[pricing_rule]
INSTEAD OF INSERT
AS
BEGIN   
    INSERT INTO [pricing_rule] (pricing_rule_id, day_of_week, flexible, fixed, one_time_play, time_frame_id)
    SELECT dbo.GeneratePricingRuleID(), day_of_week, flexible, fixed, one_time_play, time_frame_id
    FROM inserted;
END

--=====================================================================================================================================
USE [Court_Master]        --(9)
GO

CREATE TRIGGER [dbo].[trg_InsertTimeFrameID]
ON [dbo].[time_frame]
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO [time_frame] (time_frame_id, start_time, end_time, badminton_club_id)
    SELECT dbo.GenerateTimeFrameID(), start_time, end_time, badminton_club_id
    FROM inserted;
END
