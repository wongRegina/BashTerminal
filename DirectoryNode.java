/**
 * 	@author : Regina Wong 
*/

/**
 * Represents a node in the file tree.
 *
 */
public class DirectoryNode {
	private String name;
	private DirectoryNode[] children = new DirectoryNode[10];
	private DirectoryNode parent;
	private boolean isFile;
	
	/**
	 * makes a new DirectoryNode, with 3 empty children
	 * @param nodeName
	 * 	name of the node
	 * @param file
	 * 	if the node is a file
	 */
	public DirectoryNode(String nodeName, boolean file)
	{
		name = nodeName;
		isFile = file;
		for(int i = 0; i < 10 ; i++)
		{
			children[i] = null;
		}
	}
	
	/**
	 * returns the name of the node
	 * @return
	 * 		the name of the node
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * sets the name of the node
	 * @param newName
	 * 		the new name of the node
	 */
	public void setName(String newName)
	{
		name = newName;
	}
	
	/**
	 * returns the array of children of the node
	 * @return
	 * 		the array of children
	 */
	public DirectoryNode[] getChildren()
	{
		return children;
	}
	
	/**
	 * returns the parent of the node
	 * @return
	 * 		parent of the node
	 */
	public DirectoryNode getParent()
	{
		return parent;
	}
	
	/**
	 * set the parent of the node
	 * @param x
	 * 		the parent of the node
	 */
	private void setParent(DirectoryNode x)
	{
		parent = x;
	}
	
	/**
	 * return if the node is a file
	 * @return
	 * 		true if the node is a file and false otherwise
	 */
	public boolean isFile()
	{
		return isFile;
	}
	

	/**
	 * Adds newChild to any of the open child positions of this node(from left to right)
	 * <dt><b>Preconditions:</b><dd> 
	 * 		This node is not a file.
	 *		There is at least one empty position in the children of this node 
	 * @param newChild
	 * 		the child being added
	 * @throws FullDirectoryException
	 * 		Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 * 		Thrown if the current node is a file, as files cannot contain DirectoryNode references
	 * <dt><b>Postconditions:</b><dd>
	 * 		newChild has been added as a child of this node. 
	 * 		If there is no room for a new node, throw a FullDirectoryException.
	 */
	public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException
	{
		if(isFile)
		{
			throw new NotADirectoryException("This node is a file. "
					+ "Files are not allowed to have children");
		}
		boolean added = false;
		for(int i = 0 ; i < 10 ; i ++)
		{
			if(children[i] != null && children[i].getName().equals(newChild.getName()))
			{
				System.out.println("Error: The name you enter was already used. "
						+ "Nothing was added to the tree");
				return;
			}
			if(children[i] == null)
			{
				children[i] = newChild;
				children[i].setParent(this);
				added = true;
				break;
			}
		}
		if(added == false)
		{
			throw new FullDirectoryException("All child references of "
					+ "this directory are occupied.");
		}
	}
	
}
