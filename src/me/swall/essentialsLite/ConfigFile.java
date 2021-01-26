package me.swall.essentialsLite;

import java.io.*;
import java.lang.Character;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/********************************************************
 * Author: Jacob N
 * Other Contributors: none
 * Description: Interacts with config/save state files.
 *******************************************************/
public class ConfigFile 
{
    private File path = null;
    private ArrayList<String> contents;
    private FileReader in;
    private FileWriter out;
    
    ConfigFile(String path) throws IOException
    {
        this.path = new File(path);
        in = new FileReader(path);
        contents.addAll(getFile());
    }
    
    /*
     * Sets the path and name of the file. Creates a new directory and/or file if
     * it isn't found.
     */
    public void setFile(File path)
    {
        //Ensure the file path exists. Create it if it doesn't.
        File tempPath = new File(this.path.getPath());
        if (!tempPath.exists())
        {
            path.mkdir();
        }
        
        //Ensure the file exists in the directory. Create it if it doesn't.
        tempPath = this.path;
        if (!path.exists())
        {
            path.mkdir();
        }
    }
    
    /*
     * Returns the content of the ConfigFile as an ArrayList<String>
     */
    public ArrayList<String> getFile() throws IOException
    {
        BufferedReader in;
        String lineContent;
        ArrayList<String> temp = new ArrayList<String>();
        if (path.exists())
        {
            in = new BufferedReader(this.in);
            while ((lineContent = in.readLine()) != null)
            {
                temp.add(lineContent);
            }
            in.close();
        }
        else
        {
            throw new NullPointerException();
        }
        return temp;
    }
    
    private boolean isValidChar(char c)
    {
        return (Character.isLetter(c) || c == ':' || Character.isDigit(c));
    }
    
    /************************************************************************
     * Searches the config file for the state of a variable for a specified
     * player. Will throw a NoSuchElementException if anything isn't correct.
     * @param player: The user name of the player to find in the config file.
     * @param var: The variable to find the state of.
     * @return Returns the state of the variable as a string.
     ***********************************************************************/
    public String getPlayerState(String player, String var)
    {
        String state = new String();
        
        int line = 0;
        int step = 0;
        
        boolean foundPlayer = false;
        boolean foundVar = false;
        //search the file
        do
        {
            //ensure we haven't reached the end of the file.
            if (line < contents.size())
            {
                switch (step)
                {
                    //case 0 searches for the correct player.
                    case 0:
                        if (contents.get(line).contains(player))
                        {
                            line++;
                            step++;
                            foundPlayer = true;
                        }
                        else
                        {
                            line++;
                        }
                        break;
                    //case 1 searches for the specified variable and returns its current state if declared.
                    case 1:
                        if (contents.get(line).contains(var))
                        {
                            for(int i = 0; i < contents.get(line).length() - 1; i++)
                            {
                                if (contents.get(line).charAt(i) == ':')
                                {
                                    if (contents.get(line).length() > i + 1 && isValidChar(contents.get(line).charAt(i)))
                                    {
                                        state = contents.get(line).substring(i + 2);
                                    }
                                    //if there isn't anything on the right hand side of the : sign
                                    //throw an exception to be caught where the method is called.
                                    else
                                    {
                                        throw new NoSuchElementException("Variable: " + var + " uninitialized.");
                                    }
                                    step++;
                                    foundVar = true;
                                }
                            }
                        }
                        //If the the current line doesn't contain the variable, and the next one starts with a non
                        //space character, the variable isn't there. Throw an exception.
                        else if (contents.get(line + 1).charAt(0) != ' ')
                        {
                            throw new NoSuchElementException("Variable: " + var + " not found.");
                        }
                        //if none of the other conditions are true, then we are still in the player's state list.
                        //increment the line number to go to the next one.
                        else
                        {
                            line++;
                        }
                        break;
                }
            }
            //If the end of the file has been reached without finding the specified username, throw an exception.
            else
            {
                throw new NoSuchElementException("Player: " + player + " not found.");
            }
        }
        // continue searching until both have been found. Note: throw statements force the program to leave the method.
        while (!foundPlayer && !foundVar);
        return state;
    }
    
    public void addVar(String player, String var, String state)
    {
        
    }
    
    public void updateVar(String player, String var, String state)
    {
        
    }
    
    public void updateConfigFile(ArrayList<String> content)
    {
        
    }
}
