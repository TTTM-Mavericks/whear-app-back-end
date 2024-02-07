package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.BodyShape;
import com.tttm.Whear.App.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyShapeRepository extends JpaRepository<BodyShape, Integer> {
}
