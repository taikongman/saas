package com.saas.api.admin.service.common;

import java.util.List;

import com.saas.api.admin.res.select.common.AutoBrandSelect;
import com.saas.api.admin.res.select.common.AutoModelSelect;
import com.saas.api.admin.res.select.common.AutoSeriesSelect;
import com.saas.api.common.entity.common.AutoBrand;
import com.saas.api.common.entity.common.AutoModel;
import com.saas.api.common.entity.common.AutoSeries;

public interface AutoService {
	
	List<AutoBrandSelect> getSelectAutoBrandList(AutoBrand record);

	List<AutoSeriesSelect> getSelectAutoSeriesList(AutoSeries record);
	
	List<AutoModelSelect> getSelectAutoModelList(AutoModel record);
	
	List<Integer> getSelectAutoYear(AutoModel record);
	
}
