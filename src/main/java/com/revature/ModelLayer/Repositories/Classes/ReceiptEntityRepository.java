package com.revature.ModelLayer.Repositories.Classes;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.revature.ModelLayer.DTO.UserRecieptDTO;
import com.revature.ModelLayer.Entities.RecieptEntity;
import com.revature.ModelLayer.EntityManager.EntityMgrSingleton;
import com.revature.ModelLayer.NamedQueries.QueryBank;
import com.revature.ModelLayer.Repositories.Exceptions.InvalidEntityPropertyException;
import com.revature.ModelLayer.Repositories.Interfaces.PoneRepository;

//Id will be string for the stringentityrepository
// This is because I anticiapte aI will be fetching a lot of wentities 
//by username as ooposed to the pk
public class ReceiptEntityRepository implements PoneRepository<Integer, RecieptEntity> {

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
    public RecieptEntity findById(Integer id) {
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
     * deleteById deletes a reciept in the db with a matching integer id
     * 
     * @param id the pk of reciept to destory
     * @return returns true if delete succ. ozerwize false -hough hough hough
     */
    @Override
    public boolean deleteById(Integer id) {
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
     * This function udates an entity with a given id with a new status
     * 
     * @param ticketId the ticket id to update
     * @param param    the int status to update the ticket to
     * @return boolean indicating success or failure of operation
     * @throws InvalidEntityPropertyException
     */
    public boolean updateTicketStatusByIdWithStatus(UserRecieptDTO updateReciept)
            throws InvalidEntityPropertyException {
        boolean success = false;

        Integer ticketId = updateReciept.ticketNumber;
        Integer status = updateReciept.status;
        Integer processedBy = updateReciept.processedBy;

        boolean invalidTicketId = ticketId == null || ticketId < 1;
        boolean invalidStatus = status == null || status < 1;
        boolean invalidProcessor = processedBy == null || processedBy <= 0;
        EntityTransaction txn = manager.getTransaction();

        if (!invalidStatus && !invalidTicketId && !invalidProcessor) {
            RecieptEntity entityToUpdate = this.findById(ticketId);

            txn.begin();
            if (entityToUpdate != null) {
                entityToUpdate.setRemb_status(status);
                entityToUpdate.setReimb_resolver(processedBy);
                entityToUpdate.setReimb_resolved();
                manager.merge(entityToUpdate);
            }
            txn.commit();
            success = true;
        } else {
            if (invalidStatus && invalidTicketId && invalidProcessor) {

                throw new InvalidEntityPropertyException(
                        "The application attempted to pass both an invalid ticketId and status to a RecieptEntity.");

            } else if (invalidStatus) {
                throw new InvalidEntityPropertyException(
                        "The application attempted to pass an invalid status to a RecieptEntity.");

            } else if (invalidProcessor) {
                throw new InvalidEntityPropertyException(
                        "The application attempted to pass an invalid ticket processor to a RecieptEntity.");
            } else {
                throw new InvalidEntityPropertyException(
                        "The application attempted to pass an invalid ticketId to a RecieptEntity.");
            }
        }

        return success;
    }

    /**
     * This function returns all list records in the database.
     * 
     * @return List<RecieptEntity> a list of all the reciepts discoverd in the
     *         database.
     */

    public List<RecieptEntity> getAllReciepts() {

        TypedQuery<RecieptEntity> getAllRecieptsQuery = this.manager.createNamedQuery(QueryBank.FIND_ALL_RECIEPTS,
                RecieptEntity.class);
        List<RecieptEntity> allRecieptsQueryResults = getAllRecieptsQuery.getResultList();

        return allRecieptsQueryResults;
    }

    public List<RecieptEntity> getAllRecieptsByUser(Integer userId) {
        List<RecieptEntity> usersReciepts = null;

        if (userId != null && userId > 0) {
            TypedQuery<RecieptEntity> getAllRecieptsByUserQuery = manager
                    .createNamedQuery(QueryBank.FIND_ALL_RECIEPTS_BY_USERNAME, RecieptEntity.class);
            getAllRecieptsByUserQuery.setParameter("reimb_author", userId);
            usersReciepts = getAllRecieptsByUserQuery.getResultList();
        }

        return usersReciepts;
    }

}
