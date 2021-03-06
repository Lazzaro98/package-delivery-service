USE [Transport]
GO
/****** Object:  StoredProcedure [dbo].[GasBills]    Script Date: 9/24/2021 5:44:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[GasBills]
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

