/**
 * @author jugo
 * @descript define event & message
 */

package com.medical.medicalexamination.model;

public class EventMessage
{
	public static final int	MSG_CUSTOM				= 1024;
	public static final int	MSG_FLIPPER_CLOSE		= MSG_CUSTOM + 1;
	public static final int	MSG_LOGIN				= MSG_CUSTOM + 2;
	public static final int	MSG_LOGINED				= MSG_CUSTOM + 3;
	public static final int	MSG_SHOW_LOGIN			= MSG_CUSTOM + 4;
	public static final int	MSG_SHOW_HISTORY		= MSG_CUSTOM + 5;
	public static final int	MSG_SELECTED			= MSG_CUSTOM + 6;
	public static final int	MSG_TEST_SELECTED		= MSG_CUSTOM + 7;
	public static final int	MSG_SHOW_TEST_EYE		= MSG_CUSTOM + 8;
	public static final int	MSG_SHOW_TEST_HEAR		= MSG_CUSTOM + 9;
	public static final int	MSG_SHOW_TEST_TREMBLE	= MSG_CUSTOM + 10;

	/**
	 * @author jugo
	 * @descript define key code
	 */
	public static final int	KEY_BACK				= 4;
}
