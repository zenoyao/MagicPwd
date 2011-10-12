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
package com.magicpwd.e.mwiz;

import com.magicpwd.__a.mwiz.AMwizAction;

/**
 * Application: MagicPwd
 * Author     : Amon
 * Encoding   : UTF-8
 * Created    : 2010-11-14 11:16:54
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
public class HintAction extends AMwizAction
{

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        Object object = e.getSource();
        javax.swing.AbstractButton button;
        if (object instanceof javax.swing.AbstractButton)
        {
            button = (javax.swing.AbstractButton) object;
            if (button.isSelected())
            {
                mwizPtn.findHint();

                button = mwizPtn.getMenuPtn().getButton("find");
                if (button != null)
                {
                    button.setSelected(false);
                }
                mwizPtn.setFindVisible(false);
            }
            else
            {
                mwizPtn.findKeys(null);
            }
        }
    }

    @Override
    public void doInit(String value)
    {
    }

    @Override
    public void reInit(javax.swing.AbstractButton button, String value)
    {
    }
}
