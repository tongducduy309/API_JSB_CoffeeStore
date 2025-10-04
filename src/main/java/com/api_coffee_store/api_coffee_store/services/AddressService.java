package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.AddressUpdateReq;
import com.api_coffee_store.api_coffee_store.dtos.request.CreateAddressReq;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.mapper.AddressMapper;
import com.api_coffee_store.api_coffee_store.models.Address;
import com.api_coffee_store.api_coffee_store.models.Cart;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.models.User;
import com.api_coffee_store.api_coffee_store.repositories.AddressRepository;
import com.api_coffee_store.api_coffee_store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    private final UserRepository userRepository;

    private final UserService userService;

    @Transactional
    public ResponseEntity<ResponseObject> createAddress(CreateAddressReq req) throws APIException {
        User user = userRepository.findById(req.userId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found User With Id = "+req.userId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
        Address address = addressMapper.toAddress(req);
        address.setUser(user);
        if (address.isDefault()) addressRepository.clearDefault(req.userId());
        if (addressRepository.countByUser_IdAndIsDefaultTrue(req.userId())==0){
            address.setDefault(true);
        }


        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.CREATE.getStatus())
                        .message("Create Address Successfully")
                        .data(addressRepository.save(address))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseObject> setDefault(Long id) throws APIException {
        Address address = addressRepository.findById(id).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Address With Id = "+id,ErrorCode.NOT_FOUND.getHttpStatusCode()));
        String userId = address.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found User With Id = "+userId,ErrorCode.NOT_FOUND.getHttpStatusCode()));
        addressRepository.clearDefault(userId);
        address.setDefault(true);

        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Set Default Address Successfully")
                        .data(addressRepository.save(address))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseObject> deleteById(Long id) throws APIException {
        Optional<Address> address = addressRepository.findById(id);

        if (address.isPresent()){
            if (address.get().isDefault()) throw new APIException(ErrorCode.UNPROCESSABLE_ENTITY);
            addressRepository.deleteById(id);

        }
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                new ResponseObject(SuccessCode.REQUEST.getStatus(), "Remove Address Successfully","")
        );
    }

    @Transactional
    public ResponseEntity<ResponseObject> updateAddress(Long addressId, AddressUpdateReq req) throws APIException {
        Address address = addressRepository.findById(addressId).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Address With Id = "+addressId,ErrorCode.NOT_FOUND.getHttpStatusCode()));


        if (req.receiverName() != null) address.setReceiverName(req.receiverName());
        if (req.phone() != null)       address.setPhone(req.phone());
        if (req.line1() != null)       address.setLine1(req.line1());
        if (req.line2() != null)       address.setLine2(req.line2());
        if (req.district() != null)    address.setDistrict(req.district());
        if (req.city() != null)        address.setCity(req.city());
        if (req.latitude() != null)    address.setLatitude(req.latitude());
        if (req.longitude() != null)   address.setLongitude(req.longitude());

        if (req.isDefault() != null) {
            if (req.isDefault()) {
                addressRepository.clearDefault(address.getUser().getId());
                address.setDefault(true);
            } else {
                address.setDefault(false);
            }
        }

        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Update Address Successfully")
                        .data(addressRepository.save(address))
                        .build()
        );

    }

    public ResponseEntity<ResponseObject> getMyAddress() throws APIException {
        User user = userService.getProfile();
        List<Address> addresses = addressRepository.findAllByUserId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"List Carts Of User",addresses)
        );
    }
}
