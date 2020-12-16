package com.revature.ModelLayer.Repositories.Interfaces;

public interface PoneRepository<Id, Entity> {
    public Entity findById(Id id);

    public boolean deleteById(Id id);

    public boolean update(Entity entity);

    public boolean create(Entity entity);

}
