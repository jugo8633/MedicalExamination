package com.medical.medicalexamination.model;

public class Utility
{
	private static int	mnUserId	= 2048;

	public static int getUserId()
	{
		return (++mnUserId);
	}
}
