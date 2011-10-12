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
package com.magicpwd.__a;

import com.magicpwd.__i.IAction;
import com.magicpwd.v.app.tray.TrayPtn;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Amon
 */
public abstract class AAction implements IAction
{

    protected TrayPtn trayPtn;
    protected boolean enabled = true;
    protected boolean visible = true;
    protected boolean selected = false;

    @Override
    public void setTrayPtn(TrayPtn trayPtn)
    {
        this.trayPtn = trayPtn;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public boolean isVisible()
    {
        return visible;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
    }

    @Override
    public Object getValue(String key)
    {
        return null;
    }

    @Override
    public void putValue(String key, Object value)
    {
    }
}
