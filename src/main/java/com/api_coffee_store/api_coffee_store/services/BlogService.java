package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateBlogRequest;
import com.api_coffee_store.api_coffee_store.dtos.response.BlogHTMLResponse;
import com.api_coffee_store.api_coffee_store.dtos.response.CreateBlogResponse;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.*;
import com.api_coffee_store.api_coffee_store.repositories.BlogBoxEditorRepository;
import com.api_coffee_store.api_coffee_store.repositories.BlogRepository;
import com.api_coffee_store.api_coffee_store.repositories.BlogTitleRepository;
import com.api_coffee_store.api_coffee_store.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.api_coffee_store.api_coffee_store.models.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogTitleRepository blogTitleRepository;
    private final BlogBoxEditorRepository blogBoxEditorRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<ResponseObject> getAllBlogs(){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Blogs")
                        .data(blogRepository.findAll())
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseObject> createBlog(CreateBlogRequest req) throws APIException{
        Category category = categoryRepository.findById(req.categoryId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Category With Id = "+req.categoryId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
        Blog blog = Blog.builder()
                .createdAt(req.createdAt())
                .category(category)
                .build();

        BlogTitle blogTitle = BlogTitle.builder()
                .title(req.title())
                .subtitle(req.subtitle())
                .region(req.region())
                .blog(blog)
                .build();

        blogTitleRepository.save(blogTitle);


        List<BlogBoxEditor> boxes = new ArrayList<>();

        for (var box : req.boxes()){
            BlogBoxEditor blogBoxEditor = BlogBoxEditor.builder()
                    .index(box.index())
                    .content(box.content())
                    .section(box.section())
                    .blog(blog)
                    .build();
            boxes.add(blogBoxEditor);
//            blogBoxEditorRepository.save(blogBoxEditor);
        }
        blog.setBlogBoxEditors(boxes);
        blogRepository.save(blog);

        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.CREATE.getStatus())
                        .message("Create Blog Successfully")
                        .data(new CreateBlogResponse(
                                blogTitle.getSlug(),
                                blog.getIndex(),
                                blogTitle.getTitle(),
                                blogTitle.getSubtitle(),
                                blogTitle.getRegion(),
                                blog.getCreatedAt()
                        ))
                        .build()
        );


    }


    public ResponseEntity<ResponseObject> getBlogBySlug(String slug) throws APIException{
        BlogTitle blogTitle = blogTitleRepository.findById(slug).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Blog With Slug = "+slug,ErrorCode.NOT_FOUND.getHttpStatusCode()));
        Blog blog = blogTitle.getBlog();
        List<BlogBoxEditor> editors = blog.getBlogBoxEditors();

        String content = editors.stream()
                .sorted(Comparator.comparingInt(BlogBoxEditor::getIndex))
                .map(BlogBoxEditor::getContent)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n\n"));

        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Found Blog With Id = "+slug)
                        .data(new BlogHTMLResponse(
                                blog.getIndex(),
                                blogTitle.getTitle(),
                                blogTitle.getSubtitle(),
                                blog.getCreatedAt(),
                                content,
                                blog.getCategory()

                        ))
                        .build()
        );
    }
}
