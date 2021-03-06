USE [Transport]
GO
/****** Object:  StoredProcedure [dbo].[SPGrantCourierRequest]    Script Date: 9/24/2021 5:43:35 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



ALTER PROCEDURE [dbo].[SPGrantCourierRequest]
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

