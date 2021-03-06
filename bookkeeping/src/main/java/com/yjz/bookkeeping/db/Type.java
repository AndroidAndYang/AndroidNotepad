package com.yjz.bookkeeping.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author YJZ
 * date 2018/12/14
 * description 记账分类类型
 */

@Entity
public class Type implements Parcelable {

    @Id
    /** 分类 id */
    private Long id;
    /** 用户 id */
    private Long uid;
    /** 分类类型（1，支出 2，收入） */
    private Long type;
    /** 位置索引 */
    private int index;
    /** 分类名称 */
    private String name;
    /** 分类图片名称 */
    private String icon;

    protected Type(Parcel in) {
        id = in.readLong();
        uid = in.readLong();
        type = in.readLong();
        index = in.readInt();
        name = in.readString();
        icon = in.readString();
    }
    @Generated(hash = 1783441715)
    public Type(Long id, Long uid, Long type, int index, String name, String icon) {
        this.id = id;
        this.uid = uid;
        this.type = type;
        this.index = index;
        this.name = name;
        this.icon = icon;
    }
    @Generated(hash = 1782799822)
    public Type() {
    }

    public static final Creator<Type> CREATOR = new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getIndex() {
        return this.index;
    }
    public Long getUid()
    {
        return uid;
    }
    public void setUid(Long uid)
    {
        this.uid = uid;
    }
    public Long getType()
    {
        return type;
    }
    public void setType(Long type)
    {
        this.type = type;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(uid);
        parcel.writeLong(type);
        parcel.writeInt(index);
        parcel.writeString(name);
        parcel.writeString(icon);
    }
}
