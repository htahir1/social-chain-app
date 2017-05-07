//package com.navvis.category;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//
//@RepositoryRestResource(path = "category", collectionResourceRel = "category", itemResourceRel = "category", excerptProjection = InLineCategory.class)
//public interface CategoryRepository extends JpaRepository<Category, Long> {
//
//    @RestResource(rel = "category", path = "category")
//    Category findByCategoryIgnoreCase(@Param("category") String category);
//
//    @RestResource(exported = false)
//    @Override
//    void delete(Long aLong);
//
//    @RestResource(exported = false)
//    @Override
//    void delete(Category entity);
//
//    @RestResource(exported = false)
//    @Override
//    void delete(Iterable<? extends Category> entities);
//
//    @RestResource(exported = false)
//    @Override
//    void deleteAll();
//}