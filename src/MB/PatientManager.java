/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bhyatmua
 */
public class PatientManager {

    //Fields
    String username;
    //Constructor
    public PatientManager() {

    }
    public PatientManager(String user){
        username = user;
    }
    DBConnect db = new DBConnect();

    public String getPatientTbl(String sql) {
        String temp = "";

        try {
            ResultSet rs = db.queryTbl(sql);

            while (rs.next()) {
                //Used to return all fields from patient table
                temp += rs.getString(2) + "\t";
                temp += rs.getString(3) + "\t";
                temp += rs.getString(4) + "\t";
                temp += rs.getString(5) + "\t";
                temp += rs.getInt(6) + "\t";
                temp += rs.getString(7) + "\t";
                temp += rs.getString(10) + "\t";
                temp += "\n";
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return temp;
    }

    public int login(String usr, String pass) {
        int auth = 0;
        try {
            ResultSet rs = db.queryTbl("SELECT * FROM Patients WHERE Username = '" + usr + "'");
            while (rs.next()) {
                if (rs.getString("Password").equals(pass)) {
                    auth = 3;
                    return auth;
                }
            }
            
        } catch (SQLException ex) {
            auth = 0;
        }
        
        try {
            ResultSet nrs = db.queryTbl("SELECT * FROM Doctors");
            System.out.println(nrs.getString(1));
            while(nrs.next()){
                ResultSet ptd = db.queryTbl("SELECT TrustedDoc FROM Patients WHERE Username = '"+usr+"'");
                System.out.println(nrs.getString("DoctorID"));
                if(ptd.getString("TrustedDoc").equals(nrs.getString("DoctorID"))){
                    System.out.println("Success?");
                    auth = 2;
                    return auth;
                }
            
            }
        } catch (Exception e) {
            auth = 0;
            System.out.println("PROMBLEM");
        }
        return auth;
    }

    public int login(String usr) {
        int auth = 0;
        try {
            ResultSet rs = db.queryTbl("SELECT * FROM Patients WHERE Username = '" + usr + "'");
            while (rs.next()) {
                auth = 1;
            }
        } catch (SQLException ex) {
            auth = 0;
        }

        return auth;
    }

    public boolean logExixts(String usr) {
        try {
            ResultSet rs = db.queryTbl("SELECT * FROM Patients WHERE Username = '" + usr + "'");
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void updateTbl(String sql) throws SQLException {
        db.updateTbl(sql);
    }
    public boolean changePassword(String rec, String pass){
        boolean temp = false;
        try{
            ResultSet rs = db.queryTbl("SELECT * FROM Patients WHERE Username = '"+username+"'");
            System.out.println("No Issue With SQL");
            while(rs.next()){
                if(rs.getString("RecoveryCode").equals(rec)){
                    db.updateTbl("UPDATE Patients SET Password = '"+pass+"' WHERE Username = '"+username+"'");
                    System.out.println("Success");
                    temp = true;
                }
            }
        }
        catch (SQLException ex) {
            System.out.println("Issue with SQL :(");
        }
        return temp;
       
    }

}
