package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.ClothesColor;
import com.tttm.Whear.App.entity.History;
import com.tttm.Whear.App.enums.ColorType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into history (customerid, history_item) values (?1, ?2)", nativeQuery = true)
    void createHistoryItem(String customerID, String historyItem);

    @Transactional
    @Query(value = "select * from history where history.customerid = ?1", nativeQuery = true)
    List<History> getAllHistoryItemsByCustomerID(String customerID);
}
