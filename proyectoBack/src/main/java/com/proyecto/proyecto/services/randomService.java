package com.proyecto.proyecto.services;
import org.springframework.stereotype.Service;

@Service
public class randomService {

    public int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    
}
