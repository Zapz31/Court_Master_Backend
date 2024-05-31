CREATE TABLE [authenticated_user] (
  [user_id] varchar(100),
  [first_name] nvarchar(30),
  [last_name] nvarchar(30),
  [email] nvarchar(50),
  [password] nvarchar(50),
  [phone_number] varchar(30),
  [birthday] date,
  [role] int,
  [user_status] int,
  [register_date] date,
  [avatar_image_url] varchar(100),
  [court_manager_id] varchar(100),
  PRIMARY KEY ([user_id]),
  CONSTRAINT [FK_authenticated_user.court_manager_id]
    FOREIGN KEY ([court_manager_id])
      REFERENCES [authenticated_user]([user_id])
);


CREATE TABLE [address] (
  [address_id] char(8),
  [unit_number] nvarchar(100),
  [ward] nvarchar(80),
  [district] nvarchar(80),
  [province] nvarchar(80),
  PRIMARY KEY ([address_id]),

);

CREATE TABLE [badminton_club] (
  [badminton_club_id] char(8),
  [badminton_club_name] nvarchar(50),
  [address_id] char(8),
  [description] nvarchar(max),
  [badminton_club_status] int,
  [court_manager_id] varchar(100) UNIQUE,
  PRIMARY KEY ([badminton_club_id]),
  CONSTRAINT [badminton_club.court_manager_id]
    FOREIGN KEY ([court_manager_id])
      REFERENCES [authenticated_user]([user_id])
);

ALTER TABLE [badminton_club]
ADD CONSTRAINT [FK_badminton_club.address_id]
FOREIGN KEY ([address_id])
REFERENCES address ([address_id]); 

ALTER TABLE [badminton_club]
ADD CONSTRAINT unique_address_id
UNIQUE (address_id);

CREATE TABLE [badminton_club_image] (
  [image_id] char(8),
  [image_url] nvarchar(200),
  [is_main_avatar] int,
  [badminton_club_id] char(8),
  PRIMARY KEY ([image_id]),
  CONSTRAINT [badminton_club_image.badminton_club_id]
    FOREIGN KEY ([badminton_club_id])
      REFERENCES [badminton_club]([badminton_club_id])
);

CREATE TABLE [customer_playable_time] (
  [customer_id] varchar(100),
  [badminton_club_id] char(8),
  [playable_time] decimal(2,2),
  CONSTRAINT [customer_playable_time.customer_id]
    FOREIGN KEY ([customer_id])
      REFERENCES [authenticated_user]([user_id]),
  CONSTRAINT [customer_playable_time.badminton_club_id]
    FOREIGN KEY ([badminton_club_id])
      REFERENCES [badminton_club]([badminton_club_id])
);

CREATE TABLE [customer_rate] (
  [rate_id] char(8),
  [rating_point] int,
  [comment] nvarchar(max),
  [rating_date_time] datetime,
  [badminton_club_id] char(8),
  [customer_id] varchar(100),
  PRIMARY KEY ([rate_id]),
  CONSTRAINT [customer_rate.customer_id]
    FOREIGN KEY ([customer_id])
      REFERENCES [authenticated_user]([user_id]),
  CONSTRAINT [customer_rate.badminton_club_id]
    FOREIGN KEY ([badminton_club_id])
      REFERENCES [badminton_club]([badminton_club_id])
);

CREATE TABLE [payment_account] (
  [payment_account_id] varchar(100),
  [bank_name] nvarchar(100),
  [qr_code_image_url] varchar(100),
  [user_id] varchar(100) unique,
  PRIMARY KEY ([payment_account_id]),
  CONSTRAINT [payment_account.user_id]
    FOREIGN KEY ([user_id])
      REFERENCES [authenticated_user]([user_id])
);

CREATE TABLE [booking_schedule] (
  [booking_schedule_id] char(8),
  [customer_fullname] nvarchar(100),
  [customer_phone_number] varchar(30),
  [booking_schedule_status] varchar(30) CHECK(booking_schedule_status IN ('Deposited 25%', 'Deposited 50%', 'Paid', 'Canceled')),
  [start_date] date,
  [end_date] date,
  [schedule_type] varchar(30) CHECK(schedule_type IN ('One-time play', 'Fixed', 'Flexible')),
  [customer_id] varchar(100),
  PRIMARY KEY ([booking_schedule_id]),
  CONSTRAINT [booking_schedule.customer_id]
    FOREIGN KEY ([customer_id])
      REFERENCES [authenticated_user]([user_id])
);

