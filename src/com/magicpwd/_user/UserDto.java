/*
 *  Copyright (C) 2011 yaoshangwen
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.magicpwd._user;

/**
 *
 * @author yaoshangwen
 */
public class UserDto
{

    private String userType;
    private String userName;
    private String userPwds;

    public UserDto()
    {
    }

    public UserDto(String userPwds)
    {
        this.userPwds = userPwds;
    }

    public UserDto(String userName, String userPwds)
    {
        this.userName = userName;
        this.userPwds = userPwds;
    }

    public UserDto(String userType, String userName, String userPwds)
    {
        this.userType = userType;
        this.userName = userName;
        this.userPwds = userPwds;
    }

    /**
     * @return the userType
     */
    public String getUserType()
    {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the userPwds
     */
    public String getUserPwds()
    {
        return userPwds;
    }

    /**
     * @param userPwds the userPwds to set
     */
    public void setUserPwds(String userPwds)
    {
        this.userPwds = userPwds;
    }
}
