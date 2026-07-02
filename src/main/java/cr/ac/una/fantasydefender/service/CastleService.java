package cr.ac.una.fantasydefender.service;
import cr.ac.una.fantasydefender.model.Castle;
import cr.ac.una.fantasydefender.model.CastleDTO;
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
public class CastleService {
    
    private EntityManager em = EntityManagerHelper.getManager();
    private EntityTransaction et;
    
    public Respuesta getCastle(Long id){
        try{
            TypedQuery<Castle>qryCastle = em.createNamedQuery("Castle.findById", Castle.class);
            qryCastle.setParameter("id", id);
            
            CastleDTO castleDTO = new CastleDTO(qryCastle.getSingleResult());
            return new Respuesta(true ," ", " ", "Castle", castleDTO);
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "There is no Castle with the Entered ID.", "getCastle NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(CastleService.class.getName()).log(Level.SEVERE, "An error occurred while querying the Castle.", ex);
            return new Respuesta(false, "An error occurred while querying the Castle.", "getCastle NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(CastleService.class.getName()).log(Level.SEVERE, "Error to getting the Castle ID : [" + id + "]", ex);
            return new Respuesta(false, "Error to get the Castle.", "getCastle " + ex.getMessage());
        }
        
    }
    
    public Respuesta saveCastle(CastleDTO castleDTO){
        try{
            et = em.getTransaction();
            et.begin();
            
            Castle castle;
            
            if(castleDTO.getId() != null && castleDTO.getId() > 0 ){
                castle  = em.find(Castle.class, castleDTO.getId());
                
                if(castle == null)
                    return new Respuesta(false , "The Castle to be modified could not be found." , "saveCastle" );
                castle.update(castleDTO);
                castle = em.merge(castle);
                
            }else{
                castle = new Castle(castleDTO);
                em.persist(castle);
            }
            et.commit();
            return new Respuesta(true, "", "", "Castle", new CastleDTO(castle));
            
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(CastleService.class.getName()).log(Level.SEVERE, "Error to save the Castle ID", ex);
            return new Respuesta(false, "Error to save the Castle.", "saveCastle " + ex.getMessage());
        }
        
    }
    
    public Respuesta removeCastle(CastleDTO castleDTO){
        try{ 
            et = em.getTransaction();
            et.begin();
            
           Castle castle;
           
           if(castleDTO.getId() != null && castleDTO.getId() > 0 ){
               castle = em.find(Castle.class, castleDTO.getId() );
               
               if(castle == null)
                   return new Respuesta(false, "The Castle to be remove could not be found.", "removeCastle");
               
               em.remove(castle);
           } else 
               return new Respuesta(false, "Please consult the castle to be remove", "removeCastle");
           
           et.commit();
           return new Respuesta(true, "","","removeCastle",  new Castle(castleDTO));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(CastleService.class.getName()).log(Level.SEVERE, "Error to remove the Castle ID", ex);
            return new Respuesta(false, "Error to remove the Castle.", "RemoveCastle " + ex.getMessage());
        }
        
    }
}