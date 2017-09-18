/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;

import net.sf.jasperreports.engine.*;

import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author miguel
 */
public class Reporte{
   
    
    public  Container crearReporte(String jasper) throws Exception { 
        
        try {
            
            java.net.URL url = Reporte.class.getResource(jasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(url);
            
//Pasando la conexion
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/progamacion3", "root", "");
            
            //Con Beans
            //JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(miLista));
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, conexion);
            JasperViewer visualizador = new JasperViewer(jasperPrint); // es un jframe
            return visualizador.getContentPane();
        } catch (Exception e) {
            throw e;
        }
    }
}
