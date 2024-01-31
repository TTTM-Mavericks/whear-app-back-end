package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.SubRole;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.response.SubroleResponse;

public interface SubroleService {

  public SubRole updateSubrole(SubRole newSubrole) throws CustomException;

  public SubRole getSubroleByID(Integer subroleID) throws CustomException;

  public SubroleResponse convertToResponse(SubRole subrole);
}
