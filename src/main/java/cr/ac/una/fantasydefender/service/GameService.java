package cr.ac.una.fantasydefender.service;
import cr.ac.una.fantasydefender.model.Game;
import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.model.Player;
import cr.ac.una.fantasydefender.util.EntityManagerHelper;
import cr.ac.una.fantasydefender.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Takkasama
 */
public class GameService {
    
    private EntityManager em = EntityManagerHelper.getManager();
    private EntityTransaction et;

    public Respuesta getGame(Long id){
        try{
            TypedQuery<Game> qryGame = em.createNamedQuery("Game.findById", Game.class);
            qryGame.setParameter("id", id);
            
            GameDTO gameDTO = new GameDTO(qryGame.getSingleResult());
            return new Respuesta(true, " ", " ", "Game", gameDTO);
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "There is no Game with the Entered ID.", "getGame NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "An error occurred while querying the Game.", ex);
            return new Respuesta(false, "An error occurred while querying the Game.", "getGame NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error to getting the Game ID : [" + id + "]", ex);
            return new Respuesta(false, "Error to get the Game.", "getGame" + ex.getMessage());
        }
    }
    
    public Respuesta getGame(){
        try{
            TypedQuery<Game> qryGame = em.createNamedQuery("Game.findAll", Game.class);
            
            List<Game> games = qryGame.getResultList();
            
            List<GameDTO> result = new ArrayList<>();
            for(Game gm : games)
                result.add(new GameDTO(gm));
            return new Respuesta(true, " ", " ", "Games", result);
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "The Player does not have eny games", "getGame NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "An error occurred while querying the List Games.", ex);
            return new Respuesta(false, "An error occurred while querying the Game.", "getGame NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error to getting the Games " , ex);
            return new Respuesta(false, "Error to get the Game.", "getGame" + ex.getMessage());
        }
    }
    
    public Respuesta saveGame(GameDTO gameDTO){
        try{
            et = em.getTransaction();
            et.begin();
            
            Game game;
            
            if(gameDTO.getId() != null && gameDTO.getId() > 0){
                game = em.find(Game.class, gameDTO.getId());
                
                if(game == null)
                    return new Respuesta(false, "The Game to be modified could not be found.", "saveGame");
                
                game.update(gameDTO);
                
                game = em.merge(game);
                
            } else {
                game = new Game(gameDTO);
                
                Player player =em.find(Player.class, gameDTO.getPlayer().getId());
                game.setPlayer(player);
                
                em.persist(game);
            }
            et.commit();
            return new Respuesta(true, "", "", "Game", new GameDTO(game));
            
        } catch (Exception ex) {
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error to save the Game ID", ex);
            return new Respuesta(false, "Error to save the Game.", "saveGame " + ex.getMessage());
        }
    }
    
    public Respuesta removeGame(GameDTO gameDTO){
        try{ 
            et = em.getTransaction();
            et.begin();
            
            Game game;
            
            if(gameDTO.getId() != null && gameDTO.getId() > 0){
                game = em.find(Game.class, gameDTO.getId());
                
                if(game == null)
                    return new Respuesta(false, "The Game to be remove could not be found.", "removeGame");
                
                em.remove(game);
            } else 
                return new Respuesta(false, "Please consult the game to be remove", "removeGame");
            
            et.commit();
            return new Respuesta(true, "", "", "Game", new Game(gameDTO));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, "Error to remove the Game ID", ex);
            return new Respuesta(false, "Error to remove the Game.", "RemoveGame" + ex.getMessage());
        }
    }
}