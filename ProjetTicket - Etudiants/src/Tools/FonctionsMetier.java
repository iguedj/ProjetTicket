/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Entity.Etat;
import Entity.Ticket;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbuffeteau
 */
public class FonctionsMetier implements IMetier
{
    @Override
    public User GetUnUser(String login, String mdp)
    {
        
       User test = null;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idUser, nomUser, prenomUser, statutUser from users where loginUser = ? and pwdUser = ?");
            ps.setString(1, login);
            
            ps.setString(2, mdp);
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            test = new User(rs.getInt("idUser"), rs.getString("nomUser"), rs.getString("prenomUser"),rs.getString("statutUser"));
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    @Override
    public ArrayList<Ticket> GetAllTickets()
    {
        
          ArrayList<Ticket> lesTickets = new ArrayList<>();
       try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idTicket,nomTicket,dateTicket, nomEtat from etats,tickets");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                
                Ticket t = new Ticket(rs.getInt("idTicket"), rs.getString("nomTicket"), rs.getString("dateTicket"), rs.getString("nomEtat"));
                lesTickets.add(t);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets; 
    }

    @Override
    public ArrayList<Ticket> GetAllTicketsByIdUser(int idUser)
    {
        
         ArrayList<Ticket> lesTickets = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idTicket,nomTicket,dateTicket, nomEtat from etats,tickets where numUser = "+ idUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                
                Ticket t = new Ticket(rs.getInt("idTicket"), rs.getString("nomTicket"), rs.getString("dateTicket"), rs.getString("nomEtat"));
                lesTickets.add(t);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets; 
    }

    @Override
    public void InsererTicket(int idTicket, String nomTicket, String dateTicket, int idUser, int idEtat) 
    {
         try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("insert into tickets values('"+idTicket+"','"+nomTicket+"','"+dateTicket+"','"+idUser+"','"+idEtat+"'0)");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        
    }
    }

    @Override
    public void ModifierEtatTicket(int idTicket, int idEtat) 
    {
        
        
    }

    @Override
    public ArrayList<User> GetAllUsers()
    {
        
         ArrayList<User> lesUsers = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idUser, loginUser,pwdUser, statutUser from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                
                User a = new User(rs.getInt("idUser"), rs.getString("loginUser"), rs.getString("pwdUser"), rs.getString("statutUser") );
                lesUsers.add(a);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesUsers;  
    }

    @Override
    public int GetLastIdTicket()
    {
        
        int lastId = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select max(idTicket) as num from tickets");
            ResultSet rs = ps.executeQuery();
            rs.next();
            lastId = rs.getInt("num") + 1;
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastId;
    }

    @Override
    public int GetIdEtat(String nomEtat)
    {
        
        int idEtat = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idEtat from etats where nomEtat = ?");
            ps.setString(1, nomEtat );
            ResultSet rs = ps.executeQuery();
            rs.next();
            idEtat = rs.getInt(1);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idEtat;
    }

    @Override
    public ArrayList<Etat> GetAllEtats()
    {
       ArrayList<Etat> lesEtats = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select * from etats");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Etat e = new Etat(rs.getInt(1), rs.getString(2));
                lesEtats.add(e);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesEtats;
    }
}
