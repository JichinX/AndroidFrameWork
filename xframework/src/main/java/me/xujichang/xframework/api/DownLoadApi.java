package me.xujichang.xframework.api;

import io.reactivex.Observable;
import me.xujichang.util.bean.AppInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author xjc
 *         Created by xjc on 2017/7/7.
 */

public interface DownLoadApi {
    /**
     * 获取APP信息
     *
     * @param name APP包名
     * @return
     */
    @GET("static/app/update/{name}.json")
    Observable<AppInfo> getAppInfo(@Path("name") String name);

    /**
     * 获取APK文件
     *
     * @param name apk包名
     * @return
     */
    @Streaming
    @GET("static/app/update/{name}.apk")
    Observable<ResponseBody> getApkFile(@Path("name") String name);

    /**
     * 获取离线地图包
     *
     * @param name 地图包名字
     * @return
     */
    @Streaming
    @GET("static/app/map/{name}")
    Observable<ResponseBody> getOfflineMapFile(@Path("name") String name);

    /**
     * 下载文件
     *
     * @param url 文件地址
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
