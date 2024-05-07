/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.DB;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class ItemController {
    
    public static ResultSet itemSearch(String id){
        ResultSet rs = null;
        String sql = "SELECT * FROM item WHERE itemid='" + id + "'";
        try {
            rs = DB.search(sql);
        } catch (Exception ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
