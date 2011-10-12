package com.magicpwd._docs;

import com.magicpwd._util.Logs;
import java.io.File;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.nutz.http.Http;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.sender.FilePostSender;
import org.nutz.json.Json;
import org.nutz.lang.Encoding;

import java.util.HashMap;

/**
 * <p/>本API实现了 http://vdisk.me/api/doc 描述的调用
 * <p/><b>创建实例后,必须先调用get_token方法进行授权!!否则其他方法均无法使用!!</b>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>如果发现任何bug或有任何意见建议,请发送邮件到 vdisk4j@wendal.net
 * <p/>项目主页: http://vdisk4j.googlecode.com
 * <p/>
 * <p/>我的博客: http://wendal.net
 * @author wendal(vdisk4j@wendal.net)
 */
public class VDiskAPI
{

    private String token;
    private long dologid;

    /**
     * 保持同步
     * @return
     */
    public VDiskResponse keep()
    {
        HashMap<String, Object> map = createMap();
        return call("?a=keep", map);
    }

    /**
     * 获得token
     * <p/><b>本方法是其他方法的基础,必须在其他方法被调用之前调用!!</b>
     * <p/><b>本方法相当于登陆!!!</b>
     * @param account 账号
     * @param password 密码
     * @param appkey 您的appkey
     * @param appsecret 您的appsecret
     * @param app_type 账号类型,如果为新浪微博账号,则为sinat,否则为null
     * @return
     */
    public VDiskResponse getToken(String account, String password,
            String appkey, String appsecret, String app_type)
    {
        try
        {
            SecretKeySpec spec = new SecretKeySpec(appsecret.getBytes("UTF-8"),
                    "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(spec);
            long time = System.currentTimeMillis();
            String s = String.format(
                    "account=%s&appkey=%s&password=%s&time=%s", account,
                    appkey, password, time);
            byte[] data = mac.doFinal(s.getBytes("iso8859-1"));
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("account", account);
            map.put("password", password);
            map.put("appkey", appkey);
            map.put("time", time);
            map.put("signature", VDiskHelper.getHexString(data));
            if (app_type != null)
            {
                map.put("app_type", app_type);
            }
            VDiskResponse re = call("?m=auth&a=get_token", map);
            if (re != null && re.getErr_code() == VDiskConstant.Success)
            {
                token = re.getData().get("token").toString();
            }
            return re;
        }
        catch (Exception e)
        {
            Logs.exception(e);
        }
        return null;
    }

    /**
     * 保持token,以避免token失效
     * @return
     */
    public VDiskResponse keepToken()
    {
        HashMap<String, Object> map = createMap();
        return call("?m=user&a=keep_token", map);
    }

    /**
     * 文件上传(10M以下)
     * @param dir_id 目录的id,如果为0则表示跟目录
     * @param file 需要上传的文件
     * @param cover 重名时是否覆盖
     * @return
     */
    public VDiskResponse uploadFile(long dir_id, File file, boolean cover)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("file", file);
        if (cover)
        {
            map.put("cover", "yes");
        }
        else
        {
            map.put("cover", "no");
        }
        return put("?m=file&a=upload_file", map);
    }

