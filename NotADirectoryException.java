/**
 * 	@author : Regina Wong
*/

/**
 * An exception that is thrown when a file is at a node
 *
 */
public class NotADirectoryException extends Exception {
	/**
	 * Throws an error when preconditions are broken
	 * @param error
	 * 		error message
	 */
	public NotADirectoryException (String error)
	{
		super(error);
	}
}
