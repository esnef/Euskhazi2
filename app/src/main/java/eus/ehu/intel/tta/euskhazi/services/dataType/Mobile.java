package eus.ehu.intel.tta.euskhazi.services.dataType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alumno on 8/01/16.
 */
public class Mobile implements Serializable {
    private static final long serialVersionUID = 1L;


    //private int idmobiles;

    private String mobilesMAC;
    private List<User> users;


    public Mobile() {
    }

    public Mobile(String mobilesMAC,List<User> users) {
        this.mobilesMAC=mobilesMAC;
        this.users=users;
    }


    public void setMobilesMAC(String mobilesMAC) {
        this.mobilesMAC = mobilesMAC;
    }

    public String getMobilesMAC(String maCaddress) {
        return mobilesMAC;
    }


    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User addUser(User user) {
        getUsers().add(user);

        return user;
    }

    public User removeUser(User user) {
        getUsers().remove(user);
        return user;
    }





}
