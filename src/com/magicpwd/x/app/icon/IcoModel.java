/*
 *  Copyright (C) 2011 Aven
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
package com.magicpwd.x.app.icon;

import com.magicpwd._cons.ConsEnv;
import com.magicpwd.m.UserMdl;
import com.magicpwd.r.AmonFF;

/**
 * Application: MagicPwd
 * Author     : Aven
 * Encoding   : UTF-8
 * Website    : http://amon.me/mpwd
 * Project    : http://magicpwd.googlecode.com/
 * Contact    : chat@amon.me
 * CopyRight  : Winshine.biz
 * Description:
 */
class IcoModel extends javax.swing.table.AbstractTableModel
{

    private java.util.List<IcoLabel> iconList;
    private int columnCount;
    private int rowHeight;
    private int selIcon;
    private UserMdl userMdl;

    IcoModel(UserMdl userMdl)
    {
        this.userMdl = userMdl;
        iconList = new java.util.ArrayList<IcoLabel>();
        columnCount = 5;
        rowHeight = 36;
    }

    @Override
    public int getRowCount()
    {
        return iconList.size() / columnCount + 1;
    }

    @Override
    public int getColumnCount()
    {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (iconList.size() < 1)
        {
            return null;
        }
        int index = rowIndex * columnCount + columnIndex;
        if (index >= iconList.size())
        {
            return null;
        }
        return iconList.get(index);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return javax.swing.JLabel.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    public void loadIco(java.io.File icoPath, String lastIcon, int iconSize)
    {
        iconList.clear();
        selIcon = 0;

        if (icoPath != null && icoPath.exists() && icoPath.isDirectory() && icoPath.canRead())
        {
            java.io.File[] fileList = icoPath.listFiles(new AmonFF("(AM|AU)\\d{14}_" + iconSize + "\\.PNG", true));
            if (fileList != null && fileList.length > 0)
            {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(AM|AU)\\d{14}");
                int i = 0;
                for (java.io.File file : fileList)
                {
                    if (!file.isFile())
                    {
                        continue;
                    }
                    java.util.regex.Matcher matcher = pattern.matcher(file.getName());
                    if (!matcher.find())
                    {
                        continue;
                    }

                    String key = matcher.group();
                    if (key.equalsIgnoreCase(lastIcon))
                    {
                        selIcon = i;
                    }
                    i += 1;
                    iconList.add(new IcoLabel(i, key, new javax.swing.ImageIcon(file.getAbsolutePath())));
                }
            }
        }

        this.fireTableDataChanged();
    }

    private static java.awt.image.BufferedImage scaleImage(java.awt.image.BufferedImage img, int dim)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        if (w != dim || h != dim)
        {
            double dw = 16.0 / w;
            double dh = 16.0 / h;
            double d = dw <= dh ? dw : dh;
            w *= d;
            h *= d;
            java.awt.Image tmp = img.getScaledInstance(w, h, java.awt.Image.SCALE_DEFAULT);
            img = new java.awt.image.BufferedImage(dim, dim, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            img.createGraphics().drawImage(tmp, (dim - w) >> 1, (dim - h) >> 1, w, h, null);
        }
        return img;
    }

    private static void writeImage(java.awt.image.BufferedImage img, String path) throws Exception
    {
        java.io.File pngFile = new java.io.File(path);
        if (!pngFile.exists())
        {
            pngFile.createNewFile();
        }
        java.io.FileOutputStream fos = new java.io.FileOutputStream(pngFile);
        javax.imageio.ImageIO.write(img, ConsEnv.IMAGE_FORMAT, fos);
        fos.flush();
        fos.close();
    }

    public synchronized void appendIcon(java.io.File filePath, java.io.File icoPath) throws Exception
    {
        java.io.FileInputStream fis = new java.io.FileInputStream(filePath);
        java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(fis);
        fis.close();

        String key = "AU" + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        writeImage(scaleImage(img, 16), icoPath.getAbsolutePath() + java.io.File.separator + key + "_16." + ConsEnv.IMAGE_FORMAT);
        writeImage(scaleImage(img, 24), icoPath.getAbsolutePath() + java.io.File.separator + key + "_24." + ConsEnv.IMAGE_FORMAT);

        int i = iconList.size();
        iconList.add(new IcoLabel(i, key, new javax.swing.ImageIcon(img)));

        selIcon = i;
        fireTableDataChanged();
    }

    /**
     * @param columnCount the columnCount to set
     */
    public void setColumnCount(int columnCount)
    {
        if (columnCount > 0)
        {
            this.columnCount = columnCount;
        }
    }

    /**
     * @return the rowHeight
     */
    public int getRowHeight()
    {
        return rowHeight;
    }

    /**
     * @param rowHeight the rowHeight to set
     */
    public void setRowHeight(int rowHeight)
    {
        this.rowHeight = rowHeight;
    }

    public int getSelectedRow()
    {
        return selIcon / columnCount;
    }

    public int getSelectedColumn()
    {
        return selIcon % columnCount;
    }

    public String getSelectedIcon(int row, int col)
    {
        IcoLabel label = iconList.get(row * columnCount + col);
        String key = label.getKey();
        userMdl.setDataIcon(key, 16, label.getIcon());
        return key;
    }
}
