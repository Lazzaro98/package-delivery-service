
CREATE TABLE [Admin]
( 
	[Username]           varchar(20)  NOT NULL 
)
go

CREATE TABLE [City]
( 
	[PostNumber]         integer  NULL ,
	[Name]               varchar(20)  NOT NULL ,
	[IdCity]             integer  IDENTITY ( 0,1 )  NOT NULL 
)
go

CREATE TABLE [Courir]
( 
	[Username]           varchar(20)  NOT NULL ,
	[RegNumber]          varchar(20)  NULL ,
	[Number_of_deliever_packets] integer  NULL ,
	[Profit]             integer  NULL ,
	[Status]             integer  NULL 
	CONSTRAINT [0_1_1514059119]
		CHECK  ( Status BETWEEN 0 AND 1 )
)
go

CREATE TABLE [District]
( 
	[IdDistrict]         integer  IDENTITY ( 0,1 )  NOT NULL ,
	[Name]               varchar(20)  NULL ,
	[X_coord]            integer  NULL ,
	[Y_coord]            integer  NOT NULL ,
	[IdCity]             integer  NULL 
)
go

CREATE TABLE [Offer]
( 
	[IdOffer]            integer  IDENTITY ( 0,1 )  NOT NULL ,
	[PercentP]           integer  NULL ,
	[IdPackage]          integer  NULL ,
	[Username]           varchar(20)  NULL ,
	[Status]             char(18)  NULL 
)
go

CREATE TABLE [Package]
( 
	[IdPackage]          integer  IDENTITY ( 0,1 )  NOT NULL ,
	[Status]             integer  NULL 
	CONSTRAINT [0_3_1722114969]
		CHECK  ( Status BETWEEN 0 AND 3 ),
	[Price]              integer  NULL ,
	[Time]               datetime  NULL ,
	[UsernameCourir]     varchar(20)  NULL ,
	[IdDistrictFrom]     integer  NULL ,
	[IdDistrictTo]       integer  NULL ,
	[UsernameSender]     varchar(20)  NULL ,
	[Type]               integer  NULL ,
	[Weight]             decimal(10,3)  NULL 
)
go

CREATE TABLE [Request]
( 
	[Username]           varchar(20)  NOT NULL ,
	[RegNumber]          varchar(20)  NULL 
)
go

CREATE TABLE [User]
( 
	[Username]           varchar(20)  NOT NULL ,
	[Name]               varchar(20)  NULL ,
	[Surname]            varchar(20)  NULL ,
	[Password]           varchar(20)  NULL ,
	[Number_of_sent_packets] integer  NULL 
)
go

CREATE TABLE [Vehicle]
( 
	[RegNumber]          varchar(20)  NOT NULL ,
	[FuelType]           integer  NULL 
	CONSTRAINT [0_2_1623914535]
		CHECK  ( FuelType BETWEEN 0 AND 2 ),
	[Waste]              decimal(10,3)  NULL 
)
go

ALTER TABLE [Admin]
	ADD CONSTRAINT [XPKAdmin] PRIMARY KEY  CLUSTERED ([Username] ASC)
go

ALTER TABLE [City]
	ADD CONSTRAINT [XPKCity] PRIMARY KEY  CLUSTERED ([IdCity] ASC)
go

ALTER TABLE [Courir]
	ADD CONSTRAINT [XPKCourir] PRIMARY KEY  CLUSTERED ([Username] ASC)
go

ALTER TABLE [District]
	ADD CONSTRAINT [XPKDistrict] PRIMARY KEY  CLUSTERED ([IdDistrict] ASC)
go

ALTER TABLE [Offer]
	ADD CONSTRAINT [XPKOffer] PRIMARY KEY  CLUSTERED ([IdOffer] ASC)
go

ALTER TABLE [Package]
	ADD CONSTRAINT [XPKPackage] PRIMARY KEY  CLUSTERED ([IdPackage] ASC)
go

ALTER TABLE [Request]
	ADD CONSTRAINT [XPKRequest] PRIMARY KEY  CLUSTERED ([Username] ASC)
go

ALTER TABLE [User]
	ADD CONSTRAINT [XPKUser] PRIMARY KEY  CLUSTERED ([Username] ASC)
go

ALTER TABLE [Vehicle]
	ADD CONSTRAINT [XPKVehicle] PRIMARY KEY  CLUSTERED ([RegNumber] ASC)
go


ALTER TABLE [Admin]
	ADD CONSTRAINT [R_25] FOREIGN KEY ([Username]) REFERENCES [User]([Username])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [Courir]
	ADD CONSTRAINT [R_24] FOREIGN KEY ([Username]) REFERENCES [User]([Username])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go

