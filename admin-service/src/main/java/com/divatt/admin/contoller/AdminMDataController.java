package com.divatt.admin.contoller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.divatt.admin.config.UserDetails;
import com.divatt.admin.entity.BannerEntity;
import com.divatt.admin.entity.ColourEntity;
import com.divatt.admin.entity.ColourMetaEntity;
import com.divatt.admin.entity.DesignerCategoryEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.AdminMService;
import com.divatt.admin.services.CategoryService;
import com.divatt.user.entity.order.OrderDetailsEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/adminMData")
public class AdminMDataController {

	@Autowired
	private AdminMService adminMService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminMDataController.class);

	@GetMapping("/coloreList")
	public List<ColourEntity> colourList() {
		try {
			return this.adminMService.getColours();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/addColour")
	public GlobalResponse addColore(@RequestBody ColourEntity colourEntity) {
		try {
			return this.adminMService.addColour(colourEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/updateColour/{index}")

	public GlobalResponse updateColore(@RequestBody ColourEntity colourEntity,
			@PathVariable(value = "index") Integer index) {
		try {
			return this.adminMService.updateColour(colourEntity, index);
		}

		catch (Exception e) {
			throw new CustomException(e.getMessage());

		}

	}

	@PutMapping("/updateColours/{name}")

	public GlobalResponse updateColores(@RequestBody ColourEntity colourEntity,
			@PathVariable(value = "name") String name) {
		try {
			return this.adminMService.updateColours(colourEntity, name);
		}

		catch (Exception e) {
			throw new CustomException(e.getMessage());

		}

	}

	@GetMapping("/getColour/{name}")

	public ColourEntity getColore(@PathVariable(value = "name") String name) {
		try {
			return this.adminMService.getColour(name);
		}

		catch (Exception e) {
			throw new CustomException(e.getMessage());

		}

	}

	/*
	 * @PutMapping("/deleteColour/{index}")
	 * 
	 * public GlobalResponse deleteColores( @PathVariable (value="index")Integer
	 * index) { try { return this.adminMService.deleteColour(index); }
	 * 
	 * catch (Exception e) { throw new CustomException(e.getMessage());
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	@PutMapping("/deleteColours/{name}")

	public GlobalResponse deleteColore(@PathVariable(value = "name") String name) {
		try {
			return this.adminMService.deleteColours(name);
		}

		catch (Exception e) {
			throw new CustomException(e.getMessage());

		}

	}

	@GetMapping("/tblList")
	public Map<String, Object> getColourDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "colors") String metaKey, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		try {
			return this.adminMService.tblList(page, metaKey, keyword, limit, sort, sortBy, sortName);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/addBanner")
	public GlobalResponse addBanner(@Valid @RequestBody BannerEntity bannerEntity) {
		try {
			return this.adminMService.addBanner(bannerEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/updateBanner/{id}")
	public GlobalResponse updateBanner(@Valid @RequestBody BannerEntity bannerEntity,
			@PathVariable(value = "id") Long id) {
		try {
			return this.adminMService.updateBanners(bannerEntity, id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/deleteBanner/{id}")
	public GlobalResponse deleteBanner(@PathVariable(value = "id") Long id) {
		try {
			return this.adminMService.deleteBanner(id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/addDesignerCategory")
	public GlobalResponse addDesignerCaregory(@RequestBody DesignerCategoryEntity designerCategoryEntity) {
		try {
			return this.adminMService.addDesignerCategory(designerCategoryEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/updateDesignerLevels/{name}")
	public GlobalResponse updateDesignerCaregorys(@RequestBody DesignerCategoryEntity designerCategoryEntity,
			@PathVariable(value = "name") String name) {
		try {
			return this.adminMService.updateDesignerLevels(designerCategoryEntity, name);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@DeleteMapping("/deleteDesignerLevels/{name}")
	public GlobalResponse deleteDesignerCaregory(@PathVariable(value = "name") String name) {
		try {
			return this.adminMService.deleteDesignerLevels(name);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getDesignerCategory")
	public Object getDesignerList() {
		try {
			return this.adminMService.getDesignerService();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getDesignerCategory/{name}")
	public Map<String, String> getDesignerCategorybyname(@PathVariable(value = "name") String name) {
		try {
			return this.adminMService.getDesignerCategorybyname(name);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/bannerList")
	public Map<String, Object> getBannerDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - AdminMDataController.getBannerDetails()");

		try {
			return this.adminMService.bannerListing(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
