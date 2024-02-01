package com.tttm.Whear.App.service;

import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.BrandRequestDto;
import com.tttm.Whear.App.utils.response.BrandResponse;
import com.tttm.Whear.App.utils.response.HotBrandResponse;

import java.util.List;

public interface BrandService {
   BrandResponse createNewBrand(BrandRequestDto brandRequestDto) throws CustomException;
   List<HotBrandResponse> getListHotBrand() throws CustomException;
}