CREATE TABLE [invoice] (
  [invoice_id] char(8),
  [badminton_club_name] nvarchar(50),
  [court_manager_phone] varchar(30),
  [total_price] decimal(10,2),
  [booking_phone_number] varchar(30),
  [badminton_club_id] char(8),
  [booking_schedule_id] char(8),
  PRIMARY KEY ([invoice_id]),
  CONSTRAINT [invoice.badminton_club_id]
    FOREIGN KEY ([badminton_club_id])
      REFERENCES [badminton_club]([badminton_club_id]),
  CONSTRAINT [invoice.booking_schedule_id]
    FOREIGN KEY ([booking_schedule_id])
      REFERENCES [booking_schedule]([booking_schedule_id])
);

CREATE TABLE [payment_detail] (
  [payment_id] varchar(100),
  [amount] decimal(10,2),
  [payment_method] varchar(50) CHECK(payment_method in ('Online payment', 'COD')),
  [payment_time] datetime,
  [invoice_id] char(8) unique,
  [payment_account_id] varchar(100),
  PRIMARY KEY ([payment_id]),
  CONSTRAINT [payment_detail.invoice_id]
    FOREIGN KEY ([invoice_id])
      REFERENCES [invoice]([invoice_id]),
  CONSTRAINT [payment_detail.payment_account_id]
    FOREIGN KEY ([payment_account_id])
      REFERENCES [payment_account]([payment_account_id])
);

CREATE TABLE [badminton_court] (
  [badminton_court_id] char(8),  
  [badminton_court_name] nvarchar(100),
  [badminton_court_status] nvarchar(100),
  [badminton_club_id] char(8),
  PRIMARY KEY ([badminton_court_id]),
  CONSTRAINT [badminton_court.badminton_club_id]
    FOREIGN KEY ([badminton_club_id])
      REFERENCES [badminton_club]([badminton_club_id])
);

CREATE TABLE [booking_slot] (
  [booking_slot_id] char(8),
  [start_time] time,
  [end_time] time,
  [booking_date] date,
  [is_check_in] int,
  [price] int,
  [badminton_court_id] char(8),
  [booking_schedule_id] char(8),
  PRIMARY KEY ([booking_slot_id]),
  CONSTRAINT [booking_slot.badminton_court_id]
    FOREIGN KEY ([badminton_court_id])
      REFERENCES [badminton_court]([badminton_court_id]),
  CONSTRAINT [booking_slot.booking_schedule_id]
    FOREIGN KEY ([booking_schedule_id])
      REFERENCES [booking_schedule]([booking_schedule_id])
);

CREATE TABLE [time_frame] (
  [time_frame_id] char(8),
  [start_time] time,
  [end_time] time,
  [badminton_club_id] char(8),
  PRIMARY KEY ([time_frame_id]),
  CONSTRAINT [time_frame.badminton_club_id]
    FOREIGN KEY ([badminton_club_id])
      REFERENCES [badminton_club]([badminton_club_id])
);

CREATE TABLE [pricing_rule] (
  [pricing_rule_id] char(8),
  [day_of_week] varchar(3) check(day_of_week in ('Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun')),
  [price_per_hour] int,
  [schedule_type] varchar(30) CHECK(schedule_type IN ('One-time play', 'Fixed', 'Flexible')),
  [time_frame_id] char(8),
  PRIMARY KEY ([pricing_rule_id]),
  CONSTRAINT [pricing_rule.time_frame_id]
    FOREIGN KEY ([time_frame_id])
      REFERENCES [time_frame]([time_frame_id])
);

CREATE TABLE [Schedule_location] (
  [booking_slot_id] char(8),
  [time_frame_id] char(8),
  CONSTRAINT [Schedule_location.booking_slot_id]
    FOREIGN KEY ([booking_slot_id])
      REFERENCES [booking_slot]([booking_slot_id]),
  CONSTRAINT [Schedule_location.time_frame_id]
    FOREIGN KEY ([time_frame_id])
      REFERENCES [time_frame]([time_frame_id])
);