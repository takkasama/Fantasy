package cr.ac.una.fantasydefender.service;
import cr.ac.una.fantasydefender.model.Crossbow;
import cr.ac.una.fantasydefender.model.CrossbowDTO;
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
public class CrossbowService {
    
    private EntityManager em = EntityManagerHelper.getManager();
    private EntityTransaction et;

    public Respuesta getCrossbow(Long id){
        try{
            TypedQuery<Crossbow> qryCrossbow = em.createNamedQuery("Crossbow.findById", Crossbow.class);
            qryCrossbow.setParameter("id", id);
            
            CrossbowDTO crossbowDTO = new CrossbowDTO(qryCrossbow.getSingleResult());
            return new Respuesta(true, " ", " ", "Crossbow", crossbowDTO);
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "There is no Crossbow with the Entered ID.", "getCrossbow NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(CrossbowService.class.getName()).log(Level.SEVERE, "An error occurred while querying the Crossbow.", ex);
            return new Respuesta(false, "An error occurred while querying the Crossbow.", "getCrossbow NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(CrossbowService.class.getName()).log(Level.SEVERE, "Error to getting the Crossbow ID : [" + id + "]", ex);
            return new Respuesta(false, "Error to get the Crossbow.", "getCrossbow " + ex.getMessage());
        }
    }
    
    public Respuesta saveCrossbow(CrossbowDTO crossbowDTO){
        try{
            et = em.getTransaction();
            et.begin();
            
            Crossbow crossbow;
            
            if(crossbowDTO.getId() != null && crossbowDTO.getId() > 0){
                crossbow = em.find(Crossbow.class, crossbowDTO.getId());
                
                if(crossbow == null)
                    return new Respuesta(false, "The Crossbow to be modified could not be found.", "saveCrossbow");
                crossbow.update(crossbowDTO);
                crossbow = em.merge(crossbow);
                
            } else {
                crossbow = new Crossbow(crossbowDTO);
                em.persist(crossbow);
            }
            et.commit();
            return new Respuesta(true, "", "", "Crossbow", new CrossbowDTO(crossbow));
            
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(CrossbowService.class.getName()).log(Level.SEVERE, "Error to save the Crossbow ID", ex);
            return new Respuesta(false, "Error to save the Crossbow.", "saveCrossbow " + ex.getMessage());
        }
    }
    
    public Respuesta removeCrossbow(CrossbowDTO crossbowDTO){
        try{ 
            et = em.getTransaction();
            et.begin();
            
            Crossbow crossbow;
            
            if(crossbowDTO.getId() != null && crossbowDTO.getId() > 0){
                crossbow = em.find(Crossbow.class, crossbowDTO.getId());
                
                if(crossbow == null)
                    return new Respuesta(false, "The Crossbow to be remove could not be found.", "removeCrossbow");
                
                em.remove(crossbow);
            } else 
                return new Respuesta(false, "Please consult the crossbow to be remove", "removeCrossbow");
            
            et.commit();
            return new Respuesta(true, "", "", "removeCrossbow", new Crossbow(crossbowDTO));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(CrossbowService.class.getName()).log(Level.SEVERE, "Error to remove the Crossbow ID", ex);
            return new Respuesta(false, "Error to remove the Crossbow.", "RemoveCrossbow " + ex.getMessage());
        }
    }
}