package com.revature.ModelLayer.Repositories.Classes;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.EntityManager.EntityMgrSingleton;
import com.revature.ModelLayer.NamedQueries.QueryBank;
import com.revature.ModelLayer.Repositories.Interfaces.PoneRepository;

//Id will be string for the stringentityrepository
// This is because I anticiapte aI will be fetching a lot of wentities 
//by username as ooposed to the pk
public class ReceiptEntityRepository implements PoneRepository<Long, RecieptEntity> {

    /**
     * findById returns a recieptEntity
     * 
     * @param id the pk of the reciept
     * @return returns an entity with the matching id.
     */

    EntityManager manager;

    public ReceiptEntityRepository() {

        this.manager = new EntityMgrSingleton().GetSingleton();
    }

    @Override
    public RecieptEntity findById(Long id) {
        // TODO Auto-generated method stub
        RecieptEntity foundEntity = null;
        EntityTransaction txn = manager.getTransaction();
        txn.begin();
        if (id != null)
            foundEntity = manager.find(RecieptEntity.class, id);

        txn.commit();

        return foundEntity;
    }

    /**
     * findAllByDateAsc returns all reciepts that are persuisted to data sourjce
     * 
     * @return returns a Set of all entites in db
     */
    public Set<RecieptEntity> findAllByDateAsc() {
        return null;

    }

    /**
     * deleteById deletes a reciept in the db with a matching integer id
     * 
     * @param id the pk of reciept to destory
     * @return returns true if delete succ. ozerwize false -hough hough hough
     */
    @Override
    public boolean deleteById(Long id) {
        // TODO Auto-generated method stub
        boolean success = false;
        EntityTransaction txn = manager.getTransaction();
        txn.begin();

        if (id != null) {
            Query deleteRecieptQuery = manager.createNamedQuery(QueryBank.DELETE_RECIEPT);
            deleteRecieptQuery.setParameter("reimb_id", id);
            success = deleteRecieptQuery.executeUpdate() > 0;
        }

        txn.commit();

        return success;
    }

    /**
     * update updates an existing entity
     * 
     * @param entity the entity you'd like to update
     * @return returns boolean indicating success of update
     */
    @Override
    public boolean update(RecieptEntity entity) {
        // TODO Auto-generated method stub
        boolean success = false;
        EntityTransaction txn = manager.getTransaction();

        txn.begin();

        if (entity != null) {
            manager.refresh(entity);
            success = true;
        }
        txn.commit();

        return success;
    }

    /**
     * create creates an entity in db
     * 
     * @param entity the entity you want to create
     * @return returns bool which indicates if operation was success or not. if you
     *         get false, it might inidicate the element exists.
     */
    @Override
    public boolean create(RecieptEntity entity) {
        // TODO Auto-generated method stub
        EntityTransaction txn = manager.getTransaction();
        boolean success = false;
        txn.begin();

        if (entity != null) {
            manager.persist(entity);
            success = true;
        }
        txn.commit();

        return success;
    }

    /**
     * This function returns all list records in the database.
     * 
     * @return List<RecieptEntity> a list of all the reciepts discoverd in the
     *         database.
     */

    public List<RecieptEntity> getAllReciepts() {

        TypedQuery<RecieptEntity> getAllRecieptsQuery = this.manager.createQuery(QueryBank.FIND_ALL_RECIEPTS,
                RecieptEntity.class);
        List<RecieptEntity> allRecieptsQueryResults = getAllRecieptsQuery.getResultList();

        return allRecieptsQueryResults;
    }

    public Boolean updateRecieptByTicketIdToNewReimbStatus(UserRecieptDTO dtoEntityToUpdateStatusFor) {
        Boolean success = false;
        long ticketIdDTO = dtoEntityToUpdateStatusFor.ticketNumber;
        int newStatusForTicketIdDTO = dtoEntityToUpdateStatusFor.status;
        RecieptEntity entity = this.findById(ticketIdDTO);

        if (entity != null) {
            entity.setRemb_id(newStatusForTicketIdDTO);
            this.update(entity);
        }

        return success;
    }
}
