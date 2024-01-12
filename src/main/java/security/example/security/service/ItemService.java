package security.example.security.service;

import security.example.security.model.ItemUpdateRequestModel;
import security.example.security.model.ItemsRequestModel;
import security.example.security.response.ResponseModel;

public interface ItemService {
     ResponseModel insertItem(ItemsRequestModel itemsRequestModel);
     ResponseModel getAll();
     ResponseModel insertPeople(String name,String address);
     ResponseModel getPeoples();
     ResponseModel createItem(ItemsRequestModel itemsRequestModel);
     ResponseModel updateItem(ItemUpdateRequestModel itemUpdateRequestModel);

     ResponseModel deleteItem(Long id,Integer empId);
}
