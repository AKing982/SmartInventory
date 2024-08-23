package org.example.smartinventory.controllers;

import org.example.smartinventory.dto.StockDTO;
import org.example.smartinventory.entities.StockEntity;
import org.example.smartinventory.service.StockService;
import org.example.smartinventory.workbench.converter.StockConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(value="http://localhost:3000")
public class StockController
{
    private final StockService stockService;
    private final StockConverter stockConverter;

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
        return ResponseEntity.status(HttpStatus.OK).body(stockService.updateStock(stockEntity));
    }

    @PostMapping("/")
    public ResponseEntity<?> createStock(@RequestBody StockDTO stock){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") Long id){
        return null;
    }
}
