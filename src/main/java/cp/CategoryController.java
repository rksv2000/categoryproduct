package cp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryRepo cr;
	
	@PostMapping
	public Category saveCategory(@RequestBody Category c) {
		c.setProducts(c.getProducts());
		return cr.save(c);
	}
	
	@GetMapping
	public Page<Category> getAllCategory(@PageableDefault(sort="id",direction=Sort.Direction.ASC) Pageable pageable) {
		return cr.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public Category getCategory(@PathVariable("id") int id) {
		return cr.findById(id).orElse(null);
	}
	
	@PutMapping("/{id}")
	public Category updateCategory(@PathVariable("id") int id,@RequestBody Category c) {
		Optional<Category> e = cr.findById(id);
		if(e.isPresent()) {
			Category o = e.get();
			o.setName(c.getName());
			o.setProducts(c.getProducts());
			return cr.save(o);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		if(cr.findById(id).isPresent()) {
			cr.deleteById(id);
			return "Category Deleted";
		}
		return "No Such Category";
	}
}
