package com.suleymanov.dao;

import com.suleymanov.entity.Record;
import com.suleymanov.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            List.of(
                    new Record("Take a shower", RecordStatus.ACTIVE),
                    new Record("By flowers", RecordStatus.ACTIVE),
                    new Record("Go to the gym", RecordStatus.ACTIVE)
            )
    );

    public List<Record> findAllRecords() {
        return new ArrayList<>(records);
    }
}
