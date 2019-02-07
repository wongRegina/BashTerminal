/**
 * 	@author : Regina Wong
*/
import java.util.Scanner;

/**
 * Implements a 10 child tree of DirectoryNodes. 
 *
 */
public class DirectoryTree {
	private DirectoryNode root;
	private DirectoryNode cursor;
	Scanner input = new Scanner(System.in);
	String path ="root";
	
	/**
	 * creates a new directory tree with a root
	 */
	public DirectoryTree()
	{
		DirectoryNode newRoot = new DirectoryNode("root", false);
		root = newRoot;
		cursor = root;
	}
	
	/**
	 * moves the cursor to the root
	 *  <dt><b>Postconditions:</b><dd>
	 *  	the cursor is at the root
	 */
	public void resetCursor()
	{
		cursor = root;
		path = "root";
	}
	

	
	/**
	 * find all the nodes with the given name
	 * @param name
	 * 		the name that needs to be search
	 * @return
	 * 		the String with the all the nodes with the given name
	 */
	public String find(String name)
	{
		String temp = "root";
		DirectoryNode tempCursor = root;
		String answer ="";
		if (tempCursor.getChildren()[0] != null)
		{
			answer += findHelper(tempCursor , temp ,  name);
		}
		if(answer.endsWith("\nroot"))
		{
			answer = answer.substring(0, answer.length() -4);
		}
		return answer;
	}
	
	/**
	 * goes through the tree to find node(s) with the name
	 * @param node
	 * 		the current node
	 * @param path
	 * 		the current path to the node
	 * @param name
	 * 		the name that needs to be search
	 * @return
	 * 		A string with the nodes with the given name
	 */
	private String findHelper(DirectoryNode node, String path, String name)
	{
		for(int i = 0 ; i < 10 ; i++)
		{
			if((node.getChildren())[i] != null)
			{
				path += "/" + node.getChildren()[i].getName();
				if(node.getChildren()[i] != null && node.getChildren()[i].getName().equals(name))
				{
					return path + "\nroot";
				}
				else 
				{
					String x = findHelper(node.getChildren()[i], path ,name);
					if(x.substring(x.lastIndexOf("/") + 1 ).equals(name + "\nroot"))
					{
						path = x;
					}
					else 
					{
						path = path.substring(0,path.lastIndexOf("/"));
					}
				}
			}
		}
		return path;
	}
	
	/**
	 * moves the cursor to the parent
	 */
	public void moveToParent()
	{
		if(!cursor.getName().equals("root"))
		{
			int index = path.lastIndexOf("/");
			path = path.substring(0 , index -1);
			cursor = cursor.getParent();
		}
	}
	
	/**
	 * Moves the cursor to the directory with the name indicated by name.
	 * @param name
	 * 		the name the cursor should move to
	 * <dt><b>Preconditions:</b><dd> 
	 * 		'name' references a valid directory ('name' cannot reference a file).
	 * @throws NotADirectoryException
	 * 		Thrown if the node with the indicated name is a file, 
	 * 		as files cannot be selected by the cursor, or cannot be found.
	 * <dt><b>Postconditions:</b><dd>
	 * 		The cursor now references the directory with the name indicated by name. 
	 * 		If a child could not be found with that name, 
	 * 		then the user is prompted to enter a different directory name. 
	 * 		If the name was not a directory, a NotADirectoryException has been thrown
	 */
	public void changeDirectory(String name) throws NotADirectoryException
	{
		DirectoryNode tempCursor = cursor;
		boolean found = false;
		for(int i = 0 ; i < 10 ; i ++)
		{
			if((tempCursor.getChildren())[i] != null)				
			{
				if((tempCursor.getChildren())[i].getName().equals(name))
				{
					if((tempCursor.getChildren())[i].isFile())
					{
						throw new NotADirectoryException("The node with the name " + name + 
								" is a file, as files cannot be selected by the cursor");
					}
					else
					{
						cursor = (tempCursor.getChildren())[i];
						path += "/" + name;	
						found = true;
						return;
					}
				}
			}
		}
		if(found == false)
		{
			System.out.println("ERROR: No such directory named " + name + ". Enter another name: ");
			String newName = input.nextLine();
			changeDirectory(newName);
		}
	}
	
