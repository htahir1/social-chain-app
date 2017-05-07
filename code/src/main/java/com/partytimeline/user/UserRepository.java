package com.partytimeline.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(rel = "email", path = "email")
    User findByEmail(@Param("email") String email);

    @RestResource(rel = "name", path = "name")
    User findByName(@Param("firstname") String name);

    // We can also specificy custom queries
    //@Query( "select o from MyObject o where inventoryId in :ids" )
    //List<MyObject> findByInventoryIds(@Param("ids") List<Long> inventoryIdList);

    @RestResource(exported = false)
    @Override
    void delete(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(User entity);

    @RestResource(exported = false)
    @Override
    void delete(Iterable<? extends User> entities);

    @RestResource(exported = false)
    @Override
    void deleteAll();
}