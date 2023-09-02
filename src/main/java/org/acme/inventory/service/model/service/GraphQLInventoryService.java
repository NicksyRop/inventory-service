package org.acme.inventory.service.model.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.inventory.service.model.Car;
import org.acme.inventory.service.model.database.CarInventory;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.Optional;

@GraphQLApi
public class GraphQLInventoryService {

    @Inject
    CarInventory carInventory;

    @Query
    public List<Car> cars(){
        return  carInventory.getCars();
    }

    @Mutation
    public Car register(Car car){
        car.id = CarInventory.ids.incrementAndGet();
        carInventory.getCars().add(car);
        return  car;
    }

    @Mutation
    public  boolean remove(String licensePlateNumber){
        List<Car> cars = carInventory.getCars();
                Optional<Car> carToRemove = cars
                .stream().filter(car -> car.licensePlateNumber.equals(licensePlateNumber)).findAny();
                if(carToRemove.isPresent()){
                    return cars.remove(carToRemove.get());
                }else {
                    return  false;
                }
    }
}
