//package com.navvis.experiment;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//
//@RepositoryRestResource(path = "experiments", collectionResourceRel = "experiments", itemResourceRel = "experiments", excerptProjection = InLineExperiment.class)
//public interface ExperimentRepository extends JpaRepository<Experiment, Long> {
//
//    @RestResource(rel = "name", path = "name")
//    Experiment findByNameIgnoreCase(@Param("name") String name);
//
//    @RestResource(exported = false)
//    @Override
//    void delete(Long aLong);
//
//    @RestResource(exported = false)
//    @Override
//    void delete(Experiment entity);
//
//    @RestResource(exported = false)
//    @Override
//    void delete(Iterable<? extends Experiment> entities);
//
//    @RestResource(exported = false)
//    @Override
//    void deleteAll();
//}