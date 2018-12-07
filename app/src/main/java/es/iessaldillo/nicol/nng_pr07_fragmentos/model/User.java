package es.iessaldillo.nicol.nng_pr07_fragmentos.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class User implements Parcelable {
    private int id;
    private Avatar avatar;
    private String name, email, phone, address, web;

    public User() {
    }

    public User(int id, Avatar avatar, String name, String email, String phone, String address, String web) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.web = web;
    }

    public int getId() {
        return id;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getWeb() {
        return web;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;
        if (obj instanceof User
                && name.equals(((User) obj).name)
                && avatar.equals(((User) obj).avatar)
                && email.equals(((User) obj).email)
                && phone.equals(((User) obj).phone)
                && address.equals(((User) obj).address)
                && web.equals(((User) obj).web)) {

            result = true;

        }
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeString(this.web);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.web = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
