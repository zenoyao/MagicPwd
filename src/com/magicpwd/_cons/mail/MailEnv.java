/*
 *  Copyright (C) 2010 Amon
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
package com.magicpwd._cons.mail;

/**
 *
 * @author Amon
 */
public interface MailEnv
{

    String TEXT_PLAIN = "text/plain";
    String TEXT_HTML = "text/html";
    String MULTIPART = "multipart/*";
    String MESSAGE = "message/rfc822";
    String CHARSET = "charset";
}
