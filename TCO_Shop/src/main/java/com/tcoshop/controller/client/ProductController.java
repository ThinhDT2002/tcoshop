package com.tcoshop.controller.client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.Favorite;
import com.tcoshop.entity.Product;
import com.tcoshop.entity.ProductVariation;
import com.tcoshop.entity.User;
import com.tcoshop.service.FavoriteService;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.ReviewService;
import com.tcoshop.service.SubcategoryService;
import com.tcoshop.service.UserService;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserService userService;
    @Autowired
    FavoriteService favoriteService;

    private RestTemplate restTemplatet = new RestTemplate();

    @RequestMapping("/product/list")
    public String list() {
        return "tco-client/shop/product-list";
    }

    // trang product detail
    @RequestMapping("/product/detail/{id}")
    public String detail(Model model,
            @PathVariable("id") Integer id,
            @RequestParam("pid") Optional<Integer> pid,
            Authentication authentication) {
        Product item = productService.findById(id);
        String url = "http://localhost:8080/api/productVariation/" + item.getId();
        ProductVariation[] productVariations = restTemplatet.getForObject(url, ProductVariation[].class);
        List<ProductVariation> productVariationsList = Arrays.asList(productVariations);
        item.setProductVariations(productVariationsList);
        model.addAttribute("item", item);
        if (authentication != null) {
            String username = authentication.getName();
            Integer productId = item.getId();
            Favorite favorite = favoriteService.findByUsernameAndProductId(username, productId);
            model.addAttribute("favorite", favorite);
        }
        return "tco-client/shop/product-detail";
    }

    @RequestMapping("/product/favorites")
    public String getFavoriteProducts() {
        return "tco-client/shop/favorite-product";
    }
    
    @RequestMapping("/product/favorite/add/{productId}")
    public String addFavoriteProduct(@PathVariable("productId") Integer productId,
            Authentication authentication) {
        Favorite favorite = new Favorite();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        favorite.setUser(user);
        Product product = productService.findById(productId);
        favorite.setProduct(product);
        favoriteService.create(favorite);
        return "redirect:/product/detail/" + productId;
    }
    
    @RequestMapping("/product/favorite/remove/{productId}/{favoriteId}")
    public String removeFavoriteProduct(@PathVariable("favoriteId") Integer favoriteId,
            @PathVariable("productId") Integer productId) {
        favoriteService.delete(favoriteId);
        return "redirect:/product/detail/" + productId;
    }
}
