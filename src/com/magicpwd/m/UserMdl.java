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
package com.magicpwd.m;

import com.magicpwd._cons.ConsCfg;
import com.magicpwd._cons.ConsEnv;
import com.magicpwd._enum.AppView;
import com.magicpwd._util.Bean;
import com.magicpwd._util.Char;
import com.magicpwd._util.Logs;

/**
 *
 * @author Amon
 */
public final class UserMdl
{
    private boolean incBack = true;
    private boolean topMost;
    /**
     * 定时任务有更新
     */
    private boolean gtdUpdt;
    /**
     * 口令空间有更新
     */
    private boolean ucsUpdt;
    private MpwdMdl mpwdMdl;
    private LibList tpltMdl;
    private UcsList charMdl;
    private HintMdl hintMdl;
    private AppView appView = AppView.mwiz;
    private java.util.Properties userCfg;
    private static java.util.Properties defProp;
    private static java.util.Properties favProp;
    private static java.util.HashMap<String, javax.swing.Icon> defIcon;
    private static java.util.HashMap<String, javax.swing.Icon> favIcon;
    static SafeKey safeKey;

    public UserMdl(MpwdMdl mpwdMdl)
    {
        this.mpwdMdl = mpwdMdl;
        userCfg = new java.util.Properties();
        safeKey = new SafeKey(this);
    }

    public boolean loadCfg(String path)
    {
        userCfg.clear();

        java.io.FileInputStream fis = null;
        try
        {
            java.io.File file = new java.io.File(path, ConsEnv.FILE_DATA + ".config");
            if (!file.exists() || !file.canRead())
            {
                return false;
            }

            fis = new java.io.FileInputStream(file);
            userCfg.load(fis);
            return true;
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return false;
        }
        finally
        {
            Bean.closeStream(fis);
        }
    }

    public void loadDef()
    {
        userCfg.clear();
        incBack = true;
        topMost = false;
    }

    public void saveCfg()
    {
        java.io.FileOutputStream fos = null;
        try
        {
            fos = new java.io.FileOutputStream(new java.io.File(getDataDir(), ConsEnv.FILE_DATA + ".config"));
            userCfg.store(fos, "MagicPwd User Configuration File!");
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            Bean.closeStream(fos);
        }

        mpwdMdl.saveCfg();
    }

    public String getCfg(String key, String def)
    {
        return getCfg(AppView.mpwd, key, def);
    }

    public String getCfg(AppView ptn, String key, String def)
    {
        if (!Char.isValidate(key))
        {
            return def;
        }
        return userCfg.getProperty(Char.format(key, ptn.name(), safeKey.getName()), def);
    }

    public void setCfg(String key, String value)
    {
        setCfg(AppView.mpwd, key, value);
    }

    public void setCfg(AppView ptn, String key, String value)
    {
        if (Char.isValidate(key))
        {
            userCfg.setProperty(Char.format(key, ptn.name(), safeKey.getName()), value);
        }
    }

