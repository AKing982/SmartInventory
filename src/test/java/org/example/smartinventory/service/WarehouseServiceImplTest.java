package org.example.smartinventory.service;

import org.example.smartinventory.repository.WarehouseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WarehouseServiceImplTest {

    @InjectMocks
    private WarehouseServiceImpl service;

    @Mock
    private WarehouseRepository repository;

    @BeforeEach
    void setUp() {
        service = new WarehouseServiceImpl(repository);
    }

    @Test
    public void testFindAll()
    {
        fail();
    }


    @AfterEach
    void tearDown() {
    }
}