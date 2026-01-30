package com.suleymanov.dao;

import com.suleymanov.entity.Record;
import com.suleymanov.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>();

    public List<Record> findAllRecords() {
        return new ArrayList<>(records);
    }

    public void saveRecord(Record record) {
        records.add(record);
    }

    public void updateRecordStatus(Integer id
            , RecordStatus newStatus) {
        for (Record item : records) {
            if (item.getId() == id) {
                item.setStatus(newStatus);
                break;
            }
        }
    }

    public void deleteRecord(Integer id) {
        records.removeIf(record -> record.getId() == id);
    }
}
