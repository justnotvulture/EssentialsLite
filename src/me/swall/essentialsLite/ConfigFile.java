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
    private ArrayList<String> contents = new ArrayList<String>();
    private FileReader in;
    private FileWriter out;
    
    /****************************************************************
     * Class Summary:
     *    ln# egMethod(int param)
     * 
     *    045 ConfigFile(StringPath)
     *    063 isValidChar(char c)
     *    ### fixPlayerStates(int line, String var)
     *    ### fixPlayerStates(int line, String var, String state)
     *    ### setFile(File path)
     *    ### getFile()
     *    ### getPlayerState(String player, String var)
     *    ### updateVar(String player, String var, String state)
     *    ### addPlayer(String player)
     *    ### updateConfigFile()
     ***************************************************************/
    
    /****************************************************************
     * Method:
     *    Non-Default constructor
     * Parameters:
     *    @param path            The path to the file.
     * Exceptions:
     *    @exception IOException Will be thrown if the given path
     *                           doesn't exist.
     ***************************************************************/
    ConfigFile(String path) throws IOException
    {
        this.path = new File(path);
        in = new FileReader(path);
        out = new FileWriter(path);
        contents.addAll(getFile());
    }
    
    /****************************************************************
     * Method:
     *    isValidChar
     * Description:
     *    Checks to see if the given character is a character that
     *    can be found in a config file.
     * Parameters:
     *    @param  c The character to be checked.
     *    @return Whether or not the char is valid.
     ***************************************************************/
    private boolean isValidChar(char c)
    {
        return (Character.isLetter(c) || c == ':' || Character.isDigit(c));
    }
    
    /****************************************************************
     * Method:
     *    fixPlayerStates
     * Description:
     *    Adds a missing state and ensure contents maintains any
     *    necessary order.
     * Parameters:
     *    @param line The line the player's state block is found begins.
     *    @param var  The variable to be added. No state is added.
     ***************************************************************/
    private void fixPlayerStates(int line, String var)
    {
        return;
    }
    
    /****************************************************************
     * Method:
     *    fixPlayerStates
     * Description:
     *    Adds a missing state and ensure contents maintains any
     *    necessary order.
     * Parameters:
     *    @param line  The line the player's state block is found begins.
     *    @param var   The variable to be added.
     *    @param state The state the variable should be set to.
     ***************************************************************/
    private void fixPlayerStates(int line, String var, String state)
    {
        return;
    }
    
    /****************************************************************
     * Method:
     *    SetFile
     * Description:
     *    Sets the path and name of the file. Creates a new directory
     *    and/or file if it isn't found.
     * Parameters:
     *    @param path: the path where the new File is located.
     ***************************************************************/
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
    
    /****************************************************************
     * Method:
     *    getFile
     * Description:
     *    Returns the content of the ConfigFile as an ArrayList of 
     *    strings.
     * Parameters:    
     *    @return Returns an array list of strings, each is a line.
     ***************************************************************/
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
    
    /****************************************************************
     * Method:
     *    getPlayerState
     * Description:
     *    Searches the config file for the state of a variable for a 
     *    specified player. Will throw a NoSuchElementException if 
     *    anything isn't correct.
     * Parameters:
     *    @param player: The user name of the player to find in the
     *    config file.
     *    @param var: The variable to find the state of.
     *    @return Returns the state of the variable as a string.
     ***************************************************************/
    public String getPlayerState(String player, String var)
    {
        String state = new String();
        
        int line = 0;
        int step = 0;
        
        boolean foundPlayer = false;
        boolean foundVar = false;
        
        //search the file for username, then variable. Afterward, retrieve state.
        while (!(foundPlayer && foundVar))
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
        return state;
    }
    
    /****************************************************************
     * Method:
     *    updateVar
     * Description:
     *    Looks for a variable and updates it's state. If is isn't
     *    found, it will add the variable to the player's states.
     * Parameters:
     *    @param player The player whose state is to be updated.
     *    @param var    The variable to be updated.
     *    @param state  The new state of the variable.
     ***************************************************************/
    public void updateVar(String player, String var, String state)
    {
        boolean foundPlayer = false;
        boolean foundVar = false;
        
        int line = 0;
        int step = 0;
        
        while (!(foundPlayer && foundVar))
        {
            //Ensure we aren't at the end of the file.
            if (line < contents.size())
            {
                switch (step)
                {
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
                    case 1:
                        if (contents.get(line).contains(state + ':'))
                        {
                            contents.set(line, var + ": " + state);
                            foundVar = true;
                        }
                        else
                        {
                            if (isValidChar(contents.get(line + 1).charAt(0)))
                            {
                                
                            }
                            line++;
                        }
                        break;
                }
            }
            //If we have reached the end of the file, throw an exception.
            else
            {
                throw new NoSuchElementException("Player: " + player + " not found.");
            }
        }
    }
    
    /****************************************************************
     * Method:
     *    addPlayer
     * Description:
     *    Adds a player and their variables.
     * Parameters:
     *    @param player The player to be added to the file.
     ***************************************************************/
    public void addPlayer(String player)
    {
        
    }
    
    
    /****************************************************************
     * Method:
     *    updateConfigFile
     * Description:
     *    Writes the contents of the content ArrayList to the file.
     ***************************************************************/
    public void updateConfigFile()
    {
        
    }
}