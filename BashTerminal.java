/**
 * 	@author : Regina Wong 
*/
import java.util.Scanner;

/**
 * The class should contain a single main method which allows a user to interact with a 
 * 		file system implemented by an instance of DirectoryTree using commands
 *
 */
public class BashTerminal {
	/**
	 * The main method allows the user to interact with a file system by using a bash termainal
	 */
	public static void main(String[] args)
	{
		System.out.println("Starting bash terminal.");
		Scanner input = new Scanner(System.in);
		System.out.print("[REWONG@super(man)]: $ ");
		String temp = input.nextLine().trim();
		DirectoryTree tree = new DirectoryTree();
		while(!temp.equals("exit"))
		{
			if(temp.equals("pwd"))
			{
				System.out.println(tree.presentWorkingDirectory());
			}
			else if(temp.equals("ls"))
			{
				System.out.println(tree.listDirectory());
			}
			else if(temp.equals("ls -R"))
			{
				tree.printDirectoryTree();
			}
			else if(temp.equals("cd /"))
			{
				tree.resetCursor();
			}
			else if(temp.equals("cd .."))
			{
				tree.moveToParent();
			}
			else if(temp.startsWith("find"))
			{
				int indexOf = temp.indexOf(" ");
				boolean hasName = true;
				if(indexOf == -1)
				{
					System.out.println("ERROR: Please there isn't a name or a path");
					hasName = false;
				}
				if(hasName)
				{
					String name = temp.substring(indexOf+1);
					String x =(tree.find(name));
					if(x.equals("root"))
					{
						System.out.println("ERROR: No such file exits.");
					}
					else
					{
						System.out.println(x);
					}
				}
			}
			else if(temp.startsWith("cd"))
			{
				int index = temp.indexOf(" ");
				boolean hasName = true;
				if(index == -1)
				{
					System.out.println("ERROR: Please there isn't a name or a path");
					hasName = false;
				}
				if(hasName)
				{
					String name = temp.substring(index+1);
					if(name.contains("/"))
					{
						try 
						{
							tree.changeDirectoryPath(name);
						} 
						catch (NotADirectoryException e) 
						{
							System.out.println("ERROR: Cannot change directory into a file.");
						}
					}
					else
					{
						try 
						{
							tree.changeDirectory(name);
						} 
						catch (NotADirectoryException e) 
						{
							System.out.println("ERROR: Cannot change directory into a file.");
						}
					}
				}
			}
			else if(temp.startsWith("mkdir"))
			{
				int index = temp.indexOf(" ");
				if(index == -1)
				{
					System.out.println("Error: There was no name entered");
				}
				else
				{
					String name = temp.substring(index+1);
					
					try 
					{
						tree.makeDirectory(name);
					} 
					catch (IllegalArgumentException e) 
					{					
						System.out.println("ERROR: " + name + "is not a valid name");
					} 
					catch (FullDirectoryException e) 
					{
						System.out.println("ERROR: Present directory is full.");
					}
				}
			}
			else if(temp.startsWith("touch"))
			{
				int index = temp.indexOf(" ");
				if(index == -1)
				{
					System.out.println("Error: There was no name entered.");
				}
				else
				{
					String name = temp.substring(index+1);
					try 
					{
						tree.makeFile(name);
					} 
					catch (IllegalArgumentException e) 
					{					
						System.out.println("ERROR: No such directory named " + name);
					} 
					catch (FullDirectoryException e) 
					{
						System.out.println("ERROR: Present directory is full.");
					}
				}
			}
			else
			{
				System.out.println("bash: " + temp + ": command not found");
			}
			System.out.print("[REWONG@super(man)]: $ ");
			temp = input.nextLine().trim();
		}
		System.out.println("Program terminating normally");
	}
}
