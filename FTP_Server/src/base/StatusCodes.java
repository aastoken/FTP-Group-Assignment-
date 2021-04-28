package base;

public class StatusCodes {
	public static final String code_150 = "150 File status okay; about to open data connection.";
	public static final String code_200 = "200 OK";
	public static final String code_202 = "202 Command not implemented, superfluous at this site";
	public static final String code_221 = "500 Syntax error, command unrecognized";
	public static final String code_226 = "226 Closing data connection. Requested file action successful (for example, file transfer\r\n"
			+ "or file abort).";
	public static final String code_227 = "503 Bad sequence of commands";
	public static final String code_230 = "230 User logged in, proceed.";
	public static final String code_250 = "250 Requested file action okay, completed.";
	public static final String code_257 = "257 “path_of_the_directory”.";
	public static final String code_331 = "331 User name okay, need password.";
	public static final String code_350 = "350 Requested file action pending further information.";
	public static final String code_421 = "421 Service not available, closing control connection.";
	public static final String code_425 = "425 Can't open data connection";
	public static final String code_426 = "426 Connection closed; transfer aborted";
	public static final String code_450 = "450 Requested file action not taken. File unavailable (e.g., file busy).";
	public static final String code_451 = "451 Requested action aborted: local error in processing.";
	public static final String code_452 = "452 Requested action not taken. Insufficient storage space in system.";
	public static final String code_500 = "500 Syntax error, command unrecognized.";
	public static final String code_501 = "501 Syntax error in parameters or arguments.";
	public static final String code_502 = "502 Command not implemented.";
	public static final String code_503 = "503 Bad sequence of commands";
	public static final String code_530 = "530 Not logged in.";
	public static final String code_550 = "550 Requested action not taken. File unavailable (e.g., file not found, no access).";
	public static final String code_553 = "553 Requested action not taken. File name not allowed.";
	

}
