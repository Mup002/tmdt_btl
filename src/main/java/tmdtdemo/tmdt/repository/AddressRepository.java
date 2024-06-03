package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmdtdemo.tmdt.entity.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findAddressById(Long id);
    List<Address> findAddressByUserUsername(String username);
    Address findAddressByCityAndDistrictAndStreetAndUserUsername(String city,String district,String street, String username);
}
