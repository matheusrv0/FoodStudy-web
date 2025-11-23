package com.foodstudy.web.service;

import com.foodstudy.web.model.FoodCash;
import com.foodstudy.web.repository.FoodCashRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodCashService {

    private final FoodCashRepository foodCashRepository;

    public FoodCashService(FoodCashRepository foodCashRepository) {
        this.foodCashRepository = foodCashRepository;
    }

  
    public FoodCash adicionarSaldo(Long id, float valor) {
        FoodCash foodCash = foodCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FoodCash não encontrado"));

        foodCash.adicionar(valor);
        return foodCashRepository.save(foodCash);
    }

    public FoodCash descontarSaldo(Long id, float valor) {
        FoodCash foodCash = foodCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FoodCash não encontrado"));

        foodCash.descontar(valor);
        return foodCashRepository.save(foodCash);
    }

   
    public float consultarSaldo(Long id) {
        FoodCash foodCash = foodCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FoodCash não encontrado"));

        return foodCash.consultarSaldo();
    }
}
