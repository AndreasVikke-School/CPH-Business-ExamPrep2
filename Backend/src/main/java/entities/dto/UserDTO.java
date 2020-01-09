package entities.dto;

import entities.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andreas
 */
public class UserDTO {
    private String userName;
    private List<String> roleList = new ArrayList();
    
    public UserDTO(String userName, List<String> roleList) {
        this.userName = userName;
        this.roleList = roleList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}
