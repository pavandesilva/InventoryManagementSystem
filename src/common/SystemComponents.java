package common;

import view.Login;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Pavan De Silva
 */
public class SystemComponents {

    public String generateID(String table) {
        int count = 0;
        try {
            ResultSet rs = DB.search("select count(" + table + "id) as x from " + table + "");
            if (rs.next()) {
                String countString = rs.getString("x");
                count = Integer.parseInt(countString);
                ++count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(count);
    }

    public void error(javax.swing.JFrame frame, String s1) {
        JOptionPane.showMessageDialog(frame, s1, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void infoMessage(javax.swing.JFrame frame, String s1) {
        JOptionPane.showMessageDialog(frame, s1, "Done", JOptionPane.INFORMATION_MESSAGE);
    }

    public void logout(javax.swing.JFrame frame) {
        new Login().setVisible(true);
        frame.dispose();
    }
}
