package io.github.zam0k.controllers;

import io.github.zam0k.data.vo.v1.BookVO;
import io.github.zam0k.services.BookServices;
import io.github.zam0k.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Endpoints for managing books.")
public class BookController {

    @Autowired
    private BookServices services;

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML })
    @Operation(
            summary = "Finds all Books",
            description = "Finds all Books",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<PagedModel<EntityModel<BookVO>>> findAll(
            @RequestParam(value = "page", defaultValue="0") Integer page,
            @RequestParam(value = "size", defaultValue="12") Integer size,
            @RequestParam(value = "direction", defaultValue="asc") String direction
    ) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));

        return ResponseEntity.ok(services.findAll(pageable));
    }

    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML })
    @Operation(
            summary = "Find books by id",
            description = "Find book by id",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = BookVO.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public BookVO findById(@PathVariable(value = "id") Long id) {
        return services.findById(id);
    }

    @PostMapping(
            produces = { MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML },
            consumes = { MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML })
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(
            summary = "Creates a new book",
            description = "Creates a new book",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = {
                            @Content(schema = @Schema(implementation = BookVO.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public BookVO create(@RequestBody BookVO book){
        return services.create(book);
    }

    @PutMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML },
            consumes = { MediaType.APPLICATION_JSON,
                    MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML })
    @Operation(
            summary = "Updates a book",
            description = "Updates a book",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = BookVO.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public BookVO update (@PathVariable(value = "id") Long id, @RequestBody BookVO book){
        return services.update(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Creates a new book",
            description = "Creates a new book",
            tags = {"Books"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public void delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
    }
}
