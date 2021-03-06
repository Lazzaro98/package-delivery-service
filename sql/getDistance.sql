USE [Transport]
GO
/****** Object:  UserDefinedFunction [dbo].[getDistance]    Script Date: 9/24/2021 5:44:58 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



ALTER FUNCTION [dbo].[getDistance]
(
	@districtX int,
	@districtY int
)
RETURNS float
AS
BEGIN
	DECLARE @ret float
	Declare @xA int, @yA int, @xB int, @yB int

	select @xA = X_Coord, @yA = Y_Coord from District where idDistrict = @districtX
	select @xB = X_Coord, @yB = Y_Coord from District where idDistrict = @districtY

	set @ret = SQRT(POWER(@xA - @xB, 2) + POWER(@yA - @yB, 2))
	RETURN @ret
END

