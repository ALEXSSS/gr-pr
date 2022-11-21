package org.gerimedica.code;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    @Autowired
    private CodeService service;

    @Operation(description = "Save one code entity")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCode(@RequestBody Code code) {
        service.saveCode(code);
    }

    @Operation(description = "Save codes with csv")
    @PostMapping(value = "/csv", consumes = MULTIPART_FORM_DATA)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCodeByCsv(@RequestParam("file") MultipartFile file) {
        try {
            service.saveFromCsvInputStream(file.getInputStream());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot process file");
        }
    }

    @Operation(description = "Delete code entity by id")
    @DeleteMapping("/{code}")
    public void deleteCodeById(@PathVariable String code) {
        service.deleteCode(code);
    }

    @Operation(description = "Get code entity by id")
    @GetMapping("/{code}")
    public Code getCodeById(@PathVariable String code) {
        return service.getCodeByCode(code);
    }

    @Operation(description = "Get all code entities")
    @GetMapping("/all")
    public List<Code> getAllCodes() {
        return service.getAll();
    }

    @Operation(description = "Get all code entities as csv")
    @GetMapping("/csv")
    public void getAllCodesByCsv(HttpServletResponse response) {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ex.csv";
        response.setHeader(headerKey, headerValue);
        try {
            service.getToCsvOutputStream(response.getOutputStream());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
