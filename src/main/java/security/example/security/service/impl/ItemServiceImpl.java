package security.example.security.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import security.example.security.entity.AddressEntity;
import security.example.security.entity.CartEntity;
import security.example.security.entity.ItemEntity;
import security.example.security.entity.PersonEntity;
import security.example.security.model.ItemResponseModel;
import security.example.security.model.ItemUpdateRequestModel;
import security.example.security.model.ItemsRequestModel;
import security.example.security.response.ResponseCodes;
import security.example.security.response.ResponseMessage;
import security.example.security.response.ResponseModel;
import security.example.security.repository.CartRepository;
import security.example.security.repository.ItemsRepository;
import security.example.security.repository.PersonRepository;
import security.example.security.service.ItemService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ItemServiceImpl implements ItemService {

   private final CartRepository cartRepository;

    public ItemServiceImpl(CartRepository cartRepository, ItemsRepository itemsRepository, PersonRepository personRepository) {
        this.cartRepository = cartRepository;
        this.itemsRepository = itemsRepository;
        this.personRepository = personRepository;
    }

    private final ItemsRepository itemsRepository;
    private final PersonRepository personRepository;
    private static final Logger LOG = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Override
    public ResponseModel insertItem(ItemsRequestModel itemsRequestModel) {
        ResponseModel responseModel = new ResponseModel();
        try {
            List<ItemEntity> itemEntities=new ArrayList<>();
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemName(itemsRequestModel.getItemName());
            itemEntities.add(itemEntity);

            CartEntity cartEntity = new CartEntity();
            cartEntity.setCartName(itemsRequestModel.getCartName());
           // cartEntity.setItems(itemEntities);

            // Set the item to the cart
            cartEntity.addItem(itemEntity);

            // Save the cart, which will save the item as well due to cascade
            cartRepository.save(cartEntity);

            responseModel.setCode(200);
        }catch (Exception e){
            LOG.error("error");
        }
        return   responseModel;
    }

@Transactional
    @Override
    public ResponseModel insertPeople(String name, String address) {
        ResponseModel responseModel=new ResponseModel();
try {
    AddressEntity addressEntity = new AddressEntity();
    addressEntity.setAddress(address);
    PersonEntity person = new PersonEntity();
    person.setName(name);
    person.setAddress(addressEntity);
    addressEntity.setPerson(person);
    personRepository.save(person);
    responseModel.setMessage("success");
}catch (Exception e){
    responseModel.setMessage("internal server error");
}
return responseModel;


    }

    @Override
    public ResponseModel getPeoples() {
        ResponseModel responseModel=new ResponseModel();

       responseModel.setCode(200);
       responseModel.setData(personRepository.findAll());
       return responseModel;
    }

    @Override
    public ResponseModel createItem(ItemsRequestModel itemsRequestModel) {
       ResponseModel responseModel=new ResponseModel();
        try{
           ItemEntity item=new ItemEntity();
           item.setItemName(itemsRequestModel.getItemName());
           item.setCreatedBy(itemsRequestModel.getEmpId());
           itemsRepository.save(item);
           responseModel.setCode(ResponseCodes.SUCCESS_CODE_1200);
           responseModel.setMessage(ResponseMessage.SUCCESS);
       }catch (Exception e){
            responseModel.setCode(ResponseCodes.INTERNAL_SERVER_ERROR_1500);
            responseModel.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
       }
        return responseModel;
    }

    @Override
    public ResponseModel updateItem(ItemUpdateRequestModel itemUpdateRequestModel) {
       ResponseModel responseModel=new ResponseModel();
        try{
            Optional<ItemEntity> optionalItemEntity=itemsRepository.findById(itemUpdateRequestModel.getItemId());
            if(optionalItemEntity.isPresent()) {
                ItemEntity itemEntity = optionalItemEntity.get();
                itemEntity.setItemName(itemEntity.getItemName());
                itemEntity.setUpdatedBy(itemUpdateRequestModel.getEmpId());
                itemsRepository.save(itemEntity);
                responseModel.setCode(ResponseCodes.SUCCESS_CODE_1200);
                responseModel.setMessage(ResponseMessage.SUCCESS);
            }else {
                responseModel.setCode(ResponseCodes.NOT_FOUND_CODE_1404);
                responseModel.setMessage(ResponseMessage.NOT_FOUND);
            }
        }catch (Exception e){
            responseModel.setCode(ResponseCodes.INTERNAL_SERVER_ERROR_1500);
            responseModel.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
        }
        return responseModel;
    }

    @Override
    public ResponseModel getAll( ) {
       ResponseModel responseModel=new ResponseModel();
        try {
           List<ItemEntity> itemEntities = itemsRepository.findAll();
             List<ItemResponseModel> itemResponseModels=   itemEntities.stream().map(itemEntity -> {
               ItemResponseModel itemResponseModel=new ItemResponseModel();
               itemResponseModel.setItemName(itemEntity.getItemName());
               itemResponseModel.setItemId(itemEntity.getId());
               return itemResponseModel;
           }).toList();
             responseModel.setData(itemResponseModels);
             responseModel.setCode(ResponseCodes.SUCCESS_CODE_1200);
             responseModel.setMessage(ResponseMessage.SUCCESS);

       }catch (Exception e){
            responseModel.setCode(ResponseCodes.INTERNAL_SERVER_ERROR_1500);
            responseModel.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
       }
       return responseModel;
    }



    @Override
    public ResponseModel deleteItem(Long id, Integer empId) {
        ResponseModel responseModel=new ResponseModel();
        try {
            Optional<ItemEntity> optionalItemEntity=itemsRepository.findById(id);
            if(optionalItemEntity.isPresent()) {
                ItemEntity itemEntity = optionalItemEntity.get();
                itemEntity.setDeletedAt(new Timestamp(System.currentTimeMillis()));
                itemEntity.setDeletedBy(empId);
                responseModel.setCode(ResponseCodes.SUCCESS_CODE_1200);
                responseModel.setMessage(ResponseMessage.SUCCESS);
            }else{
                responseModel.setCode(ResponseCodes.NOT_FOUND_CODE_1404);
                responseModel.setMessage(ResponseMessage.NOT_FOUND);
            }
        }catch (Exception e){
            responseModel.setCode(ResponseCodes.INTERNAL_SERVER_ERROR_1500);
            responseModel.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
        }
        return responseModel;
    }
}
