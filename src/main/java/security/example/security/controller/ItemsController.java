package security.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.ItemUpdateRequestModel;
import security.example.security.model.ItemsRequestModel;
import security.example.security.response.ResponseModel;
import security.example.security.service.ItemService;

@RestController
@RequestMapping("/api/v1/auth/item")
@CrossOrigin
public class ItemsController {
    private final ItemService itemService;

    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/insert/item")
    public ResponseModel insertItem(@RequestBody ItemsRequestModel itemsRequestModel){
       return itemService.insertItem(itemsRequestModel);
    }
    @GetMapping("/all")
    public  ResponseModel getAll(){
        return itemService.getAll();
    }
    @PostMapping("/insert/people/{name}/{address}")
    public ResponseModel insertPeople(@PathVariable String name,@PathVariable String address){
        return itemService.insertPeople(name, address);
    }
    @GetMapping("/get-peoples")
     public ResponseModel getPeoples(){
        return itemService.getPeoples();
    }
    @PostMapping("/create-item")
    public ResponseModel createItem(@RequestBody ItemsRequestModel itemsRequestModel){
        return itemService.createItem(itemsRequestModel);
    }
    @PutMapping("/update-item")
    public ResponseModel updateItem(@RequestBody ItemUpdateRequestModel itemUpdateRequestModel){
        return itemService.updateItem(itemUpdateRequestModel);
    }

    @PutMapping("/delete-item")
    public ResponseModel deleteItem(Long id,Integer empId){
        return itemService.deleteItem(id,empId);
    }

}
