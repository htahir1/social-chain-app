package com.partytimeline.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(rel = "username", path = "username")
    User findByUsername(@Param("username") String username);

    @RestResource(rel = "firstname", path = "firstname")
    User findByfirstName(@Param("firstname") String firstName);

    @RestResource(rel = "lastname", path = "lastname")
    User findBylastName(@Param("lastname") String lastName);

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