	/**
	 * given a path it moves the cursor to that path
	 * @param path
	 * 		the path to the node
	 * @throws NotADirectoryException
	 * 		the node is a file
	 */
	public void changeDirectoryPath(String path) throws NotADirectoryException
	{
		String nameOfPath[] = path.split("/");
		DirectoryNode tempCursor = root;
		for(int i = 1; i < nameOfPath.length ; i ++)
		{
			boolean found = false;
			for(int ij = 0; ij < 10; ij ++)
			{
				if((tempCursor.getChildren())[ij] != null)
				{
					if((tempCursor.getChildren())[ij].getName().equals(nameOfPath[i]))
					{
						if((tempCursor.getChildren())[ij].isFile())
						{
							throw new NotADirectoryException("The node with the name " + nameOfPath[i] + 
									" is a file and has no children");
						}
						else
						{
							if(i == nameOfPath.length -1)
							{
								cursor = tempCursor.getChildren()[ij];
							}
							tempCursor = (tempCursor.getChildren())[ij];
							path += "/" + tempCursor.getChildren();	
							found = true;
							break;
						}
					}
				}
			}
			if(found == false)
			{
				System.out.println("The path you enterd isn't valid. Enter another path: ");
				String newPath = input.nextLine();
				changeDirectoryPath(newPath);
				break;
			}
		}
	}
	
	/**
	 * Returns a String containing the path of directory names 
	 * from the root node of the tree to the cursor, 
	 * with each name separated by a forward slash "/".
	 * @return
	 * 		The path to get to the cursor
	 * <dt><b>Postconditions:</b><dd>
	 * 	The cursor remains at the same DirectoryNode.
	 */
	public String presentWorkingDirectory()
	{	
		return path;
	}
	
	/**
	 * Returns a String containing a space-separated list of names of 
	 * all the child directories or files of the cursor.
	 * @return
	 * 		a list of children of the node in one line separated by a space
	 * <dt><b>Postconditions:</b><dd>
	 * 		The cursor remains at the same DirectoryNode.
	 */
	public String listDirectory()
	{
		String temp = "";
		DirectoryNode[] children = cursor.getChildren();
		for(int i = 0 ; i < 10 ; i ++)
		{
			if(children[i] != null)
			{
				temp += children[i].getName() + " ";
			}
			else
			{
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Prints a formatted nested list of names 
	 * of all the nodes in the directory tree, starting from the cursor.
	 * <dt><b>Postconditions:</b><dd>
	 * 		The cursor remains at the same DirectoryNode.
	 */
	public void printDirectoryTree()
	{
		DirectoryNode tempCursor = cursor;
		System.out.println("|- " + cursor.getName());
		for(int i = 0 ; i < 10 ; i++)
		{
			if((cursor.getChildren())[i] != null)
			{
				printDirectoryTreeHelper(tempCursor.getChildren()[i], 1);
			}
		}
	}
	
	/**
	 * helper method of preingDirectoryTree
	 * @param node
	 * 		the current node
	 * @param tabs
	 * 		the numbers of tabs that need to happen
	 */
	private void printDirectoryTreeHelper(DirectoryNode node, int tabs)
	{
		String temp = "|- ";
		String space = "";
		if(node.isFile())
		{
			temp = "- ";
		}
		for (int i = 0 ; i < tabs ; i ++)
		{
			space += "    " ;
		}
		System.out.println(space + temp + node.getName());
		for(int i = 0 ; i < 10 ; i++)
		{
			if((node.getChildren())[i] != null)
			{
				printDirectoryTreeHelper(node.getChildren()[i], tabs + 1);
			}
			else
			{
				return;
			}
		}
	}
	
	/**
	 * Creates a directory with the indicated name and adds it to the children of the cursor node.
	 * @param name
	 * 	The name of the directory to add.
	 * <dt><b>Preconditions:</b><dd> 
	 * 		'name' is a legal argument (does not contain spaces " " or forward slashes "/").
	 * @throws IllegalArgumentException
	 * 		Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 * 		Thrown if all child references of this directory are occupied.
	 *  <dt><b>Postconditions:</b><dd>
	 *  	A new DirectoryNode has been added to the children of the cursor, or an exception has been thrown.
	 */
	public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException
	{
		if(name.contains(" ") || name.contains("/"))
		{
			throw new IllegalArgumentException("The name is not valid");
		}
		DirectoryNode temp = new DirectoryNode(name, false);
		try 
		{
			cursor.addChild(temp);
		} 
		catch (NotADirectoryException e) 
		{
			
		}
	}
	
	/**
	 * Creates a file with the indicated name and adds it to the children of the cursor node.
	 * @param name
	 * 	The name of the file to add.
	 * <dt><b>Preconditions:</b><dd> 
	 * 		'name' is a legal argument (does not contain spaces " " or forward slashes "/").
	 * @throws IllegalArgumentException
	 * 		Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 * 		Thrown if all child references of this directory are occupied.
	 *  <dt><b>Postconditions:</b><dd>
	 *  	A new DirectoryNode has been added to the children of the cursor, or an exception has been thrown.
	 */
	public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException
	{
		if(name.contains(" ") || name.contains("/"))
		{
			throw new IllegalArgumentException("The name is not valid");
		}
		DirectoryNode temp = new DirectoryNode(name, true);
		try 
		{
			cursor.addChild(temp);
		} 
		catch (NotADirectoryException e) 
		{
			
		}
	}
}