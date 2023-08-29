package com.tech.blog;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
	
    @GetMapping(path = "/")
    public String data(Model model) {
    	return "home";
    }
    

	@GetMapping(path = "/allblogs")
	public @ResponseBody List<Item> getAllBlog() {
		return itemRepository.findAll();
	}	
    
	@PostMapping(path = "/postblog")
	public ResponseEntity<String> postBlog(@RequestParam String title, @RequestParam String about,
			@RequestParam String reference, @RequestParam String image, @RequestParam String name,
			@RequestParam String email, @RequestParam String blog)

	{
		try {
			Item i = new Item();
			i.setTitle(title);
			i.setName(name);
			i.setEmail(email);
			i.setBlog(blog);
			i.setAbout(about);
			i.setReference(reference);
			itemRepository.save(i);
			ClassPathResource resource = new ClassPathResource("templates/index.html");
			InputStream inputStream = resource.getInputStream();
			String content = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
			return ResponseEntity.ok(content);

		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Failed to upload image");
		}
	}
}
