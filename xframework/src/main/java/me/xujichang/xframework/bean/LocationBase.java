package me.xujichang.xframework.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * des:
 * 对原有location类进行扩展 添加 经纬度编码类型、定位精度
 *
 * @author xjc by 2017/11/15 14:06 .
 */

public class LocationBase implements Parcelable {
    /**
     * 精度
     */
    protected float radius;
    /**
     * 坐标编码类型
     */
    protected String coorType;
    /**
     * 此坐标的定位类型等返回码
     */
    protected int locType;

    protected LocationBase(Parcel in) {
        radius = in.readFloat();
        coorType = in.readString();
        locType = in.readInt();
    }

    protected LocationBase() {

    }

    public static final Creator<LocationBase> CREATOR = new Creator<LocationBase>() {
        @Override
        public LocationBase createFromParcel(Parcel in) {
            return new LocationBase(in);
        }

        @Override
        public LocationBase[] newArray(int size) {
            return new LocationBase[size];
        }
    };

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getCoorType() {
        return coorType;
    }

    public void setCoorType(String coorType) {
        this.coorType = coorType;
    }

    public int getLocType() {
        return locType;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(radius);
        dest.writeString(coorType);
        dest.writeInt(locType);
    }
}
