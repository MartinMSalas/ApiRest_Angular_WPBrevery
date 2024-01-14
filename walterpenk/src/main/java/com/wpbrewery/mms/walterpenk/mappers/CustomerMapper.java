package com.wpbrewery.mms.walterpenk.mappers;


import com.wpbrewery.mms.walterpenk.entity.Customer;
import com.wpbrewery.mms.walterpenk.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}
