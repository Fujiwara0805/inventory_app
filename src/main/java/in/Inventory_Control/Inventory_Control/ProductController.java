package in.Inventory_Control.Inventory_Control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    //error画面
    @GetMapping("/error")
    public String showErrorPage(){
        return "error";
    }

    //ダッシュボード画面
    @GetMapping("/dashboard")
    public String showDashboardPage(Model model) {
        model.addAttribute("username", "Dashboard");
        return "dashboard";
    }

    // 商品一覧
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("username", "商品一覧画面");
        return "/products";
    }

    // 商品登録画面
    @GetMapping("/create")
    public String showCreateProductPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("username", "商品登録画面");
        return "product_create";
    }

    // 商品登録
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        Product newProduct = productService.createProduct(product);
        if (newProduct != null) {
            productService.linkProductToSupplier(newProduct.getId(), product.getSupplierId());
            return "redirect:/products";
        } else {
            model.addAttribute("errorMessage", "商品登録に失敗しました。もう一度お試しください");
            return "redirect:/products/create";
        }
    }

    // 商品詳細
    @GetMapping("/product/{id}")
    public String showProductDetailsPage(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("username", "商品詳細画面");
            return "product_detail";
        } else {
            return "redirect:/products";
        }
    }

    // 商品編集
    @GetMapping("/product/edit/{id}")
    public String showEditProductPage(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("username", "商品編集画面");
            return "product_edit";
        } else {
            return "redirect:/products";
        }
    }

    // 商品更新
    @PostMapping("/product/update/{id}")
    public String updateProducts(@PathVariable Integer id, @ModelAttribute Product product) {
        product.setId(id);
        Product updateProduct = productService.updateProduct(product);
        if (updateProduct != null) {
            return "redirect:/products";
        } else {
            return "404";
        }
    }

    // 商品削除
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if(product != null){
            productService.deleteProducts(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
