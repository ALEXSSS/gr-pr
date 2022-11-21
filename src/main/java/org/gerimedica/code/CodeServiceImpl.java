package org.gerimedica.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Component
public class CodeServiceImpl implements CodeService {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private CodeCsvService csvService;

    @Override
    public Code getCodeByCode(String code) {
        List<Code> codeEntity = template.query(
                "SELECT * FROM CODE WHERE code = :code",
                singletonMap("code", code),
                codeRowMapper
        );
        if (codeEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There isn't code entity with such code " + code);
        }
        return codeEntity.get(0);
    }

    @Override
    public List<Code> getAll() {
        return template.query("SELECT * FROM CODE", codeRowMapper);
    }

    @Override
    public void saveCode(Code code) {
        template.update(INSERT_QUERY, getParamMap(code));
    }

    @Override
    public void saveCodes(List<Code> codes) {
        // todo make batchUpdate
        codes.forEach(this::saveCode);
    }

    @Override
    public void saveFromCsvInputStream(InputStream in) {
        List<Code> allCodes = csvService.getAllCodes(in);
        saveCodes(allCodes);
    }

    @Override
    public void getToCsvOutputStream(OutputStream out) {
        csvService.writeCodes(out, getAll());
    }

    @Override
    public void deleteCode(String code) {
        int rows = template.update("DELETE FROM CODE WHERE code = :code", singletonMap("code", code));
        if (rows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There isn't code entity with such code: " + code);
        }
    }

    private static Map<String, Object> getParamMap(Code code) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("code", code.getCode());
        paramMap.put("source", code.getSource());
        paramMap.put("code_list_code", code.getCodeListCode());
        paramMap.put("display_value", code.getDisplayValue());
        paramMap.put("long_description", code.getLongDescription());
        paramMap.put("from_date", code.getFromDate());
        paramMap.put("to_date", code.getToDate());
        paramMap.put("sorting_priority", code.getSortingPriority());

        return paramMap;
    }

    private static final RowMapper<Code> codeRowMapper = (rs, rowNum) -> {
        Code code = new Code();
        code.setCode(rs.getString("code"));
        code.setSource(rs.getString("source"));
        code.setCodeListCode(rs.getString("code_list_code"));
        code.setDisplayValue(rs.getString("display_value"));
        code.setLongDescription(rs.getString("long_description"));
        code.setFromDate(rs.getDate("from_date").toLocalDate());

        Date to_date = rs.getDate("to_date");
        code.setToDate(to_date == null ? null : to_date.toLocalDate());
        String sorting_priority = rs.getString("sorting_priority");
        code.setSortingPriority(sorting_priority == null ? null : Integer.parseInt(sorting_priority));
        return code;
    };

    private static final String INSERT_QUERY =
            "INSERT INTO CODE(code, source, code_list_code, display_value, long_description, from_date, to_date, sorting_priority) " +
                    "VALUES (:code, :source, :code_list_code, :display_value, :long_description, :from_date, :to_date, :sorting_priority)";
}
