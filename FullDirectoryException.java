/**
 * 	@author : Regina Wong 
*/

/**
 * An exception that is thrown when there is no more room to add a child
 *
 */
public class FullDirectoryException extends Exception {
	/**
	 * returns an error when preconditions are broken
	 * @param error
	 * 	the error message
	 */
	public FullDirectoryException (String error)
	{
		super(error);
	}
}
