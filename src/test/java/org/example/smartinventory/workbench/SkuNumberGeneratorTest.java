package org.example.smartinventory.workbench;

import org.example.smartinventory.entities.SkuHistoryEntity;
import org.example.smartinventory.model.SkuNumber;
import org.example.smartinventory.service.SkuHistoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkuNumberGeneratorTest {

    @InjectMocks
    private SkuNumberGenerator skuNumberGenerator;

    @Mock
    private SkuHistoryService skuHistoryService;

    @Mock
    private Map<String, AtomicInteger> sequenceCache = new ConcurrentHashMap<>();

    @BeforeEach
    void setUp() {
    }



    @AfterEach
    void tearDown() {
    }
}