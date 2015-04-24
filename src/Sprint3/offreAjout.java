/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprint3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

/**
 * @author seif
 */
public class offreAjout extends MIDlet implements CommandListener, Runnable {



    Display disp = Display.getDisplay(this);
    //Form 1
    Form f1 = new Form("ajout Offre");
    
    
    //les Listes
    List etatList = new List("Select etat", List.IMPLICIT);
    List typeImmobList = new List("Select type immobliler", List.IMPLICIT);
    List natureList= new List("Select nature ", List.IMPLICIT);
    
  
    //textFieald
    TextField tfpayement = new TextField("payement", null, 100, TextField.NUMERIC);
    TextField tfsurface = new TextField("surface", null, 100, TextField.NUMERIC);
    TextField tfnbrPiece = new TextField("nombre piece", null, 100, TextField.NUMERIC);
    TextField tfdescription = new TextField("Description", null, 500, TextField.ANY);
    
    ChoiceGroup equipementGp = new ChoiceGroup("Select Equipement ", Choice.MULTIPLE);
    

    
    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdBack = new Command("cmdBack", Command.BACK, 0);
    Command cmdNextEtat = new Command("etat", Command.BACK, 0);
    Command cmdNextimmob = new Command("type Immob", Command.BACK, 0);
    Command cmdNextnature = new Command("nature", Command.BACK, 0);
    Command cmdNextEquipement = new Command("Next", Command.BACK, 0);

    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);

    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/pidev_sprint2/web/app_dev.php/v1/offres.xml";
    StringBuffer sb = new StringBuffer();
    int ch;
    private Object IOUtils;

    public void startApp() {
        
        
        //affectation des Etats
        etatList.append("Bonne", null);
        etatList.append("mauvaise", null);
        etatList.append("mediocre", null);
        etatList.append("nouvelle", null);
        
        typeImmobList.append("Villa",null);
        typeImmobList.append("Appartement",null);
        typeImmobList.append("Entrepot",null);
        typeImmobList.append("terrain",null);
        
        natureList.append("Louer", null);
        natureList.append("Vente", null);
        
        etatList.addCommand(cmdNextimmob);
        etatList.setCommandListener(this);
        typeImmobList.addCommand(cmdNextnature);
        typeImmobList.setCommandListener(this);
        natureList.addCommand(cmdNextEquipement);
        natureList.setCommandListener(this);
        
        //ChoiceGroupe
        equipementGp.append("ascneceur", null);
        equipementGp.append("cuisineEquipe", null);
        equipementGp.append("jardin", null);
        equipementGp.append("entreeIndep", null);
        equipementGp.append("gazDeVille", null);
        equipementGp.append("chauffage", null);
        equipementGp.append("meuble", null);
        equipementGp.append("climatisation", null);
          
        
        
        f1.append(tfpayement);
        f1.append(tfsurface);
        f1.append(tfnbrPiece);
        f1.append(tfdescription);
        
        //affectation des commande
        f1.addCommand(cmdNextEtat);
        f1.setCommandListener(this);
        
        f2.append(equipementGp);
        f2.addCommand(cmdBack);
        f2.addCommand(cmdValider);
        f2.setCommandListener(this);
        disp.setCurrent(f1);

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdNextEtat) {
          disp.setCurrent(etatList);
        }
        if (c == cmdNextimmob) {
          disp.setCurrent(typeImmobList);
        }
        if (c == cmdNextnature) {
          disp.setCurrent(natureList);
        }
        if (c == cmdBack) {
            disp.setCurrent(f1);
        }
        if (c == cmdNextEquipement) {
            disp.setCurrent(f2);
        }
        if(c==cmdValider){
            Thread th = new Thread(this);
            th.start();
        }
        
    }

    public void run() {
         try {
                hc = (HttpConnection) Connector.open(url+"?prix="+tfpayement.getString().trim()+"&nbrpiece="+tfnbrPiece.getString().trim()
                +"&surface="+tfsurface.getString().trim()+"&id_gerant=1"+"&etat="+etatList.getString(etatList.getSelectedIndex())
                +"&nature="+natureList.getString(natureList.getSelectedIndex())+"&typeimmob="+typeImmobList.getString(typeImmobList.getSelectedIndex())
                +"&urlImage=http://url"
                );
                
                
                
                
                int code = hc.getResponseCode();
               
                if (code==201) {
                    disp.setCurrent(f2);
                }else{
                    System.out.println(url+"?prix="+tfpayement.getString().trim()+"&nbrpiece="+tfnbrPiece.getString().trim()
                +"&surface="+tfsurface.getString().trim()+"&id_gerant=1"+"&etat="+etatList.getString(etatList.getSelectedIndex())
                +"&nature="+natureList.getString(natureList.getSelectedIndex())+"&typeimmob="+typeImmobList.getString(typeImmobList.getSelectedIndex())
                +"&urlImage=http://url");
                    f3.append(Integer.toString(code));
                    disp.setCurrent(f3);
                   
                }
                sb = new StringBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
     
    }
}