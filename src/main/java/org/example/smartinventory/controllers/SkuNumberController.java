package org.example.smartinventory.controllers;

import org.example.smartinventory.model.SkuNumber;
import org.example.smartinventory.workbench.SkuNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skus")
@CrossOrigin(value="http://localhost:3000")
public class SkuNumberController {

    private SkuNumberGenerator skuNumberGenerator;

    @Autowired
    public SkuNumberController(SkuNumberGenerator skuNumberGenerator) {
        this.skuNumberGenerator = skuNumberGenerator;
    }

    @GetMapping("/")
    public ResponseEntity<SkuNumber> getSkuNumber(@RequestParam String category) {
        SkuNumber skuNumber = skuNumberGenerator.generateSkuNumber(category);
        return ResponseEntity.ok(skuNumber);
    }
}