    /**
     * @return the menuViw
     */
    public boolean isMenuVisible(AppView ptn)
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getCfg(ptn, ConsCfg.CFG_VIEW_MENU, ConsCfg.DEF_TRUE));
    }

    /**
     * @param visible
     *            the menuViw to set
     */
    public void setMenuVisible(AppView ptn, boolean visible)
    {
        setCfg(ptn, ConsCfg.CFG_VIEW_MENU, visible ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    /**
     * @return the toolViw
     */
    public boolean isToolVisible(AppView view)
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getCfg(view, ConsCfg.CFG_VIEW_TOOL, ""));
    }

    /**
     * @param visible
     *            the toolViw to set
     */
    public void setToolVisible(AppView view, boolean visible)
    {
        setCfg(view, ConsCfg.CFG_VIEW_TOOL, visible ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    /**
     * @return the infoViw
     */
    public boolean isInfoVisible(AppView view)
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getCfg(view, ConsCfg.CFG_VIEW_INFO, ConsCfg.DEF_TRUE));
    }

    /**
     * @param visible
     *            the infoViw to set
     */
    public void setInfoVisible(AppView view, boolean visible)
    {
        setCfg(view, ConsCfg.CFG_VIEW_INFO, visible ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    /**
     * @return the findViw
     */
    public boolean isFindVisible(AppView view)
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getCfg(view, ConsCfg.CFG_VIEW_FIND, ConsCfg.DEF_TRUE));
    }

    /**
     * @param visible
     *            the findViw to set
     */
    public void setFindVisible(AppView view, boolean visible)
    {
        setCfg(view, ConsCfg.CFG_VIEW_FIND, visible ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    /**
     * @return the toolLoc
     */
    public String getToolLoc(AppView view)
    {
        return getCfg(view, ConsCfg.CFG_VIEW_TOOL_LOC, "North");
    }

    /**
     * @param toolLoc
     *            the toolLoc to set
     */
    public void setToolLoc(AppView view, String toolLoc)
    {
        setCfg(view, ConsCfg.CFG_VIEW_TOOL_LOC, toolLoc);
    }

    /**
     * @return the clipDlt
     */
    public int getClipDlt()
    {
        return Integer.parseInt(getCfg(AppView.mpwd, ConsCfg.CFG_SAFE_CLIP_DLT, ConsCfg.DEF_CLIP_DLT));
    }

    /**
     * @param clipDlt
     *            the clipDlt to set
     */
    public void setClipDlt(int clipDlt)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_SAFE_CLIP_DLT, Integer.toString(clipDlt));
    }

    /**
     * @return the pwdsLen
     */
    public String getPwdsLen()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_PWDS_SIZE, ConsCfg.DEF_PWDS_SIZE);
    }

    /**
     * @param pwdsLen
     *            the pwdsLen to set
     */
    public void setPwdsLen(int pwdsLen)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_PWDS_SIZE, "" + pwdsLen);
    }

    /**
     * @return the pwdsKey
     */
    public String getPwdsKey()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_PWDS_HASH, ConsCfg.DEF_PWDS_HASH);
    }

    /**
     * @param pwdsKey
     *            the pwdsKey to set
     */
    public void setPwdsKey(String pwdsKey)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_PWDS_HASH, pwdsKey);
    }

    public int getHintInt()
    {
        String txt = getCfg(AppView.mpwd, ConsCfg.CFG_HINT_INT, "60");
        if (Char.isValidatePositiveInteger(txt))
        {
            return Integer.parseInt(txt);
        }
        return 60;
    }

    public void setHintInt(int hintInt)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_HINT_INT, "" + hintInt);
    }

    public int getHintPre()
    {
        String txt = getCfg(AppView.mpwd, ConsCfg.CFG_HINT_PRE, "300");
        if (Char.isValidatePositiveInteger(txt))
        {
            return Integer.parseInt(txt);
        }
        return 300;
    }

    public void setHintPre(int hintPre)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_HINT_PRE, "" + hintPre);
    }

    public String getDataDir()
    {
        return mpwdMdl.getDatPath(safeKey.getName());
    }

    public void setDataDir(String dataDir)
    {
        mpwdMdl.setDatPath(safeKey.getName(), dataDir);
    }

    /**
     * @return the bakNum
     */
    public int getDumpCnt()
    {
        String txt = getCfg(AppView.mpwd, ConsCfg.CFG_SAFE_DUMP_CNT, "3");
        if (Char.isValidatePositiveInteger(txt))
        {
            return Integer.parseInt(txt);
        }
        return 3;
    }

    /**
     * @param dumpCnt
     *            the dumpCnt to set
     */
    public void setDumpCnt(int dumpCnt)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_SAFE_DUMP_CNT, "" + dumpCnt);
    }

    /**
     * @return the bakDir
     */
    public String getDumpDir()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_SAFE_DUMP_DIR, ConsCfg.DEF_DUMP_PATH);
    }

    /**
     * @param dumpDir
     *            the backDir to set
     */
    public void setDumpDir(String dumpDir)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_SAFE_DUMP_DIR, dumpDir);
    }

    /**
     * @return the editViw
     */
    public boolean isEditVisible(AppView view)
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getCfg(view, ConsCfg.CFG_VIEW_EDIT_VIW, ConsCfg.DEF_TRUE));
    }

    /**
     * @param editViw
     *            the editViw to set
     */
    public void setEditVisible(AppView view, boolean editViw)
    {
        setCfg(view, ConsCfg.CFG_VIEW_EDIT_VIW, editViw ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    public boolean isEditIsolate(AppView view)
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getCfg(view, ConsCfg.CFG_VIEW_EDIT_WND, ConsCfg.DEF_FALSE));
    }

    /**
     * @param isolate
     *            the editWnd to set
     */
    public void setEditIsolate(AppView view, boolean isolate)
    {
        setCfg(view, ConsCfg.CFG_VIEW_EDIT_WND, isolate ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    public String getPwdsLoop()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_PWDS_LOOP, ConsCfg.DEF_TRUE);
    }

    /**
     * @return the pwdUpt
     */
    public boolean isPwdsLoop()
    {
        return ConsCfg.DEF_TRUE.equalsIgnoreCase(getPwdsLoop());
    }

    /**
     * @param pwdsLoop
     *            the pwdsUpt to set
     */
    public void setPwdsLoop(boolean pwdsLoop)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_PWDS_LOOP, pwdsLoop ? ConsCfg.DEF_TRUE : ConsCfg.DEF_FALSE);
    }

    public String getSkin()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_SKIN, ConsCfg.DEF_SKIN_DEF);
    }

    public void setSkin(String skin)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_SKIN, skin);
    }

    public String getLook()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_SKIN_LOOK, ConsCfg.DEF_SKIN_LOOK_SYS);
    }

    public void setLook(String look)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_SKIN_LOOK, look);
    }

    public String getFeel()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_SKIN_FEEL, ConsCfg.DEF_SKIN_FEEL_DEF);
    }

    public void setFeel(String feel)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_SKIN_FEEL, feel);
    }

    public String getLang()
    {
        String lang = getCfg(AppView.mpwd, ConsCfg.CFG_LANG, "");
        if (!Char.isValidate(lang))
        {
            lang = System.getProperty("user.language");//Locale.getDefault().getLanguage();
            String country = System.getProperty("user.country");//Locale.getDefault().getCountry();
            if (Char.isValidate(country))
            {
                lang += '_' + country;
            }
        }
        return lang;
    }

    public void setLang(String lang)
    {
        setCfg(AppView.mpwd, ConsCfg.CFG_LANG, lang);
    }

    public String getCode()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_USER_CODE, "");
    }

    public String getName()
    {
        return getCfg(AppView.mpwd, ConsCfg.CFG_USER_NAME, "");
    }

