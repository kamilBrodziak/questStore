package DAO;

import Model.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
//    void addAdmin(Admin admin) throws SQLException;
//    void editAdmin(Admin admin) throws SQLException;
//    void deleteAdmin(int id) throws SQLException;
//    List<Admin> getAdmins() throws SQLException;
//    Admin getAdmin(int id) throws SQLException;
    Admin getAdminByLoginID(int id) throws SQLException;
}
