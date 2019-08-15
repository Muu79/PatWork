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
    //Constructor
    public PatientManager() {

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
    
    public boolean login(String usr, String pass){
        boolean ok = false;
        try {            
            ResultSet rs = db.queryTbl("SELECT * FROM Patients WHERE PatientID = '"+usr+"'");
            while(rs.next()){
                if(rs.getString("Password").equals(pass)){
                    ok = true;
                }
            }           
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }
}