//    public javax.swing.ImageIcon readDataIcon(String path)
//    {
//        return Bean.readIcon(path.replace(ConsEnv.FEEL_ARGS, getFeel()));
//    }
    public javax.swing.ImageIcon readFeelIcon(String path)
    {
        return Bean.readIcon(path.replace(ConsEnv.FEEL_ARGS, getFeel()));
    }

    public java.awt.image.BufferedImage readFeelImage(String path)
    {
        java.io.File file = new java.io.File(path.replace(ConsEnv.FEEL_ARGS, getFeel()));
        if (!file.exists() || !file.isFile() || !file.canRead())
        {
            return null;
        }

        try
        {
            return javax.imageio.ImageIO.read(file);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
            return null;
        }
    }

    public static void loadFeelDef()
    {
        // 读取默认配置文件
        defProp = new java.util.Properties();
        java.io.InputStream stream = null;
        try
        {
            stream = UserMdl.class.getResourceAsStream("/res/feel.amf");
            defProp.load(stream);
        }
        catch (Exception ex)
        {
            Logs.exception(ex);
        }
        finally
        {
            Bean.closeStream(stream);
        }

        // 加载默认图标
        defIcon = new java.util.HashMap<String, javax.swing.Icon>();
        try
        {
            stream = UserMdl.class.getResourceAsStream(ConsEnv.RES_ICON + "icon.png");
            java.awt.image.BufferedImage bufImg = javax.imageio.ImageIO.read(stream);

            int w = bufImg.getWidth();
            int h = bufImg.getHeight();
            for (int i = 0, j = 0; j < w; i += 1)
            {
                defIcon.put("def:" + i, new javax.swing.ImageIcon(bufImg.getSubimage(j, 0, h, h)));
                j += h;
            }
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            Bean.closeStream(stream);
        }
    }

    public void loadFeelFav()
    {
        // 读取用户配置文件
        favProp = new java.util.Properties();
        java.io.File file = new java.io.File(ConsEnv.DIR_SKIN, ConsEnv.DIR_FEEL);
        if (!file.exists() || !file.isDirectory() || !file.canRead())
        {
            return;
        }
        file = new java.io.File(file, getFeel() + java.io.File.separator + ConsEnv.SKIN_FEEL_FORM);
        if (!file.exists() || !file.isFile() || !file.canRead())
        {
            return;
        }

        java.io.InputStream stream = null;
        try
        {
            stream = new java.io.FileInputStream(file);
            favProp.load(stream);
        }
        catch (Exception exp)
        {
            Logs.exception(exp);
        }
        finally
        {
            Bean.closeStream(stream);
        }
    }

    /**
     * 系统默认图片
     * @param favHash
     * @return
     */
    public javax.swing.Icon getFeelDef(String favHash)
    {
        if (!Char.isValidate(favHash))
        {
            return Bean.getNone();
        }

        if (defProp.containsKey(favHash))
        {
            favHash = defProp.getProperty(favHash);
        }
        return defIcon.get("def:" + favHash);
    }

    /**
     * 缓存用户偏好图片
     * @param favHash
     * @param favIcon
     */
    public void setFeelFav(String favHash, javax.swing.Icon favIcon)
    {
        if (Char.isValidate(favHash))
        {
//            if (defProp.containsKey(favHash))
//            {
//                favHash = defProp.getProperty(favHash);
//            }
            defIcon.put("fav:" + favHash, favIcon);
        }
    }

    /**
     * 加载用户配置界面图标
     * @param key
     * @param chache
     * @return
     */
    public javax.swing.Icon getFeelFav(String key, String uri)
    {
        if (favProp == null)
        {
            loadFeelFav();
        }

        boolean keep = Char.isValidate(key);

        javax.swing.Icon icon = null;
        // 读取缓存数据
        if (keep)
        {
            icon = defIcon.get("fav:" + key);
            if (icon != null)
            {
                return icon;
            }
        }

        if (Char.isValidate(uri))
        {
            if (uri.toLowerCase().startsWith("var:"))
            {
                uri = uri.substring(4);
                if (favProp.containsKey(uri))
                {
                    icon = readFeelIcon(ConsEnv.FEEL_PATH + favProp.getProperty(uri));
                }
                else
                {
                    icon = getFeelDef(uri);
                }
            }
            else
            {
                icon = Bean.readIcon(uri);
            }
        }

        if (icon == null)
        {
            icon = Bean.getNone();
        }
        if (keep)
        {
            setFeelFav(key, icon);
        }
        return icon;
    }

    /**
     * @return the incBack
     */
    public boolean isIncBack()
    {
        return incBack;
    }

    /**
     * @param incBack the incBack to set
     */
    public void setIncBack(boolean incBack)
    {
        this.incBack = incBack;
    }

    /**
     * @return the viewTop
     */
    public boolean isTopMost()
    {
        return topMost;
    }

    /**
     * @param topMost
     *            the viewTop to set
     */
    public void setTopMost(boolean topMost)
    {
        this.topMost = topMost;
    }

    /**
     * @return the tpltMdl
     */
    public LibList getTpltMdl()
    {
        if (tpltMdl == null)
        {
            tpltMdl = new LibList(this);
            tpltMdl.initData();
        }
        return tpltMdl;
    }

    /**
     * @return the charMdl
     */
    public UcsList getCharMdl()
    {
        if (charMdl == null)
        {
            charMdl = new UcsList(this);
            charMdl.initData();
        }
        return charMdl;
    }

    public HintMdl getHintMdl()
    {
        if (hintMdl == null)
        {
            hintMdl = new HintMdl(this);
            hintMdl.init();
        }
        return hintMdl;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return safeKey.getName();
    }

    /**
     * 用户登录
     * @param userName
     * @param userPwds
     * @param userSalt
     * @throws Exception
     */
    public boolean signIn(String userName, String userPwds) throws Exception
    {
        safeKey.setName(userName);
        safeKey.setPwds(userPwds);
        return safeKey.signIn();
    }

    public boolean signLs(String userPwds) throws Exception
    {
        safeKey.setPwds(userPwds);
        return safeKey.signIn();
    }

    public boolean signRs(String userName, String userPwds) throws Exception
    {
        safeKey.setName(userName);
        safeKey.setPwds(userPwds);
        return safeKey.signIn();
    }

    public boolean signPb(String userName, String userPwds) throws Exception
    {
        safeKey.setName(userName);
        safeKey.setPwds(userPwds);
        return safeKey.signPb();
    }

    /**
     * 修改登录口令
     * @param oldPwds
     * @param userPwds
     * @throws Exception
     */
    public boolean signPk(String oldPwds, String newPwds) throws Exception
    {
        if (safeKey == null)
        {
            return false;
        }
        return safeKey.signPk(oldPwds, newPwds);
    }

    /**
     * 口令找回
     *
     * @param secPwds
     * @return
     * @throws Exception
     */
    public boolean signFp(String usrName, StringBuffer secPwds) throws Exception
    {
        if (safeKey == null)
        {
            return false;
        }
        return safeKey.signFp(usrName, secPwds);
    }

    /**
     * 设置安全口令
     * @param secPwds
     * @return
     * @throws java.lang.Exception
     */
    public boolean signSk(String oldPwds, String secPwds) throws Exception
    {
        if (safeKey == null)
        {
            return false;
        }
        return safeKey.signSk(oldPwds, secPwds);
    }

    /**
     * 用户注销
     * @param userName
     * @param userPwds
     * @throws Exception
     */
    public boolean signOx(String userName, String userPwds)
    {
        return safeKey.signOx();
    }

    /**
     * 用户注册
     * @param userName
     * @param userPwds
     * @return
     * @throws Exception
     */
    public boolean signUp(String userName, String userPwds) throws Exception
    {
        safeKey.setName(userName);
        safeKey.setPwds(userPwds);
        return safeKey.signUp();
    }

    public boolean hasSkey()
    {
        return safeKey.hasSkey();
    }

    public int getTrayHintCnt()
    {
        String txt = userCfg.getProperty(ConsCfg.CFG_TRAY_HINT_CNT);
        if (Char.isValidatePositiveInteger(txt))
        {
            return Integer.parseInt(txt);
        }
        return -1;
    }

    public void setTrayHintCnt(int cnt)
    {
        userCfg.setProperty(ConsCfg.CFG_TRAY_HINT_CNT, "" + cnt);
    }

    /**
     * @return the mpwdMdl
     */
    public MpwdMdl getMpwdMdl()
    {
        return mpwdMdl;
    }

    public boolean isGtdTemplateUpdated()
    {
        return gtdUpdt;
    }

    public void setGtdTemplateUpdated(boolean updated)
    {
        gtdUpdt = updated;
    }

    public boolean isUcsTemplateUpdated()
    {
        return ucsUpdt;
    }

    public void setUcsTemplateUpdated(boolean updated)
    {
        ucsUpdt = updated;
    }

    /**
     * @return the bakPath
     */
    public String getBakPath()
    {
        return "bakPath";
    }

    /**
     * @param bakPath the bakPath to set
     */
    public void setBakPath(String bakPath)
    {
        if (!Char.isValidate(bakPath))
        {
            bakPath = "bak";
        }
        if (bakPath.endsWith("/"))
        {
            bakPath = bakPath.substring(0, bakPath.length() - 1);
        }
//        this.bakPath = bakPath;
    }

    /**
     * @return the appView
     */
    public AppView getAppView()
    {
        return appView;
    }

    /**
     * @param appView the appView to set
     */
    public void setAppView(AppView appView)
    {
        this.appView = appView;
    }

    public void clearDataIcon()
    {
        if (favIcon != null)
        {
            favIcon.clear();
        }
    }

    public void clearDataIcon(String hash)
    {
        if (favIcon != null && hash != null)
        {
            favIcon.remove(hash);
        }
    }

    public javax.swing.Icon getDataIcon(String path, String hash, int size)
    {
        if (!Char.isValidateHash(hash))
        {
            return Bean.getNone();
        }
        if (favIcon == null)
        {
            favIcon = new java.util.HashMap<String, javax.swing.Icon>();
        }
        String key = hash + size;
        if (Char.isValidate(path))
        {
            hash = path + '/' + hash;
        }
        if (!favIcon.containsKey(key))
        {
            favIcon.put(key, new javax.swing.ImageIcon(Char.format("{0}/{1}/{2}_" + size + ".png", getDataDir(), ConsEnv.DIR_ICO, hash)));
        }
        return favIcon.get(key);
    }

    public void setDataIcon(String hash, int size, javax.swing.Icon icon)
    {
        if (!Char.isValidateHash(hash))
        {
            return;
        }
        if (favIcon == null)
        {
            favIcon = new java.util.HashMap<String, javax.swing.Icon>();
        }
        favIcon.put(hash + size, icon);
    }
}