    /**
     * 上传文件并分享(10M以下)
     * @param dir_id 目录的id,如果为0则表示跟目录
     * @param file 需要上传的文件
     * @param cover 重名时是否覆盖
     * @return
     */
    public VDiskResponse uploadShareFile(long dir_id, File file, boolean cover)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("file", file);
        if (cover)
        {
            map.put("cover", "yes");
        }
        else
        {
            map.put("cover", "no");
        }
        return put("?m=file&a=upload_share_file", map);
    }

    /**
     * 创建目录
     * @param create_name 目录的名称
     * @param parent_id 父目录的id, 如果是根目录值为: 0
     * @return
     */
    public VDiskResponse createDir(String create_name, long parent_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("create_name", create_name);
        map.put("parent_id", parent_id);
        return call("?m=dir&a=create_dir", map);
    }

    /**
     * 获得列表
     * @param dir_id 目录id, 如果是根目录值为: 0
     * @param page 当前页码,以1开始
     * @param pageSize 每页条数,最小为2
     * @return
     */
    public VDiskResponse getList(long dir_id, int page, int pageSize)
    {
        if (page < 1)
        {
            page = 1;
        }
        if (pageSize < 2)
        {
            pageSize = 2;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("token", token); // .put("dologid", dologid)
        map.put("dir_id", dir_id);
        map.put("page", page);
        map.put("parent_id", pageSize);
        return call("?m=dir&a=getlist", map);
    }

    /**
     * 获得容量信息
     * @return
     */
    public VDiskResponse getQuota()
    {
        HashMap<String, Object> map = createMap();
        return call("?m=file&a=get_quota", map);
    }

    /**
     * 无文件上传(sha1)
     * @param dir_id 目录id, 根目录值为: 0
     * @param sha1 文件的sha1值
     * @param file_name 文件名
     * @return
     */
    public VDiskResponse uploadWithSha1(long dir_id, String sha1,
            String file_name)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dir_id", dir_id);
        map.put("sha1", sha1);
        map.put("file_name", file_name);
        return call("?m=file&a=upload_with_sha1", map);
    }

    /**
     * 获得单个文件信息
     * @param fid 文件id
     * @return
     */
    public VDiskResponse getFileInfo(long fid)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        return call("m=file&a=get_file_info", map);
    }

    /**
     * 删除目录
     * @param dir_id 目录id (不能为0)
     * @return
     */
    public VDiskResponse deleteDir(long dir_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dir_id", dir_id);
        return call("?m=dir&a=delete_dir", map);
    }

    /**
     * 删除文件
     * @param fid 文件id
     * @return
     */
    public VDiskResponse deleteFile(long fid)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        return call("?m=file&a=delete_file", map);
    }

    /**
     * 复制文件
     * @param fid 文件id
     * @param new_name 副本的名称
     * @param to_dir_id 目标目录的id, 根目录值为: 0
     * @return
     */
    public VDiskResponse copyFile(long fid, String new_name, long to_dir_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        map.put("new_name", new_name);
        map.put("to_dir_id", to_dir_id);
        return call("?m=file&a=copy_file", map);
    }

    /**
     * 移动文件
     * @param fid 文件id
     * @param new_name 移动后的文件名
     * @param to_dir_id 目标目录的id, 根目录值为: 0
     * @return
     */
    public VDiskResponse moveFile(long fid, String new_name, long to_dir_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        map.put("new_name", new_name);
        map.put("to_dir_id", to_dir_id);
        return call("?m=file&a=move_file", map);
    }

    /**
     * 文件重命名
     * @param fid 文件id
     * @param new_name 新文件名
     * @return
     */
    public VDiskResponse renameFile(long fid, String new_name)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        map.put("new_name", new_name);
        return call("?m=file&a=rename_file", map);
    }

    /**
     * 目录重命名
     * @param dir_id 目录id(不能为0)
     * @param new_name 新名称
     * @return
     */
    public VDiskResponse renameDir(long dir_id, String new_name)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("new_name", new_name);
        map.put("dir_id", dir_id);
        return call("?m=file&a=rename_file", map);
    }

    /**
     * 移动目录
     * @param dir_id 目录id(不能为0)
     * @param new_name 新名称
     * @param to_parent_id 目标父目录id, 根目录值为: 0
     * @return
     */
    public VDiskResponse moveDir(long dir_id, String new_name,
            long to_parent_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("to_parent_id", to_parent_id);
        map.put("new_name", new_name);
        map.put("dir_id", dir_id);
        return call("?m=file&a=move_dir", map);
    }

    /**
     * 分享文件
     * @param fid 文件id
     * @return
     */
    public VDiskResponse shareFile(long fid)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        return call("?m=file&a=share_file", map);
    }

    /**
     * 取消分享
     * @param fid 文件id
     * @return
     */
    public VDiskResponse cancelShareFile(long fid)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        return call("?m=file&a=cancel_share_file", map);
    }

    /**
     * 获得回收站列表
     * @param page 第几页
     * @param pageSize 每页显示条数
     * @return
     */
    public VDiskResponse getRecycleList(int page, int pageSize)
    {
        if (page < 1)
        {
            page = 1;
        }
        if (pageSize < 2)
        {
            pageSize = 2;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("pagesize", pageSize);
        return call("?m=recycle&a=get_list", map);
    }

    /**
     * 清空回收站
     * @return
     */
    public VDiskResponse truncateRecycle()
    {
        HashMap<String, Object> map = createMap();
        return call("?m=file&a=truncate_recycle", map);
    }

    /**
     * 从回收站中彻底删除一个文件
     * @param fid 要删除的文件id
     * @return
     */
    public VDiskResponse deleteRecycleFile(long fid)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        return call("?m=recycle&a=delete_file", map);
    }

    /**
     * 从回收站中彻底删除一个目录
     * @param dir_id 要删除的目录id
     * @return
     */
    public VDiskResponse deleteRecycleDir(long dir_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dir_id", dir_id);
        return call("?m=recycle&a=delete_dir", map);
    }

    /**
     * 从回收站中还原一个文件
     * @param fid 要还原的文件id
     * @return
     */
    public VDiskResponse restoreRecycleFile(long fid)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fid", fid);
        return call("?m=recycle&a=restore_file", map);
    }

    /**
     * 从回收站中还原一个目录
     * @param dir_id 要还原的目录id
     * @return
     */
    public VDiskResponse restoreRecycleDir(long dir_id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dir_id", dir_id);
        return call("?m=recycle&a=restore_dir", map);
    }

    /**
     * 通过路径得到目录id
     * @param path 路径
     * @return
     */
    public VDiskResponse getDiridWithPath(String path)
    {
        HashMap<String, Object> map = createMap();
        map.put("path", path);
        return call("?m=recycle&a=get_dirid_with_path", map);
    }

    protected VDiskResponse call(String url, Map<String, Object> parms)
    {
        String resp = Http.post(VDiskConstant.SERVER + url, parms,
                Encoding.UTF8, Encoding.UTF8);
        if (resp == null)
        {
            return null;// TODO 抛出异常
        }
        VDiskResponse re = Json.fromJson(VDiskResponse.class, resp);
        if (re.getErr_code() == VDiskConstant.Success)
        {
            dologid = re.getDologid();
        }
        return re;
    }

    protected VDiskResponse put(String url, Map<String, Object> parms)
    {
        FilePostSender sender = new FilePostSender(Request.create(
                VDiskConstant.SERVER + url, METHOD.POST, parms));
        String resp = sender.send().getContent();
        if (resp == null)
        {
            return null;// TODO 抛出异常
        }
        VDiskResponse re = Json.fromJson(VDiskResponse.class, resp);
        if (re.getErr_code() == VDiskConstant.Success)
        {
            //需要先判断是否存在dolog_dir, thanks "木子开泰"
            if (re.getDologdir() == null || re.getDologdir().length == 0)
            {
                dologid = re.getDologid();
            }
        }
        return re;
    }

    protected HashMap<String, Object> createMap()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("dologid", dologid);
        return map;
    }
}
