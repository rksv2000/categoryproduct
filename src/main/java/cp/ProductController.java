package cp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepo pr;
	
	@PostMapping
	public Product saveProduct(@RequestBody Product c) {
		return pr.save(c);
	}
	
	@GetMapping
	public Page<Product> getAllProduct(@PageableDefault(sort="id",direction=Sort.Direction.ASC) Pageable pageable) {
		return pr.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable("id") int id) {
		return pr.findById(id).orElse(null);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") int id,@RequestBody Product c) {
		Optional<Product> e = pr.findById(id);
		if(e.isPresent()) {
			Product o = e.get();
			o.setName(c.getName());
			o.setPrice(c.getPrice());
			return pr.save(o);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		if(pr.findById(id).isPresent()) {
			pr.deleteById(id);
			return "Product Deleted";
		}
		return "No Such Product";
	}
}

