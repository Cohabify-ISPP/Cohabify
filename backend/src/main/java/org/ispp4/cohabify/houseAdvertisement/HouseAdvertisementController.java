package org.ispp4.cohabify.houseAdvertisement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.AdvertisementHouseRequest;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.house.Heating;
import org.ispp4.cohabify.house.House;
import org.ispp4.cohabify.house.HouseService;
import org.ispp4.cohabify.storage.StorageService;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/advertisements/houses")
@AllArgsConstructor
public class HouseAdvertisementController {
    
    private HouseAdvertisementService advertisementService;
    private HouseService houseService;
    private StorageService storageService;

    @Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<HouseAdvertisement>> getAllAdvertisements() {
        List<HouseAdvertisement> advertisements = advertisementService.findAll();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<HouseAdvertisement> getAdvertisement(@PathVariable String id) {
        Optional<HouseAdvertisement> advertisement = advertisementService.findById(new ObjectId(id));
        if(advertisement.isPresent()){
            return new ResponseEntity<>(advertisement.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
	public ResponseEntity<?> createAdvertisement(@Valid @RequestPart("string-data") AdvertisementHouseRequest request, BindingResult result, 
    @RequestPart(value = "images",required = false) List<MultipartFile> images) throws BadRequestException {
            
       
            House house = new House();
            house.setRoomsNumber(request.getHouse().getRoomsNumber());
            house.setBathroomsNumber(request.getHouse().getBathroomsNumber());
            house.setArea(request.getHouse().getArea());
            house.setFloor(request.getHouse().getFloor());
            house.setLocation(request.getHouse().getLocation());
            house.setCadastre(request.getHouse().getCadastre());
            house.setHeating(request.getHouse().getHeating());
            house.setTags(request.getHouse().getTags());
            GeoJsonPoint point = new GeoJsonPoint(2, 2);
            house.setLocationPoint(point);
            house = houseService.save(house);
            

            HouseAdvertisement advertisement = new HouseAdvertisement();
            advertisement.setTitle(request.getTitle());
            advertisement.setDescription(request.getDescription());
            advertisement.setPrice(request.getPrice());
            advertisement.setTenants(request.getTenants());
            advertisement.setHouse(house);
            advertisement.setAuthor(request.getAuthor());
            advertisement = advertisementService.save(advertisement);
            
            // Save the image and add the static uri to the user
            
            List<String> imagesPath = new ArrayList<>();
            for(int i = 0; i < images.size(); i++){
                MultipartFile image = images.get(i);
                String[] filename_split = images.get(i).getOriginalFilename().split("\\.");
                String filename = advertisement.getJsonId() + "." + filename_split[filename_split.length-1];
                String static_path;
                try {
                    static_path = storageService.saveImage(filename, image);
                } catch (IOException e) {
                    advertisementService.deleteById(advertisement.getId());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(e.getMessage());
                }
            
                imagesPath.add(static_path);
                advertisement.setImages(imagesPath);
                advertisement = advertisementService.save(advertisement);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
							 .body(advertisement);
    }
		
    


     @GetMapping("/heating")
    public ResponseEntity<List<Heating>> findHeating() {
        try {
            List<Heating> heatings = List.of(Heating.values());

            if (heatings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(heatings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<HouseAdvertisement> updateAdvertisement(@PathVariable ObjectId id, @RequestBody HouseAdvertisement advertisement) {
        HouseAdvertisement updatedAdvertisement = advertisementService.update(id, advertisement);
        return new ResponseEntity<>(updatedAdvertisement, HttpStatus.OK);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdvertisement(@Valid @RequestPart("string-data") AdvertisementHouseRequest request, BindingResult result, 
    @RequestPart(value = "images",required = false) List<MultipartFile> images,@PathVariable ObjectId id) throws BadRequestException {
		
		if(result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(result.getFieldErrors()
										 	 .stream()
										 	 	.map(fe -> new FormItemValidationError(fe))
										 	 	.toList());
		}

            
            House house = houseService.findById(request.getHouseId());
            house.setRoomsNumber(request.getHouse().getRoomsNumber());
            house.setBathroomsNumber(request.getHouse().getBathroomsNumber());
            house.setArea(request.getHouse().getArea());
            house.setFloor(request.getHouse().getFloor());
            house.setLocation(request.getHouse().getLocation());
            house.setCadastre(request.getHouse().getCadastre());
            house.setHeating(request.getHouse().getHeating());
            house.setTags(request.getHouse().getTags());
            GeoJsonPoint point = new GeoJsonPoint(2, 2);
            house.setLocationPoint(point);
            house = houseService.save(house);

            HouseAdvertisement advertisement = advertisementService.findAdById(id);
            advertisement.setTitle(request.getTitle());
            advertisement.setDescription(request.getDescription());
            advertisement.setPrice(request.getPrice());
            advertisement.setTenants(request.getTenants());
            advertisement.setHouse(house);
            advertisement.setAuthor(request.getAuthor());
            advertisement = advertisementService.save(advertisement);
            
            // Save the image and add the static uri to the user
            
            List<String> imagesPath = request.getImagesB();
            if(images != null){
                
                for(int i = 0; i < images.size(); i++){
                    MultipartFile image = images.get(i);
                    String[] filename_split = images.get(i).getOriginalFilename().split("\\.");
                    String filename = advertisement.getJsonId() + "." + filename_split[filename_split.length-1];
                    String static_path;
                    try {
                        static_path = storageService.saveImage(filename, image);
                    } catch (IOException e) {
                        advertisementService.deleteById(advertisement.getId());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(e.getMessage());
                    }
                
                    imagesPath.add(static_path);
                    
                    
                    advertisement.setImages(imagesPath);
                    advertisement = advertisementService.save(advertisement);
                }
                
            }else{
                imagesPath = request.getImagesB();
                advertisement.setImages(imagesPath);
                advertisement = advertisementService.save(advertisement);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED)
                                .body(advertisement);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable ObjectId id) {
        advertisementService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

