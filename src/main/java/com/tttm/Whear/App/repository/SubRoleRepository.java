package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.SubRole;
import com.tttm.Whear.App.enums.ESubRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRoleRepository extends JpaRepository<SubRole, Integer> {
  public SubRole getSubRolesBySubRoleID(Integer subRoleID);
  public SubRole getSubRolesBySubRoleName(ESubRole subRoleName);
}
