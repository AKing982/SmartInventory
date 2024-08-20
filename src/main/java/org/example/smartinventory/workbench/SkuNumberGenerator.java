package org.example.smartinventory.workbench;

import org.example.smartinventory.model.Category;
import org.example.smartinventory.model.SkuNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
public class SkuNumberGenerator
{
    private ThreadPoolTaskExecutor threadPoolExecutor;

    @Autowired
    public SkuNumberGenerator(@Qualifier("threadPoolTaskExecutor") ThreadPoolTaskExecutor threadPoolExecutor){
        this.threadPoolExecutor = threadPoolExecutor;
    }


    public SkuNumber generateSkuNumber(Category category){
        return null;
    }
}
