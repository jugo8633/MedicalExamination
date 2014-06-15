package com.medici.app.model;

public class Utility
{
	private static int	mnUserId	= 2048;

	public static int getUserId()
	{
		return (++mnUserId);
	}
}
