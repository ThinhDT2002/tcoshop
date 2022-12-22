package com.tcoshop.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcoshop.entity.Category;
import com.tcoshop.entity.Product;
import com.tcoshop.entity.ProductVariation;
import com.tcoshop.entity.Review;
import com.tcoshop.entity.Subcategory;
import com.tcoshop.entity.Variation;
import com.tcoshop.service.CategoryService;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.ReviewService;
import com.tcoshop.service.SubcategoryService;

@Controller
public class ProductManagementController {
    @Autowired
    ProductService productService;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ReviewService reviewService;

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/tco-admin/product/grid")
    public String productGird() {
        return "tco-admin/product/product-grid";
    }

    @RequestMapping("/tco-admin/product/list")
    public String listProduct(Model model, @RequestParam("cid") Optional<String> cid) {
        if (cid.isPresent()) {
            List<Product> list = productService.findByCategoryId(cid.get());
            model.addAttribute("items", list);
        } else {
            List<Product> list = productService.findAll();
            model.addAttribute("items", list);
        }
        return "tco-admin/product/product-list";
    }

    @SuppressWarnings("null")
    @RequestMapping("/tco-admin/product/add")
    public String addProduct(Model model, @ModelAttribute("item") Product product) {
        product = new Product();
        product.setId(-999);
        product.setImage1("default-product.png");
        product.setImage2("default-product.png");
        product.setImage3("default-product.png");
        product.setImage4("default-product.png");
        Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
                Variation[].class);
        List<ProductVariation> productVariations = new ArrayList<>();
        for (int i = 0; i < variations.length; i++) {
            ProductVariation productVariation = new ProductVariation();
            productVariation.setName(variations[i].getName());
            productVariation.setValue(" ");
            productVariations.add(productVariation);
        }
        product.setProductVariations(productVariations);
        model.addAttribute("variations", variations);
        model.addAttribute("item", product);
        return "tco-admin/product/product-add";
    }

    @SuppressWarnings("null")
    @RequestMapping("/tco-admin/product/{id}")
    public String detailAdmin(Model model, @PathVariable("id") Integer id) {
        Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
                Variation[].class);
        Product product = productService.findById(id);
        List<ProductVariation> productVariations = new ArrayList<>();
        for (int i = 0; i < variations.length; i++) {
            ProductVariation productVariation = new ProductVariation();
            productVariation.setName(variations[i].getName());
            productVariation.setProduct(product);
            productVariations.add(productVariation);
        }
        if (product.getProductVariations().isEmpty()) {
            product.setProductVariations(productVariations);
        }
        List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
        product.setImage1(product.getImage1());
        product.setImage2(product.getImage2());
        product.setImage3(product.getImage3());
        product.setImage4(product.getImage4());
        model.addAttribute("item", product);
        model.addAttribute("variations", variations);
        model.addAttribute("sub", subcategories);
        return "tco-admin/product/product-add";
    }

    @RequestMapping("/tco-admin/product/detail/{id}")
    public String detailProduct(Model model, @PathVariable("id") Integer id) {
        Product product = productService.findById(id);
        model.addAttribute("item", product);
        List<Review> review = reviewService.findByProductId(id);
        model.addAttribute("reviews", review);
        return "tco-admin/product/product-detail";
    }

    @SuppressWarnings("null")
    @RequestMapping(value = "/tco-admin/product/submit", method = RequestMethod.POST)
    public String submitProduct(RedirectAttributes redirectAttributes, Model model,
            @Validated @ModelAttribute("item") Product product, Errors errors,
            @RequestParam("img1") Optional<MultipartFile> imageFile1,
            @RequestParam("img2") Optional<MultipartFile> imageFile2,
            @RequestParam("img3") Optional<MultipartFile> imageFile3,
            @RequestParam("img4") Optional<MultipartFile> imageFile4) {        
        if (errors.hasErrors()) {
        	Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
                    Variation[].class);
            List<ProductVariation> productVariations = new ArrayList<>();
            for (int i = 0; i < variations.length; i++) {
                ProductVariation productVariation = new ProductVariation();
                productVariation.setName(variations[i].getName());
                productVariation.setValue(" ");
                productVariations.add(productVariation);
            }
            product.setProductVariations(productVariations);
            product.setId(-999);
            model.addAttribute("errorMessage", "Thêm sản phẩm thất bại!");
            return "tco-admin/product/product-add";
        }
        product.setId(null);
        Category category = categoryService.findById(product.getCategory().getId());
        product.setCategory(category);
        Subcategory subcategory = subcategoryService.findById(product.getSubcategory().getId());
        product.setSubcategory(subcategory);
        List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
        redirectAttributes.addFlashAttribute("sub", subcategories);
        setImage(product, 1, imageFile1);
        setImage(product, 2, imageFile2);
        setImage(product, 3, imageFile3);
        setImage(product, 4, imageFile4);
        String url = "http://localhost:8080/api/products/";
        HttpEntity<Product> httpEntity = new HttpEntity<>(product);
        ResponseEntity<Product> productEntity = restTemplate.postForEntity(url, httpEntity, Product.class);
        List<ProductVariation> productVariations = product.getProductVariations();
        product = productEntity.getBody();
        Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
                Variation[].class);        
        if(productVariations != null) {
            for (int i = 0; i < variations.length; i++) {      
                productVariations.get(i).setName(variations[i].getName());
                productVariations.get(i).setProduct(product);
            }
            HttpEntity<List<ProductVariation>> productVariationsEntity = new HttpEntity<>(productVariations);
            String apiProductVariationsUrl = "http://localhost:8080/api/productVariation/all";
            restTemplate.postForObject(apiProductVariationsUrl, productVariationsEntity, ProductVariation[].class);
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Thêm sản phẩm thành công!");
        redirectAttributes.addFlashAttribute("item", product);
        return "redirect:/tco-admin/product/" + product.getId();
    }

    @SuppressWarnings("null")
    @RequestMapping("/tco-admin/product/update/{id}")
    public String updateProduct(RedirectAttributes redirectAttributes, Model model,
            @Validated @ModelAttribute("item") Product product, Errors errors,
            @PathVariable("id") Integer productId,
            @RequestParam("img1") Optional<MultipartFile> imageFile1,
            @RequestParam("img2") Optional<MultipartFile> imageFile2,
            @RequestParam("img3") Optional<MultipartFile> imageFile3,
            @RequestParam("img4") Optional<MultipartFile> imageFile4) {       
        Product productImages = productService.findById(product.getId());
        product.setImage1(productImages.getImage1());
        product.setImage2(productImages.getImage2());
        product.setImage3(productImages.getImage3());
        product.setImage4(productImages.getImage4());
        if (errors.hasErrors()) {
        	Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
                    Variation[].class);
        	List<ProductVariation> productVariations = new ArrayList<>();
        	for (int i = 0; i < variations.length; i++) {
                ProductVariation productVariation = new ProductVariation();
                productVariation.setName(variations[i].getName());
                productVariation.setValue(" ");
                productVariations.add(productVariation);
            }

            product.setProductVariations(productVariations);
            
            model.addAttribute("errorMessage", "Cập nhật sản phẩm thất bại!");
            model.addAttribute("item", product);
            List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
            model.addAttribute("sub", subcategories);
            return "tco-admin/product/product-add";
        }
        Category category = categoryService.findById(product.getCategory().getId());
        product.setCategory(category);
        Subcategory subcategory = subcategoryService.findById(product.getSubcategory().getId());
        product.setSubcategory(subcategory);
        List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
        redirectAttributes.addFlashAttribute("sub", subcategories);
        setImage(product, 1, imageFile1);
        setImage(product, 2, imageFile2);
        setImage(product, 3, imageFile3);
        setImage(product, 4, imageFile4);
        String url = "http://localhost:8080/api/products/" + product.getId();
        HttpEntity<Product> httpEntity = new HttpEntity<Product>(product);
        restTemplate.put(url, httpEntity);
        Variation[] variations = restTemplate.getForObject("http://localhost:8080/api/variation/all",
                Variation[].class);
        ProductVariation[] productVariationsInDB = restTemplate.getForObject(
                "http://localhost:8080/api/productVariation/" + product.getId(), ProductVariation[].class);
        List<ProductVariation> productVariationsUpdate = product.getProductVariations();     
        if(productVariationsUpdate != null) {
            for (int i = 0; i < variations.length; ++i) {
                productVariationsUpdate.get(i).setName(variations[i].getName());
                productVariationsUpdate.get(i).setProduct(product);                
            }
            if(productVariationsInDB.length != 0) {     
                restTemplate.delete("http://localhost:8080/api/productVariation/" + product.getId());                               
            }
            HttpEntity<List<ProductVariation>> productVariationsEntity = new HttpEntity<>(productVariationsUpdate);
            String apiProductVariationsUrl = "http://localhost:8080/api/productVariation/all";
            restTemplate.postForObject(apiProductVariationsUrl, productVariationsEntity, ProductVariation[].class);
        }                          
        redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật sản phẩm thành công!");
        redirectAttributes.addFlashAttribute("item", product);
        return "redirect:/tco-admin/product/" + product.getId();
    }

    @RequestMapping("/tco-admin/product/delete/{id}")
    public String deleteProduct(RedirectAttributes redirectAttributes,
            @PathVariable("id") Integer productId) {
        String url = "http://localhost:8080/api/products/" + productId;
        restTemplate.delete(url);
        redirectAttributes.addFlashAttribute("errorMessage", "Xóa sản phẩm thành công!");
        return "redirect:/tco-admin/product/add"; 
    }

    @SuppressWarnings("null")
    private void setImage(Product product, Integer imageNumber, Optional<MultipartFile> multipartFile) {
        String fileName = "default-product.png";
        if (!multipartFile.get().isEmpty()) {
            fileName = multipartFile.get().getOriginalFilename();
        }
        switch (imageNumber) {
            case 1: {
                if (product.getImage1() == null || product.getImage1().equals("default-product.png")) {
                    product.setImage1(fileName);
                } else {
                    if (!fileName.equals(product.getImage1()) && !fileName.equals("default-product.png")) {
                        product.setImage1(fileName);
                    } else {
                        product.setImage1(product.getImage1());
                    }
                }
                break;
            }
            case 2: {
                if (product.getImage2() == null || product.getImage2().equals("default-product.png")) {
                    product.setImage2(fileName);
                } else {
                    if (!fileName.equals(product.getImage2()) && !fileName.equals("default-product.png")) {
                        product.setImage2(fileName);
                    } else {
                        product.setImage2(product.getImage2());
                    }
                }
                break;
            }
            case 3: {
                if (product.getImage3() == null || product.getImage3().equals("default-product.png")) {
                    product.setImage3(fileName);
                } else {
                    if (!fileName.equals(product.getImage3()) && !fileName.equals("default-product.png")) {
                        product.setImage3(fileName);
                    } else {
                        product.setImage3(product.getImage3());
                    }
                }
                break;
            }
            case 4: {
                if (product.getImage4() == null || product.getImage4().equals("default-product.png")) {
                    product.setImage4(fileName);
                } else {
                    if (!fileName.equals(product.getImage4()) && !fileName.equals("default-product.png")) {
                        product.setImage4(fileName);
                    } else {
                        product.setImage4(product.getImage4());
                    }
                }
                break;
            }
            default:
                throw new IllegalArgumentException(imageNumber + " invalid");
        }
    }

}
