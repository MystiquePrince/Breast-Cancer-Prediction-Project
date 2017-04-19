
package breastcancerprediction.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class Classifier {
    private double probabilityYes=0;
    private double probabilityNo=0;
    private static final String DB_URL="jdbc:mysql://localhost:3306/breastcancerproject";
    private static Connection  conn=null;
    private static Statement stmt=null;
    private ResultSet rs=null;
    private static Classifier handler=null;
    
    public Classifier(){
        createConnection();
    }
        
     public static Classifier getInstance(){
        if(handler==null){
            handler=new Classifier();
        }
        return handler;
    }
    public final  void createConnection(){
         try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(DB_URL, "root", "");
            System.out.println("Connection created successfully");
        }catch(ClassNotFoundException | SQLException e){
             System.out.println(e);
        }
    }
    
    public boolean execAction(String qu){
     try{
         stmt=conn.createStatement();
         stmt.execute(qu);
         return true;
     }catch(SQLException ex){
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error: ");
         alert.setContentText(ex.getMessage()+ "Error occured");
         alert.showAndWait();
         
         System.out.println("Exception at execQuery: dataHandler "+ ex.getLocalizedMessage());
         return false;
     }finally{
         
     }
 }
    public ResultSet execQuery(String query){
     ResultSet result;
     try{
         stmt=conn.createStatement();
         result=stmt.executeQuery(query);
     }catch(SQLException ex){   
         System.out.println("Exception at execQuery: datahandler "+ex.getLocalizedMessage());
         return null;
     }finally{
         
     }
     return result;
 } 
    
    public void classifyPatient(double age, double bmi,String familyHistory,String alcohol,String radiation,String breastLump,String invertedNipples,String colorChange,String nipplePain,String rash,String nippleDischarge){
            
        try {
            int class1=0;
            int class2=0;
            int occurrence=0;
            stmt=conn.createStatement();
            String query = "SELECT COUNT(*) AS Occurrence FROM TrainingSet WHERE (status = 'yes') ";
            stmt.execute(query);
            rs = stmt.getResultSet();
            if (rs.next()) //Count of cases when prediction was yes in the training set
            {
                class1 = rs.getInt(1);
            }
            
            query = "SELECT COUNT(*) AS Occurrence FROM TrainingSet WHERE (status = 'no') ";
            stmt.execute(query);
            rs = stmt.getResultSet();
            if (rs.next()) //Count of cases when prediction was no in the training set
            {
                class2 = rs.getInt(1);
            }
            
            query = "SELECT COUNT(*) AS Occurrence FROM TrainingSet ";
            stmt.execute(query);
            rs = stmt.getResultSet();
            if (rs.next()) //Count of total cases in training set
            {
                occurrence = rs.getInt(1);
            }
            
            double pclass1 = (double) class1 / occurrence; //General probability for class1
            double pclass2 = (double) class2 / occurrence; //General probability for class2
            
            double likelihoodYes=evaluatePatient(age, bmi,familyHistory, alcohol, radiation, breastLump, invertedNipples, colorChange, nipplePain, rash, nippleDischarge, "Yes");
             double likelihoodNo=evaluatePatient(age, bmi,familyHistory, alcohol, radiation, breastLump, invertedNipples, colorChange, nipplePain, rash, nippleDischarge, "No");
             
             likelihoodYes=likelihoodYes * pclass1;
             likelihoodNo=likelihoodNo * pclass2;
             
             //normalize the likelihoods to get probability
              probabilityYes=likelihoodYes/(likelihoodYes+likelihoodNo)*100;
              probabilityNo=likelihoodNo/(likelihoodYes+likelihoodNo)*100;
           
        } catch (SQLException ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getPYes(){
        return probabilityYes;
    }
    
    public double getPNo(){
        return probabilityNo;
    }
   
     public double evaluatePatient(double age, double bmi, String familyHistory,String alcohol,String radiation,String breastLump,String invertedNipples,String colorChange,String nipplePain,String rash,String nippleDischarge,String status){
        double result=0.0;
         try{
           int a = 0, b = 0, c = 0, d = 0, e=0, f=0,g=0,h=0,i=0,j=0,k=0, total = 0;
           
           String query;
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE (age = '" + age + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               a = rs.getInt(1)+1;
           }
           // a = count of values in training set having age , status same as passed in argument
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE (bmi = '" + bmi + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               b = rs.getInt(1)+1;
           }
           //b= count of values in training set having bmi, status same as passed in argument
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (familyHistory = '" + familyHistory + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               c = rs.getInt(1)+1;
           }
           //c= count of values in training set having familyHistory, status same as passed in argument
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (alcohol = '" + alcohol + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               d = rs.getInt(1)+1;
           }
           //d= count of values in training set having alcohol, status same as passed in argument
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (radiation = '" + radiation + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               e = rs.getInt(1)+1;
           }
           //e=count of values in training set having radiation, status same as passed in argument
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (breastLump = '" + breastLump + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               f = rs.getInt(1)+1;
           }
           //f=
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (invertedNipples = '" + invertedNipples + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               g = rs.getInt(1)+1;
           }
           //g=
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (colorChange = '" + colorChange + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               h = rs.getInt(1)+1;
           }
           //h=
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (nipplePain = '" + nipplePain + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               i = rs.getInt(1)+1;
           }
           //i=
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (rash = '" + rash + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               j = rs.getInt(1)+1;
           }
           //j=
           
           query = "SELECT COUNT(*) AS Count FROM trainingset WHERE   (discharge = '" + nippleDischarge + "' ) AND (status = '" + status + "') ";
           stmt.execute(query);
           rs = stmt.getResultSet();
           if (rs.next()) {
               k = rs.getInt(1)+1;
           }
           //k=
           
           query = "SELECT COUNT(*) AS Expr1 FROM   trainingset WHERE (status = '" + status + "') ";
            stmt.execute(query);
            rs = stmt.getResultSet();
            if (rs.next()) {
                total = rs.getInt(1); //total no resuults
            }
            
            //calculating probability using naive bayes
            result=(float) a / (float) total * (float) b / (float) total * (float) c / (float) total * (float) d / (float) total * (float) e / (float) total *
                    (float) f / (float) total * (float) g / (float) total * (float) h / (float) total * (float) i / (float) total * (float) j / (float) total  ;
       }catch(SQLException ex){
             Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null,ex);
        }
       return result;
    }
  
}