ALTER TABLE [Courir]
	ADD CONSTRAINT [R_29] FOREIGN KEY ([RegNumber]) REFERENCES [Vehicle]([RegNumber])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [District]
	ADD CONSTRAINT [R_23] FOREIGN KEY ([IdCity]) REFERENCES [City]([IdCity])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Offer]
	ADD CONSTRAINT [R_32] FOREIGN KEY ([IdPackage]) REFERENCES [Package]([IdPackage])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Offer]
	ADD CONSTRAINT [R_33] FOREIGN KEY ([Username]) REFERENCES [Courir]([Username])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Package]
	ADD CONSTRAINT [R_31] FOREIGN KEY ([UsernameCourir]) REFERENCES [Courir]([Username])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Package]
	ADD CONSTRAINT [R_34] FOREIGN KEY ([IdDistrictFrom]) REFERENCES [District]([IdDistrict])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Package]
	ADD CONSTRAINT [R_36] FOREIGN KEY ([IdDistrictTo]) REFERENCES [District]([IdDistrict])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Package]
	ADD CONSTRAINT [R_37] FOREIGN KEY ([UsernameSender]) REFERENCES [User]([Username])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Request]
	ADD CONSTRAINT [R_26] FOREIGN KEY ([Username]) REFERENCES [User]([Username])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Request]
	ADD CONSTRAINT [R_28] FOREIGN KEY ([RegNumber]) REFERENCES [Vehicle]([RegNumber])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go



CREATE FUNCTION getDistance
(
	-- Add the parameters for the function here
	@districtA int, @districtB int
)
RETURNS float
AS
BEGIN
	-- Declare the return variable here
	DECLARE @ret float
	Declare @xA int, @yA int, @xB int, @yB int

	select @xA = X_Coord, @yA = Y_Coord from District where idDistrict = @districtA
	select @xB = X_Coord, @yB = Y_Coord from District where idDistrict = @districtB

	set @ret = SQRT(POWER(@xA - @xB, 2) + POWER(@yA - @yB, 2))
	RETURN @ret
END

go



CREATE PROCEDURE SPGrantCourierRequest
	@ret int output,
	@username varchar(100) 
AS
BEGIN
	Declare @RegNumber varchar(100)
	set @RegNumber = coalesce((SELECT RegNumber FROM request where username=@username), '')
	if @RegNumber = ''
		set @ret = -1;
	else begin
		INSERT INTO courir (username, RegNumber) values (@username, @RegNumber)
		if @@ROWCOUNT = 1
			DELETE FROM request where username=@username
		select @ret = count(*) from Courir where username = @username and RegNumber= @RegNumber
	end
END

go
CREATE PROCEDURE GasBills
	@courier varchar(100),
	@d1 int,
	@d2 int
AS
BEGIN
	SET NOCOUNT ON;

	declare @fuelType int
	declare @consumption float
	declare @distance float
	declare @gasPrice float
	declare @bill float
	declare @licensePlate varchar(100)


	-- Get licensePlate
	select @licensePlate = RegNumber from Courir where Username= @courier

	-- Get vehicle fuelType and consumption
	select @fuelType = FuelType, @consumption = Waste from Vehicle where RegNumber = @licensePlate

	-- get distance
	select @distance = [dbo].[getDistance] (@d1, @d2)

	-- get gas price
	select @gasPrice = case @fuelType
		when 0 then 15
		when 1 then 32
		when 2 then 36
	end

	-- calculate bill
	set @bill = @consumption * @distance * @gasPrice

	-- update profit
	update Courir set profit = profit - @bill where Username = @courier
END

go



CREATE TRIGGER [dbo].[TR_TransportOffer_Accepted]
   ON  [dbo].[Offer] 
   FOR UPDATE
AS 
BEGIN
	SET NOCOUNT ON;

    Declare @pkgId int, @offerId int, @courier varchar(100)
	Declare @cursor CURSOR
	Declare @pricePercentage float
	Declare @price float, @type int, @weight float
	Declare @d1 int, @d2 int
	Declare @distance float
	Declare @courierPrice float

	set @cursor = cursor for
	select IdOffer, IdPackage, Username, PercentP
	from inserted
	
	open @cursor
	
	fetch next from @cursor
	into @offerId, @pkgId, @courier, @pricePercentage
	while @@FETCH_STATUS = 0
	begin
		select @type = type, @weight = weight, @d1 = IdDistrictFrom, @d2 = IdDistrictTo
		from Package where IdPackage = @pkgId

		select @distance = [dbo].[getDistance] (@d1, @d2)
		select @price = [dbo].[getPrice] (@type, @weight, @distance)

		select @courierPrice = @price * @pricePercentage / 100
		set @price = @price + @courierPrice

		update Package
		set UsernameCourir = @courier, 
			Time = GETDATE(),
			status = 1,
			Price = @price
		where IdPackage = @pkgId

		delete from Offer where IdPackage = @pkgId

		update Courir 
		set profit = profit + @price
		where Username = @courier
		
		fetch next from @cursor
		into @offerId, @pkgId, @courier, @pricePercentage
	end
END

go


CREATE FUNCTION getPrice
(
	-- Add the parameters for the function here
	@pkgType int, @weight float, @distance float
)
RETURNS float
AS
BEGIN
	-- Declare the return variable here
	DECLARE @ret float
	Declare @basePrice float
	Declare @weightFactor float
	Declare @weightPrice float
	-- Add the T-SQL statements to compute the return value here
	set @basePrice = CASE @pkgType   
     WHEN 0 THEN 10  
     WHEN 1 THEN 25 
     WHEN 2 THEN 75
	end

	set @weightFactor = CASE @pkgType   
     WHEN 0 THEN 0  
     WHEN 1 THEN 1
     WHEN 2 THEN 2
	end

	set @weightPrice = case @pkgType
		WHEN 0 THEN 0
		WHEN 1 THEN 100
		WHEN 2 THEN 300
	end
	
	set @ret = (@basePrice + (@weightFactor * @weight) * @weightPrice) * @distance
	-- Return the result of the function
	RETURN @ret
END