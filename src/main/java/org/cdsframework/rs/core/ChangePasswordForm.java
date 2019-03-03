/*
 * Copyright (C) 2019 sdn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cdsframework.rs.core;

/**
 *
 * @author sdn
 */
public class ChangePasswordForm {

    private String userName;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String passwordToken;

    /**
     * Get the value of passwordToken
     *
     * @return the value of passwordToken
     */
    public String getPasswordToken() {
        return passwordToken;
    }

    /**
     * Set the value of passwordToken
     *
     * @param passwordToken new value of passwordToken
     */
    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken;
    }

    /**
     * Get the value of confirmPassword
     *
     * @return the value of confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Set the value of confirmPassword
     *
     * @param confirmPassword new value of confirmPassword
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Get the value of newPassword
     *
     * @return the value of newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set the value of newPassword
     *
     * @param newPassword new value of newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get the value of currentPassword
     *
     * @return the value of currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Set the value of currentPassword
     *
     * @param currentPassword new value of currentPassword
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
