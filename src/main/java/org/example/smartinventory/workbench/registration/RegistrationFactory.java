package org.example.smartinventory.workbench.registration;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegistrationFactory
{
    private Map<String, RegistrationStrategy> strategies = new HashMap<>();

    public RegistrationFactory(List<RegistrationStrategy> strategyList){
        initializeStrategyList(strategyList);
    }

    void initializeStrategyList(List<RegistrationStrategy> strategyList){
        for(RegistrationStrategy strategy : strategyList){
            strategies.put(strategy.getRole(), strategy);
        }
    }

     public RegistrationStrategy getStrategy(String role){
         RegistrationStrategy strategy = strategies.get(role);
         if(strategy == null){
             throw new IllegalArgumentException("Invalid Registration Type: " + role);
         }
         return strategy;
     }
}
