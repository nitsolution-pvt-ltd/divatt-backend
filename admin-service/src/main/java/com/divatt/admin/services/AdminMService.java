package com.divatt.admin.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.divatt.admin.entity.BannerEntity;
import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.DesignerCategoryEntity;
import com.divatt.admin.entity.GlobalResponse;

public interface AdminMService {

	List<ColourEntity> getColours();

	GlobalResponse addColour(ColourEntity colourEntity);

	GlobalResponse updateColours(ColourEntity colourEntity, String name);

	GlobalResponse deleteColour(String name);

	ColourEntity getColour(String name);

	GlobalResponse deleteColours(String name);

	Map<String, Object> tblList(int page, String metaKey, String keyword, int limit, String sort,
			Optional<String> sortBy, String sortName);

	GlobalResponse addBanner(@Valid BannerEntity bannerEntity);

	GlobalResponse updateBanners(@Valid BannerEntity bannerEntity, Long id);

	GlobalResponse deleteBanner(Long id);

	GlobalResponse addDesignerCategory(DesignerCategoryEntity designerCategoryEntity);

	GlobalResponse updateDesignerLevels(DesignerCategoryEntity designerCategoryEntity, String name);

	GlobalResponse deleteDesignerLevels(String name);

	Object getDesignerService();

	Map<String, String> getDesignerCategorybyname(String name);

	Map<String, Object> bannerListing(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	BannerEntity getBanner(Long id);

	GlobalResponse changeBannerStatus(Long id);

}
