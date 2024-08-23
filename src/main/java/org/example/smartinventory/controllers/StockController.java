package org.example.smartinventory.controllers;

import org.example.smartinventory.dto.StockDTO;
import org.example.smartinventory.entities.StockEntity;
import org.example.smartinventory.service.StockService;
import org.example.smartinventory.workbench.converter.StockConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(value="http://localhost:3000")
public class StockController
{
    private final StockService stockService;
    private final StockConverter stockConverter;
    private Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    public StockController(StockService stockService,
                           StockConverter stockConverter)
    {
        this.stockService = stockService;
        this.stockConverter = stockConverter;
    }

    @GetMapping("/")
    public ResponseEntity<?> fetchAllStocks(){

        Collection<StockEntity> stocks = stockService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(stocks);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> fetchStockByProductId(@PathVariable int id){
        Collection<StockEntity> stocks = stockService.getStocksByProductId(id);
        return ResponseEntity.status(HttpStatus.OK).body(stocks);
    }

    @GetMapping("/alert/{id}")
    public ResponseEntity<?> stockAlert(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable("id") Long id, @RequestBody StockDTO stock){

        StockEntity stockEntity = stockConverter.convert(stock);
        LOGGER.info("Stock: {}", stockEntity);
        StockEntity updatedStock = stockService.updateStock(stockEntity);
        LOGGER.info("Updated Stock: {}", updatedStock);
        return new ResponseEntity<>(stockService.updateStock(stockEntity), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createStock(@RequestBody StockDTO stock){
        StockEntity stockEntity = stockConverter.convert(stock);
        try
        {
            stockService.save(stockEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(stockEntity);

        }catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") Long id){
        Optional<StockEntity> stockEntity = stockService.findById(id);
        try
        {
            if(stockEntity.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            stockService.delete(stockEntity.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
