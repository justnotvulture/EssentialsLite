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
     *    062 isValidChar(char c)
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
     *    locatePlayer
     * Description:
     *    Locates the player in the contents array
     * @param player
     * @return The index in the contents ArrayList that contains the
     *    player.
     ***************************************************************/
    private int locatePlayer(String player)
    {
        for (int i = 0; i < contents.size(); i++)
        {
            String line = contents.get(i);
            if (line.length() > 0 && line != null & line.charAt(0) != '#')
            {
                if (line.contains(player))
                    return i;
            }
        }
        return -1;
    }
    
    /****************************************************************
     * Method:
     *    locateVar
     * Description:
     *    Uses locatePlayer to find the index of a specified player's state.
     * @param player
     * @param var
     * @return
     ***************************************************************/
    private int locateVar(String player, String var)
    {   
        int i = locatePlayer(player);
        if (i != -1)
        {
            for (; i < contents.size(); i++)
            {
                if (contents.get(i).contains(var))
                    return i;
                else if (contents.get(i).indexOf(0) != ' ')
                    return -2;
            }
        }
        return -1;
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
        FileReader reader = new FileReader(path);
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
        int line = locateVar(player, var);
        String state = new String("You're not supposed to be here!");
        int col;
        
        switch (line)
        {
            case -1:
                throw new NoSuchElementException("Player: \"" + player + "\" not found.");
            case -2:
                throw new NoSuchElementException("Var: \"" + var + "\" not found.");
            default:
                col = contents.get(line).indexOf(':');
                state = contents.get(line).substring(col + 2);
                break;
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
        
            if (contents.get(contents.size() - 1).length() != 0)
            {
                contents.add("");
            }
            contents.add("x: ");
            
            
    }
    
    
    /****************************************************************
     * Method:
     *    updateConfigFile
     * Description:
     *    Writes the contents of the content ArrayList to the file.
     ***************************************************************/
    public void updateConfigFile()
    {
        try
        {
            FileWriter writer = new FileWriter(path);
            writer.close();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}