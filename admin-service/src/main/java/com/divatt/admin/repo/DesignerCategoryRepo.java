package com.divatt.admin.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.DesignerCategoryEntity;

@Repository
public interface DesignerCategoryRepo extends MongoRepository<DesignerCategoryEntity, Integer>{

	//Page<DesignerCategoryEntity> findByMetaKey(String metaKey, String string, Pageable pagingSort);

	//Page<DesignerCategoryEntity> Search(Map<String, String> designerCategorybyname, String string, Pageable pagingSort);

	/*
	 * @Query(value = "{[ {{$regex:?0} }]}") Page<DesignerCategoryEntity>
	 * Search(String keyword, String string, Pageable pagingSort);
	 */

	

}
