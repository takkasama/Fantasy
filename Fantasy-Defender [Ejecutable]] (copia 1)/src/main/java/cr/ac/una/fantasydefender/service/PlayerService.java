package cr.ac.una.fantasydefender.service;

import cr.ac.una.fantasydefender.model.Player;
import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.util.EntityManagerHelper;
import cr.ac.una.fantasydefender.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Takkasama
 */
public class PlayerService {
    
    private EntityManager em = EntityManagerHelper.getManager();
    private EntityTransaction et;

    public Respuesta getPlayer(Long id){
        try{
            TypedQuery<Player>qryPlayer = em.createNamedQuery("Player.findById", Player.class);
            qryPlayer.setParameter("id", id);
            
            PlayerDTO playerDTO = new PlayerDTO(qryPlayer.getSingleResult());
            return new Respuesta(true ," ", " ", "Player", playerDTO);
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "There is no Player with the Entered ID.", "getPlayer NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "An error occurred while querying the Player.", ex);
            return new Respuesta(false, "An error occurred while querying the Player.", "getPlayer NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error to getting the player ID : [" + id + "]", ex);
            return new Respuesta(false, "Error to get the Player.", "getPlayer " + ex.getMessage());
        }
        
    }
    public Respuesta getPlayer(String nameOrEmail){
        try{
            TypedQuery<Player>qryPlayer = em.createNamedQuery("Player.findByNameOrEmail", Player.class);
            qryPlayer.setParameter("email", nameOrEmail);
            qryPlayer.setParameter("name", nameOrEmail);
            PlayerDTO playerDTO = new PlayerDTO(qryPlayer.getSingleResult());
            return new Respuesta(true ," ", " ", "Player", playerDTO);
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "The player with the indicated email or name does not exist.", "getPlayer NoResulExeption");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "An error occurred while querying the Player.", ex);
            return new Respuesta(false, "An error occurred while querying the Player.", "getPlayer NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error to getting the player", ex);
            return new Respuesta(false, "Error to get the Player.", "getPlayer " + ex.getMessage());
        }
        
    }    
    
    public Respuesta savePlayer(PlayerDTO playerDTO){
        try{
            et = em.getTransaction();
            et.begin();
            
            Player player;
            
            if(playerDTO.getId() != null && playerDTO.getId() > 0 ){
                player  = em.find(Player.class, playerDTO.getId());
                
                if(player == null)
                    return new Respuesta(false , "The Player to be modified could not be found." , "savePlayer" );
                player.update(playerDTO);
                player = em.merge(player);
                
            }else{
                player = new Player(playerDTO);
                em.persist(player);
            }
            et.commit();
            return new Respuesta(true, "", "", "savePlayer", new PlayerDTO(player));
            
        } catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error to save the Player ID", ex);
            return new Respuesta(false, "Error to save the Player.", "savePlayer " + ex.getMessage());
        }
        
    }
    
    public Respuesta removePlayer(PlayerDTO playerDTO){
        try{ 
            et = em.getTransaction();
            et.begin();
            
           Player player;
           
           if(playerDTO.getId() != null && playerDTO.getId() > 0 ){
               player = em.find(Player.class, playerDTO.getId() );
               
               if(player == null)
                   return new Respuesta(false, "The Player to be remove could not be found.", "removePlayer");
               
               em.remove(player);
           } else 
               return new Respuesta(false, "Please consult the player to be remove", "removePlayer");
           
           et.commit();
           return new Respuesta(true, "","","removePlayer",  new Player(playerDTO));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error to remove the Player ID", ex);
            return new Respuesta(false, "Error to remove the Player.", "RemovePlayer " + ex.getMessage());
        }
        
    }
}
